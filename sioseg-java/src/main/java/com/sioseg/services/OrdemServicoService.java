package com.sioseg.services;

import com.sioseg.models.MaterialUsado;
import com.sioseg.models.OrdemServico;
import com.sioseg.repositories.MaterialUsadoRepository;
import com.sioseg.repositories.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicoService {
    private final OrdemServicoRepository repo;
    private final MaterialUsadoRepository materialRepo;
    private final MaterialUsadoService materialService;
    private final ProdutoService produtoService;

    public OrdemServicoService(OrdemServicoRepository repo, MaterialUsadoRepository materialRepo, MaterialUsadoService materialService, ProdutoService produtoService) {
        this.repo = repo;
        this.materialRepo = materialRepo;
        this.materialService = materialService;
        this.produtoService = produtoService;
    }

    public List<OrdemServico> findAll() {
        return repo.findAll();
    }

    public Optional<OrdemServico> findById(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public Long criar(OrdemServico os) throws Exception {
        // verificar conflitos se técnico e data fornecidos
        if (os.getTecnico() != null && os.getDataAgendamento() != null) {
            List<OrdemServico> conflitos = repo.findConflitosHorario(os.getTecnico().getIdTec(), os.getDataAgendamento());
            if (!conflitos.isEmpty()) {
                OrdemServico c = conflitos.get(0);
                throw new Exception("Horário indisponível. Conflito com OS id=" + c.getIdOs());
            }
        }

        OrdemServico saved = repo.save(os);
        return saved.getIdOs();
    }

    @Transactional
    public boolean atualizarOS(Long id, OrdemServico dados) throws Exception {
        OrdemServico atual = repo.findById(id).orElseThrow(() -> new Exception("OS não encontrada"));

        // Se alterar data_agendamento ou técnico, verificar conflitos
        LocalDateTime novaData = dados.getDataAgendamento() != null ? dados.getDataAgendamento() : atual.getDataAgendamento();
        Long idTec = dados.getTecnico() != null ? dados.getTecnico().getIdTec() : (atual.getTecnico() != null ? atual.getTecnico().getIdTec() : null);
        if (idTec != null && novaData != null) {
            List<OrdemServico> conflitos = repo.findConflitosHorario(idTec, novaData);
            // excluir a própria OS do conflito
            conflitos.removeIf(c -> c.getIdOs().equals(id));
            if (!conflitos.isEmpty()) {
                throw new Exception("Horário indisponível (conflito detectado)");
            }
        }

        // Atualizar campos permitidos (simplificado)
        atual.setServicoPrestado(dados.getServicoPrestado() != null ? dados.getServicoPrestado() : atual.getServicoPrestado());
        atual.setTipoServico(dados.getTipoServico() != null ? dados.getTipoServico() : atual.getTipoServico());
        atual.setStatus(dados.getStatus() != null ? dados.getStatus() : atual.getStatus());
        atual.setDataAgendamento(dados.getDataAgendamento() != null ? dados.getDataAgendamento() : atual.getDataAgendamento());
        atual.setDataEncerramento(dados.getDataEncerramento() != null ? dados.getDataEncerramento() : atual.getDataEncerramento());
        atual.setConclusaoCliente(dados.getConclusaoCliente() != null ? dados.getConclusaoCliente() : atual.getConclusaoCliente());
        atual.setConclusaoTecnico(dados.getConclusaoTecnico() != null ? dados.getConclusaoTecnico() : atual.getConclusaoTecnico());
        if (dados.getTecnico() != null) atual.setTecnico(dados.getTecnico());
        if (dados.getUsuario() != null) atual.setUsuario(dados.getUsuario());
        if (dados.getCliente() != null) atual.setCliente(dados.getCliente());

        repo.save(atual);
        return true;
    }

    @Transactional
    public boolean encerrarOS(Long idOs, boolean removerMateriais) throws Exception {
        try {
            if (removerMateriais) {
                // remover materiais (estornar estoque)
                List<MaterialUsado> usados = materialRepo.findByOrdemIdOs(idOs);
                for (MaterialUsado mu : usados) {
                    if (mu.getProduto() != null) {
                        produtoService.incrementarEstoque(mu.getProduto().getIdProd(), mu.getQtdUsada());
                    }
                }
                // apagar materiais usados
                materialRepo.deleteAll(usados);
            }

            OrdemServico os = repo.findById(idOs).orElseThrow(() -> new Exception("OS não encontrada"));
            os.setStatus("encerrada");
            os.setDataEncerramento(LocalDateTime.now());
            repo.save(os);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<OrdemServico> buscarOSTecnicoDia(Long idTec) {
        String today = LocalDate.now().toString();
        return repo.findOSTecnicoDia(idTec, today);
    }

    public List<OrdemServico> findHorariosBloqueados(Long idTec, String inicio, String fim) {
        return repo.findHorariosBloqueados(idTec, inicio, fim);
    }
    
    public List<OrdemServico> findAllWithRelations() {
        return repo.findAllWithRelations();
    }
    
    public List<OrdemServico> findByStatusWithRelations(String status) {
        return repo.findByStatusWithRelations(status);
    }
    
    public long countByStatus(String status) {
        return repo.countByStatus(status);
    }
    
    public java.util.Map<String, Double> getMediaAvaliacoesUltimosDias(int dias) {
        java.util.Map<String, Double> result = new java.util.LinkedHashMap<>();
        String[] labels = {"D-6", "D-5", "D-4", "D-3", "D-2", "Ontem", "Hoje"};
        
        for (int i = 0; i < dias; i++) {
            Double media = repo.getMediaAvaliacoesPorDia(i);
            result.put(labels[i], media != null ? media : 0.0);
        }
        
        return result;
    }
    
    public java.util.List<java.util.Map<String, Object>> getTopTecnicosByAvaliacao(int limit) {
        java.util.List<Object[]> results = repo.findTopTecnicosByAvaliacao(limit);
        java.util.List<java.util.Map<String, Object>> tecnicos = new java.util.ArrayList<>();
        
        for (Object[] row : results) {
            java.util.Map<String, Object> tecnico = new java.util.HashMap<>();
            tecnico.put("nome", row[0]);
            tecnico.put("mediaAvaliacoes", row[1]);
            tecnico.put("totalAvaliacoes", row[2]);
            tecnicos.add(tecnico);
        }
        
        return tecnicos;
    }
    
    public java.util.Map<String, Long> getOSConcluidasUltimosDias(int dias) {
        java.util.Map<String, Long> result = new java.util.LinkedHashMap<>();
        String[] labels = {"D-6", "D-5", "D-4", "D-3", "D-2", "Ontem", "Hoje"};
        
        for (int i = 0; i < dias; i++) {
            Long count = repo.countOSConcluidasPorDia(i);
            result.put(labels[i], count != null ? count : 0L);
        }
        
        return result;
    }
    
    public java.util.List<java.util.Map<String, Object>> getTopTecnicosByOS(int limit) {
        java.util.List<Object[]> results = repo.findTopTecnicosByOS(limit);
        java.util.List<java.util.Map<String, Object>> tecnicos = new java.util.ArrayList<>();
        
        for (Object[] row : results) {
            java.util.Map<String, Object> tecnico = new java.util.HashMap<>();
            tecnico.put("nome", row[0]);
            tecnico.put("totalOS", row[1]);
            tecnicos.add(tecnico);
        }
        
        return tecnicos;
    }
}


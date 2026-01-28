package com.sioseg.services;

import com.sioseg.models.OrdemServico;
import com.sioseg.repositories.OrdemServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("ordemServicoServiceNew")
public class OrdemServicoServiceNew {
    private final OrdemServicoRepository repo;

    public OrdemServicoServiceNew(OrdemServicoRepository repo) {
        this.repo = repo;
    }

    public List<OrdemServico> findAll() {
        return repo.findAll();
    }

    public Page<OrdemServico> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<OrdemServico> findById(Long id) {
        return repo.findById(id);
    }

    public OrdemServico save(OrdemServico ordemServico) {
        if (ordemServico.getDataAbertura() == null) {
            ordemServico.setDataAbertura(LocalDateTime.now());
        }
        if (ordemServico.getStatus() == null) {
            ordemServico.setStatus("aberta");
        }
        return repo.save(ordemServico);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public OrdemServico updateStatus(Long id, String status) {
        Optional<OrdemServico> osOpt = findById(id);
        if (osOpt.isPresent()) {
            OrdemServico os = osOpt.get();
            os.setStatus(status);
            if ("encerrada".equals(status) && os.getDataEncerramento() == null) {
                os.setDataEncerramento(LocalDateTime.now());
            }
            return save(os);
        }
        return null;
    }

    public Page<OrdemServico> searchByClienteNome(String nome, Pageable pageable) {
        return repo.findByClienteNomeCliContainingIgnoreCase(nome, pageable);
    }

    public Page<OrdemServico> searchByTecnicoNome(String nome, Pageable pageable) {
        return repo.findByTecnicoNomeTecContainingIgnoreCase(nome, pageable);
    }
}
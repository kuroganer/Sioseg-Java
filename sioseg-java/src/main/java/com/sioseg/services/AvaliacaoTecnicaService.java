package com.sioseg.services;

import com.sioseg.models.AvaliacaoTecnica;
import com.sioseg.repositories.AvaliacaoTecnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AvaliacaoTecnicaService {
    
    @Autowired
    private AvaliacaoTecnicaRepository repository;
    
    public List<AvaliacaoTecnica> findAll() {
        return repository.findAll();
    }
    
    public Optional<AvaliacaoTecnica> findById(Long id) {
        return repository.findById(id);
    }
    
    public Optional<AvaliacaoTecnica> findByOrdemServicoId(Long idOs) {
        return repository.findByOrdemServicoIdOs(idOs);
    }
    
    public List<AvaliacaoTecnica> findByClienteId(Long idCliente) {
        return repository.findByOrdemServicoClienteIdCli(idCliente);
    }
    
    public AvaliacaoTecnica save(AvaliacaoTecnica avaliacao) {
        return repository.save(avaliacao);
    }
    
    public Map<String, Double> getMediaAvaliacoesUltimosDias(int dias) {
        Map<String, Double> result = new LinkedHashMap<>();
        String[] labels = {"D-6", "D-5", "D-4", "D-3", "D-2", "Ontem", "Hoje"};
        
        for (int i = 0; i < dias; i++) {
            Double media = repository.getMediaAvaliacoesPorDia(i);
            result.put(labels[i], media != null ? media : 0.0);
        }
        
        return result;
    }
    
    public List<Map<String, Object>> getTopTecnicosByAvaliacao(int limit) {
        List<Object[]> results = repository.findTopTecnicosByAvaliacao(limit);
        List<Map<String, Object>> tecnicos = new ArrayList<>();
        
        for (Object[] row : results) {
            Map<String, Object> tecnico = new HashMap<>();
            tecnico.put("nome", row[0]);
            tecnico.put("mediaAvaliacoes", row[1]);
            tecnico.put("totalAvaliacoes", row[2]);
            tecnicos.add(tecnico);
        }
        
        return tecnicos;
    }
}
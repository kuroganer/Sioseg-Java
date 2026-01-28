package com.sioseg.services;

import com.sioseg.models.MaterialUsado;
import com.sioseg.repositories.MaterialUsadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialUsadoService {
    private final MaterialUsadoRepository repo;

    public MaterialUsadoService(MaterialUsadoRepository repo) {
        this.repo = repo;
    }

    public List<MaterialUsado> findByOrdemId(Long idOs) {
        return repo.findByOrdemIdOs(idOs);
    }

    public MaterialUsado save(MaterialUsado mu) {
        return repo.save(mu);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

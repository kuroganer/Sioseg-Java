package com.sioseg.services;

import com.sioseg.models.Tecnico;
import com.sioseg.repositories.TecnicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    private final TecnicoRepository repo;

    public TecnicoService(TecnicoRepository repo) {
        this.repo = repo;
    }

    public List<Tecnico> findAll() {
        return repo.findAll();
    }

    public Page<Tecnico> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Tecnico> findById(Long id) {
        return repo.findById(id);
    }

    public Tecnico save(Tecnico tecnico) {
        if (tecnico.getDataCadastroTec() == null) {
            tecnico.setDataCadastroTec(LocalDateTime.now());
        }
        if (tecnico.getStatus() == null) {
            tecnico.setStatus("ativo");
        }
        return repo.save(tecnico);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Optional<Tecnico> findByEmail(String email) {
        return repo.findByEmailTec(email);
    }

    public Page<Tecnico> searchByNome(String nome, Pageable pageable) {
        return repo.findByNomeTecContainingIgnoreCase(nome, pageable);
    }

    public Page<Tecnico> searchByCpf(String cpf, Pageable pageable) {
        return repo.findByCpfTecContaining(cpf, pageable);
    }

    public Page<Tecnico> searchByEmail(String email, Pageable pageable) {
        return repo.findByEmailTecContainingIgnoreCase(email, pageable);
    }

    public Tecnico updateStatus(Long id, String status) {
        Optional<Tecnico> tecnicoOpt = findById(id);
        if (tecnicoOpt.isPresent()) {
            Tecnico tecnico = tecnicoOpt.get();
            tecnico.setStatus(status);
            return save(tecnico);
        }
        return null;
    }

    public List<Tecnico> findAllActive() {
        return repo.findByStatus("ativo");
    }
}

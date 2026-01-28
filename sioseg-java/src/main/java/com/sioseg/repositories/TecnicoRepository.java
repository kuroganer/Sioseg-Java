package com.sioseg.repositories;

import com.sioseg.models.Tecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    Optional<Tecnico> findByEmailTec(String emailTec);
    
    Page<Tecnico> findByNomeTecContainingIgnoreCase(String nome, Pageable pageable);
    
    Page<Tecnico> findByCpfTecContaining(String cpf, Pageable pageable);
    
    Page<Tecnico> findByEmailTecContainingIgnoreCase(String email, Pageable pageable);
    
    List<Tecnico> findByStatus(String status);
}

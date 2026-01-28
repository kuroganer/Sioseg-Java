package com.sioseg.repositories;

import com.sioseg.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailUsu(String emailUsu);
    
    Page<Usuario> findByNomeUsuContainingIgnoreCase(String nome, Pageable pageable);
    
    Page<Usuario> findByEmailUsuContainingIgnoreCase(String email, Pageable pageable);
    
    Page<Usuario> findByCpfUsuContaining(String cpf, Pageable pageable);
    
    java.util.List<Usuario> findByNomeUsuContainingIgnoreCase(String nome);
}

package com.sioseg.repositories;

import com.sioseg.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmailCli(String email);
    
    Optional<Cliente> findByCpfCli(String cpf);
    
    Optional<Cliente> findByCnpj(String cnpj);
    
    Page<Cliente> findByNomeCliContainingIgnoreCaseOrRazaoSocialContainingIgnoreCase(
            String nomeCli, String razaoSocial, Pageable pageable);
    
    Page<Cliente> findByStatus(String status, Pageable pageable);
}

package com.sioseg.repositories;

import com.sioseg.models.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    @Query("SELECT p FROM Pedido p WHERE p.status LIKE %:status%")
    Page<Pedido> findByStatusContaining(@Param("status") String status, Pageable pageable);
    
    @Query("SELECT p FROM Pedido p WHERE p.produto.nome LIKE %:nome%")
    Page<Pedido> findByProdutoNomeContaining(@Param("nome") String nome, Pageable pageable);
    
    @Query("SELECT p FROM Pedido p WHERE p.status = :status")
    Page<Pedido> findByStatus(@Param("status") String status, Pageable pageable);
}
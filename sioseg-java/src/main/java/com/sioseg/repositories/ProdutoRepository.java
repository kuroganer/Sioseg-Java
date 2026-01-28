package com.sioseg.repositories;

import com.sioseg.models.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    Page<Produto> findAllByOrderByIdProdDesc(Pageable pageable);
    
    List<Produto> findByNomeContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrModeloContainingIgnoreCase(
            String nome, String marca, String modelo);
    
    List<Produto> findByStatusOrderByNomeAsc(String status);
    
    Page<Produto> findByStatus(String status, Pageable pageable);
    
    List<Produto> findByStatus(String status);

    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.qtde = p.qtde - :qty WHERE p.idProd = :id AND p.qtde >= :qty")
    int decrementIfAvailable(@Param("id") Long id, @Param("qty") Integer qty);

    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.qtde = p.qtde + :qty WHERE p.idProd = :id")
    int incrementQuantity(@Param("id") Long id, @Param("qty") Integer qty);
}

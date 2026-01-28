package com.sioseg.repositories;

import com.sioseg.models.MaterialUsado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialUsadoRepository extends JpaRepository<MaterialUsado, Long> {
    List<MaterialUsado> findByOrdemIdOs(Long idOs);
}

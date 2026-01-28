package com.sioseg.repositories;

import com.sioseg.models.AvaliacaoTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoTecnicaRepository extends JpaRepository<AvaliacaoTecnica, Long> {
    
    Optional<AvaliacaoTecnica> findByOrdemServicoIdOs(Long idOs);
    
    List<AvaliacaoTecnica> findByOrdemServicoClienteIdCli(Long idCliente);
    
    @Query(value = "SELECT AVG(av.nota) as media FROM avaliacao_tecnica av JOIN ordem_servico os ON av.id_os_fk = os.id_os WHERE DATE(os.data_encerramento) = DATE_SUB(CURDATE(), INTERVAL :dias DAY)", nativeQuery = true)
    Double getMediaAvaliacoesPorDia(@Param("dias") int dias);
    
    @Query(value = "SELECT t.nome_tec as nome, AVG(av.nota) as mediaAvaliacoes, COUNT(av.id_ava) as totalAvaliacoes FROM tecnico t INNER JOIN ordem_servico os ON t.id_tec = os.id_tec_fk INNER JOIN avaliacao_tecnica av ON os.id_os = av.id_os_fk WHERE os.data_encerramento >= DATE_SUB(NOW(), INTERVAL 30 DAY) AND t.status = 'ativo' AND os.status IN ('concluida', 'encerrada') GROUP BY t.id_tec, t.nome_tec HAVING COUNT(av.id_ava) >= 1 ORDER BY AVG(av.nota) DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopTecnicosByAvaliacao(@Param("limit") int limit);
}
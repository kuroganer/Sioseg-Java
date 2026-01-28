package com.sioseg.repositories;

import com.sioseg.models.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    List<OrdemServico> findByStatus(String status);
    
    @Query("SELECT os FROM OrdemServico os LEFT JOIN FETCH os.cliente LEFT JOIN FETCH os.tecnico")
    List<OrdemServico> findAllWithRelations();
    
    @Query("SELECT os FROM OrdemServico os LEFT JOIN FETCH os.cliente LEFT JOIN FETCH os.tecnico WHERE LOWER(os.status) = LOWER(:status) ORDER BY os.idOs DESC")
    List<OrdemServico> findByStatusWithRelations(@Param("status") String status);

    @Query(value = "SELECT * FROM ordem_servico WHERE id_tec_fk = :idTec AND status IN ('aberta','em andamento') AND ((:dataAgendamento BETWEEN data_agendamento AND DATE_ADD(data_agendamento, INTERVAL 3 HOUR)) OR (data_agendamento BETWEEN :dataAgendamento AND DATE_ADD(:dataAgendamento, INTERVAL 3 HOUR)))" , nativeQuery = true)
    List<OrdemServico> findConflitosHorario(@Param("idTec") Long idTec, @Param("dataAgendamento") LocalDateTime dataAgendamento);

    @Query(value = "SELECT * FROM ordem_servico os JOIN cliente c ON os.id_cli_fk = c.id_cli JOIN tecnico t ON os.id_tec_fk = t.id_tec JOIN usuario u ON os.id_usu_fk = u.id_usu WHERE os.id_tec_fk = :idTec AND ((DATE(os.data_agendamento) = :today AND os.status = 'aberta') OR os.status = 'em andamento') ORDER BY os.data_agendamento ASC", nativeQuery = true)
    List<OrdemServico> findOSTecnicoDia(@Param("idTec") Long idTec, @Param("today") String today);

    @Query(value = "SELECT * FROM ordem_servico WHERE id_tec_fk = :idTec AND data_agendamento BETWEEN :dataInicio AND :dataFim AND status IN ('aberta','em andamento') ORDER BY data_agendamento ASC", nativeQuery = true)
    List<OrdemServico> findHorariosBloqueados(@Param("idTec") Long idTec, @Param("dataInicio") String dataInicio, @Param("dataFim") String dataFim);
    
    Page<OrdemServico> findByClienteNomeCliContainingIgnoreCase(String nome, Pageable pageable);
    
    Page<OrdemServico> findByTecnicoNomeTecContainingIgnoreCase(String nome, Pageable pageable);
    
    @Query("SELECT COUNT(os) FROM OrdemServico os WHERE LOWER(os.status) = LOWER(:status)")
    long countByStatus(@Param("status") String status);
    
    @Query(value = "SELECT AVG(COALESCE(av.nota, 0)) as media FROM ordem_servico os LEFT JOIN avaliacao_tecnica av ON os.id_os = av.id_os_fk WHERE DATE(os.data_encerramento) = DATE_SUB(CURDATE(), INTERVAL :dias DAY)", nativeQuery = true)
    Double getMediaAvaliacoesPorDia(@Param("dias") int dias);
    
    @Query(value = "SELECT t.nome_tec as nome, AVG(av.nota) as mediaAvaliacoes, COUNT(av.id_ava) as totalAvaliacoes FROM tecnico t INNER JOIN ordem_servico os ON t.id_tec = os.id_tec_fk INNER JOIN avaliacao_tecnica av ON os.id_os = av.id_os_fk WHERE os.data_encerramento >= DATE_SUB(NOW(), INTERVAL 30 DAY) AND t.status = 'ativo' AND os.status IN ('concluida', 'encerrada') GROUP BY t.id_tec, t.nome_tec HAVING COUNT(av.id_ava) >= 1 ORDER BY AVG(av.nota) DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopTecnicosByAvaliacao(@Param("limit") int limit);
    
    @Query(value = "SELECT COUNT(*) FROM ordem_servico WHERE status IN ('concluida', 'encerrada') AND DATE(data_encerramento) = DATE_SUB(CURDATE(), INTERVAL :dias DAY)", nativeQuery = true)
    Long countOSConcluidasPorDia(@Param("dias") int dias);
    
    @Query(value = "SELECT t.nome_tec as nome, COUNT(os.id_os) as totalOS FROM tecnico t INNER JOIN ordem_servico os ON t.id_tec = os.id_tec_fk WHERE os.data_encerramento >= DATE_SUB(NOW(), INTERVAL 30 DAY) AND t.status = 'ativo' AND os.status IN ('concluida', 'encerrada') GROUP BY t.id_tec, t.nome_tec ORDER BY COUNT(os.id_os) DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopTecnicosByOS(@Param("limit") int limit);
}

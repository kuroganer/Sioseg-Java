package com.sioseg.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_tecnica")
public class AvaliacaoTecnica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ava")
    private Long idAva;
    
    @Column(name = "nota", nullable = false)
    private Integer nota;
    
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
    
    @Column(name = "data_avaliacao", nullable = false)
    private LocalDateTime dataAvaliacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_os_fk", nullable = false)
    private OrdemServico ordemServico;
    
    // Constructors
    public AvaliacaoTecnica() {
        this.dataAvaliacao = LocalDateTime.now();
    }
    
    public AvaliacaoTecnica(Integer nota, String comentario, OrdemServico ordemServico) {
        this();
        this.nota = nota;
        this.comentario = comentario;
        this.ordemServico = ordemServico;
    }
    
    // Getters and Setters
    public Long getIdAva() {
        return idAva;
    }
    
    public void setIdAva(Long idAva) {
        this.idAva = idAva;
    }
    
    public Integer getNota() {
        return nota;
    }
    
    public void setNota(Integer nota) {
        this.nota = nota;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }
    
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
    
    public OrdemServico getOrdemServico() {
        return ordemServico;
    }
    
    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
}
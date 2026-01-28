package com.sioseg.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_os")
    private Long idOs;

    @Column(name = "servico_prestado")
    private String servicoPrestado;

    @Column(name = "tipo_servico")
    private String tipoServico;

    @Column(name = "status")
    private String status;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(name = "data_encerramento")
    private LocalDateTime dataEncerramento;

    @Column(name = "conclusao_cliente")
    private String conclusaoCliente;

    @Column(name = "conclusao_tecnico")
    private String conclusaoTecnico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tec_fk")
    private Tecnico tecnico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usu_fk")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cli_fk")
    private Cliente cliente;

    // Getters and setters

    public Long getIdOs() {
        return idOs;
    }

    public void setIdOs(Long idOs) {
        this.idOs = idOs;
    }

    public String getServicoPrestado() {
        return servicoPrestado;
    }

    public void setServicoPrestado(String servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getConclusaoCliente() {
        return conclusaoCliente;
    }

    public void setConclusaoCliente(String conclusaoCliente) {
        this.conclusaoCliente = conclusaoCliente;
    }

    public String getConclusaoTecnico() {
        return conclusaoTecnico;
    }

    public void setConclusaoTecnico(String conclusaoTecnico) {
        this.conclusaoTecnico = conclusaoTecnico;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

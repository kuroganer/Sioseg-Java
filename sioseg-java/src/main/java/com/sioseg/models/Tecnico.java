package com.sioseg.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tecnico")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tec")
    private Long idTec;

    @Column(name = "nome_tec")
    private String nomeTec;

    @Column(name = "cpf_tec")
    private String cpfTec;

    @Column(name = "rg_tec")
    private String rgTec;

    @Column(name = "rg_emissor_tec")
    private String rgEmissorTec;

    @Column(name = "data_expedicao_rg_tec")
    private LocalDate dataExpedicaoRgTec;

    @Column(name = "data_nascimento_tec")
    private LocalDate dataNascimentoTec;

    @Column(name = "data_cadastro_tec")
    private LocalDateTime dataCadastroTec;

    @Column(name = "tel_pessoal")
    private String telPessoal;

    @Column(name = "tel_empresa")
    private String telEmpresa;

    @Column(name = "email_tec")
    private String emailTec;

    @Column(name = "senha_hash_tec")
    private String senhaHashTec;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL)
    private List<OrdemServico> ordens = new ArrayList<>();

    // Getters and setters omitted for brevity (can be generated)

    public Long getIdTec() {
        return idTec;
    }

    public void setIdTec(Long idTec) {
        this.idTec = idTec;
    }

    public String getNomeTec() {
        return nomeTec;
    }

    public void setNomeTec(String nomeTec) {
        this.nomeTec = nomeTec;
    }

    public String getCpfTec() {
        return cpfTec;
    }

    public void setCpfTec(String cpfTec) {
        this.cpfTec = cpfTec;
    }

    public String getRgTec() {
        return rgTec;
    }

    public void setRgTec(String rgTec) {
        this.rgTec = rgTec;
    }

    public String getRgEmissorTec() {
        return rgEmissorTec;
    }

    public void setRgEmissorTec(String rgEmissorTec) {
        this.rgEmissorTec = rgEmissorTec;
    }

    public LocalDate getDataExpedicaoRgTec() {
        return dataExpedicaoRgTec;
    }

    public void setDataExpedicaoRgTec(LocalDate dataExpedicaoRgTec) {
        this.dataExpedicaoRgTec = dataExpedicaoRgTec;
    }

    public LocalDate getDataNascimentoTec() {
        return dataNascimentoTec;
    }

    public void setDataNascimentoTec(LocalDate dataNascimentoTec) {
        this.dataNascimentoTec = dataNascimentoTec;
    }

    public LocalDateTime getDataCadastroTec() {
        return dataCadastroTec;
    }

    public void setDataCadastroTec(LocalDateTime dataCadastroTec) {
        this.dataCadastroTec = dataCadastroTec;
    }

    public String getTelPessoal() {
        return telPessoal;
    }

    public void setTelPessoal(String telPessoal) {
        this.telPessoal = telPessoal;
    }

    public String getTelEmpresa() {
        return telEmpresa;
    }

    public void setTelEmpresa(String telEmpresa) {
        this.telEmpresa = telEmpresa;
    }

    public String getEmailTec() {
        return emailTec;
    }

    public void setEmailTec(String emailTec) {
        this.emailTec = emailTec;
    }

    public String getSenhaHashTec() {
        return senhaHashTec;
    }

    public void setSenhaHashTec(String senhaHashTec) {
        this.senhaHashTec = senhaHashTec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdemServico> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }
}

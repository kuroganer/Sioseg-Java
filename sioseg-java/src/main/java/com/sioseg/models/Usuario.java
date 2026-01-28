package com.sioseg.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long idUsu;

    @Column(name = "nome_usu")
    private String nomeUsu;

    @Column(name = "cpf_usu")
    private String cpfUsu;

    @Column(name = "rg_usu")
    private String rgUsu;

    @Column(name = "rg_emissor_usu")
    private String rgEmissorUsu;

    @Column(name = "data_expedicao_rg_usu")
    private LocalDate dataExpedicaoRgUsu;

    @Column(name = "data_nascimento_usu")
    private LocalDate dataNascimentoUsu;

    @Column(name = "data_cadastro_usu")
    private LocalDateTime dataCadastroUsu;

    @Column(name = "tel1_usu")
    private String tel1Usu;

    @Column(name = "tel2_usu")
    private String tel2Usu;

    @Column(name = "tel3_usu")
    private String tel3Usu;

    @Column(name = "email_usu")
    private String emailUsu;

    @Column(name = "senha_hash_usu")
    private String senhaHashUsu;

    @Column(name = "perfil")
    private String perfil;

    @Column(name = "status")
    private String status;

    // Getters and setters

    public Long getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(Long idUsu) {
        this.idUsu = idUsu;
    }

    public String getNomeUsu() {
        return nomeUsu;
    }

    public void setNomeUsu(String nomeUsu) {
        this.nomeUsu = nomeUsu;
    }
    
    // Alias para compatibilidade com templates
    public String getNome() {
        return nomeUsu;
    }
    
    public void setNome(String nome) {
        this.nomeUsu = nome;
    }

    public String getCpfUsu() {
        return cpfUsu;
    }

    public void setCpfUsu(String cpfUsu) {
        this.cpfUsu = cpfUsu;
    }

    public String getRgUsu() {
        return rgUsu;
    }

    public void setRgUsu(String rgUsu) {
        this.rgUsu = rgUsu;
    }

    public String getRgEmissorUsu() {
        return rgEmissorUsu;
    }

    public void setRgEmissorUsu(String rgEmissorUsu) {
        this.rgEmissorUsu = rgEmissorUsu;
    }

    public LocalDate getDataExpedicaoRgUsu() {
        return dataExpedicaoRgUsu;
    }

    public void setDataExpedicaoRgUsu(LocalDate dataExpedicaoRgUsu) {
        this.dataExpedicaoRgUsu = dataExpedicaoRgUsu;
    }

    public LocalDate getDataNascimentoUsu() {
        return dataNascimentoUsu;
    }

    public void setDataNascimentoUsu(LocalDate dataNascimentoUsu) {
        this.dataNascimentoUsu = dataNascimentoUsu;
    }

    public LocalDateTime getDataCadastroUsu() {
        return dataCadastroUsu;
    }

    public void setDataCadastroUsu(LocalDateTime dataCadastroUsu) {
        this.dataCadastroUsu = dataCadastroUsu;
    }

    public String getTel1Usu() {
        return tel1Usu;
    }

    public void setTel1Usu(String tel1Usu) {
        this.tel1Usu = tel1Usu;
    }

    public String getTel2Usu() {
        return tel2Usu;
    }

    public void setTel2Usu(String tel2Usu) {
        this.tel2Usu = tel2Usu;
    }

    public String getTel3Usu() {
        return tel3Usu;
    }

    public void setTel3Usu(String tel3Usu) {
        this.tel3Usu = tel3Usu;
    }

    public String getEmailUsu() {
        return emailUsu;
    }

    public void setEmailUsu(String emailUsu) {
        this.emailUsu = emailUsu;
    }

    public String getSenhaHashUsu() {
        return senhaHashUsu;
    }

    public void setSenhaHashUsu(String senhaHashUsu) {
        this.senhaHashUsu = senhaHashUsu;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<OrdemServico> ordens = new ArrayList<>();

    public List<OrdemServico> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }
}

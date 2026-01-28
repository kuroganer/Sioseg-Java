package com.sioseg.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cli")
    private Long idCli;

    @Column(name = "nome_cli")
    private String nomeCli;

    @Column(name = "nome_social")
    private String nomeSocial;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "cpf_cli")
    private String cpfCli;

    @Column(name = "rg_cli")
    private String rgCli;

    @Column(name = "rg_emissor_cli")
    private String rgEmissorCli;

    @Column(name = "data_expedicao_rg_cli")
    private LocalDate dataExpedicaoRgCli;

    @Column(name = "data_nascimento_cli")
    private LocalDate dataNascimentoCli;

    @Column(name = "data_cadastro_cli")
    private LocalDateTime dataCadastroCli;

    @Column(name = "tipo_pessoa")
    private String tipoPessoa;

    @Column(name = "tel1_cli")
    private String tel1Cli;

    @Column(name = "tel2_cli")
    private String tel2Cli;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "email_cli")
    private String emailCli;

    @Column(name = "senha_hash_cli")
    private String senhaHashCli;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "tipo_moradia")
    private String tipoMoradia;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "uf")
    private String uf;

    @Column(name = "cep")
    private String cep;

    @Column(name = "ponto_referencia")
    private String pontoReferencia;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "num_end")
    private String numEnd;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<OrdemServico> ordens = new ArrayList<>();

    // Getters and setters

    public Long getIdCli() {
        return idCli;
    }

    public void setIdCli(Long idCli) {
        this.idCli = idCli;
    }

    public String getNomeCli() {
        return nomeCli;
    }

    public void setNomeCli(String nomeCli) {
        this.nomeCli = nomeCli;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpfCli() {
        return cpfCli;
    }

    public void setCpfCli(String cpfCli) {
        this.cpfCli = cpfCli;
    }

    public String getRgCli() {
        return rgCli;
    }

    public void setRgCli(String rgCli) {
        this.rgCli = rgCli;
    }

    public String getRgEmissorCli() {
        return rgEmissorCli;
    }

    public void setRgEmissorCli(String rgEmissorCli) {
        this.rgEmissorCli = rgEmissorCli;
    }

    public LocalDate getDataExpedicaoRgCli() {
        return dataExpedicaoRgCli;
    }

    public void setDataExpedicaoRgCli(LocalDate dataExpedicaoRgCli) {
        this.dataExpedicaoRgCli = dataExpedicaoRgCli;
    }

    public LocalDate getDataNascimentoCli() {
        return dataNascimentoCli;
    }

    public void setDataNascimentoCli(LocalDate dataNascimentoCli) {
        this.dataNascimentoCli = dataNascimentoCli;
    }

    public LocalDateTime getDataCadastroCli() {
        return dataCadastroCli;
    }

    public void setDataCadastroCli(LocalDateTime dataCadastroCli) {
        this.dataCadastroCli = dataCadastroCli;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getTel1Cli() {
        return tel1Cli;
    }

    public void setTel1Cli(String tel1Cli) {
        this.tel1Cli = tel1Cli;
    }

    public String getTel2Cli() {
        return tel2Cli;
    }

    public void setTel2Cli(String tel2Cli) {
        this.tel2Cli = tel2Cli;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getEmailCli() {
        return emailCli;
    }

    public void setEmailCli(String emailCli) {
        this.emailCli = emailCli;
    }

    public String getSenhaHashCli() {
        return senhaHashCli;
    }

    public void setSenhaHashCli(String senhaHashCli) {
        this.senhaHashCli = senhaHashCli;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoMoradia() {
        return tipoMoradia;
    }

    public void setTipoMoradia(String tipoMoradia) {
        this.tipoMoradia = tipoMoradia;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumEnd() {
        return numEnd;
    }

    public void setNumEnd(String numEnd) {
        this.numEnd = numEnd;
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

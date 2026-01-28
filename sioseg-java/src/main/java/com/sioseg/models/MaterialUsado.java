package com.sioseg.models;

import javax.persistence.*;

@Entity
@Table(name = "material_usado")
public class MaterialUsado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mu")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_os_fk")
    private OrdemServico ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prod_fk")
    private Produto produto;

    @Column(name = "qtd_usada")
    private Integer qtdUsada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemServico getOrdem() {
        return ordem;
    }

    public void setOrdem(OrdemServico ordem) {
        this.ordem = ordem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQtdUsada() {
        return qtdUsada;
    }

    public void setQtdUsada(Integer qtdUsada) {
        this.qtdUsada = qtdUsada;
    }
}

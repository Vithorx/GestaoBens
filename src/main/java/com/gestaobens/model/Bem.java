/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaobens.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import jakarta.persistence.*;


/**
 *
 * @author vitho
 */
@Entity
@Table(name = "bem")
public class Bem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome",nullable = false)
    private String nome;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_produto_id")
    private TipoProduto tipoProduto;
    
    @Column(name = "preco_compra",nullable = false)
    private BigDecimal precoCompra;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_compra",nullable = false)
    private Date dataCompra;
    
    @Column(name = "vida_util_anos",nullable = false)
    private int vidaUtilAnos;
    
    @Column(name = "valor_residual",nullable = false)
    private BigDecimal valorResidual;
    
    @Transient
    public BigDecimal getDepreciacaoAnual(){
        if(precoCompra == null || vidaUtilAnos <= 0){
            return BigDecimal.ZERO;
        }
        BigDecimal valorResidualReal = (valorResidual != null) ? valorResidual : BigDecimal.ZERO;
        BigDecimal baseCalculo = precoCompra.subtract(valorResidualReal);
        if(baseCalculo.compareTo(BigDecimal.ZERO) < 0){
            return BigDecimal.ZERO;
        }
        return baseCalculo.divide(new BigDecimal(vidaUtilAnos),2,RoundingMode.HALF_UP);         
    }
    //Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getVidaUtilAnos() {
        return vidaUtilAnos;
    }

    public void setVidaUtilAnos(int vidaUtilAnos) {
        this.vidaUtilAnos = vidaUtilAnos;
    }

    public BigDecimal getValorResidual() {
        return valorResidual;
    }

    public void setValorResidual(BigDecimal valorResidual) {
        this.valorResidual = valorResidual;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestao.controller;
import com.gestaobens.dao.BemDAO;
import com.gestaobens.dao.TipoProdutoDAO;
import com.gestaobens.model.Bem;
import com.gestaobens.model.TipoProduto;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
/**
 *
 * @author vitho
 */
@Named("bemBean")
@ViewScoped
public class BemBean implements Serializable{
    @Inject
    private BemDAO bemDAO;
    @Inject
    private TipoProdutoDAO tipoProdutoDAO;
    private Bem bem;
    private List<Bem> bens;
    private List<TipoProduto> tiposDeProduto;
    
    @PostConstruct
    public void init(){
        novo();
        carregarBens();
    }
    public void novo(){
        bem = new Bem();
    }
    public void carregarBens(){
        tiposDeProduto = tipoProdutoDAO.listarTodos();
    }
    public void carregarTiposDeProdutos(){
        tiposDeProduto = tipoProdutoDAO.listarTodos();
    }
    public void salvar(){
        try{
           if(bem.getTipoProduto() == null){
               addMessage(FacesMessage.SEVERITY_ERROR,"Erro de validação","O campo 'Tipo de Produto' é obrigatório.");
               return;
           }
           if(bem.getPrecoCompra() == null || bem.getPrecoCompra().compareTo(BigDecimal.ZERO) <= 0){
               addMessage(FacesMessage.SEVERITY_ERROR,"Erro de validação","O campo preço compra deve ser maior que zero.");
               return;
           }
           if(bem.getVidaUtilAnos() <= 0){
               addMessage(FacesMessage.SEVERITY_ERROR,"Erro de validação","A vida util dever ser de no minimo 1 ano.");
               return;
           }
           if(bem.getDataCompra() == null || bem.getDataCompra().after(new Date())){
               addMessage(FacesMessage.SEVERITY_ERROR,"Erro de validação","A data de compra é obrigatoria e não pode ser uma data no futuro.");
               return;
           }
           if(bem.getValorResidual() != null && bem.getValorResidual().compareTo(BigDecimal.ZERO) <= 0){
               addMessage(FacesMessage.SEVERITY_ERROR,"Erro de validação","O valor residual não pode ser maior que o valor de compra.");
               return;
           }
           String feedback;
           if(bem.getId() == null){
               bemDAO.salvar(bem);
               feedback = "Bem cadastrado com sucesso!";
           }else{
               bemDAO.atualizar(bem);
               feedback = "Bem atualizado com sucesso!";
           }
           addMessage(FacesMessage.SEVERITY_INFO,"Sucesso!",feedback);
           carregarBens();
           novo();
           
        }catch(Exception e){
            addMessage(FacesMessage.SEVERITY_FATAL,"Erro no Sistema","Ocorreu ao tentar salvar.");
            e.printStackTrace();
        }
        
    }
    public void editar(Bem bemEditar){
        this.bem = bemEditar;
    }
    public void remover(Bem bemRemover){
        try{
            bemDAO.remover(bemRemover.getId());
            addMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Bem deletedo.");
            carregarBens();
        }catch(Exception e){
            addMessage(FacesMessage.SEVERITY_ERROR,"Erro","Ocorreu um erro ao tentar deletar o Bem.");
            e.printStackTrace();
        }
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
}
    //Getters e Setters

    public BemDAO getBemDAO() {
        return bemDAO;
    }

    public void setBemDAO(BemDAO bemDAO) {
        this.bemDAO = bemDAO;
    }

    public TipoProdutoDAO getTipoProdutoDAO() {
        return tipoProdutoDAO;
    }

    public void setTipoProdutoDAO(TipoProdutoDAO tipoProdutoDAO) {
        this.tipoProdutoDAO = tipoProdutoDAO;
    }

    public Bem getBem() {
        return bem;
    }

    public void setBem(Bem bem) {
        this.bem = bem;
    }

    public List<Bem> getBens() {
        return bens;
    }

    public void setBens(List<Bem> bens) {
        this.bens = bens;
    }

    public List<TipoProduto> getTiposDeProduto() {
        return tiposDeProduto;
    }

    public void setTiposDeProduto(List<TipoProduto> tiposDeProduto) {
        this.tiposDeProduto = tiposDeProduto;
    }
    
    
}

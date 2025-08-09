/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestaobens.controller;
import br.com.gestaobens.dao.BemDAO;
import br.com.gestaobens.dao.TipoProdutoDAO;
import br.com.gestaobens.model.Bem;
import br.com.gestaobens.model.TipoProduto;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ejb.EJB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vitho
 */
@Named("bemBean")
@ViewScoped
public class BemBean implements Serializable {

    @EJB
    private BemDAO bemDAO;

    @EJB
    private TipoProdutoDAO tipoProdutoDAO;

    private Bem bem;
    private List<Bem> bens;
    private List<TipoProduto> tiposDeProduto;
    private String termoPesquisa;

    @PostConstruct
    public void init() {
        carregarBens();
        carregarTiposDeProduto();
        novo();
    }

    public void novo() {
        bem = new Bem();
    }

    public void carregarBens() {
        bens = bemDAO.listarTodos();
    }
    
    private void carregarTiposDeProduto(){
        tiposDeProduto = tipoProdutoDAO.listarTodos();
    }

    public void salvar() {
        try {
            if (bem.getTipoProduto() == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "O campo 'Tipo de Produto' é obrigatório.");
                return;
            }
            if (bem.getPrecoCompra() == null || bem.getPrecoCompra().compareTo(BigDecimal.ZERO) <= 0) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "O preço de compra deve ser maior que zero.");
                return;
            }
            if (bem.getVidaUtilAnos() <= 0) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "A vida útil deve ser de pelo menos 1 ano.");
                return;
            }
            if (bem.getDataCompra() == null || bem.getDataCompra().after(new Date())) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "A data da compra é obrigatória e não pode ser no futuro.");
                return;
            }
            if (bem.getValorResidual() != null && bem.getValorResidual().compareTo(bem.getPrecoCompra()) > 0) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "O valor residual não pode ser maior que o preço de compra.");
                return;
            }

           
            String feedback;
            if (bem.getId() == null) {
                bemDAO.salvar(bem);
                feedback = "Bem cadastrado com sucesso!";
            } else {
                bemDAO.atualizar(bem);
                feedback = "Bem atualizado com sucesso!";
            }
            
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", feedback);
            carregarBens(); 
            novo(); 

        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_FATAL, "Erro no Sistema", "Ocorreu um erro inesperado ao tentar salvar.");
            e.printStackTrace(); 
        }
    }
    
    public void editar(Bem bemParaEditar){
        this.bem = bemParaEditar;
    }
    
    public void remover(Bem bemParaRemover){
        try{
            bemDAO.remover(bemParaRemover.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Bem removido.");
            carregarBens();
        } catch (Exception e){
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao tentar remover o bem.");
            e.printStackTrace();
        }
    }
    public void pesquisar(){
        if(termoPesquisa == null || termoPesquisa.trim().isEmpty()){
            carregarBens();
        }else{
            bens = bemDAO.pesquisarPorNome(termoPesquisa);
        }
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
    //Getters e Setters

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }
    
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
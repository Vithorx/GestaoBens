/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestaobens.controller;
import br.com.gestaobens.dao.TipoProdutoDAO;
import br.com.gestaobens.model.TipoProduto;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author vitho
 */
@Named
@ViewScoped
public class TipoProdutoBean implements Serializable {
    @Inject
    private TipoProdutoDAO tipoProdutoDAO;
    private TipoProduto tipoProduto;
    private List<TipoProduto> tipos;
    private String termoPesquisa;

    @PostConstruct
    public void init() {
        novo();
        carregarTipos();
    }
    public void novo() {
        tipoProduto = new TipoProduto(); 
    }
    public void carregarTipos() { 
        tipos = tipoProdutoDAO.listarTodos(); 
    }
    public void salvar() {
        try {
            if (tipoProduto.getId() == null) tipoProdutoDAO.salvar(tipoProduto);
            else tipoProdutoDAO.atualizar(tipoProduto);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Tipo de produto salvo.");
            novo();
            carregarTipos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um tipo com este nome.");
        }
    }
    public void editar(TipoProduto tipo) {
        this.tipoProduto = tipo; 
    }
    public void remover(TipoProduto tipo) {
        try {
            tipoProdutoDAO.remover(tipo.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Tipo de produto removido.");
            carregarTipos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é possível remover. Este tipo está sendo usado por um bem.");
        }
    }
    public void pesquisar() {
        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
        carregarTipos();
        } else {
            tipos = tipoProdutoDAO.pesquisarPorNome(termoPesquisa);
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
    
    public TipoProdutoDAO getTipoProdutoDAO() {
        return tipoProdutoDAO;
    }

    public void setTipoProdutoDAO(TipoProdutoDAO tipoProdutoDAO) {
        this.tipoProdutoDAO = tipoProdutoDAO;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public List<TipoProduto> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoProduto> tipos) {
        this.tipos = tipos;
    }
    
}

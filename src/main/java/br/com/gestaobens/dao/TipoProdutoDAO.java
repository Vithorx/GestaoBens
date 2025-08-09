/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestaobens.dao;
import br.com.gestaobens.model.TipoProduto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author vitho
 */
@Stateless
public class TipoProdutoDAO {
    @PersistenceContext(unitName = "gestao_bens_pu") 
    private EntityManager em;

    public void salvar(TipoProduto tipoProduto) {
        em.persist(tipoProduto); 
    }
    public TipoProduto atualizar(TipoProduto tipoProduto) {
        return em.merge(tipoProduto); 
    }
    public void remover(Long id) {
        TipoProduto tipoProduto = em.find(TipoProduto.class, id);
        if (tipoProduto != null) em.remove(tipoProduto);
    }
    public List<TipoProduto> listarTodos() {
        return em.createQuery("SELECT t FROM TipoProduto t ORDER BY t.nome", TipoProduto.class).getResultList();
    }
}
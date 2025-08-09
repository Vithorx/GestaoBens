/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaobens.dao;
import com.gestaobens.model.Bem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author vitho
 */
@Stateless
public class BemDAO {
    @PersistenceContext(name = "gestao_bens_pu")
    private EntityManager em;
    
    public void salvar(Bem bem){
        em.persist(bem);
        
    }
    public Bem atualizar(Bem bem){
        return em.merge(bem);
    }
    public void remover(Long id){
        Bem bem = em.find(Bem.class,id);
        if(bem != null){
            em.remove(bem);
        }
    }
    public List<Bem> listarTodos(){
         return em.createQuery("SELECT b FROM Bem b JOIN FETCH b.tipoProduto ORDER BY b.nome", Bem.class).getResultList();
    }
    public List<Bem> pesquisarPorNome(String nome){
        return em.createQuery("SELECT b FROM Bem b JOIN FETCH b.tipoProduto WHERE lower(b.nomeProduto) LIKE :nome ORDER BY b.nome",Bem.class).setParameter("nome","%"+nome.toLowerCase()+"%").getResultList();
        
    }
}

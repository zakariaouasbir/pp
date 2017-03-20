/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Produit;
import controler.util.SearchUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author B-M
 */
@Stateless
public class ProduitFacade extends AbstractFacade<Produit> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduitFacade() {
        super(Produit.class);
    }
     public List<Produit> search(Produit produit,Double prixMin,Double prixMax,int Qtemin,int Qtemax)
    {
        String requette="SELECT p FROM Produit p WHERE 1=1 ";
    requette+=SearchUtil.addConstraintMinMax("p","prix",prixMin, prixMax);
    requette+=SearchUtil.addConstraintMinMax("p","qteg",Qtemin, Qtemax);
   
  return em.createQuery(requette).getResultList();
}
    
}

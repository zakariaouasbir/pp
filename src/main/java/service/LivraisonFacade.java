/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.Livraison;
import controler.util.SearchUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author B-M
 */
@Stateless
public class LivraisonFacade extends AbstractFacade<Livraison> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LivraisonFacade() {
        super(Livraison.class);
    }
     public List<Livraison> search(Commande commande,Date datemin,Date datemax)
    {
        String requette="SELECT l FROM Livraison l WHERE 1=1 ";
    requette+=SearchUtil.addConstraintMinMaxDate("l","datelivraison",datemin,datemax);
    requette+=SearchUtil.addConstraint("l", "commande.id", "=", commande.getId());
  return em.createQuery(requette).getResultList();
}
    
}

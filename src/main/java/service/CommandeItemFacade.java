/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.CommandeItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author B-M
 */
@Stateless
public class CommandeItemFacade extends AbstractFacade<CommandeItem> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeItemFacade() {
        super(CommandeItem.class);
    }
    public List<CommandeItem> finditemsbymontantnull()
    { String requette="SELECT ci FROM CommandeItem ci WHERE ci.enregistre=0";
        return em.createQuery(requette).getResultList();
    }
   public List<CommandeItem> finditemsbycommande(Commande comm)
    { String requette="SELECT ci FROM CommandeItem ci WHERE ci.commande.id="+comm.getId();
        return em.createQuery(requette).getResultList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.Email;
import bean.Fournisseur;
import bean.Magasin;
import bean.Stock;
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
public class EmailFacade extends AbstractFacade<Email> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    public EmailFacade() {
        super(Email.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

   
}


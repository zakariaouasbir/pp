/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import bean.Magasin;
import bean.Produit;
import bean.Stock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import service.ProduitFacade;
import service.StockFacade;

@Named("datacontroler")
@RequestScoped
public class Datacontroler implements Serializable {
    
    private List<Produit> produits;
   @EJB
    private ProduitFacade ejbproduit;
    @EJB
    private StockFacade ejbstock;
    private Magasin selected;

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public ProduitFacade getEjbproduit() {
        return ejbproduit;
    }

    public void setEjbproduit(ProduitFacade ejbproduit) {
        this.ejbproduit = ejbproduit;
    }

    public Magasin getSelected() {
        return selected;
    }

    public void setSelected(Magasin selected) {
        this.selected = selected;
    }
    
    @PostConstruct
    public void initialise() {
      createdataproduit();

    }
    public void createdataproduit()
    { if(selected!=null)
    {  produits=new ArrayList<Produit>();
        List<Stock> stocks=ejbstock.findstockbymagasin(selected);
for(int i=0;i<stocks.size();i++)
{
    produits.add(stocks.get(i).getProduit());
}
    }
    else
   { 
       produits = new ArrayList<Produit>();
       produits=ejbproduit.findAll();
    }
}
}   


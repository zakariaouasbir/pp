/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author B-M
 */
@Entity
public class LivraisonItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Livraison livraison;
    @OneToOne
    private CommandeItem commandeItem;
    private int qte=0;
    @ManyToOne
    private LivraisonItem livraisonItem;
    @OneToMany(mappedBy = "livraisonItem")
    private List<LivraisonItem> livraisonItems;

    public LivraisonItem() {
    }

    public LivraisonItem getLivraisonItem() {
        return livraisonItem;
    }

    public void setLivraisonItem(LivraisonItem livraisonItem) {
        this.livraisonItem = livraisonItem;
    }

    public List<LivraisonItem> getLivraisonItems() {
        return livraisonItems;
    }

    public void setLivraisonItems(List<LivraisonItem> livraisonItems) {
        this.livraisonItems = livraisonItems;
    }

    
    public Livraison getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public CommandeItem getCommandeItem() {
        return commandeItem;
    }

    public void setCommandeItem(CommandeItem commandeItem) {
        this.commandeItem = commandeItem;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LivraisonItem)) {
            return false;
        }
        LivraisonItem other = (LivraisonItem) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.LivraisonItem[ id=" + id + " ]";
    }
    
}

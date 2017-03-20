/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author B-M
 */
@Entity
public class Inventaire implements Serializable {

    @OneToMany(mappedBy = "inventaire")
    private List<InventaireItem> inventaireItems;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateInvent;
    @ManyToOne
    private Magasin magasin=null;
    @ManyToOne
    private Stock stock=null;
    private String descri;

    public List<InventaireItem> getInventaireItems() {
        return inventaireItems;
    }

    public void setInventaireItems(List<InventaireItem> inventaireItems) {
        this.inventaireItems = inventaireItems;
    }

    public Date getDateInvent() {
        return dateInvent;
    }

    public void setDateInvent(Date dateInvent) {
        this.dateInvent = dateInvent;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }
    

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventaire)) {
            return false;
        }
        Inventaire other = (Inventaire) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Inventaire[ id=" + id + " ]";
    }
    
}

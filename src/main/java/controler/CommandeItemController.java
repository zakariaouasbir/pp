package controler;

import bean.Commande;
import bean.CommandeItem;
import bean.Produit;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.CommandeItemFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.CommandeFacade;
import service.ProduitFacade;

@Named("commandeItemController")
@SessionScoped
public class CommandeItemController implements Serializable {

    @EJB
    private service.CommandeItemFacade ejbFacade;
    @EJB
    private service.ProduitFacade ejbproduit;
    @EJB
    private service.CommandeFacade ejbcommande;
    private List<CommandeItem> items ;
    private CommandeItem selected;
    private Commande commandeselected;

    public CommandeItemController() {
    }

    public CommandeItem getSelected() {
        return selected;
    }

    public CommandeItemFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CommandeItemFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public ProduitFacade getEjbproduit() {
        return ejbproduit;
    }

    public void setEjbproduit(ProduitFacade ejbproduit) {
        this.ejbproduit = ejbproduit;
    }

    public CommandeFacade getEjbcommande() {
        return ejbcommande;
    }

    public void setEjbcommande(CommandeFacade ejbcommande) {
        this.ejbcommande = ejbcommande;
    }

    public Commande getCommandeselected() {
        return commandeselected;
    }

    public void setCommandeselected(Commande commandeselected) {
        this.commandeselected = commandeselected;
    }

    public void setSelected(CommandeItem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CommandeItemFacade getFacade() {
        return ejbFacade;
    }

    public CommandeItem prepareCreate() {
        selected = new CommandeItem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommandeItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommandeItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommandeItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CommandeItem> getItems() {
        if(items==null && getFacade().finditemsbymontantnull()!=null)
            items=getFacade().finditemsbymontantnull();
        return items;
    }
   
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                 items.add(selected);
                } else {
                    getFacade().remove(selected);
                    items.remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public CommandeItem getCommandeItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CommandeItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CommandeItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CommandeItem.class)
    public static class CommandeItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommandeItemController controller = (CommandeItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "commandeItemController");
            return controller.getCommandeItem(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CommandeItem) {
                CommandeItem o = (CommandeItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CommandeItem.class.getName()});
                return null;
            }
        }

    }

      public void save()
       { Double prixtotal=0D;
        long indice;
           for(int i=0;i<items.size();i++)
           {items.get(i).setEnregistre(true);
           ejbFacade.edit(items.get(i));
               Produit p=ejbproduit.find( items.get(i).getProduit().getId());
        p.setQteg(p.getQteg()+items.get(i).getQteCommander());
               ejbproduit.edit(p);
               prixtotal=prixtotal+(items.get(i).getQteCommander()*p.getPrix());
           }
         Commande c=items.get(0).getCommande();
         c.setPrixtot(prixtotal);
           ejbcommande.edit(c);
           items.clear();
       }
       public void changeitems()
      {
          items=ejbFacade.finditemsbycommande(commandeselected);
          
      }
}

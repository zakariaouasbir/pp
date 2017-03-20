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
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import static oracle.jrockit.jfr.events.Bits.intValue;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;
import service.ProduitFacade;
import service.StockFacade;

@Named("graph")
@ManagedBean
@RequestScoped
public class graph implements Serializable {

   
    private ProduitFacade ejbFacade;
    @EJB
    private StockFacade ejb;

    private Magasin selected;
    List<Produit> produits;
    

    //charts//
    private BarChartModel bar;
    private LineChartModel line;

    public Magasin getSelected() {
        return selected;
    }

    public void setSelected(Magasin selected) {
        this.selected = selected;
    }

    public LineChartModel getLine() {
        return line;
    }

    public void setLine(LineChartModel line) {
        this.line = line;
    }

   

    public ProduitFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ProduitFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public BarChartModel getBar() {
        return bar;
    }

    public void setBar(BarChartModel bar) {
        this.bar = bar;
    }

    public StockFacade getEjb() {
        return ejb;
    }

    public void setEjb(StockFacade ejb) {
        this.ejb = ejb;
    }

//////
    public List<Produit> getProduits() {
        if (produits == null) {
            produits = ejbFacade.findAll();
        }
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @PostConstruct
    public void initialise() {
//        createpie();
        createbar();
        Createline();
    }

//
//   
//    line
    public void Createline()
    {
        line=new LineChartModel();
        ChartSeries commande=new BarChartSeries();
     
           commande.set(19, 29);
           commande.set(10, 20);
           line.addSeries(commande);
           line.setTitle("Category Chart");
line.setLegendPosition("e");
line.setShowPointLabels(true);
Axis yAxis = line.getAxis(AxisType.Y);
yAxis.setLabel("Births");
yAxis.setMin(0);
yAxis.setMax(200);

    }
//bar//

    public void createbar() {
        int maxsize = 0;
        double maxprice = 0;
        if (selected != null) {
            System.out.println("==============>");
            List<Stock> stocks = ejb.findstockbymagasin(selected);
            bar = new BarChartModel();

            ChartSeries qte = new BarChartSeries();
            ChartSeries prixt = new BarChartSeries();
            qte.setLabel("Quantite");
            prixt.setLabel("Prix unitaire");
            for (int i = 0; i < stocks.size(); i++) {
                if (stocks.get(i).getProduit().getPrix() > maxprice) {
                    maxprice = stocks.get(i).getProduit().getPrix();
                }
                if (stocks.get(i).getQte() > maxsize) {
                    maxsize = stocks.get(i).getQte();
                }

                qte.set("Stock:" + stocks.get(i).getId() + "(" + stocks.get(i).getProduit().getNom() + ")", stocks.get(i).getQte());
                prixt.set("Stock:" + stocks.get(i).getId() + "(" + stocks.get(i).getProduit().getNom() + ")", intValue(stocks.get(i).getProduit().getPrix()));
            }

            bar.addSeries(prixt);
            bar.addSeries(qte);
            bar.setTitle("Stocks Existant dans le Magasin :" + selected.getNom());
            bar.setLegendPosition("ne");
            Axis xAxis = bar.getAxis(AxisType.X);
            xAxis.setLabel("Stocks");
            Axis yAxis = bar.getAxis(AxisType.Y);
            yAxis.setLabel("Quantite&prix");
            yAxis.setMin(0);
            yAxis.setMax(maximum(maxsize, maxprice));
        } else {
            List<Stock> stocks = ejb.findAll();
            bar = new BarChartModel();

            ChartSeries qte = new BarChartSeries();
            ChartSeries prixt = new BarChartSeries();
            qte.setLabel("Quantite");
            prixt.setLabel("Prix unitaire");
            for (int i = 0; i < stocks.size(); i++) {
                Double pt;
//    pt=(stocks.get(i).getProduit().getPrix())*(stocks.get(i).getProduit().getQteg());
                qte.set("Stock:" + stocks.get(i).getId(), stocks.get(i).getProduit().getQteg());
                prixt.set("Stock:" + stocks.get(i).getId(), intValue(stocks.get(i).getProduit().getPrix()));
            }

            bar.addSeries(prixt);
            bar.addSeries(qte);
            bar.setTitle("Stocks Existant dans la base de donnes");
            bar.setLegendPosition("ne");
            Axis xAxis = bar.getAxis(AxisType.X);
            xAxis.setLabel("Stocks");
            Axis yAxis = bar.getAxis(AxisType.Y);
            yAxis.setLabel("Quantite&prix");
            yAxis.setMin(0);
            yAxis.setMax(4000);

        }

    }

    //max siez xaxis
    public int maximum(int a, Double b) {
        if (a > b) {
            return a + 100;
        } else {
            return intValue(b) + 100;
        }
    }
}

package controler;
 
import bean.Commande;
import bean.Magasin;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import service.CommandeFacade;
@Named("chartView")
@ManagedBean
@RequestScoped
public class ChartView implements Serializable {
 
    private LineChartModel line;
    private Magasin selected;
  @EJB
  private CommandeFacade ejbcommande;
     
    @PostConstruct
    public void init() {
       createline();
    }

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

    public CommandeFacade getEjbcommande() {
        return ejbcommande;
    }

    public void setEjbcommande(CommandeFacade ejbcommande) {
        this.ejbcommande = ejbcommande;
    }
    
 
   
    
     
    public void createline() {
      if(selected!=null){
          List<Commande> commandes=ejbcommande.findcommandebymagasin(selected);
       if(commandes!=null){
          line = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel(selected.getNom());
 for(int i=0;i<commandes.size();i++){
        series1.set(commandes.get(i).getId(), commandes.get(i).getPrixtot());
       
 } line.addSeries(series1);
  line.setTitle("Variation des Montants totales en fonction du commandes effectue par Magasin:");
       }
       else{
       line = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Variation des Commande en fonction de Montanttotal:");
         series1.set(0,1);
         series1.set(5,6);
  line.addSeries(series1);
   line.setTitle("default1");
       }
       
      }
      else{
          line = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Variation des Commande en fonction de Montanttotal:");
         series1.set(0,1);
line.addSeries(series1);
  line.setTitle("default");
       }
      
    }
     
    
}

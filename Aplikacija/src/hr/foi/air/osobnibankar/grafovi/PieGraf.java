package hr.foi.air.osobnibankar.grafovi;

import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.List;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PiePlot;
import org.afree.chart.title.TextTitle;
import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;
import org.afree.graphics.geom.Font;

import com.activeandroid.query.Select;

import android.content.Context;
import android.graphics.Typeface;


public class PieGraf extends GrafView {

    public PieGraf(Context context) {
        super(context);

        final PieDataset dataset = createDataset();
        final AFreeChart chart = createChart(dataset);

        setChart(chart);
    }
    
    
    private static PieDataset createDataset() {
    
    DefaultPieDataset dataset = new DefaultPieDataset();
    	
    List<Transakcija> transakcije = new Select().all().from(Transakcija.class).where("tip_id=0").execute();
    
    for (int i =0;i<transakcije.size();i++) {
    
    String naziv = transakcije.get(i).kategorija;
    Double iznos = transakcije.get(i).iznos;
        
        dataset.setValue(naziv, iznos);
    
    }
return dataset;}
    
    private static AFreeChart createChart(PieDataset dataset) {

        AFreeChart chart = ChartFactory.createPieChart(
        		"Pie Chart", // chart title
                dataset, // data
                true, // include legend
                true,
                false);
        TextTitle title = chart.getTitle();
        title.setToolTipText("A title tooltip!");

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Typeface.NORMAL, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;

    }
}

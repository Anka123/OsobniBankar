package hr.foi.air.osobnibankar.grafovi;

import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.List;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.CategoryDataset;
import org.afree.data.category.DefaultCategoryDataset;

import android.content.Context;

import com.activeandroid.query.Select;

public class BarGraf extends GrafView {
	
	public BarGraf(Context context) {
        super(context);

        CategoryDataset dataset = createDataset();
        AFreeChart chart = createChart(dataset);

        setChart(chart);
    }

    public static CategoryDataset createDataset() {

    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	List<Transakcija> listaPrihoda = new Select().all().from(Transakcija.class).where("tip_id = 0").execute();
    	for(int i =0;i<listaPrihoda.size();i++){
    	String series1 = "Prihodi";	
    	Double iznos = listaPrihoda.get(i).iznos;
    	String category = listaPrihoda.get(i).naziv;
    	dataset.addValue(iznos, series1, category);
    	
    
    	}
    	
    	List<Transakcija> listaRashoda = new Select().all().from(Transakcija.class).where("tip_id = 1").execute();
    	for(int i =0;i<listaRashoda.size();i++){
        	String series2 = "Rashodi";	
        	Double iznos = listaRashoda.get(i).iznos;
        	String category = listaRashoda.get(i).naziv;
        	
        	dataset.addValue(iznos, series2, category);
        	}
    	
    	List<Transakcija> listaStednji = new Select().all().from(Transakcija.class).where("tip_id = 4").execute();
    	for(int i =0;i<listaStednji.size();i++){
        	String series1 = "Stednje";	
        	Double iznos = listaStednji.get(i).iznos;
        	String category = listaStednji.get(i).naziv;
        	
        	dataset.addValue(iznos, series1, category);
        	}

        return dataset;

    }


    public static AFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        AFreeChart chart = ChartFactory.createBarChart(
            "Bar Graf",      // chart title
            "Transakcija",               // domain axis label
            "Iznos",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        
        return chart;

   

}}

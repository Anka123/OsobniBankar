package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.grafovi.PieChart;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PieChartActivity extends Activity {
	
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       PieChart mView = new PieChart(this);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(mView);
   }
}

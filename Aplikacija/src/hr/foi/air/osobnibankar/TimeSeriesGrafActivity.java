package hr.foi.air.osobnibankar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import hr.foi.air.osobnibankar.grafovi.TimeSeriesGraf;

public class TimeSeriesGrafActivity extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        TimeSeriesGraf mView = new TimeSeriesGraf(this);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(mView);
	    }
}

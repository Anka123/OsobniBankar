package hr.foi.air.osobnibankar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import hr.foi.air.osobnibankar.grafovi.LineGraf;

public class LineGrafActivity extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        LineGraf mView = new LineGraf(this);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(mView);
	    }
}

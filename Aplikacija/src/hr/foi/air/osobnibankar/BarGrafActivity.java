package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.grafovi.BarGraf;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BarGrafActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BarGraf mView = new BarGraf(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(mView);
	}
}

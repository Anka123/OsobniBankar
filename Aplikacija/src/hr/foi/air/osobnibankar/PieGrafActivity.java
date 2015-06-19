package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.grafovi.PieGraf;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PieGrafActivity extends Activity {
	
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       PieGraf mView = new PieGraf(this);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(mView);
   }
}

package hr.foi.air.osobnibankar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PiRActivity extends Activity  {
	Context c = this;	
	Dialog dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piractivity);
		final Context context = this;
	
		Button btnDodajPiR = (Button) findViewById(R.id.btnDodajPiR);
		btnDodajPiR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog = new Dialog(context);
				dialog.setContentView(R.layout.dodajpir);
				dialog.show();
				
			}
		});
		
		
		
		
	}
}

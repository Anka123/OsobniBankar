package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.database.Login;

import com.activeandroid.ActiveAndroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Context c = this;
		ActiveAndroid.initialize(c);		
		
		registracija();
		
		
	}

	private void registracija() {
		EditText etLozinka = (EditText) findViewById(R.id.etLozinka);
		final String lozinka = etLozinka.getText().toString();
		
		EditText etPotvrda = (EditText) findViewById(R.id.etPotvrda);
		final String potvrda = etPotvrda.getText().toString();
		
		EditText etOdgovor = (EditText) findViewById(R.id.etOdgovor);
		final String odgovor = etOdgovor.getText().toString();
		
		Spinner spinPitanje = (Spinner) findViewById(R.id.spinPitanje);
		final String pitanje = spinPitanje.getSelectedItem().toString();
		
		ImageButton btnRegistracija = (ImageButton) findViewById(R.id.btnPrijava);
			
		btnRegistracija.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (lozinka.equals(potvrda)){
						Toast.makeText(getApplicationContext(), "Lozinka potvrdjena!", Toast.LENGTH_SHORT).show();
						Login login = new Login(lozinka, pitanje, odgovor);
						login.save();
					}
					else
						Toast.makeText(getApplicationContext(), "Lozinka nije potvrdjena!", Toast.LENGTH_SHORT).show();
						
				}
				});
		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}

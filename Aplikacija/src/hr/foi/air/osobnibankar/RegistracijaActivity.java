package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.database.Registracija;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistracijaActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registracija);	
		
		ImageButton btnRegistracija = (ImageButton) findViewById(R.id.btnRegistracija);
			
		btnRegistracija.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					EditText etLozinka = (EditText) findViewById(R.id.etLozinka);
					final String lozinka = etLozinka.getText().toString();
					
					EditText etPotvrda = (EditText) findViewById(R.id.etPotvrda);
					final String potvrda = etPotvrda.getText().toString();
					
					EditText etOdgovor = (EditText) findViewById(R.id.etOdgovor);
					final String odgovor = etOdgovor.getText().toString();
					
					Spinner spinPitanje = (Spinner) findViewById(R.id.spinPitanje);
					final String pitanje = spinPitanje.getSelectedItem().toString();
					
						if (lozinka.equals(potvrda)){
							Toast.makeText(getApplicationContext(), "Lozinka potvrdjena!", Toast.LENGTH_SHORT).show();
							Registracija registracija = new Registracija(lozinka, pitanje, odgovor);
							registracija.save();
							Intent i = new Intent(getApplicationContext(), GlavniIzbornikActivity.class);
							startActivity(i);
						}
						else 
							Toast.makeText(getApplicationContext(), "Lozinka nije potvrdjena!", Toast.LENGTH_SHORT).show();						
					    }
				});	
	}
	
}


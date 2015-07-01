package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.db.Registracija;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

public class RegistracijaActivity extends Activity {
	public static int jezik;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registracija);

		final List<Registracija> registracija = new Select().all()
				.from(Registracija.class).execute();

		final RadioButton rb = (RadioButton) findViewById(R.id.radioHR);
		RadioButton rbeng = (RadioButton) findViewById(R.id.radioENG);
		if (jezik == 2) {
			rb.setChecked(true);
			rbeng.setChecked(false);
		} else {
			rbeng.setChecked(true);
			rb.setChecked(false);
		}
		rb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Locale locale = new Locale("hr");
				Locale.setDefault(locale);
				Configuration configuration = new Configuration();
				configuration.locale = locale;
				getBaseContext().getResources().updateConfiguration(
						configuration,
						getBaseContext().getResources().getDisplayMetrics());
				jezik = 2;
				finish();
				startActivity(getIntent());
			}
		});

		rbeng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Locale locale1 = new Locale("en");
				Locale.setDefault(locale1);
				Configuration configuration1 = new Configuration();
				configuration1.locale = locale1;
				getBaseContext().getResources().updateConfiguration(
						configuration1,
						getBaseContext().getResources().getDisplayMetrics());
				jezik = 1;
				finish();
				startActivity(getIntent());
			}
		});
		
		ImageButton btnRegistracija = (ImageButton) findViewById(R.id.btnRegistracija);
		btnRegistracija.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (registracija.size() > 0) {
					Toast.makeText(getApplicationContext(),
							"Vec ste registrirani!", Toast.LENGTH_SHORT).show();
				}

				else {
					EditText etLozinka = (EditText) findViewById(R.id.etLozinka);
					final String lozinka = etLozinka.getText().toString();

					EditText etPotvrda = (EditText) findViewById(R.id.etPotvrda);
					final String potvrda = etPotvrda.getText().toString();

					EditText etOdgovor = (EditText) findViewById(R.id.etOdgovor);
					final String odgovor = etOdgovor.getText().toString();

					Spinner spinPitanje = (Spinner) findViewById(R.id.spinPitanje);
					final String pitanje = spinPitanje.getSelectedItem()
							.toString();

					if (lozinka.equals(potvrda)) {

						if (lozinka.isEmpty() || potvrda.isEmpty()
								|| odgovor.isEmpty()) {
							Toast.makeText(getApplicationContext(),
									"Popunite sva polja!", Toast.LENGTH_SHORT)
									.show();
						}

						else {
							Toast.makeText(getApplicationContext(),
									"Lozinka potvrdjena!", Toast.LENGTH_SHORT)
									.show();
							Registracija registracija = new Registracija(
									lozinka, pitanje, odgovor);
							registracija.save();
							Intent i = new Intent(getApplicationContext(),
									GlavniIzbornikActivity.class);
							startActivity(i);
						}
					}

					else
						Toast.makeText(getApplicationContext(),
								"Lozinka nije potvrdjena!", Toast.LENGTH_SHORT)
								.show();
				}
			}
		});
	}
}

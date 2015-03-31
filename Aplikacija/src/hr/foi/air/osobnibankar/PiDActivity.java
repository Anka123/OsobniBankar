package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.database.Dugovanje;
import hr.foi.air.osobnibankar.database.Potrazivanje;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class PiDActivity extends Activity {
	Context c = this;
	Dialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pidactivity);

		final Context context = this;

		Button btnDodajPiR = (Button) findViewById(R.id.btnDodajPiD);
		btnDodajPiR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = new Dialog(context);
				dialog.setContentView(R.layout.dodajpid);
				dialog.setTitle(R.string.noviUnos);
				dialog.show();

				RadioGroup rgPiR = (RadioGroup) dialog
						.findViewById(R.id.radioGroup3);
				rgPiR.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					int izbor;

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						final int chkId = checkedId;
						Button btnSpremi = (Button) dialog
								.findViewById(R.id.btnSpremi);
						btnSpremi.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {

								switch (chkId) {
								case R.id.rbPotrazivanje:
									izbor = 0;
									unos(izbor);
									Toast.makeText(getApplicationContext(),
											"potraživanje", Toast.LENGTH_SHORT)
											.show();
									dialog.dismiss();
									break;

								case R.id.rbDugovanje:
									izbor = 1;
									unos(izbor);
									dialog.dismiss();
									break;
								}

							}
						});
					}
				});
			}
		});
	}

	public void unos(int izbor) {
		int iz = izbor;
		EditText etNaziv = (EditText) dialog.findViewById(R.id.etNaziv);
		String naziv = etNaziv.getText().toString();
		EditText etOpis = (EditText) dialog.findViewById(R.id.etOpis);
		String opis = etOpis.getText().toString();
		EditText etIznos = (EditText) dialog.findViewById(R.id.etIznos);
		Double iznos = 0.0;
		iznos = Double.valueOf(etIznos.getText().toString());

		DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
		int day = dp.getDayOfMonth();
		int month = dp.getMonth();
		int year = dp.getYear();

		String datum = day + "." + (month + 1) + "." + year + ".";

		if (iz == 0) {
			Potrazivanje potrazivanje = new Potrazivanje(naziv, opis, iznos,
					datum);
			potrazivanje.save();
			Toast.makeText(getApplicationContext(), "Potrazivanje spremljeno",
					Toast.LENGTH_SHORT).show();

		} else if (iz == 1) {
			Dugovanje dugovanje = new Dugovanje(naziv, opis, iznos, datum);
			Toast.makeText(getApplicationContext(), "Dugovanje spremljeno",
					Toast.LENGTH_SHORT).show();
			dugovanje.save();
		}

	}
}

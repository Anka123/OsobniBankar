package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TransakcijeAdapter;
import hr.foi.air.osobnibankar.core.Transakcije;
import hr.foi.air.osobnibankar.db.Transakcija;
import hr.foi.air.osobnibankar.dodatno.Datum;
import hr.foi.air.osobnibankar.interfaces.ITransakcija;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

public class PotrazivanjaDugovanjaActivity extends Activity {
	Context c = this;
	Dialog dialog = null;
	int izbor;
	int grupa = 0;

	boolean potrazivanjaSelected = false;
	boolean dugovanjaSelected = false;

	Date date = new Date(System.currentTimeMillis());
	@SuppressLint("SimpleDateFormat") SimpleDateFormat datum = new SimpleDateFormat("dd/mm/yyyy");
	final Datum d = new Datum();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TransakcijeAdapter.tipTransakcije = true;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pidactivity);

		//CalendarView kalendar = (CalendarView)findViewById(R.id.calendarView1);
		
		Date date = new Date(System.currentTimeMillis());

		@SuppressWarnings("deprecation")
		int mjesec = date.getMonth() + 1;

		String mj = d.nazivMj(mjesec);
		
		final TextView txtDatum = (TextView) findViewById(R.id.textMjesec);
		txtDatum.setText(mj);
		
		ImageButton lijevo = (ImageButton)findViewById(R.id.imgBtnLijevo);
		ImageButton desno = (ImageButton)findViewById(R.id.imgBtnDesno);
		
		lijevo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String trenutni = txtDatum.getText().toString();
				int odabrani = d.brojMj(trenutni);
				odabrani--;
				String nazivMjeseca = d.nazivMj(odabrani);
				txtDatum.setText(nazivMjeseca);
				pregledSve(odabrani);
			}
		});
		
		desno.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String trenutni = txtDatum.getText().toString();
				int odabrani = d.brojMj(trenutni);
				odabrani++;
				String mjesec = d.nazivMj(odabrani);
				txtDatum.setText(mjesec);
				pregledSve(odabrani);
				
			}
		});
		
		pregledSve(mjesec);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.potrazivanja_dugovanja, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Date date = new Date(System.currentTimeMillis());

		@SuppressWarnings("deprecation")
		int mjesec = date.getMonth() + 1;
		switch (item.getItemId()) {
		case R.id.itemPotrazivanja:
			if (item.isChecked()) {
				item.setChecked(false);
				izbor = 3;
				potrazivanjaSelected = false;
				if (!potrazivanjaSelected && !dugovanjaSelected) {
					pregledSve(mjesec);
					return true;
				}
			} else {
				item.setChecked(true);
				izbor = 2;
				potrazivanjaSelected = true;
				if (potrazivanjaSelected && dugovanjaSelected) {
					pregledSve(mjesec);
					return true;
				}
			}
			pregled(izbor, mjesec);
			return true;
		case R.id.itemDugovanja:
			if (item.isChecked()) {
				item.setChecked(false);
				izbor = 2;
				dugovanjaSelected = false;
				if (!potrazivanjaSelected && !dugovanjaSelected) {
					pregledSve(mjesec);
					return true;
				}
			} else {
				item.setChecked(true);
				izbor = 3;
				dugovanjaSelected = true;
				if (potrazivanjaSelected && dugovanjaSelected) {
					pregledSve(mjesec);
					return true;
				}
			}
			pregled(izbor, mjesec);
			return true;
		case R.id.itemDodaj:
			noviUnos();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void noviUnos() {
		dialog = new Dialog(c);
		dialog.setContentView(R.layout.dodajpid);
		dialog.setTitle(R.string.noviUnos);
		dialog.show();

		RadioGroup rgPiR = (RadioGroup) dialog.findViewById(R.id.radioGroup3);
		rgPiR.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			int izbor;

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				final int chkId = checkedId;
				Button btnSpremi = (Button) dialog.findViewById(R.id.btnSpremi);
				btnSpremi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						switch (chkId) {
						case R.id.rbPotrazivanje:
							izbor = 2;
							unos(izbor);
							Toast.makeText(getApplicationContext(),
									"potraživanje", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							break;

						case R.id.rbDugovanje:
							izbor = 3;
							unos(izbor);
							dialog.dismiss();
							break;
						}

					}
				});
			}
		});
	}

	public void unos(int izbor) {
		EditText etNaziv = (EditText) dialog.findViewById(R.id.etNaziv);
		String naziv = etNaziv.getText().toString();
		EditText etOpis = (EditText) dialog.findViewById(R.id.etOpis);
		String opis = etOpis.getText().toString();
		EditText etIznos = (EditText) dialog.findViewById(R.id.etIznos);
		Double iznos = 0.0;
		iznos = Double.valueOf(etIznos.getText().toString());
		
		Date date = new Date(System.currentTimeMillis());

		@SuppressWarnings("deprecation")
		int mjesec = date.getMonth() + 1;

		DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
		int day = dp.getDayOfMonth();
		int month = dp.getMonth();
		int year = dp.getYear();

		String datum = day + "." + (month + 1) + "." + year + ".";

		Transakcija prethodni = new Select().from(Transakcija.class)
				.orderBy("remote_id DESC").executeSingle();
		long trenutniId;
		try {
			trenutniId = prethodni.getRemote_id();
			trenutniId++;
		} catch (Exception e) {
			trenutniId = 0;
		}

		Transakcija transakcija = new Transakcija(trenutniId, naziv, opis, iznos, false, null,null, datum, mjesec,izbor);
		transakcija.save();

		Toast.makeText(getApplicationContext(), "Transakcija spremljena",
				Toast.LENGTH_SHORT).show();
	}

	public void pregled(int iz, int brojMj) {
		int t = iz;
		int mjesec = brojMj;
		
		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTipTransakcije(t, mjesec);

		ListView list = (ListView) findViewById(R.id.listview);

		TransakcijeAdapter pirAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pirAdapter);

	}

	public void pregledSve(int brojMj) {
		int mjesec = brojMj;

		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTransakcije(grupa,
				mjesec);

		ListView list = (ListView) findViewById(R.id.listview);

		TransakcijeAdapter pirAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pirAdapter);
	}
}

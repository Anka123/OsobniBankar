package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TransakcijeAdapter;
import hr.foi.air.osobnibankar.core.Transakcije;
import hr.foi.air.osobnibankar.database.Prihod;
import hr.foi.air.osobnibankar.database.Rashod;
import hr.foi.air.osobnibankar.database.Transakcija;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

public class PrihodiRashodiActivity extends Activity {
	Context c = this;
	Dialog dialog = null;
	int izbor;
	int grupa = 1;

	boolean prihodiSelected = false;
	boolean rashodiSelected = false;

	Date date = new Date(System.currentTimeMillis());
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat datum = new SimpleDateFormat("dd/mm/yyyy");
	int mjesec = java.text.DateFormat.MONTH_FIELD + 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piractivity);

		final Datum datum = new Datum();
		String mj = datum.nazivMj(mjesec);

		final TextView txtDatum = (TextView) findViewById(R.id.textMjesec);
		txtDatum.setText(mj);

		ImageButton lijevo = (ImageButton) findViewById(R.id.imgBtnLijevo);
		ImageButton desno = (ImageButton) findViewById(R.id.imgBtnDesno);

		lijevo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String trenutni = txtDatum.getText().toString();
				int odabrani = datum.brojMj(trenutni);
				odabrani--;
				String nazivMjeseca = datum.nazivMj(odabrani);
				txtDatum.setText(nazivMjeseca);
				pregledZajedno(odabrani);
			}
		});

		desno.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String trenutni = txtDatum.getText().toString();
				int odabrani = datum.brojMj(trenutni);
				odabrani++;
				String mjesec = datum.nazivMj(odabrani);
				txtDatum.setText(mjesec);
				pregledZajedno(odabrani);

			}
		});

		pregledZajedno(mjesec);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.prihodi_rashodi, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.itemPrihodi:
			if (item.isChecked()) {
				item.setChecked(false);
				izbor = 1;
				prihodiSelected = false;
				if (!prihodiSelected && !rashodiSelected) {
					pregledZajedno(mjesec);
					return true;
				}
			} else {
				item.setChecked(true);
				izbor = 0;
				prihodiSelected = true;
				if (prihodiSelected && rashodiSelected) {
					pregledZajedno(mjesec);
					return true;
				}
			}
			pregled(izbor, mjesec);
			return true;
		case R.id.itemRashodi:
			if (item.isChecked()) {
				item.setChecked(false);
				izbor = 0;
				rashodiSelected = false;
				if (!prihodiSelected && !rashodiSelected) {
					pregledZajedno(mjesec);
					return true;
				}
			} else {
				item.setChecked(true);
				izbor = 1;
				rashodiSelected = true;
				if (prihodiSelected && rashodiSelected) {
					pregledZajedno(mjesec);
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
		dialog.setContentView(R.layout.dodajpir);
		dialog.setTitle(R.string.noviUnos);
		dialog.show();

		RadioGroup rgPiR = (RadioGroup) dialog.findViewById(R.id.radioGroup2);
		rgPiR.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				final int chkId = checkedId;
				Button btnSpremi = (Button) dialog.findViewById(R.id.btnSpremi);
				btnSpremi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						switch (chkId) {

						case R.id.rbPrihod:
							izbor = 0;
							unos(izbor);
							Toast.makeText(getApplicationContext(), "prihod",
									Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							break;

						case R.id.rbRashod:
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

	public void unos(int izbor) {
		EditText etNaziv = (EditText) dialog.findViewById(R.id.etNaziv);
		String naziv = etNaziv.getText().toString();
		EditText etOpis = (EditText) dialog.findViewById(R.id.etOpis);
		String opis = etOpis.getText().toString();
		EditText etIznos = (EditText) dialog.findViewById(R.id.etIznos);
		Double iznos = 0.0;
		iznos = Double.valueOf(etIznos.getText().toString());
		String danasnjiDatum = datum.format(date);
		Spinner sp = (Spinner) dialog.findViewById(R.id.spinKategorija);
		String kategorija = sp.getSelectedItem().toString();

		if (izbor == 0) {
			Prihod prethodni = new Select().from(Prihod.class)
					.orderBy("remote_id DESC").executeSingle();
			long trenutniId;
			try {
				trenutniId = prethodni.getRemote_id();
				trenutniId++;
			} catch (Exception e) {
				trenutniId = 0;
			}

			Prihod prihod = new Prihod(trenutniId, naziv, opis, kategorija,
					iznos, danasnjiDatum, mjesec);
			prihod.save();

			Toast.makeText(getApplicationContext(), "Prihod spremljen",
					Toast.LENGTH_SHORT).show();

		} else if (izbor == 1) {
			Rashod prethodni = new Select().from(Rashod.class)
					.orderBy("remote_id DESC").executeSingle();
			long trenutniId;
			try {
				trenutniId = prethodni.getRemote_id();
				trenutniId++;
			} catch (Exception e) {
				trenutniId = 0;
			}

			Rashod rashod = new Rashod(trenutniId, naziv, opis, kategorija,
					iznos, danasnjiDatum, mjesec);
			rashod.save();

			Toast.makeText(getApplicationContext(), "Rashod spremljen",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void pregled(int iz, int brojMj) {
		int t = iz;
		int mjesec = brojMj;

		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTipTransakcije(t, mjesec);

		ListView list = (ListView) findViewById(R.id.list);

		TransakcijeAdapter pirAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pirAdapter);

	}

	public void pregledZajedno(int brojMj) {
		int mjesec = brojMj;

		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTransakcije(grupa,
				mjesec);

		ListView list = (ListView) findViewById(R.id.list);

		TransakcijeAdapter pirAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pirAdapter);
	}

}

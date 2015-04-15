package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TransakcijeAdapter;
import hr.foi.air.osobnibankar.core.Transakcije;
import hr.foi.air.osobnibankar.database.Dugovanje;
import hr.foi.air.osobnibankar.database.Potrazivanje;
import hr.foi.air.osobnibankar.database.Transakcija;
import hr.foi.air.osobnibankar.interfaces.ITransakcija;

import java.util.List;

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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class PotrazivanjaDugovanjaActivity extends Activity {
	Context c = this;
	Dialog dialog = null;
	int izbor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pidactivity);

		pregledZajedno();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.potrazivanja_dugovanja, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPotrazivanja:
			izbor = 2;
			pregled(izbor);
			return true;
		case R.id.itemDugovanja:
			izbor = 3;
			pregled(izbor);
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
			Potrazivanje potrazivanje = new Potrazivanje(naziv, opis, null,
					iznos, datum);
			potrazivanje.save();

			Toast.makeText(getApplicationContext(), "Potrazivanje spremljeno",
					Toast.LENGTH_SHORT).show();
		} else if (iz == 1) {
			Dugovanje dugovanje = new Dugovanje(naziv, opis, null, iznos, datum);

			Toast.makeText(getApplicationContext(), "Dugovanje spremljeno",
					Toast.LENGTH_SHORT).show();
			dugovanje.save();
		}
	}

	public void pregled(int iz) {
		int t = iz;
		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTipTransakcije(t);

		ListView list = (ListView) findViewById(R.id.list);

		TransakcijeAdapter transakcijeAdapter = new TransakcijeAdapter(this,
				R.layout.item_pid, listaTransakcija);
		list.setAdapter(transakcijeAdapter);

	}

	public void pregledZajedno() {
		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTransakcije();

		ListView list = (ListView) findViewById(R.id.listview);

		TransakcijeAdapter prihodiAdapter = new TransakcijeAdapter(this,
				R.layout.item_pid, listaTransakcija);
		list.setAdapter(prihodiAdapter);
	}
}

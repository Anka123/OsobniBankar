package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.PrihodiAdapter;
import hr.foi.air.osobnibankar.adapters.RashodiAdapter;
import hr.foi.air.osobnibankar.database.Prihod;
import hr.foi.air.osobnibankar.database.Rashod;

import java.util.Date;
import java.util.List;

import com.activeandroid.query.Select;

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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class PrihodiRashodiActivity extends Activity {
	Context c = this;
	Dialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piractivity);

		pregledPrihoda();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.prihodi_rashodi, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPrihodi:
			
			return true;
		case R.id.itemRashodi:
			item.setChecked(true);
			pregledRashoda();
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
			int izbor;

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
		int iz = izbor;
		EditText etNaziv = (EditText) dialog.findViewById(R.id.etNaziv);
		String naziv = etNaziv.getText().toString();
		EditText etOpis = (EditText) dialog.findViewById(R.id.etOpis);
		String opis = etOpis.getText().toString();
		EditText etIznos = (EditText) dialog.findViewById(R.id.etIznos);
		Double iznos = 0.0;
		iznos = Double.valueOf(etIznos.getText().toString());
		Date datum = new Date(System.currentTimeMillis());
		String danasnjiDatum = datum.toString();

		if (iz == 0) {
			Prihod prihod = new Prihod(naziv, opis, iznos, danasnjiDatum);
			prihod.save();

			Toast.makeText(getApplicationContext(), "Prihod spremljen",
					Toast.LENGTH_SHORT).show();

		} else if (iz == 1) {
			Rashod rashod = new Rashod(naziv, opis, iznos, danasnjiDatum);

			Toast.makeText(getApplicationContext(), "Rashod spremljen",
					Toast.LENGTH_SHORT).show();
			rashod.save();
		}

	}

	public void pregledPrihoda() {
		ListView list = (ListView) findViewById(R.id.list);
		List<Prihod> listaPrihoda = new Select().all().from(Prihod.class)
				.execute();

		PrihodiAdapter prihodiAdapter = new PrihodiAdapter(this,
				R.layout.item_prihod, listaPrihoda);
		list.setAdapter(prihodiAdapter);

	}
	
	public void pregledRashoda(){
		ListView list = (ListView) findViewById(R.id.list);
		List<Rashod> listaRashoda = new Select().all().from(Rashod.class)
				.execute();

		RashodiAdapter rashodiAdapter  = new RashodiAdapter(this,
				R.layout.item_rashod, listaRashoda);
		list.setAdapter(rashodiAdapter);
		
	}
}

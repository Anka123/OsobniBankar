package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

public class KontinuiranaActivity extends Activity {

	int brojMjeseci = 0;
	Double sumaPrivremene = null;
	Dialog dialog = null;
	Context c = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kontinuirana);

		NumberPicker nmbMjesec = (NumberPicker) findViewById(R.id.nmbMjesec);
		nmbMjesec.setMaxValue(100);
		nmbMjesec.setMinValue(1);

		provjeriStanje();
		toggleButton();

	}

	private void progressBar() {

		Double sumaKontinuirane = 0.0;
		List<Transakcija> listaKontinuirane = new Select().all()
				.from(Transakcija.class).where("tip_id=5 AND zatvoreno = 0")
				.execute();

		for (int i = 0; i < listaKontinuirane.size(); i++) {

			sumaKontinuirane += listaKontinuirane.get(i).iznos;
		}

		int i = (int) Math.round(sumaKontinuirane);
		ProgressBar progCilj = (ProgressBar) findViewById(R.id.progCilj);
		progCilj.getProgressDrawable().setColorFilter(Color.RED, Mode.SRC_IN);

		int maksimalna;
		maksimalna = (int) Math.round(sumaPrivremene);
		progCilj.setMax(maksimalna);
		progCilj.setProgress(i);
		
		TextView txtNapredak = (TextView)findViewById(R.id.txtNapredak);
		
		String napredak = String.valueOf(i);
		String cilj = String.valueOf(maksimalna);
		String cijniIznos = napredak + "/" + cilj;
		
		txtNapredak.setText(cijniIznos);
	}

	private void provjeriStanje() {

		List<Transakcija> listaStednje = new Select().all()
				.from(Transakcija.class).where("tip_id=6").execute();

		for (Transakcija transakcija : listaStednje) {

			sumaPrivremene = transakcija.getIznos();
			brojMjeseci = transakcija.getMjesec();
		}

		ToggleButton togCilj = (ToggleButton) findViewById(R.id.togZapocni);

		if (sumaPrivremene != null) {

			progressBar();

			EditText iznosKontinuirane = (EditText) findViewById(R.id.etIznosKontinuirane);
			iznosKontinuirane.setFocusable(false);

			Toast.makeText(getApplicationContext(), "Nije prazno",
					Toast.LENGTH_SHORT).show();
			togCilj.setChecked(true);

			Calendar cal = Calendar.getInstance();
			int dan = cal.get(Calendar.DAY_OF_MONTH);

			if (dan == 1) {

				izracunajStednju();

			} else
				zaustaviStednju();
		} else {
			Toast.makeText(getApplicationContext(), "Prazno",
					Toast.LENGTH_SHORT).show();
			togCilj.setChecked(false);
		}
	}

	private void toggleButton() {

		ToggleButton togCilj = (ToggleButton) findViewById(R.id.togZapocni);
		togCilj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					zapocniStednju();
				else {
					zaustaviStednju();
				}
			}
		});
	}

	private void zapocniStednju() {
		
		Double iznos = null;

		NumberPicker nmbMjesec = (NumberPicker) findViewById(R.id.nmbMjesec);
		int mjesec = nmbMjesec.getValue();

		EditText iznosKontinuirane = (EditText) findViewById(R.id.etIznosKontinuirane);

		if (iznosKontinuirane.getText().toString().isEmpty()){
			Toast.makeText(c, "Unesite iznos!", Toast.LENGTH_SHORT).show();
			
		}
		
		else {
			
			iznos = Double.valueOf(iznosKontinuirane.getText().toString());
			int izbor = 6;

			Transakcija prethodni = new Select().from(Transakcija.class)
					.orderBy("remote_id DESC").executeSingle();
			long trenutniId;
			try {
				trenutniId = prethodni.getRemote_id();
				trenutniId++;
			} catch (Exception e) {
				trenutniId = 0;
			}

			
			Transakcija privremena = new Transakcija(trenutniId, null, null, iznos,
					false, null, null, null, mjesec, izbor);
			privremena.save();
		}
	}

	public void zaustaviStednju() {

		new Delete().from(Transakcija.class).where("tip_id=6").execute();

		List<Transakcija> listaKontinuiranih = new Select().all()
				.from(Transakcija.class).where("tip_id=5").execute();

		for (int i = 0; i < listaKontinuiranih.size(); i++) {

			listaKontinuiranih.get(i).zatvoreno = true;
			listaKontinuiranih.get(i).save();
		}
	}
	
	private void izracunajStednju() {

		Double iznosMjesecne = 0.0;
		iznosMjesecne = sumaPrivremene / brojMjeseci;

		dialog = new Dialog(c);
		dialog.setContentView(R.layout.mjesecnakontinuirana);
		dialog.show();

		TextView txtIznos = (TextView) dialog
				.findViewById(R.id.txtIznosMjesecne);
		String iznos = Double.valueOf(iznosMjesecne).toString();
		txtIznos.setText(iznos);

		Button btnSpremi = (Button) dialog.findViewById(R.id.btnSpremi);
		btnSpremi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Double iznosMjesecne = 0.0;
				iznosMjesecne = sumaPrivremene / brojMjeseci;

				int izbor = 5;

				Transakcija prethodni = new Select().from(Transakcija.class)
						.orderBy("remote_id DESC").executeSingle();
				long trenutniId;
				try {
					trenutniId = prethodni.getRemote_id();
					trenutniId++;
				} catch (Exception e) {
					trenutniId = 0;
				}

				String naziv = "Kontinuirana";
				Transakcija kontinuirana = new Transakcija(trenutniId, naziv,
						null, iznosMjesecne, false, null, null, null, 0, izbor);
				kontinuirana.save();

				Transakcija dohvatPrivremene = new Select().all()
						.from(Transakcija.class).where("tip_id=6")
						.executeSingle();

				int stariMjeseci = dohvatPrivremene.getMjesec();

				List<Transakcija> listaKontinuiranih = new Select().all().from(Transakcija.class).where("tip_id = 5 AND zatvoreno = 0").execute();
				
				progressBar();
				
				if (listaKontinuiranih.size() >= stariMjeseci)
					zaustaviStednju();
				
				dialog.dismiss();
				
				
			}
		});
	}
}

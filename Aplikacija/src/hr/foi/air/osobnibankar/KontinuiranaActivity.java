package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.R;
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
	public static int jezik;
	int brojMjeseci = 0;
	Double sumaPrivremene = null;
	Dialog dialog = null;
	Context c = this;
	Double iznos = null;
	
	
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

		TextView txtNapredak = (TextView) findViewById(R.id.txtNapredak);

		String napredak = String.valueOf(i);
		String cilj = String.valueOf(maksimalna);
		String cijniIznos = napredak + "/" + cilj;

		txtNapredak.setText(cijniIznos);
	}

	
	/**
	 * metoda provjerava da li postoji stednja koja je zapoceta ili ne postoji,
	 * ako u tom mjesecu ne postoji nikakav zapis i ako je prvi u mjesecu onda
	 * izracunava stednju
	 */
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

			togCilj.setChecked(true);

			Calendar cal = Calendar.getInstance();
			int dan = cal.get(Calendar.DAY_OF_MONTH);
			int mjesec = cal.get(Calendar.MONTH) + 1;

			List<Transakcija> provjeraMjeseci = new Select().all()
					.from(Transakcija.class)
					.where("mjesec=? AND (tip_id=5 AND zatvoreno=0)", mjesec)
					.execute();
			
			for (int i = 0; i < provjeraMjeseci.size(); i++) {
				iznos = provjeraMjeseci.get(i).iznos;
			}

			if (dan == 1 && iznos == null) {

				izracunajStednju();
			} 
			else if (dan==1 && iznos !=null){
				
				progressBar();
			}
			else if (dan !=1 && iznos !=null) {
				Toast.makeText(c, R.string.pokrenutaStednja, Toast.LENGTH_SHORT).show();
				
				progressBar();
			}
			else
				zaustaviStednju();
		} else {
			togCilj.setChecked(false);
		}
	}

	/**
	 * definira se sto ce se dogoditi s  obzirom na to u kojem stanju
	 * je gumb
	 */
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

	/**
	 * zapocni stednju na nacin da se unese broj mjeseci i iznos zeljene stednje
	 * te se na temelju toga racuna svaka buduca mjesecna stednja
	 */
	private void zapocniStednju() {

		Double iznos = null;

		NumberPicker nmbMjesec = (NumberPicker) findViewById(R.id.nmbMjesec);
		int mjesec = nmbMjesec.getValue();

		EditText iznosKontinuirane = (EditText) findViewById(R.id.etIznosKontinuirane);

		ToggleButton togCilj = (ToggleButton) findViewById(R.id.togZapocni);
		if (iznosKontinuirane.getText().toString().isEmpty()) {
			togCilj.setChecked(false);
			if (jezik == 2) {
				Toast.makeText(c, "Unesite iznos!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(c, "Enter the amount!", Toast.LENGTH_SHORT)
						.show();
			}
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

			Transakcija privremena = new Transakcija(trenutniId, null, null,
					iznos, false, null, null, mjesec, izbor);
			privremena.save();
		}
	}

	/**
	 * metoda koja brise privremenu stednju iz transakacije, 
	 * a svim kontinuiranima postavlja vrijednost zatvoreno na true
	 */
	public void zaustaviStednju() {

		new Delete().from(Transakcija.class).where("tip_id=6").execute();

		List<Transakcija> listaKontinuiranih = new Select().all()
				.from(Transakcija.class).where("tip_id=5").execute();

		for (int i = 0; i < listaKontinuiranih.size(); i++) {

			listaKontinuiranih.get(i).zatvoreno = true;
			listaKontinuiranih.get(i).save();
		}
		provjeriStanje();
	}

	/**
	 * metoda koja izracunava mjesecnu stednju na temelju privremene stednje,
	 * metoda se izvrsava onoliko puta koliki je broj mjeseci u bazi
	 */
	private void izracunajStednju() {

		Double iznosMjesecne = 0.0;
		iznosMjesecne = sumaPrivremene / brojMjeseci;

		dialog = new Dialog(c);
		dialog.setContentView(R.layout.mjesecnakontinuirana);
		dialog.setTitle(R.string.kontinuiranaIznos);
		dialog.show();

		if (jezik == 2) {
			TextView txtIznos = (TextView) dialog
					.findViewById(R.id.txtIznosMjesecne);
			String iznos = Double.valueOf(iznosMjesecne).toString();
			txtIznos.setText(iznos);
		} else {
			TextView txtAmount = (TextView) dialog
					.findViewById(R.id.txtIznosMjesecne);
			String iznos = Double.valueOf(iznosMjesecne).toString();
			txtAmount.setText(iznos);
		}
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

				Calendar cal = Calendar.getInstance();
				int trenutno = cal.get(Calendar.MONTH)+1;
				String naziv = "Kontinuirana";
				Transakcija kontinuirana = new Transakcija(trenutniId, naziv,
						null, iznosMjesecne, false, null, null, trenutno, izbor);
				kontinuirana.save();

				Transakcija dohvatPrivremene = new Select().all()
						.from(Transakcija.class).where("tip_id=6")
						.executeSingle();

				int stariMjeseci = dohvatPrivremene.getMjesec();

				List<Transakcija> listaKontinuiranih = new Select().all()
						.from(Transakcija.class)
						.where("tip_id = 5 AND zatvoreno = 0").execute();

				progressBar();

				if (listaKontinuiranih.size() >= stariMjeseci)
					zaustaviStednju();

				dialog.dismiss();
				

			}
		});
	}

}

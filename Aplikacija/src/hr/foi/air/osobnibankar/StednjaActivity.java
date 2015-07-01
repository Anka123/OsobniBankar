package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.db.Transakcija;
import hr.foi.air.osobnibankar.dodatno.Datum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activeandroid.query.Select;

public class StednjaActivity extends Activity {
	Dialog dialog = null;
	Context c = this;
	Calendar cal = Calendar.getInstance();

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat datum = new SimpleDateFormat("dd/mm/yyyy");
	final Datum d = new Datum();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stednje);

		izracunajStednju();

		ImageButton btnProizvoljna = (ImageButton) findViewById(R.id.imgProizvoljna);
		btnProizvoljna.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog = new Dialog(c);
				dialog.setContentView(R.layout.proizvoljna);
				dialog.setTitle(R.string.kontinuiranaIznos);
				dialog.show();

				Button btnOk = (Button) dialog.findViewById(R.id.btnOK);
				btnOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						EditText etProizvoljna = (EditText) dialog
								.findViewById(R.id.etProizvoljna);
						Double iznosProizvoljne = 0.0;
						iznosProizvoljne = Double.valueOf(etProizvoljna
								.getText().toString());

						Transakcija prethodni = new Select()
								.from(Transakcija.class)
								.orderBy("remote_id DESC").executeSingle();
						long trenutniId;
						try {
							trenutniId = prethodni.getRemote_id();
							trenutniId++;
						} catch (Exception e) {
							trenutniId = 0;
						}

						int mjesec = cal.get(Calendar.MONTH) + 1;
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int year = cal.get(Calendar.YEAR);

						String datum = day + "." + mjesec + "." + year + ".";

						int izbor = 4;

						String naziv = "Proizvoljna";
						Transakcija transakcija = new Transakcija(trenutniId,
								naziv, null, iznosProizvoljne, false, null,
								datum, mjesec, izbor);
						transakcija.save();

						dialog.dismiss();
						izracunajStednju();
					}
				});
			}
		});

		ImageButton btnKontinuirana = (ImageButton) findViewById(R.id.imgKontinuirana);
		btnKontinuirana.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						KontinuiranaActivity.class);
				startActivity(i);

			}

		});

	}

	/**
	 * metoda koja izracunava stednju
	 */
	public void izracunajStednju() {

		double sumaStednje = 0;

		List<Transakcija> listaStednje = new Select().all()
				.from(Transakcija.class).where("tip_id=4 OR tip_id=5")
				.execute();

		for (Transakcija transakcija : listaStednje) {

			sumaStednje += transakcija.getIznos();

		}

		TextView txtUstedeni = (TextView) findViewById(R.id.txtUstedeniIznos);
		String iznosStednje = Double.valueOf(sumaStednje).toString();

		txtUstedeni.setText(iznosStednje);
	}

	@Override
	protected void onResume() {
		izracunajStednju();
		super.onResume();
	}

}

package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TransakcijeAdapter;
import hr.foi.air.osobnibankar.core.Transakcije;
import hr.foi.air.osobnibankar.db.Profil;
import hr.foi.air.osobnibankar.db.Tip;
import hr.foi.air.osobnibankar.db.Transakcija;
import hr.foi.air.osobnibankar.dodatno.Datum;
import hr.foi.air.osobnibankar.interfaces.ITransakcija;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

public class PrihodiRashodiActivity extends Activity {
	Context c = this;
	Dialog dialog = null;
	int izbor;
	int grupa = 1;
	Calendar calendar = new GregorianCalendar();
	int mjesec = calendar.get(Calendar.MONTH) + 1;
	public static View v;
	boolean prihodiSelected = false;
	boolean rashodiSelected = false;
	Double limit = 0.0;
	int g_mjesec;
	Double ogranicenje = null;
	List<Profil> listaOgranicenja = new Select().all().from(Profil.class)
			.execute();

	final Datum d = new Datum();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		ActiveAndroid.initialize(this);
		TransakcijeAdapter.tipTransakcije = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piractivity);

		int mj = d.nazivMj(mjesec);

		final TextView txtDatum = (TextView) findViewById(R.id.textMjesec);
		txtDatum.setText(mj);

		provjeriLimit();
		ImageButton lijevo = (ImageButton) findViewById(R.id.imgBtnLijevo);
		ImageButton desno = (ImageButton) findViewById(R.id.imgBtnDesno);

		lijevo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String trenutni = txtDatum.getText().toString();
				int odabrani = d.brojMj(trenutni);
				odabrani--;
				g_mjesec = odabrani;
				int nazivMjeseca = d.nazivMj(odabrani);
				txtDatum.setText(nazivMjeseca);
				pregledZajedno(odabrani);
				izracunajMjesecni(odabrani);
			}
		});

		desno.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String trenutni = txtDatum.getText().toString();
				int odabrani = d.brojMj(trenutni);
				odabrani++;
				int mjesec = d.nazivMj(odabrani);
				g_mjesec = odabrani;
				txtDatum.setText(mjesec);
				pregledZajedno(odabrani);
				izracunajMjesecni(odabrani);
			}
		});

		pregledZajedno(mjesec);
		g_mjesec = mjesec;
		izracunajMjesecni(mjesec);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.prihodi_rashodi, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Calendar calendar = new GregorianCalendar();
		int mjesec = calendar.get(Calendar.MONTH) + 1;

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
		case R.id.itemLimit:
			odrediLimit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * metoda koja odredjuje limit mjesecne potrosnje
	 */
	public void odrediLimit() {
		dialog = new Dialog(c);
		dialog.setContentView(R.layout.odredilimit);
		dialog.setTitle(R.string.limit);
		dialog.show();

		Button btnOk = (Button) dialog.findViewById(R.id.btnSpremi);

		for (Profil ogranicenje1 : listaOgranicenja) {

			ogranicenje = ogranicenje1.getOgranicenje();

		}

		if (ogranicenje != null) {
			EditText etLimit = (EditText) dialog.findViewById(R.id.etLimit);
			etLimit.setText(ogranicenje.toString());

			btnOk.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					EditText etLimit = (EditText) dialog
							.findViewById(R.id.etLimit);
					Double limit1 = Double
							.valueOf(etLimit.getText().toString());

					Profil profil = new Select().all().from(Profil.class)
							.executeSingle();

					profil.ogranicenje = limit1;
					profil.save();

					dialog.dismiss();
				}
			});

		}

		else {
			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					EditText etLimit = (EditText) dialog
							.findViewById(R.id.etLimit);
					limit = Double.valueOf(etLimit.getText().toString());

					Profil prethodni = new Select().from(Profil.class)
							.orderBy("remote_id DESC").executeSingle();
					long trenutniId;
					try {
						trenutniId = prethodni.getRemoteId();
						trenutniId++;
					} catch (Exception e) {
						trenutniId = 0;
					}

					Profil profil = new Profil(trenutniId, null, null, limit);
					profil.save();

					dialog.dismiss();

				}
			});
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
							String nazivPrihod = "Prihod";
							Tip tipPrihod = new Tip(izbor, nazivPrihod);
							tipPrihod.save();
							unos(izbor);
							dialog.dismiss();
							break;

						case R.id.rbRashod:
							izbor = 1;
							String nazivRashod = "Rashod";
							Tip tipRashod = new Tip(izbor, nazivRashod);
							tipRashod.save();
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
		Double iznos = 0.00;
		iznos = Double.valueOf(etIznos.getText().toString());

		int dan = calendar.get(Calendar.DAY_OF_MONTH);

		int godina = calendar.get(Calendar.YEAR);

		String danasnjiDatum = dan + "." + mjesec + "." + godina + ".";

		Spinner sp = (Spinner) dialog.findViewById(R.id.spinKategorija);
		String kategorija = sp.getSelectedItem().toString();

		Transakcija prethodni = new Select().from(Transakcija.class)
				.orderBy("remote_id DESC").executeSingle();
		long trenutniId;
		try {
			trenutniId = prethodni.getRemote_id();
			trenutniId++;
		} catch (Exception e) {
			trenutniId = 0;
		}

		
		
		if (naziv.isEmpty() | opis.isEmpty())
			Toast.makeText(c, R.string.popunaPolja, Toast.LENGTH_SHORT).show();
		else {
			Transakcija transakcija = new Transakcija(trenutniId, naziv, opis,
					iznos, false, kategorija, danasnjiDatum, mjesec, izbor);
			transakcija.save();
		}
			

		pregledZajedno(g_mjesec);
		izracunajMjesecni(mjesec);
	}

	public void onItemClick(ListView list) {
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {

				final String tagPosition = (v.getTag().toString());

				AlertDialog.Builder ad = new AlertDialog.Builder(c);
				ad.setMessage(c.getResources().getString(R.string.poruka));
				ad.setPositiveButton(
						c.getResources().getString(R.string.promijeni),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									android.content.DialogInterface dialog,
									int odabir) {

								final Dialog dialog1 = new Dialog(c);
								final Transakcija t;

								t = new Select()
										.all()
										.from(Transakcija.class)
										.where("remote_id =? AND(tip_id=0 OR tip_id=1)",
												tagPosition).executeSingle();

								dialog1.setContentView(R.layout.dodajpir);
								if (t.getTip() == 0) {
									RadioButton r = (RadioButton) dialog1
											.findViewById(R.id.rbPrihod);
									r.setChecked(true);
								} else {
									RadioButton r = (RadioButton) dialog1
											.findViewById(R.id.rbRashod);
									r.setChecked(false);
								}
								final int tip = t.getTip();
								dialog1.setTitle(R.string.noviUnos);
								dialog1.show();
								EditText etNaziv = (EditText) dialog1
										.findViewById(R.id.etNaziv);
								etNaziv.setText(t.getNaziv());
								EditText etOpis = (EditText) dialog1
										.findViewById(R.id.etOpis);
								etOpis.setText(t.getOpis());
								EditText etIznos = (EditText) dialog1
										.findViewById(R.id.etIznos);
								etIznos.setText(t.getIznos().toString());
								Button btnSpremi = (Button) dialog1
										.findViewById(R.id.btnSpremi);
								btnSpremi
										.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {

												switch (tip) {
												case 0:
													promjena(dialog1, 0, null,
															1, t.getId());
													break;

												case 1:
													promjena(dialog1, 1, null,
															1, t.getId());
													break;
												}
												dialog1.dismiss();
											}
										});
							}

						});
				ad.setNegativeButton(
						c.getResources().getString(R.string.izbrisi),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									android.content.DialogInterface dialog,
									int odabir) {
								new Delete().from(Transakcija.class)
										.where("remote_id =?", tagPosition)
										.execute();
								pregledZajedno(g_mjesec);
								izracunajMjesecni(mjesec);
							}
						});

				ad.show();
			}
		});
	}

	/**
	 * metoda koja sluzi za pregled prihoda i rashoda po mjesecima
	 * 
	 * @param iz
	 * @param brojMj
	 */
	public void pregled(int iz, int brojMj) {
		int t = iz;
		int mjesec = brojMj;

		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr
				.dohvatiTipTransakcije(t, mjesec);

		ListView list = (ListView) findViewById(R.id.list);

		TransakcijeAdapter pirAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);

		list.setAdapter(pirAdapter);
		onItemClick(list);
	}

	/**
	 * metoda koja sluzi za pregled prihoda i rashoda zajedno
	 * 
	 * @param brojMj
	 */
	public void pregledZajedno(int brojMj) {
		int mjesec = brojMj;

		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTransakcije(grupa,
				mjesec);

		ListView list = (ListView) findViewById(R.id.list);

		TransakcijeAdapter pirAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pirAdapter);

		onItemClick(list);

	}

	/**
	 * metoda koja sluzi prilikom promjene postojeceg prihoda ili rashoda
	 * 
	 * @param dialog
	 * @param unos
	 * @param danasnjiDatum
	 * @param mjesec
	 * @param id
	 */
	void promjena(Dialog dialog, int unos, String danasnjiDatum, int mjesec,
			long id) {
		EditText etNaziv = (EditText) dialog.findViewById(R.id.etNaziv);
		String naziv = etNaziv.getText().toString();
		EditText etOpis = (EditText) dialog.findViewById(R.id.etOpis);
		String opis = etOpis.getText().toString();
		EditText etIznos = (EditText) dialog.findViewById(R.id.etIznos);
		Double iznos = 0.0;
		iznos = Double.valueOf(etIznos.getText().toString());
		Spinner sp = (Spinner) dialog.findViewById(R.id.spinKategorija);
		String kategorija = sp.getSelectedItem().toString();

		Transakcija transakcija = Transakcija.load(Transakcija.class, id);
		transakcija.naziv = naziv;
		transakcija.opis = opis;
		transakcija.iznos = iznos;
		transakcija.kategorija = kategorija;
		transakcija.save();

		pregledZajedno(g_mjesec);
		izracunajMjesecni(mjesec);
	}

	/**
	 * metoda koja izracunava trenutni mjesecni iznos
	 * 
	 * @param trenutni
	 */
	public void izracunajMjesecni(int trenutni) {
		int mj = trenutni;
		double sumaPrihoda = 0;
		double sumaRashoda = 0;
		List<Transakcija> listaTransakcija = new Select().all()
				.from(Transakcija.class)
				.where("mjesec == ? AND (tip_id = 0 OR tip_id=1)", mj)
				.execute();

		for (Transakcija transakcija : listaTransakcija) {
			if (transakcija.getTip() == 0) {
				sumaPrihoda += transakcija.getIznos();
			} else if (transakcija.getTip() == 1) {
				sumaRashoda += transakcija.getIznos();
			}
		}

		double sumaStednje = 0;

		List<Transakcija> listaStednje = new Select().all()
				.from(Transakcija.class).where("tip_id=4 OR tip_id=5")
				.execute();

		for (Transakcija transakcija : listaStednje) {

			sumaStednje += transakcija.getIznos();

		}

		double ukupno = sumaPrihoda - (sumaRashoda + sumaStednje);
		String iznos = getResources().getString(R.string.txtTrenutni);
		TextView tv = (TextView) findViewById(R.id.txtTrenutni);

		tv.setText(iznos + String.valueOf(ukupno));
	}

	/**
	 * metoda koja provjerava da li je potrosnja veca od zadanog limita
	 */
	public void provjeriLimit() {
		List<Profil> listaOgranicenja = new Select().all().from(Profil.class)
				.execute();

		for (Profil ogranicenje1 : listaOgranicenja) {

			ogranicenje = ogranicenje1.getOgranicenje();

		}

		if (ogranicenje != null) {

			double sumaMjesecne = 0.0;

			List<Transakcija> listaMjesecne = new Select().all()
					.from(Transakcija.class)
					.where("mjesec == ? AND tip_id=1", mjesec).execute();
			for (int i = 0; i < listaMjesecne.size(); i++) {
				sumaMjesecne += listaMjesecne.get(i).getIznos();
			}

			if (sumaMjesecne > ogranicenje) {

				dialog = new Dialog(c);
				dialog.setContentView(R.layout.potrosnja);
				dialog.setTitle(R.string.prekoracena);
				dialog.show();

				TextView potrosnja = (TextView) dialog
						.findViewById(R.id.txtStanje);
				String sumaR = Double.valueOf(sumaMjesecne).toString();
				potrosnja.setText(sumaR);

				TextView txtlimit = (TextView) dialog
						.findViewById(R.id.txtTrenutniLimit);
				String postavljeni = Double.valueOf(ogranicenje).toString();
				txtlimit.setText(postavljeni);

				Button ok = (Button) dialog.findViewById(R.id.btnOK);

				ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}

				});

				Button limit = (Button) dialog.findViewById(R.id.btnLimit);

				limit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();
						odrediLimit();

					}

				});

			}
		}
	}

	@Override
	public void onResume() {
		izracunajMjesecni(mjesec);
		super.onResume();
	}

}
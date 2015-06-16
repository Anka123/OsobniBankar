package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TransakcijeAdapter;
import hr.foi.air.osobnibankar.core.Transakcije;
import hr.foi.air.osobnibankar.db.Tip;
import hr.foi.air.osobnibankar.db.Transakcija;
import hr.foi.air.osobnibankar.dodatno.Datum;
import hr.foi.air.osobnibankar.interfaces.ITransakcija;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

public class PotrazivanjaDugovanjaActivity extends Activity {
	Context c = this;
	Dialog dialog = null;
	int izbor;
	int grupa = 0;
	int g_mjesec;
	
	Calendar calendar = new GregorianCalendar();
	int mjesec = calendar.get(Calendar.MONTH) + 1;
	
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
				g_mjesec = odabrani;
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
				g_mjesec = odabrani;
				String mjesec = d.nazivMj(odabrani);
				txtDatum.setText(mjesec);
				pregledSve(odabrani);
				
			}
		});
		g_mjesec = mjesec;
		pregledSve(mjesec);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.potrazivanja_dugovanja, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		//Date date = new Date(System.currentTimeMillis());

		//@SuppressWarnings("deprecation")
		//int mjesec = date.getMonth() + 1;
		
		Calendar calendar = new GregorianCalendar();
		int mjesec = calendar.get(Calendar.MONTH) + 1;
		
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

		RadioGroup rgPiD = (RadioGroup) dialog.findViewById(R.id.radioGroup3);
		rgPiD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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
							String nazivPotrazivanje = "Potrazivanje";
							Tip tipPotrazivanje = new Tip(izbor, nazivPotrazivanje);
							tipPotrazivanje.save();
							unos(izbor);
							Toast.makeText(getApplicationContext(),
									"potraživanje", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							break;

						case R.id.rbDugovanje:
							izbor = 3;
							String nazivDugovanje = "Prihod";
							Tip tipDugovanje = new Tip(izbor, nazivDugovanje);
							tipDugovanje.save();
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

		TransakcijeAdapter pidAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pidAdapter);
		onItemClick(list);
	}

	public void pregledSve(int brojMj) {
		int mjesec = brojMj;

		ITransakcija tr = new Transakcije();
		List<Transakcija> listaTransakcija = tr.dohvatiTransakcije(grupa,
				mjesec);

		ListView list = (ListView) findViewById(R.id.listview);

		TransakcijeAdapter pidAdapter = new TransakcijeAdapter(this,
				R.layout.item_transakcija, listaTransakcija);
		list.setAdapter(pidAdapter);
		onItemClick(list);
	}
	
	public void onItemClick(ListView list){
		list.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2,
                long arg3) {

			final String tagPosition = (v.getTag().toString());
			
			AlertDialog.Builder ad = new AlertDialog.Builder(c);
			ad.setMessage(c.getResources().getString(R.string.poruka));
			ad.setPositiveButton(c.getResources().getString(R.string.promijeni),
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								android.content.DialogInterface dialog,
								int odabir) {
								
								final Dialog dialog1 =new Dialog(c);
								final Transakcija t;
								
								dialog1.setContentView(R.layout.dodajpid);
								t = new  Select().all().from(Transakcija.class).where("remote_id =? AND(tip_id=2 OR tip_id=3)",tagPosition).executeSingle();
								
								if(t.getTip()==2){
									RadioButton r = (RadioButton)dialog1.findViewById(R.id.rbPotrazivanje);
									r.setChecked(true);
								}
								else{
									RadioButton r = (RadioButton)dialog1.findViewById(R.id.rbDugovanje);
									r.setChecked(false);
								}
								final int tip = t.getTip();
								dialog1.setTitle(R.string.noviUnos);
								dialog1.show();
								EditText etNaziv = (EditText) dialog1.findViewById(R.id.etNaziv);
								etNaziv.setText(t.getNaziv());
								EditText etOpis = (EditText) dialog1.findViewById(R.id.etOpis);
								etOpis.setText(t.getOpis());
								EditText etIznos = (EditText) dialog1.findViewById(R.id.etIznos);
								etIznos.setText(t.getIznos().toString());
								Button btnSpremi = (Button) dialog1.findViewById(R.id.btnSpremi);
								btnSpremi.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {

										switch (tip) {
										case 0:
											unos(dialog1,2, t.getId());
											break;

										case 1:
											unos(dialog1,2, t.getId());
											break;
										}
										dialog1.dismiss();
									}
								});
						}

					});
			ad.setNegativeButton(c.getResources().getString(R.string.izbrisi),
					new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(
						android.content.DialogInterface dialog,int odabir) {
						new Delete().from(Transakcija.class).where("remote_id =?",tagPosition).execute();
						pregledSve(g_mjesec);
						
				}
			
			
			});
			ad.setNeutralButton(c.getResources().getString(R.string.podmiri),
					new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(
						android.content.DialogInterface dialog,int odabir) {
					Transakcija t = null;
					
					
					t = new  Select().all().from(Transakcija.class).where("remote_id =? AND(tip_id=2 OR tip_id=3)",tagPosition).executeSingle();
				
						if (t.getTip() == 2)
						{
							podmiriPotrazivanja(izbor, t.getId());
							
						}else 
						{
							podmiriDugovanja(izbor, t.getId());
						}
				
					
						pregledSve(g_mjesec);
						
				}
			});
			ad.show();
		}
	});
	}
		public void unos(Dialog dialog, int izbor, long id) {
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

			Transakcija transakcija = Transakcija.load(Transakcija.class, id);
			transakcija.naziv = naziv;
			transakcija.opis = opis;
			transakcija.iznos = iznos;
			transakcija.datum = datum;
			transakcija.save();
			pregledSve(g_mjesec);
		}
		
		public void podmiriPotrazivanja(int izbor, long id)
		{
			int tipPrihodi = 0;
				
			Transakcija transakcija = Transakcija.load(Transakcija.class, id);
			transakcija.tip_id = tipPrihodi;
			transakcija.save();
			
		}
		
		public void podmiriDugovanja(int izbor, long id)
		{
			int tipRashodi = 1;
				
			Transakcija transakcija = Transakcija.load(Transakcija.class, id);
			transakcija.tip_id = tipRashodi;
			transakcija.save();
			
		}
	}



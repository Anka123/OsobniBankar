package hr.foi.air.osobnibankar.adapters;

import hr.foi.air.osobnibankar.R;

import hr.foi.air.osobnibankar.database.Transakcija;

import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class TransakcijeAdapter extends ArrayAdapter<Transakcija> {
	public Context c;
	static String tagPosition;
	private List<Transakcija> transakcije;
	public static boolean tipTransakcije = false;
	public static int tip;
	
	public TransakcijeAdapter(Context c, int resource, List<Transakcija> transakcije) {
		super(c, resource, transakcije);
		this.c = c;
		this.transakcije = transakcije;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_transakcija, parent, false);

		Transakcija transakcija = transakcije.get(position);
		v.setTag(transakcija.getRemote_id());
		ImageView iv = (ImageView) v.findViewById(R.id.imageView1);

		if(transakcija.getTip()==0){
			iv.setImageResource(R.drawable.plus);
		}
		else if(transakcija.getTip()==1){
			iv.setImageResource(R.drawable.minus);
		}
		else if(transakcija.getTip()==2){
			iv.setImageResource(R.drawable.kvacica);
		}
		else if(transakcija.getTip()==3){
			iv.setImageResource(R.drawable.x);
		}
		
		
		
		TextView naziv = (TextView) v.findViewById(R.id.txtNaziv);
		naziv.setText(String.valueOf(transakcija.getNaziv()));
		TextView kategorija = (TextView) v.findViewById(R.id.txtKategorija);
		kategorija.setText(String.valueOf(transakcija.getKategorija()));
		TextView iznos = (TextView) v.findViewById(R.id.txtIznos);
		iznos.setText(transakcija.getIznos().toString());

		v.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {

				tagPosition = (v.getTag().toString());
				
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
									RadioGroup rg;
									if(tipTransakcije == true){
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
										rg = (RadioGroup) dialog1.findViewById(R.id.radioGroup3);
									}
									else{
										
										t = new  Select().all().from(Transakcija.class).where("remote_id =? AND(tip_id=0 OR tip_id=1)",tagPosition).executeSingle();
									
										dialog1.setContentView(R.layout.dodajpir);
										if(t.getTip()==0){
											RadioButton r = (RadioButton)dialog1.findViewById(R.id.rbPrihod);
											r.setChecked(true);
										}
										else{
											RadioButton r = (RadioButton)dialog1.findViewById(R.id.rbRashod);
											r.setChecked(false);
										}
										rg = (RadioGroup) dialog1.findViewById(R.id.radioGroup2);
									}
									tip = t.getTip();
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
												unos(dialog1,0,null,1, t.getId());
												break;

											case 1:
												unos(dialog1,1,null,1, t.getId());
												break;
											case 2:
												unos2(dialog1,2, t.getId());
												break;
											case 3:
												unos2(dialog1,3, t.getId());
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
					}
				});
		
				ad.show();
				return true;
			}
		});
		return v;
	}
	
	void unos(Dialog dialog, int unos, String danasnjiDatum, int mjesec, long id) {
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

	}
	
	public void unos2(Dialog dialog, int izbor, long id) {
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
	}
}



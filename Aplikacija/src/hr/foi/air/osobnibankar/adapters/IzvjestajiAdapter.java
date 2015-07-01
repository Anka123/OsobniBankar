package hr.foi.air.osobnibankar.adapters;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class IzvjestajiAdapter extends ArrayAdapter<Transakcija> {
	public Context c;
	static String tagPosition;
	private List<Transakcija> transakcije;
	public static boolean tipTransakcije = false;
	public static int tip;
	
	public IzvjestajiAdapter(Context c, int resource, List<Transakcija> transakcije) {
		super(c, resource, transakcije);
		this.c = c;
		this.transakcije = transakcije;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_izvjestaji, parent, false);

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
		TextView iznos = (TextView) v.findViewById(R.id.txtIznos);
		iznos.setText(transakcija.getIznos().toString());
		TextView datum = (TextView) v.findViewById(R.id.txtDatum);
		datum.setText(transakcija.getDatum().toString());
		TextView opis = (TextView) v.findViewById(R.id.txtOpis);
		opis.setText(transakcija.getIznos().toString());
		
		return v;
	}

}



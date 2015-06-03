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
		return v;
	}

}



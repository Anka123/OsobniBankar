package hr.foi.air.osobnibankar.adapters;

import java.util.List;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.database.Prihod;
import hr.foi.air.osobnibankar.database.Rashod;
import hr.foi.air.osobnibankar.database.Transakcija;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PrihodiAdapter extends ArrayAdapter<Transakcija> {
	public Context c;
	private List<Transakcija> transakcije;
	public enum tip {PRIHODI, RASHODI}

	public PrihodiAdapter(Context c, int resource, List<Transakcija> transakcije) {
		super(c, resource, transakcije);
		this.c = c;
		this.transakcije = transakcije;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_prihod, parent, false);

		Transakcija transakcija = transakcije.get(position);
		if (transakcija.getPrihod() != null) {
			Prihod prihod = transakcija.getPrihod();
			TextView naziv = (TextView) v.findViewById(R.id.txtNaziv);
			naziv.setText(String.valueOf(prihod.getNaziv()));
			TextView opis = (TextView) v.findViewById(R.id.txtOpis);
			opis.setText(String.valueOf(prihod.getOpis()));
			TextView iznos = (TextView) v.findViewById(R.id.txtIznos);
			iznos.setText(String.valueOf(prihod.getIznos()));

		} else if (transakcija.getRashod() != null) {
			Rashod rashod = transakcija.getRashod();
			TextView naziv = (TextView) v.findViewById(R.id.txtNaziv);
			naziv.setText(String.valueOf(rashod.getNaziv()));
			TextView opis = (TextView) v.findViewById(R.id.txtOpis);
			opis.setText(String.valueOf(rashod.getOpis()));
			TextView iznos = (TextView) v.findViewById(R.id.txtIznos);
			iznos.setText(String.valueOf(rashod.getIznos()));
		}

		return v;
	}
}

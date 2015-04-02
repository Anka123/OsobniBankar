package hr.foi.air.osobnibankar.adapters;

import java.util.List;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.database.Rashod;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RashodiAdapter extends ArrayAdapter<Rashod> {

	public Context c;
	private List<Rashod> vrijednosti;

	public RashodiAdapter(Context c, int resource, List<Rashod> vrijednosti) {
		super(c, resource, vrijednosti);
		this.c = c;
		this.vrijednosti = vrijednosti;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_rashod, parent, false);

		Rashod rashod = vrijednosti.get(position);
		TextView naziv = (TextView) v.findViewById(R.id.txtNaziv);
		naziv.setText(String.valueOf(rashod.getNaziv()));
		TextView iznos = (TextView) v.findViewById(R.id.txtIznos);
		iznos.setText(String.valueOf(rashod.getIznos()));

		return v;
	}
}

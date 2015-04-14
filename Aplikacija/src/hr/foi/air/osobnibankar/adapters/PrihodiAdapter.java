package hr.foi.air.osobnibankar.adapters;

import java.util.List;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.database.Prihod;
import hr.foi.air.osobnibankar.database.Rashod;
import hr.foi.air.osobnibankar.database.Tip;
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
		
		return v;
	}
}

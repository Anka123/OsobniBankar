package hr.foi.air.osobnibankar.adapters;

import java.util.List;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.db.Tecaj;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TecajeviAdapter extends ArrayAdapter<Tecaj>{

	private final Context context;
	private final List<Tecaj> values; 
	
	public TecajeviAdapter(Context context, int textViewResourceId, List<Tecaj> values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
	}
	
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate (R.layout.tecaj_entry, parent, false);
		
		Tecaj tecaj = values.get(position);
		TextView valuta = (TextView) v.findViewById(R.id.entry_tecaj_valuta);
		valuta.setText(String.valueOf(tecaj.getValuta()));
		TextView prodajni = (TextView) v.findViewById(R.id.entry_tecaj_prodajni);
		prodajni.setText(String.valueOf(tecaj.getProdajni()));
		TextView srednji = (TextView) v.findViewById(R.id.entry_tecaj_srednji);
		srednji.setText(String.valueOf(tecaj.getSrednji()));
		TextView kupovni = (TextView) v.findViewById(R.id.entry_tecaj_kupovni);
		kupovni.setText(String.valueOf(tecaj.getKupovni()));
		
		return v;
		
		
	}
}

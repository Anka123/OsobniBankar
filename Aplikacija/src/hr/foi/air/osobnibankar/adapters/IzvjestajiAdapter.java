package hr.foi.air.osobnibankar.adapters;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IzvjestajiAdapter extends BaseExpandableListAdapter {
	public Context c;
	static String tagPosition;
	public Activity activity;
	public LayoutInflater inflater;
	public List<Transakcija> transakcije;
	public static boolean tipTransakcije = false;
	public static int tip;

	public IzvjestajiAdapter(Context c, List<Transakcija> transakcije) {
		this.c = c;
		this.transakcije = transakcije;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.activity = activity;
		this.inflater = inflater;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return transakcije.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return transakcije.get(childPosition).remote_id;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = inflater.inflate(R.layout.detalj_izvjestaj, parent, false);

		Transakcija transakcija = transakcije.get(groupPosition);
		v.setTag(transakcija.getRemote_id());

		TextView datum = (TextView) v.findViewById(R.id.txtDatum);
		datum.setText(String.valueOf(transakcija.getDatum()));
		TextView opis = (TextView) v.findViewById(R.id.txtOpis);
		opis.setText(transakcija.getIznos().toString());
		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return transakcije.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return transakcije.get(groupPosition).remote_id;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = inflater.inflate(R.layout.item_izvjestaj, parent, false);

		Transakcija transakcija = transakcije.get(groupPosition);
		v.setTag(transakcija.getRemote_id());
		ImageView iv = (ImageView) v.findViewById(R.id.imageView1);

		if (transakcija.getTip() == 0) {
			iv.setImageResource(R.drawable.plus);
		} else if (transakcija.getTip() == 1) {
			iv.setImageResource(R.drawable.minus);
		} else if (transakcija.getTip() == 2) {
			iv.setImageResource(R.drawable.kvacica);
		} else if (transakcija.getTip() == 3) {
			iv.setImageResource(R.drawable.x);
		}

		TextView naziv = (TextView) v.findViewById(R.id.txtNaziv);
		naziv.setText(String.valueOf(transakcija.getNaziv()));
		TextView iznos = (TextView) v.findViewById(R.id.txtIznos);
		iznos.setText(transakcija.getIznos().toString());
		return v;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}

package hr.foi.air.osobnibankar.dodatno;

import com.activeandroid.query.Select;

import hr.foi.air.osobnibankar.R;
import hr.foi.air.osobnibankar.db.Profil;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Notifikacija {
	Dialog dialog;
	
	public void prikazNotifikacije(Context context, Double suma, final Double l) {

		Double sumaRashoda = suma;
		Double limit = l;
		final Context c = context;

		dialog = new Dialog(c);
		dialog.setContentView(R.layout.potrosnja);
		dialog.setTitle(R.string.prekoracena);
		dialog.show();
		
		TextView potrosnja = (TextView) dialog.findViewById(R.id.txtStanje);
		String sumaR = Double.valueOf(sumaRashoda).toString();
		potrosnja.setText(sumaR);
	
		TextView txtlimit = (TextView) dialog
				.findViewById(R.id.txtTrenutniLimit);
		String postavljeni = Double.valueOf(limit).toString();
		txtlimit.setText(postavljeni);
	
		Button ok = (Button) dialog.findViewById(R.id.btnOK);
	
		ok.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				dialog.dismiss();
	
			}
	
		});
	
		Button lim = (Button) dialog.findViewById(R.id.btnLimit);
	
		lim.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				dialog.dismiss();
				odrediLimit(c, l);
	
			}
	
		});
		
		
	}
	
	public void odrediLimit(Context c, final Double l) {
		dialog = new Dialog(c);
		dialog.setContentView(R.layout.odredilimit);
		dialog.setTitle(R.string.limit);
		dialog.show();

		
		Button btnOk = (Button) dialog.findViewById(R.id.btnSpremi);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Double limit = l;
				EditText etLimit = (EditText) dialog.findViewById(R.id.etLimit);
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

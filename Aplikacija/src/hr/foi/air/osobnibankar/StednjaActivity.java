package hr.foi.air.osobnibankar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.activeandroid.query.Select;

import hr.foi.air.osobnibankar.database.Transakcija;
import hr.foi.air.osobnibankar.dodatno.Datum;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class StednjaActivity extends Activity{
	Dialog dialog = null;
	Context c = this;
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat datum = new SimpleDateFormat("dd/mm/yyyy");
	final Datum d = new Datum();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stednje);
		
		
		izracunajStednju();
		
		ImageButton btnProizvoljna = (ImageButton)findViewById(R.id.imgProizvoljna);
		btnProizvoljna.setOnClickListener (new OnClickListener () {

			@Override
			public void onClick(View v) {
				
			
			dialog = new Dialog(c);
			dialog.setContentView(R.layout.proizvoljna);
			dialog.setTitle(R.string.proizvoljna);
			dialog.show();
			
			Button btnOk = (Button)dialog.findViewById(R.id.btnOK);
			btnOk.setOnClickListener(new OnClickListener (){

				@Override
				public void onClick(View v) {
					
					EditText etProizvoljna = (EditText)dialog.findViewById(R.id.etProizvoljna);
					Double iznosProizvoljne = 0.0;
					iznosProizvoljne = Double.valueOf(etProizvoljna.getText().toString());
					
					Transakcija prethodni = new Select().from(Transakcija.class)
							.orderBy("remote_id DESC").executeSingle();
					long trenutniId;
					try {
						trenutniId = prethodni.getRemote_id();
						trenutniId++;
					} catch (Exception e) {
						trenutniId = 0;
					}
					
					Date date = new Date(System.currentTimeMillis());
					@SuppressWarnings("deprecation")
					int mjesec = date.getMonth() + 1;
					String danasnjiDatum = datum.format(date);
					
					int izbor = 4;
					
					Transakcija transakcija = new Transakcija(trenutniId, null, null,
							iznosProizvoljne, false, null, null, danasnjiDatum, mjesec, izbor);
					transakcija.save();
					
					dialog.dismiss();
				}
				
				
			});
			}});
		
	}
	
	public void izracunajStednju() {
		
		double sumaStednje = 0;
		
		List<Transakcija> listaStednje = new Select().all()
				.from(Transakcija.class).where("tip_id=4")
				.execute();

		for (Transakcija transakcija : listaStednje) {
			
				sumaStednje += transakcija.getIznos();
			
		}
		
		TextView txtUstedeni = (TextView)findViewById(R.id.txtUstedeniIznos);
		String iznosStednje = Double.valueOf(sumaStednje).toString();
		
		txtUstedeni.setText(iznosStednje);
	}

}

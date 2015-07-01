package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.db.Transakcija;
import java.util.List;
import com.activeandroid.query.Select;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class GlavniIzbornikActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glavni_izbornik);
		izracunajTrenutni();
		
		ImageButton btnPiR = (ImageButton)findViewById(R.id.imgPiR);
		btnPiR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PrihodiRashodiActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnPiD = (ImageButton)findViewById(R.id.imgPiD);
		btnPiD.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PotrazivanjaDugovanjaActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnTecajevi =(ImageButton)findViewById(R.id.imgTecaj);
		btnTecajevi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), TecajActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnStednja =(ImageButton)findViewById(R.id.imgStednja);
		btnStednja.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),StednjaActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnGrafikoni = (ImageButton)findViewById(R.id.imgGrafikoni);
		btnGrafikoni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), GrafikoniActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnIzvjestaji = (ImageButton)findViewById(R.id.imgIzvjestaji);
		btnIzvjestaji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), IzvjestajiActivity.class);
				startActivity(i);
				
			}
		});
	}
	
	/**
	 * metoda koja izracunava trenutni iznos 
	 */
	public void izracunajTrenutni() {

		double sumaPrihoda = 0;
		double sumaRashoda = 0;
		List<Transakcija> listaTransakcija = new Select().all()
				.from(Transakcija.class).where("tip_id=0 OR tip_id=1")
				.execute();

		for (Transakcija transakcija : listaTransakcija) {
			if (transakcija.getTip() == 0) {
				sumaPrihoda += transakcija.getIznos();
			} else if (transakcija.getTip() == 1) {
				sumaRashoda += transakcija.getIznos();
			}
		}
	
		double sumaStednje = 0;

		List<Transakcija> listaStednje = new Select().all()
				.from(Transakcija.class).where("tip_id=4 OR tip_id=5")
				.execute();

		for (Transakcija transakcija : listaStednje) {

			sumaStednje += transakcija.getIznos();

		}

			double ukupno = sumaPrihoda - (sumaRashoda + sumaStednje);
			
			String iznos = getResources().getString(R.string.txtTrenutni);
			TextView tv = (TextView) findViewById(R.id.txtTrenutni);
			
			tv.setText(iznos + String.valueOf(ukupno));
	}
	
	@Override
	protected void onResume() {
		izracunajTrenutni();
		super.onResume();
	}
}

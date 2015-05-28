package hr.foi.air.osobnibankar;

import java.util.List;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.osobnibankar.core.Tecajevi;
import hr.foi.air.osobnibankar.db.Tecaj;
import hr.foi.air.osobnibankar.interfaces.ITecajevi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class TecajActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		Context c = this;
		setContentView(R.layout.tecaj);
		//NetworkInfo[] ni = null;
		//ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		ITecajevi tecajClass = new Tecajevi();
		List<Tecaj> listaTecajeva = tecajClass.dohvatiSveTecajeve();
		ListView listview = (ListView) findViewById(R.id.listview);
		TecajeviAdapter tecajeviAdapter = new TecajeviAdapter(this, R.layout.tecaj_entry, listaTecajeva);
		listview.setAdapter(tecajeviAdapter);
	}


}

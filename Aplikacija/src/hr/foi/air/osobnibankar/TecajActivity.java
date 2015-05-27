package hr.foi.air.osobnibankar;

import java.util.List;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.osobnibankar.core.Tecajevi;
import hr.foi.air.osobnibankar.db.Tecaj;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ListView;

public class TecajActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tecaj);
		
		//ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		Tecajevi tecajClass = new Tecajevi();
		List<Tecaj> listaTecajeva = tecajClass.dohvatiSveTecajeve();
		ListView listview = (ListView) findViewById(R.id.listview);
		TecajeviAdapter tecajeviAdapter = new TecajeviAdapter(this, R.layout.tecaj_entry, listaTecajeva);
		listview.setAdapter(tecajeviAdapter);
	}


}
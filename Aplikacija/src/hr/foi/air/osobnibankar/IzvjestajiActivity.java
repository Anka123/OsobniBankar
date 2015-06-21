package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TransakcijeAdapter;
import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Select;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;

public class IzvjestajiActivity extends Activity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.izvjestaji);
	
	final ListView lista = (ListView) findViewById(R.id.izvjestaj);
	
	final Spinner spin = (Spinner) findViewById(R.id.izbornik);
	spin.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String kategorija_odabir = spin.getSelectedItem().toString();
			List<Transakcija> trans = new Select().all().from(Transakcija.class).where("kategorija == ?", kategorija_odabir).execute();
			
			TransakcijeAdapter izvjestaj_adapter = new TransakcijeAdapter(getApplicationContext(), R.layout.item_transakcija, trans);
			lista.setAdapter(izvjestaj_adapter);
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});	
			
	
	
	
}
}

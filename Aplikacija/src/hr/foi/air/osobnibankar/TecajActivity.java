package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.tecajhnb.TecajHNB;
import hr.foi.air.tecajinterface.ITecaj;
import hr.foi.air.tecajinterface.Tecaj;
import ht.foi.air.tecajtxt.TecajeviTXT;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class TecajActivity extends Activity {
	Context c = this;
	Activity activity;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Context c = this;
		setContentView(R.layout.tecaj);
		NetworkInfo[] ni = null;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm != null) {
			ni = cm.getAllNetworkInfo();

			if (ni != null) {

				for (int i = 0; i < ni.length; i++)
					if (ni[i].getState() == NetworkInfo.State.CONNECTED) {
						
						ITecaj tecajHnb = new TecajHNB(); 
						List<Tecaj> tecaj = tecajHnb.dohvatiTecaj(c); 
						ispis(tecaj);
						 
						Toast.makeText(c, R.string.connected, Toast.LENGTH_LONG)
								.show();
					}

					else if (ni[i].getState() == NetworkInfo.State.DISCONNECTED) {
						ITecaj tecajTxt = new TecajeviTXT();
						List<Tecaj> tecajeviTxt = tecajTxt.dohvatiTecaj(c);
						ispis(tecajeviTxt);
						Toast.makeText(c, R.string.disconnected,
								Toast.LENGTH_LONG).show();
					}
			}

		}

	}

	public void ispis(List<Tecaj> tecajevi) {
		List<hr.foi.air.tecajinterface.Tecaj> lista = tecajevi;
		ListView listView = (ListView) findViewById(R.id.lvTecajevi);
		TecajeviAdapter lwAdapter = new TecajeviAdapter(c,
				R.layout.tecaj_entry, lista);
		listView.setAdapter(lwAdapter);

	}

}

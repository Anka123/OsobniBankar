package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.tecajhnb.TecajHNB;
import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;

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
	Context c =this;

	@SuppressLint("SimpleDateFormat") @Override
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
						ispisTecaja();
						Toast.makeText(c, R.string.connected, Toast.LENGTH_LONG)
								.show();
					}

					else if (ni[i].getState() == NetworkInfo.State.DISCONNECTED) {
						Toast.makeText(c, R.string.disconnected,
								Toast.LENGTH_LONG).show();
					}
			}

		}

	}

	private void ispisTecaja() {
		TecajHNB tecajHNB = new TecajHNB();
		String url = "http://hnbex.eu/api/v1/rates/daily/?date=";
		Object pTecajevi[] = new Object[]{ url, listaTecajeva};
		tecajHNB.execute(pTecajevi);
				
				

	}
	
	ResultHandler listaTecajeva = new ResultHandler() {
		
		@Override
		public void handleResult(List<Tecaj> tecajResult) {
			List<hr.foi.air.tecajinterface.Tecaj> lista = tecajResult;
			ListView listView = (ListView) findViewById(R.id.lvTecajevi);
			TecajeviAdapter lwAdapter = new TecajeviAdapter(c,
			R.layout.tecaj_entry, lista);
			listView.setAdapter(lwAdapter);
			
		}
	};
}

package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.osobnibankar.core.Tecajevi;
import hr.foi.air.osobnibankar.db.Tecaj;
import hr.foi.air.osobnibankar.interfaces.ITecaj;
import hr.foi.air.tecajhnb.TecajHNB;

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
		tecajHNB.execute(url);
		
		
		/*ITecaj t = new Tecajevi();
		
		List<Tecaj> listaTecajeva = t.dohvatiTecaj();
		ListView listView = (ListView) findViewById(R.id.lvTecajevi);
		TecajeviAdapter lwAdapter = new TecajeviAdapter(this,
				R.layout.tecaj_entry, listaTecajeva);
		listView.setAdapter(lwAdapter);
		*/

	}
}

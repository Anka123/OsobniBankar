package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.tecajhnb.TecajHNB;
import hr.foi.air.tecajinterface.ITecaj;
import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;
import ht.foi.air.tecajtxt.TecajeviTXT;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TecajActivity extends Activity {
	Context c = this;
	Activity activity;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Context c = this;
		setContentView(R.layout.tecaj);
		NetworkInfo ni = null;

		ResultHandler h = new ResultHandler() {

			@Override
			public void handleResult(List<Tecaj> rezultat) {
				List<Tecaj> tecaj = rezultat;
				ispis(tecaj);
			}
		};

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		dialog = new Dialog(c);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle(R.string.ucitavanjePodaci);
		dialog.show();

		ProgressBar pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
		pb.animate();
		TextView ucitavanje = (TextView) dialog.findViewById(R.id.ucitavanje);
		ucitavanje.setText(R.string.ucitavanje);
		
		if (cm != null) {
			ni = cm.getActiveNetworkInfo();

			if (ni != null) {
				if (ni.isConnectedOrConnecting()
						&& ((ni.getType() == ConnectivityManager.TYPE_MOBILE) | (ni
								.getType() == ConnectivityManager.TYPE_WIFI))) {

					ITecaj tecajHnb = new TecajHNB();
					tecajHnb.dohvatiTecaj(c, h);

					Toast.makeText(c, R.string.connected, Toast.LENGTH_LONG)
							.show();
					//dialog.dismiss();
				}

			} else {
				ITecaj tecajTxt = new TecajeviTXT();
				tecajTxt.dohvatiTecaj(c, h);
				Toast.makeText(c, R.string.disconnected, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	/**Metoda koja listi teèajeva postavlja adapter potreban za ispis liste na zaslon.
	 * @param tecajevi
	 */
	public void ispis(List<Tecaj> tecajevi) {
		dialog.dismiss();
		List<hr.foi.air.tecajinterface.Tecaj> lista = tecajevi;
		ListView listView = (ListView) findViewById(R.id.lvTecajevi);
		TecajeviAdapter lwAdapter = new TecajeviAdapter(c,
				R.layout.tecaj_entry, lista);
		listView.setAdapter(lwAdapter);

	}

}

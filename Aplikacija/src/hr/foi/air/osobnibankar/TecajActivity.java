package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.adapters.TecajeviAdapter;
import hr.foi.air.tecajhnb.TecajHNB;
import hr.foi.air.tecajinterface.ITecaj;
import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;
import hr.foi.air.tecajpbz.TecajPBZ;
import ht.foi.air.tecajtxt.TecajeviTXT;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TecajActivity extends Activity implements OnDismissListener {
	Context c = this;
	ResultHandler h;
	Activity activity;
	int izbor_banke;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tecaj);
		izbor_banke = 0;

		h = new ResultHandler() {

			@Override
			public void handleResult(List<Tecaj> rezultat) {
				List<Tecaj> tecaj = rezultat;
				ispis(tecaj);
			}
		};

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
		
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.izbor_tecaja, null);
        
		final AlertDialog alertDialog = alertDialogBuilder.create(); 
		alertDialog.setView(convertView);
		alertDialog.show();
		alertDialog.setOnDismissListener(this);
		
		RadioGroup rgPiD = (RadioGroup) alertDialog.findViewById(R.id.radioGrupBanke);
		rgPiD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			final int chkId = checkedId;
			Button btnSpremi = (Button) alertDialog.findViewById(R.id.button1OK);
			btnSpremi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					switch (chkId) {
					case R.id.radioButton1hnb:
						izbor_banke = 1;
						alertDialog.dismiss();
						break;

					case R.id.radioButton2pbz:
						izbor_banke = 2;
						alertDialog.dismiss();
						break;
					}
				}		
			});
		}
	});
	}
	
	public void dohvatiWebTecaj() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni;
		if (cm != null) {
			ni = cm.getActiveNetworkInfo();

			if (ni != null) {
				if (ni.isConnectedOrConnecting()
						&& ((ni.getType() == ConnectivityManager.TYPE_MOBILE) | (ni
								.getType() == ConnectivityManager.TYPE_WIFI))) {

					ITecaj tecajBanke = null;
					switch (izbor_banke) {
					case 1:
						tecajBanke = new TecajHNB();
						break;
					case 2:
						tecajBanke = new TecajPBZ();
						break;
					default:
						Toast.makeText(c, R.string.izbor_banke, Toast.LENGTH_LONG)
						.show();
						break;
					}
					
					if (tecajBanke != null) {
						
						tecajBanke.dohvatiTecaj(c, h);

						Toast.makeText(c, R.string.connected, Toast.LENGTH_LONG)
								.show();
	
					}
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
		//alertdialog.dismiss();
		List<hr.foi.air.tecajinterface.Tecaj> lista = tecajevi;
		ListView listView = (ListView) findViewById(R.id.lvTecajevi);
		TecajeviAdapter lwAdapter = new TecajeviAdapter(c,
				R.layout.tecaj_entry, lista);
		listView.setAdapter(lwAdapter);

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		dohvatiWebTecaj();
	}

}

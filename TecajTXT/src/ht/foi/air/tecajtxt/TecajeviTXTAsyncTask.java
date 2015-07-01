package ht.foi.air.tecajtxt;

import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

public class TecajeviTXTAsyncTask extends AsyncTask<Object, Void, Object[]> {

	protected Object[] doInBackground(Object... params) {
		Object rezultat[] = new Object[] { null, null };
		Context c = (Context) params[0];
		rezultat[0] = (ResultHandler) params[1];

		List<Tecaj> tecaji = callHNB(c);
		rezultat[1] = tecaji;

		return rezultat;
	}

	/**Metoda dohvaæa txt datoteku iz resursa te ju èita i sprema u rezultat tipa String. 
	 * Nakon toga se poziva metoda JsonTXT kako bi se iz String formata dobila lista teèaja.
	 * @param c
	 * @return tecaj
	 */
	public List<Tecaj> callHNB(Context c) {
		int resId = R.raw.txttecaj;
		InputStream is = c.getResources().openRawResource(resId);
		InputStreamReader ir = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ir);
		String line;

		StringBuilder sb = new StringBuilder();

		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = sb.toString();
		List<Tecaj> tecaj = null;

		try {
			JSONtxt jhnb = new JSONtxt();
			tecaj = jhnb.listaTecajeva(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tecaj;
	}

	@SuppressWarnings("unchecked")
	protected void onPostExecute(Object[] res) {
		if ((ResultHandler) res[0] != null) {
			((ResultHandler) res[0]).handleResult((List<Tecaj>) res[1]);
		}
	}

}

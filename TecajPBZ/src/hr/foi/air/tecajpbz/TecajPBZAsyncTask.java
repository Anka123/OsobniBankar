package hr.foi.air.tecajpbz;

import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class TecajPBZAsyncTask extends AsyncTask<Object, Void, Object[]> {

	@Override
	protected Object[] doInBackground(Object... params) {
		// TODO Auto-generated method stub
		Object rezultat[] = new Object[] { null, null };
		String url = (String) params[0];
		rezultat[0] = (ResultHandler) params[1];

		List<Tecaj> tecaji = callPBZ(url);
		rezultat[1] = tecaji;
		
		return rezultat;
	}
	public List<Tecaj> callPBZ(String url) {

		Calendar calendar = new GregorianCalendar();
		int date = calendar.get(Calendar.DATE);
		SimpleDateFormat datum = new SimpleDateFormat("yyyy-mm-dd",
				Locale.getDefault());
		String d = datum.format(date);

		ResponseHandler<String> handler = new BasicResponseHandler();
		HttpClient httpClient = new DefaultHttpClient();
		String link = url + d;
		HttpGet request = new HttpGet(link);
		String result = "";
		List<Tecaj> tecaj = null;
		
		try {
			result = httpClient.execute(request, handler);
			
			JsonPBZ jpbz = new JsonPBZ();
			tecaj = jpbz.listaTecajeva(result);
			httpClient.getConnectionManager().shutdown();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
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

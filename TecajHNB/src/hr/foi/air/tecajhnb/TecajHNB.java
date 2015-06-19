package hr.foi.air.tecajhnb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import hr.foi.air.tecajinterface.ITecaj;
import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class TecajHNB extends AsyncTask<Object, Void, Object[]> implements ITecaj{

	@Override
	protected Object[] doInBackground(Object... params) {
		Object rezultat[] = new Object[]{null,null};
		String url = (String) params[0];
		rezultat[0] = (ResultHandler) params[1];
		
		List<hr.foi.air.tecajinterface.Tecaj> result= new ArrayList<hr.foi.air.tecajinterface.Tecaj>();
		
		String webResult = callHNB(url);
		
		if (webResult !=null && webResult != "") {
			
			result = dohvatiTecaj(webResult);
			rezultat[1] = result;
		}
		
		
		return rezultat;
		}
		

	public String callHNB(String url) {
		
		Calendar calendar = new GregorianCalendar();
		int date = calendar.get(Calendar.DATE);
		SimpleDateFormat datum = new SimpleDateFormat("yyyy-mm-dd");
		String d = datum.format(date);		
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		HttpClient httpClient = new DefaultHttpClient();
		String link = url + d;
		HttpGet request = new HttpGet(link);
		
		try {
			String result = httpClient.execute(request, handler);
			httpClient.getConnectionManager().shutdown();
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

   @SuppressWarnings("unchecked")
protected void onPostExecute(Object[] result) {
	if (result[0]!=null) {
		((ResultHandler) result[0]).handleResult((List<Tecaj>) result[1]);
	}
}


@Override
public List<hr.foi.air.tecajinterface.Tecaj> dohvatiTecaj(String result) {
	// TODO Auto-generated method stub
	String webResult = result;
	JsonHNB jhnb = new JsonHNB();
	List<hr.foi.air.tecajinterface.Tecaj> tecajevi = jhnb.listaTecajeva(webResult);
	return tecajevi;		    	
}
    
}

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
import hr.foi.air.tecajhnb.Tecaj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TecajHNB extends AsyncTask<String, Void, List<hr.foi.air.tecajinterface.Tecaj>> implements ITecaj{

	@Override
	protected List<hr.foi.air.tecajinterface.Tecaj> doInBackground(String... urls) {
		String[] url = urls;
		
		List<hr.foi.air.tecajinterface.Tecaj> result= new ArrayList<hr.foi.air.tecajinterface.Tecaj>();
		
		String webResult = callHNB(url);
		
		if (webResult !=null && webResult != "") {
			dohvatiTecaj(webResult);
		}
		
		return null;
		}
		

	public String callHNB(String[] url) {
		
		Calendar calendar = new GregorianCalendar();
		int date = calendar.get(Calendar.DATE);
		SimpleDateFormat datum = new SimpleDateFormat("yyyy-mm-dd");
		String d = datum.format(date);		
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		HttpClient httpClient = new DefaultHttpClient();
		List<TecajHNB> tecajevi = new ArrayList<TecajHNB>();
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

   protected void onPostExecute(Object[] result) {
	
}


@Override
public List<hr.foi.air.tecajinterface.Tecaj> dohvatiTecaj(String result) {
	// TODO Auto-generated method stub
	String webResult = result;
	List<hr.foi.air.tecajinterface.Tecaj> tecajevi = new ArrayList<hr.foi.air.tecajinterface.Tecaj>();
	try{
		JSONArray jsonArray = new JSONArray(webResult);
		   
	    for(int i=0;i<jsonArray.length();i++){
	     JSONObject jsonObject = jsonArray.getJSONObject(i);
	     
	     String naziv = jsonObject.getString("currency_code");
	     String prodajniTecaj = jsonObject.getString("selling_rate");
	     String srednjiTecaj = jsonObject.getString("median_rate");
	     String kupovniTecaj = jsonObject.getString("buying_rate");
	    
	     hr.foi.air.tecajinterface.Tecaj tecaj = new hr.foi.air.tecajinterface.Tecaj(naziv, kupovniTecaj, srednjiTecaj, prodajniTecaj);
	     tecajevi.add(tecaj);
	    }
	    return tecajevi;
		}catch (Exception e){
	    	e.printStackTrace();
	    }
	return tecajevi;		    	
}
    
}

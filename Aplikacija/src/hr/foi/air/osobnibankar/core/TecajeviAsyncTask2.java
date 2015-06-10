package hr.foi.air.osobnibankar.core;

import hr.foi.air.osobnibankar.db.Tecaj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class TecajeviAsyncTask2 extends AsyncTask<String, Void, List<Tecaj>>{

	@Override
	protected List<Tecaj> doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		HttpClient httpClient = new DefaultHttpClient();
		List<Tecaj> tecajevi = new ArrayList<Tecaj>();
		String link = "https://konet.kovanica.hr/konet/tecajna";
		HttpGet request = new HttpGet(link);
		
		try {
			String result = httpClient.execute(request,handler);
			httpClient.getConnectionManager().shutdown();
			
			 if (result !=null && result != "") {
				 JSONObject jsonobject = new JSONObject(result);
				 JSONObject j2 = jsonobject.getJSONObject("ExchRates");
				 JSONObject j3 = j2.getJSONObject("ExchRate");
				 
				 	JSONArray jCurrency = j3.getJSONArray("Currency");
				   
				    for(int i=0;i<jCurrency.length();i++){
				     JSONObject jsonObject = jCurrency.getJSONObject(i);
				     
				     String naziv = jsonObject.getString("-Valuta");
				     String prodajniTecaj = jsonObject.getString("-ProdajniEfektiva");
				     String srednjiTecaj = jsonObject.getString("-Srednji");
				     String kupovniTecaj = jsonObject.getString("-KupovniEfektiva");
				    		 
				     Tecaj noviTecaj = new Tecaj(naziv, prodajniTecaj, srednjiTecaj, kupovniTecaj);
				     tecajevi.add(noviTecaj);
				}
				return tecajevi;
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("Error");
		e.printStackTrace();
	}
		
		
		return null;
	}

}

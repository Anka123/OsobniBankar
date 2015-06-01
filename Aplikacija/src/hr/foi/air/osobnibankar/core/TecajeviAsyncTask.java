package hr.foi.air.osobnibankar.core;

import hr.foi.air.osobnibankar.db.Tecaj;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class TecajeviAsyncTask extends AsyncTask<String, Void, List<Tecaj>>{
	

	@Override
	protected List<Tecaj> doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		ResponseHandler<String> handler = new BasicResponseHandler();
		HttpClient httpClient = new DefaultHttpClient();
		List<Tecaj> tecajevi = new ArrayList<Tecaj>();
		String link = "http://hnbex.eu/api/v1/rates/daily/";
		HttpGet request = new HttpGet(link);
		
		try {
			 String result = httpClient.execute(request,handler);
			 httpClient.getConnectionManager().shutdown();
			 
			 if (result !=null && result != "") {
				 	JSONObject jsonObject = new JSONObject(result);
				    JSONObject jsonObject2 =jsonObject.getJSONObject("currency");
				   
				    for(int i=0;i<jsonObject2.length();i++){
				     JSONObject currency = jsonObject2.getJSONObject(String.valueOf(i+1));
				     
				     String naziv = currency.getString("name");
				     String prodajniTecaj = currency.getString("buyRateForeign");
				     String srednjiTecaj = currency.getString("meanRate");
				     String kupovniTecaj = currency.getString("sellRateForeign");
				     
				     Tecaj noviTecaj = new Tecaj(naziv, kupovniTecaj, srednjiTecaj, prodajniTecaj);
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

package hr.foi.air.osobnibankar.core;

import hr.foi.air.osobnibankar.db.Tecaj;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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
				 	JSONArray jsonArray = new JSONArray(result);
				   
				    for(int i=0;i<jsonArray.length();i++){
				     JSONObject jsonObject = jsonArray.getJSONObject(i);
				     
				     String naziv = jsonObject.getString("currency_code");
				     String prodajniTecaj = jsonObject.getString("selling_rate");
				     String srednjiTecaj = jsonObject.getString("median_rate");
				     String kupovniTecaj = jsonObject.getString("buying_rate");
				    		 
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

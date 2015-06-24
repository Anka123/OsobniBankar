package hr.foi.air.tecajhnb;

import hr.foi.air.tecajinterface.Tecaj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHNB {
	public List<Tecaj> listaTecajeva(String Jsonresult){
		String webResult = Jsonresult;
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
		    
			}catch (Exception e){
		    	e.printStackTrace();
		    }
		
		return tecajevi;
		
	}
}

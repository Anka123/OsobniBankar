package hr.foi.air.tecajpbz;

import hr.foi.air.tecajinterface.Tecaj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonPBZ {

    public List<Tecaj> listaTecajeva(String Jsonresult){
    	
    	String webResult = Jsonresult;
		List<hr.foi.air.tecajinterface.Tecaj> tecajevi = new ArrayList<hr.foi.air.tecajinterface.Tecaj>();
		
		try {
			
			JSONObject jsonObject = new JSONObject(webResult);
			JSONObject jsonObject2 = jsonObject.getJSONObject("currency");

			
			for(int i=0;i<jsonObject.length();i++){
				
			    
				JSONObject currency = jsonObject2.getJSONObject(String.valueOf(i + 1));
				String naziv = currency.getString("name");
				String prodajniTecaj = currency.getString("sellRateForeign");
				String srednjiTecaj = currency.getString("meanRate");
				String kupovniTecaj = currency.getString("buyRateForeign");

				 hr.foi.air.tecajinterface.Tecaj tecaj = new hr.foi.air.tecajinterface.Tecaj(naziv, prodajniTecaj,srednjiTecaj, kupovniTecaj);
			     tecajevi.add(tecaj);			    
			}	     
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return tecajevi;
}
}

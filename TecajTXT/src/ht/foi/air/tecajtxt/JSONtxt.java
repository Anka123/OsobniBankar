package ht.foi.air.tecajtxt;

import hr.foi.air.tecajinterface.Tecaj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class JSONtxt {
	/**Metoda koja rezultat zapisan u String formatu pretvara u listu, odnosno separira json objekte i polja, 
	 * kako bi se dobio jedan zapis i kao takav se pohranio u listu.
	 * @param Jsonresult
	 * @return tecajevi
	 */
	public List<Tecaj> listaTecajeva(String Jsonresult) {
		String podaci = Jsonresult;

		List<Tecaj> tecajevi = new ArrayList<Tecaj>();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(podaci);
			JSONObject jsonObject2 = jsonObject.getJSONObject("currency");
			for (int i = 0; i < jsonObject2.length(); i++) {
				JSONObject currency = jsonObject2.getJSONObject(String
						.valueOf(i + 1));
				String naziv = currency.getString("name");
				String prodajniTecaj = currency.getString("sellRate");
				String srednjiTecaj = currency.getString("meanRate");
				String kupovniTecaj = currency.getString("buyRate");

				Tecaj noviTecaj = new Tecaj(naziv, prodajniTecaj, srednjiTecaj,
						kupovniTecaj);
				tecajevi.add(noviTecaj);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return tecajevi;
	}
}

package ht.foi.air.tecajtxt;

import hr.foi.air.tecajinterface.ITecaj;
import hr.foi.air.tecajinterface.Tecaj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class TecajeviTXT implements ITecaj {

	public void citajDatoteku(Context ctx, int resId){
		
		InputStream is = ctx.getResources().openRawResource(resId);
		InputStreamReader ir = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ir);
		String line;
		
		StringBuilder sb = new StringBuilder();
		
		try {
			while ((line = br.readLine()) != null)
					{
					sb.append(line);
					sb.append('\n');
					}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = sb.toString();
		dohvatiTecaj(result);
		
		
	}

	@Override
	public List<Tecaj> dohvatiTecaj(String webResult) {
		
		String podaci = webResult;
		
		List<Tecaj> tecajevi = new ArrayList<Tecaj>();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(podaci);
			JSONObject jsonObject2 = jsonObject.getJSONObject("currency");
			for (int i = 0; i < jsonObject2.length(); i++) {
				JSONObject currency = jsonObject2.getJSONObject(String.valueOf(i+1));
				String naziv = currency.getString("name");
				String prodajniTecaj = currency.getString("sellRate");
				String srednjiTecaj = currency.getString("meanRate");
				String kupovniTecaj = currency.getString("buyRate");
				
				Tecaj noviTecaj = new Tecaj(naziv, prodajniTecaj,srednjiTecaj, kupovniTecaj);
				tecajevi.add(noviTecaj);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return tecajevi;
	}
    
    
}

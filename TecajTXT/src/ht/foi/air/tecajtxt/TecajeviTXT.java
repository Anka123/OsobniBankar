package ht.foi.air.tecajtxt;

import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;

public class TecajeviTXT implements hr.foi.air.tecajinterface.ITecaj {
	
	@Override
	public List<Tecaj> dohvatiTecaj(Context c) {
		Context ctx = c;
		int resId = R.raw.txttecaj;
		Object rezultat[] = new Object[]{null, ""};
		rezultat[0] = (ResultHandler) result;

		InputStream is = ctx.getResources().openRawResource(resId);
		InputStreamReader ir = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ir);
		String line;

		StringBuilder sb = new StringBuilder();

		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = sb.toString();
		rezultat[1] = result;
		List<Tecaj> tecajevi = null;
		
		if (rezultat[0] != null) {
			
			tecajevi = ((ResultHandler) rezultat[0]).handleResult((String) rezultat[1]);
		}
		
		return tecajevi;
		

	}
	
	ResultHandler result = new ResultHandler() {
		
		@Override
		public List<Tecaj> handleResult(String rezultat) {
			String result = rezultat;
			JSONtxt jtxt = new JSONtxt();
			List<Tecaj> tecajevi = jtxt.listaTecajeva(result);
			return tecajevi;
		}
	};

}

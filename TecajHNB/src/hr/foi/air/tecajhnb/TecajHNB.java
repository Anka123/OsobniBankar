package hr.foi.air.tecajhnb;

import hr.foi.air.tecajinterface.ResultHandler;
import hr.foi.air.tecajinterface.Tecaj;

import java.util.List;

import com.activeandroid.query.Select;

import android.content.Context;

public class TecajHNB implements hr.foi.air.tecajinterface.ITecaj {
	List<Tecaj> tecaj;
	@Override
	public List<Tecaj> dohvatiTecaj(Context c) {
		
		ResultHandler listaTecajeva = new ResultHandler() {
			
			@Override
			public List<Tecaj> handleResult(String rezultat) {
				String result = rezultat;
				JsonHNB jhnb = new JsonHNB();
				tecaj = jhnb.listaTecajeva(result);
				for (Tecaj t : tecaj)
					t.save();
				return tecaj;
				
			}
		};
		
		TecajHNBAsyncTask tecajHnb = new TecajHNBAsyncTask();
		String url = "http://hnbex.eu/api/v1/rates/daily/?date=";
		Object pTecajevi[] = new Object[] { url, listaTecajeva };
		tecajHnb.execute(pTecajevi);
		List<Tecaj> tecaj = new Select().all().from(Tecaj.class).execute();
		return tecaj;

	}
	
}

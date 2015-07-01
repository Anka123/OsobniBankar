package hr.foi.air.tecajhnb;

import hr.foi.air.tecajinterface.ResultHandler;
import android.content.Context;

public class TecajHNB implements hr.foi.air.tecajinterface.ITecaj {
	
	@Override
	public void dohvatiTecaj(Context c, ResultHandler h) {
		TecajHNBAsyncTask tecajHnb = new TecajHNBAsyncTask();
		String url = "http://hnbex.eu/api/v1/rates/daily/?date=";
		Object pTecajevi[] = new Object[] { url, h };
		tecajHnb.execute(pTecajevi);
	}

}

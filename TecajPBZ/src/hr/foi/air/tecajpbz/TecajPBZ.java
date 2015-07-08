package hr.foi.air.tecajpbz;

import hr.foi.air.tecajinterface.ResultHandler;
import android.content.Context;

public class TecajPBZ implements hr.foi.air.tecajinterface.ITecaj{

	@Override
	public void dohvatiTecaj(Context c, ResultHandler h) {
		TecajPBZAsyncTask tecajPbz = new TecajPBZAsyncTask();
		String url = "http://tecajnalista.net/service/getJSON/a/www.tecajnalista.net/e7f41cefebc2957f658f2c83ba4bf48e/";
		Object pTecajevi[] = new Object[] { url, h };
		tecajPbz.execute(pTecajevi);
		
	}

}

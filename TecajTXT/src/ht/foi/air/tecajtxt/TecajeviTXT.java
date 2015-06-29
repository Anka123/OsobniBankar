package ht.foi.air.tecajtxt;

import hr.foi.air.tecajinterface.ResultHandler;
import android.content.Context;

public class TecajeviTXT implements hr.foi.air.tecajinterface.ITecaj {

	@Override
	public void dohvatiTecaj(Context c, ResultHandler h) {
		TecajeviTXTAsyncTask tecajTxt = new TecajeviTXTAsyncTask();
		Context context = c;
		Object pTecajevi[] = new Object[] { context, h };
		tecajTxt.execute(pTecajevi);

	}
}

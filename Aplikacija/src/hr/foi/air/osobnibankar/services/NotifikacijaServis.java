package hr.foi.air.osobnibankar.services;
import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.Calendar;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.activeandroid.query.Select;

public class NotifikacijaServis extends Service {
	Context c = this;
	Calendar calendar = Calendar.getInstance();
	Notifikacija notifikacija;

	public class NotifikacijaBinder extends Binder {
		NotifikacijaServis getService() {
		return NotifikacijaServis.this;
		}
	}

	@Override
	public void onCreate() {

		List<Transakcija> pid = new Select().all().from(Transakcija.class)
				.where("tip_id=2 OR tip_id=3").execute();

		for (int d = 0; d < pid.size(); d++) {

			String datum = pid.get(d).datum;
			String[] dohvatiDan = datum.split("\\.");
			String dan = dohvatiDan[0];
			int dani = Integer.parseInt(dan);

			String[] dohvatiMjesec = datum.split("\\.");
			String mjeseci = dohvatiMjesec[1];
			int mjesec = Integer.parseInt(mjeseci);

			String[] dohvatiGodinu = datum.split("\\.");
			String godina = dohvatiGodinu[2];
			int godine = Integer.parseInt(godina);

			int trenutniDan = calendar.get(Calendar.DAY_OF_MONTH);
			int trenutniMjesec = calendar.get(Calendar.MONTH) + 1;
			int trenutnaGodina = calendar.get(Calendar.YEAR);

			if (dani == trenutniDan && mjesec == trenutniMjesec
					&& godine == trenutnaGodina) {
				notifikacija.notifikacija();
			}
		}
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}

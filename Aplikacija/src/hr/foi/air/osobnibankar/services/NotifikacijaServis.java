package hr.foi.air.osobnibankar.services;

import hr.foi.air.osobnibankar.PotrazivanjaDugovanjaActivity;
import hr.foi.air.osobnibankar.db.Transakcija;

import java.util.List;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.activeandroid.query.Select;

public class NotifikacijaServis extends Service {
	Context c = this;
	List<Transakcija> pid = new Select().all().from(Transakcija.class)
			.where("tip_id=3 OR tip_id=2 OR tip_id").execute();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		Toast.makeText(c, "onCreate", Toast.LENGTH_SHORT).show();
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		NotificationCompat.Builder notif = new NotificationCompat.Builder(c);
		notif.setSmallIcon(hr.foi.air.osobnibankar.R.drawable.minus);
		notif.setContentTitle("Dospjece obveze!");
		notif.setContentText("Imate obvezu koju je potrebno podmiriti!");
		int notifid = 001;
		NotificationManager notifMan = (NotificationManager) c
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent i = new Intent(getApplicationContext(),
				PotrazivanjaDugovanjaActivity.class);

		PendingIntent pending = PendingIntent.getActivity(this, 0, i, 0);

		notif.setContentIntent(pending);

		notifMan.notify(notifid, notif.build());
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}

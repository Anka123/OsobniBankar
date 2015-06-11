package hr.foi.air.osobnibankar;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotifikacijaActivity extends Activity {

	Context c = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		NotificationCompat.Builder notif = new NotificationCompat.Builder(this);
		notif.setSmallIcon(R.drawable.minus);
		notif.setContentTitle("Dospjeæe obveze!");
		notif.setContentText("Imate obvezu koju je potrebno podmiriti!");
		int notifid = 001;
		NotificationManager notifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notifMan.notify(notifid, notif.build());

	}

}

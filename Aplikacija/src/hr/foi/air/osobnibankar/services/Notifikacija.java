package hr.foi.air.osobnibankar.services;

import hr.foi.air.osobnibankar.R;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class Notifikacija {
	Context c;

	public void notifikacija   (){
		
		NotificationCompat.Builder notif = new NotificationCompat.Builder(c);
		notif.setSmallIcon(R.drawable.minus);
		notif.setContentTitle("Dospjeæe obveze!");
		notif.setContentText("Imate obvezu koju je potrebno podmiriti!");
		int notifid = 001;
		NotificationManager notifMan = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
		notifMan.notify(notifid, notif.build());
	}
}

package hr.foi.air.osobnibankar.services;

import hr.foi.air.osobnibankar.GlavniIzbornikActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class Notifikacija {
	Context c;

	public void notifikacija   (){
		
		NotificationCompat.Builder notif = new NotificationCompat.Builder(c);
		notif.setSmallIcon(hr.foi.air.osobnibankar.R.drawable.minus);
		notif.setContentTitle("Dospjeæe obveze!");
		notif.setContentText("Imate obvezu koju je potrebno podmiriti!");
		int notifid = 001;
		NotificationManager notifMan = (NotificationManager) c
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent i = new Intent(c, GlavniIzbornikActivity.class);
		
		PendingIntent pending = PendingIntent.getActivity(c,0,i,0);
		
		notif.setContentIntent(pending);
		
		notifMan.notify(notifid, notif.build());
	}
}

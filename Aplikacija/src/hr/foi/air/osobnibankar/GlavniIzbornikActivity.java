package hr.foi.air.osobnibankar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class GlavniIzbornikActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glavni_izbornik);
		
		ImageButton btnPiR = (ImageButton)findViewById(R.id.imgPiR);
		btnPiR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PrihodiRashodiActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnPiD = (ImageButton)findViewById(R.id.imgPiD);
		btnPiD.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PotrazivanjaDugovanjaActivity.class);
				startActivity(i);
				
			}
		});
		
		ImageButton btnTecajevi =(ImageButton)findViewById(R.id.imgTecaj);
		btnTecajevi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton btnStednja =(ImageButton)findViewById(R.id.imgStednja);
		btnStednja.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton btnGrafikoni = (ImageButton)findViewById(R.id.imgGrafikoni);
		btnGrafikoni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton btnIzvjestaji = (ImageButton)findViewById(R.id.imgIzvjestaji);
		btnIzvjestaji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

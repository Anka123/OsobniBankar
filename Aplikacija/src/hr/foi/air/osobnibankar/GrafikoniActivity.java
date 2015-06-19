package hr.foi.air.osobnibankar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class GrafikoniActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grafovi);

	ImageButton imgBar = (ImageButton)findViewById(R.id.btnBar);
	imgBar.setOnClickListener(new OnClickListener (){

		@Override
		public void onClick(View v) {

			Intent i = new Intent(getApplicationContext(),BarGrafActivity.class);
			startActivity(i);
		}
		
		
	});

	ImageButton imgLine = (ImageButton)findViewById(R.id.btnLine);
	
	imgLine.setOnClickListener(new OnClickListener (){

		@Override
		public void onClick(View v) {

			Intent i = new Intent(getApplicationContext(),LineGrafActivity.class);
			startActivity(i);
			
		}
		
		
	});

ImageButton imgPieRashodi = (ImageButton)findViewById(R.id.btnPieRashodi);
	
	imgPieRashodi.setOnClickListener(new OnClickListener (){

		@Override
		public void onClick(View v) {

			Intent i = new Intent(getApplicationContext(),PieGrafActivity.class);
			startActivity(i);
			
		}
		
		
	});
	
ImageButton imgPiePrihodi = (ImageButton)findViewById(R.id.btnPiePrihodi);
	
	imgPiePrihodi.setOnClickListener(new OnClickListener (){

		@Override
		public void onClick(View v) {

			Intent i = new Intent(getApplicationContext(),PieGrafActivity.class);
			startActivity(i);
			
		}
		
		
	});
}}

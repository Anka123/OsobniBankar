package hr.foi.air.osobnibankar;

import hr.foi.air.osobnibankar.db.Transakcija;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.activeandroid.query.Select;

public class KontinuiranaActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.kontinuirana);
            
           NumberPicker nmbMjesec= (NumberPicker)findViewById(R.id.nmbMjesec);
           nmbMjesec.setMaxValue(100);
           nmbMjesec.setMinValue(1);
            
           izracunajStednju();
          //EditText etKontinuirana = (EditText)findViewById(R.id.etIznosKontinuirane);
          //int cilj = Integer.valueOf(etKontinuirana.getText().toString());
          
          //ProgressBar progCilj = (ProgressBar)findViewById(R.id.progCilj);
          //progCilj.setMax(cilj);
          
    }
	
	private void izracunajStednju () {
		
		ToggleButton togCilj = (ToggleButton)findViewById(R.id.togZapocni);
		togCilj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					
					zapocniStednju();
				}
				else{
					
					Toast.makeText(getApplicationContext(), "Zaustavi", Toast.LENGTH_SHORT).show();
				}
				
			}
			
			
		});
		
	}
	
	private void zapocniStednju() {
		
		NumberPicker nmbMjesec= (NumberPicker)findViewById(R.id.nmbMjesec);
		int mjesec = nmbMjesec.getValue();
		
		EditText iznosKontinuirane =(EditText)findViewById(R.id.etIznosKontinuirane);
		double iznos = Double.valueOf(iznosKontinuirane.getText().toString());
		
		int izbor = 5;
			
		Transakcija prethodni = new Select().from(Transakcija.class)
				.orderBy("remote_id DESC").executeSingle();
		long trenutniId;
		try {
			trenutniId = prethodni.getRemote_id();
			trenutniId++;
		} catch (Exception e) {
			trenutniId = 0;
		}
		
		Transakcija kontinuirana = new Transakcija (trenutniId, null, null,
				iznos, false, null, null, null, mjesec, izbor);
		kontinuirana.save();
		
	}

}

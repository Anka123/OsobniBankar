package hr.foi.air.osobnibankar;
 
import hr.foi.air.osobnibankar.database.Registracija;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.activeandroid.query.Select;
 
public class LoginActivity extends Activity{
       
       
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.login);
               
                login();
                
        }
       
 
        private void login() {
               
                ImageButton btnLogin = (ImageButton) findViewById(R.id.btnLogin);
               
                btnLogin.setOnClickListener(new OnClickListener() {
                                                            
                        @Override
                        public void onClick(View v) {
                                EditText etLozinka1 = (EditText) findViewById(R.id.etLozinka1);
                                final String lozinka1 = etLozinka1.getText().toString();
                                if(provjeraLogina(lozinka1))
                                {
                                       Toast.makeText(getApplicationContext(), "Prijava uspješna!", Toast.LENGTH_SHORT).show();
                                       Intent i = new Intent(getApplicationContext(), GlavniIzbornikActivity.class);
                                       startActivity(i);
                                }
                                else
                                {
                                        Toast.makeText(getApplicationContext(), "Prijava neuspješna!", Toast.LENGTH_SHORT).show();                                            
                                }
                                                  
                        }
                });
}
		private boolean provjeraLogina(String lozinka1){ 
               List<Registracija> registracija;
               registracija = new Select().all().from(Registracija.class).execute();
               for(int i = 0 ;i< registracija.size();i++)
               {
                   if((registracija.get(i).lozinka).equals(lozinka1))
                   {
                    return true;                   
                   }
               }
               return false;
               }
       
}


package hr.foi.air.osobnibankar;
 
import hr.foi.air.osobnibankar.database.Registracija;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
 
public class LoginActivity extends Activity{
       
	Dialog dialog = null;
	final Context context = this;
     
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.login);
        		
        		Context c = this;
        		ActiveAndroid.initialize(c);	
               
                login();
                
        }
       
 
        private void login() {
               
                ImageButton btnLogin = (ImageButton) findViewById(R.id.imgBtnPrijava);
               
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
                
                ImageButton btnRegistracija = (ImageButton) findViewById(R.id.imgBtnRegistracija);
                
                btnRegistracija.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i = new Intent(getApplicationContext(),RegistracijaActivity.class);
						startActivity(i);
						
					}
				});
                
                TextView txtZaboravljena = (TextView) findViewById(R.id.txtZaboravljena);
                txtZaboravljena.setOnClickListener(new OnClickListener() {
                
					@Override
					public void onClick(View v) {
							dialog = new Dialog(context);
							dialog.setContentView(R.layout.zaboravljena);
							dialog.setTitle(R.string.zabLozinka);
							dialog.show();
						
							
							String p = pitanje();
							TextView txtPitanje =(TextView)dialog.findViewById(R.id.txtPitanje);
							txtPitanje.setText(p);
							
							
							
							Button btnProvjeri = (Button)dialog.findViewById(R.id.btnProvjeri);
							btnProvjeri.setOnClickListener(new OnClickListener() {
							
								@Override
								public void onClick(View v) {
									EditText etodg = (EditText)dialog.findViewById(R.id.etodg);
									final String odg = etodg.getText().toString();
									
									if(provjeraOdgovora(odg))
		                            {
		                                   Toast.makeText(getApplicationContext(), "Odgovor tocan!", Toast.LENGTH_SHORT).show();
		                                   Intent i = new Intent(getApplicationContext(), GlavniIzbornikActivity.class);
		                                   startActivity(i);
		                            }
		                            else
		                            {
		                                    Toast.makeText(getApplicationContext(), "Nije dobro!", Toast.LENGTH_SHORT).show();                                            
		                            }     
								}
							});                                    							
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
		
		private boolean provjeraOdgovora (String odg){
			List<Registracija> registracija;
			registracija = new Select().all().from(Registracija.class).execute();
			for (int i = 0; i<registracija.size(); i++)
			{
				if(registracija.get(i).odgovor.equals(odg))
				{
					return true;
				}
			}
			return false;			
		}
		
		private String pitanje (){
			String a = "";
			List<Registracija> registracija;
			registracija = new Select().all().from(Registracija.class).execute();
			for (int i = 0; i<registracija.size(); i++)
			{
				a = registracija.get(i).pitanje;

			}
			return a;
					
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
       
}




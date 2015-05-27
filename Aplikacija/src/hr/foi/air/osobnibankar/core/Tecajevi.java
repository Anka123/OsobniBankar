package hr.foi.air.osobnibankar.core;

import hr.foi.air.osobnibankar.db.Tecaj;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.R.string;

public class Tecajevi {
	
		public int dohvatiBrojTecaja(){
			return 0;					
		}
		public Tecaj dohvatiTecaj(string imeTecaja){
			return null;						
		}
		
		public List<Tecaj> dohvatiSveTecajeve()
		{
			TecajeviAsyncTask tecajevi = new TecajeviAsyncTask();
			tecajevi.execute();
			
			try {
				return tecajevi.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;		
		}
}

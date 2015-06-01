package hr.foi.air.osobnibankar.core;

import java.util.List;
import java.util.concurrent.ExecutionException;

import hr.foi.air.osobnibankar.db.Tecaj;



public class Tecajevi {

	
public List<Tecaj> dohvatiTecajeve() {
	
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


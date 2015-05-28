package hr.foi.air.osobnibankar.core;

import java.util.List;

import hr.foi.air.osobnibankar.db.Tecaj;
import hr.foi.air.osobnibankar.interfaces.ITecajevi;


public class Tecajevi implements ITecajevi {

	@Override
	public int dohvatiBrojTecaja() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tecaj dohvatiTecaj(String imeTecaja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tecaj> dohvatiSveTecajeve() {
		// TODO Auto-generated method stub
		TecajeviAsyncTask tecajevi = new TecajeviAsyncTask();
		tecajevi.execute();
		
		try {
			return tecajevi.get();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
		

		
}


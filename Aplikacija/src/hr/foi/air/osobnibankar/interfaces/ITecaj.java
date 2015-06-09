package hr.foi.air.osobnibankar.interfaces;

import hr.foi.air.osobnibankar.db.Tecaj;

import java.util.List;

public interface ITecaj {

	List<Tecaj> tecajevi = null;
	
	public List<Tecaj> dohvatiTecaj();
	
}

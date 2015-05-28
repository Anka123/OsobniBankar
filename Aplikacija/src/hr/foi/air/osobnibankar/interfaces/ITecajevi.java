package hr.foi.air.osobnibankar.interfaces;

import hr.foi.air.osobnibankar.db.Tecaj;

import java.util.List;


public interface ITecajevi {
	List <Tecaj> tecajevi = null;
	
	public int dohvatiBrojTecaja();
	
	public Tecaj dohvatiTecaj(String imeTecaja);
	
	public List <Tecaj> dohvatiSveTecajeve();

	

}

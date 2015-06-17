package hr.foi.air.tecajinterface;

import java.util.List;

public interface ITecaj {

	List<Tecaj> tecajevi = null;
	
	public List<Tecaj> dohvatiTecaj(String webResult);
	
}

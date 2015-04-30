package hr.foi.air.osobnibankar.interfaces;

import hr.foi.air.osobnibankar.database.Transakcija;

import java.util.List;

public interface ITransakcija {
	List<Transakcija> transakcije = null;

	public List<Transakcija> dohvatiTransakcije(int grupa, int mjesec);

	public List<Transakcija> dohvatiTipTransakcije(int t, int mjesec);
}

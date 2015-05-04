package hr.foi.air.osobnibankar.core;

import hr.foi.air.osobnibankar.database.Transakcija;
import hr.foi.air.osobnibankar.interfaces.ITransakcija;

import java.util.List;

import com.activeandroid.query.Select;

public class Transakcije implements ITransakcija {

	@Override
	public List<Transakcija> dohvatiTransakcije(int grupa, int mjesec) {
		int grupaTransakcija = grupa;
		int trenutniMjesec = mjesec;
		
		switch(grupaTransakcija){
		case 1:
			List<Transakcija> prihodi_rashodi = new Select().all().from(Transakcija.class).where("mjesec = ? AND (tip_id=? OR tip_id=?)", trenutniMjesec,0,1)
						.execute();
	
				return prihodi_rashodi;
		case 0:
			List<Transakcija> potrazivanja_dugovanja = new Select().all().from(Transakcija.class).where("mjesec = ? AND (tip_id=? OR tip_id=?)", trenutniMjesec,2,3)
			.execute();
				return potrazivanja_dugovanja;
		}
		return null;
	}

	@Override
	public List<Transakcija> dohvatiTipTransakcije(int t, int mjesec) {
		int tip = t;
		int trenutniMjesec = mjesec;
		List<Transakcija> transakcije = new Select().all().from(Transakcija.class).where("mjesec = ? AND tip_id= ?", trenutniMjesec, tip)
					.execute();
		return transakcije;

	}
}

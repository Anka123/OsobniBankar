package hr.foi.air.osobnibankar.dodatno;

import hr.foi.air.osobnibankar.R;


public class Datum {

	public int nazivMj(int mj) {
		int mjesec = mj;
		int nazivMjeseca = 0;
		if (mjesec == 1)
			nazivMjeseca = R.string.sijecanj;
		else if (mjesec == 2)
			nazivMjeseca = R.string.veljaca;
		else if (mjesec == 3)
			nazivMjeseca = R.string.ozujak;
		else if (mjesec == 4)
			nazivMjeseca = R.string.travanj;
		else if (mjesec == 5)
			nazivMjeseca = R.string.svibanj;
		else if (mjesec == 6)
			nazivMjeseca = R.string.lipanj;
		else if (mjesec == 7)
			nazivMjeseca = R.string.srpanj;
		else if (mjesec == 8)
			nazivMjeseca = R.string.kolovoz;
		else if (mjesec == 9)
			nazivMjeseca = R.string.rujan;
		else if (mjesec == 10)
			nazivMjeseca = R.string.listopad;
		else if (mjesec == 11)
			nazivMjeseca = R.string.studeni;
		else if (mjesec == 12)
			nazivMjeseca = R.string.prosinac;
		return nazivMjeseca;
	}
	
	public int brojMj(String mj) {
		String mjesec = mj;
		int brMjeseca = 0;
		if ((mjesec.equals("Sijeèanj"))|(mjesec.equals("January")))
			brMjeseca = 1;
		
		else if ((mjesec.equals("Veljaèa"))|(mjesec.equals("February")))
			brMjeseca = 2;
		
		else if ((mjesec.equals("Ožujak"))|(mjesec.equals("March")))
			brMjeseca = 3;		
		else if ((mjesec.equals("Travanj"))|(mjesec.equals("April")))
			brMjeseca = 4;

		else if ((mjesec.equals("Svibanj"))|(mjesec.equals("May")))
			brMjeseca = 5;

		else if ((mjesec.equals("Lipanj"))|(mjesec.equals("June")))
			brMjeseca = 6;

		else if ((mjesec.equals("Srpanj"))|(mjesec.equals("July")))
			brMjeseca = 7;

		else if ((mjesec.equals("Kolovoz"))|(mjesec.equals("August")))
			brMjeseca = 8;

		else if ((mjesec.equals("Rujan"))|(mjesec.equals("September")))
			brMjeseca = 9;

		else if ((mjesec.equals("Listopad"))|(mjesec.equals("October")))
			brMjeseca = 10;

		else if ((mjesec.equals("Studeni"))|(mjesec.equals("November")))
			brMjeseca = 11;
	
		else if ((mjesec.equals("Prosinac"))|(mjesec.equals("December")))
			brMjeseca = 12;

		return brMjeseca;
	}

}

package hr.foi.air.osobnibankar.dodatno;


public class Datum {

	public String nazivMj(int mj) {
		int mjesec = mj;
		String nazivMjeseca = "";
		if (mjesec == 1)
			nazivMjeseca = "Sijeèanj";
		else if (mjesec == 2)
			nazivMjeseca = "Veljaèa";
		else if (mjesec == 3)
			nazivMjeseca = "Ožujak";
		else if (mjesec == 4)
			nazivMjeseca = "Travanj";
		else if (mjesec == 5)
			nazivMjeseca = "Svibanj";
		else if (mjesec == 6)
			nazivMjeseca = "Lipanj";
		else if (mjesec == 7)
			nazivMjeseca = "Srpanj";
		else if (mjesec == 8)
			nazivMjeseca = "Kolovoz";
		else if (mjesec == 9)
			nazivMjeseca = "Rujan";
		else if (mjesec == 10)
			nazivMjeseca = "Listopad";
		else if (mjesec == 11)
			nazivMjeseca = "Studeni";
		else if (mjesec == 12)
			nazivMjeseca = "Prosinac";
		return nazivMjeseca;
	}
	
	public int brojMj(String mj) {
		String mjesec = mj;
		int brMjeseca = 0;
		if (mjesec.equals("Sijeèanj"))
			brMjeseca = 1;
		else if (mjesec.equals("Veljaèa"))
			brMjeseca = 2;
		else if (mjesec.equals("Ožujak"))
			brMjeseca = 3;
		else if (mjesec.equals("Travanj"))
			brMjeseca = 4;
		else if (mjesec.equals("Svibanj"))
			brMjeseca = 5;
		else if (mjesec.equals("Lipanj"))
			brMjeseca = 6;
		else if (mjesec.equals("Srpanj"))
			brMjeseca = 7;
		else if (mjesec.equals("Kolovoz"))
			brMjeseca = 8;
		else if (mjesec.equals("Rujan"))
			brMjeseca = 9;
		else if (mjesec.equals("Listopad"))
			brMjeseca = 10;
		else if (mjesec.equals("Studeni"))
			brMjeseca = 11;
		else if (mjesec.equals("Prosinac"))
			brMjeseca = 12;
		return brMjeseca;
	}

}

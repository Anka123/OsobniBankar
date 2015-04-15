package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Dugovanje")
public class Dugovanje extends Model {
	@Column(name = "naziv")
	public String naziv;
	@Column(name = "opis")
	public String opis;
	@Column(name = "kategorija")
	public String kategorija;
	@Column(name = "iznos")
	public Double iznos;
	@Column(name = "datum")
	public String datum;

	public Dugovanje() {
		super();
	}

	public Dugovanje(String naziv, String opis, String kategorija, Double iznos, String datum) {
		this.naziv = naziv;
		this.opis = opis;
		this.kategorija = kategorija;
		this.iznos = iznos;
		this.datum = datum;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return opis;
	}
	
	public String getKategorija() {
		return kategorija;
	}

	public Double getIznos() {
		return iznos;
	}

	public String getDatum() {
		return datum;
	}

}

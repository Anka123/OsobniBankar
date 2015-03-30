package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Rashod")
public class Rashod extends Model {
	@Column(name = "naziv")
	public String naziv;
	@Column(name = "opis")
	public String opis;
	@Column(name = "iznos")
	public Double iznos;
	@Column(name = "datum")
	public String datum;

	public Rashod() {
		super();
	}

	public Rashod(String naziv, String opis, Double iznos, String datum) {
		this.naziv = naziv;
		this.opis = opis;
		this.iznos = iznos;
		this.datum = datum;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return naziv;
	}

	public Double getIznos() {
		return iznos;
	}

	public String getDatum() {
		return datum;
	}

}

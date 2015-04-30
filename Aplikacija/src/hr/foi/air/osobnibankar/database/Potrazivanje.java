package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Potrazivanje")
public class Potrazivanje extends Model {
	@Column(name = "remote_id")
	public long remote_id;
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
	@Column(name = "mjesec")
	public int mjesec;

	public Potrazivanje() {
		super();
	}

	public Potrazivanje(long remote_id, String naziv, String opis, String kategorija, Double iznos, String datum, int mjesec) {
		this.remote_id = remote_id;
		this.naziv = naziv;
		this.opis = opis;
		this.kategorija = kategorija;
		this.iznos = iznos;
		this.datum = datum;
		this.mjesec = mjesec;
	}

	public long getRemoteId() {
		return remote_id;
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
	
	public int getMjesec() {
		return mjesec;
	}

}

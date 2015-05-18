package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Transakcija")
public class Transakcija extends Model{
	@Column (name = "remote_id")
	public long remote_id;
	@Column (name = "naziv")
	public String naziv;
	@Column (name = "iznos")
	public Double iznos;
	@Column (name = "opis")
	public String opis;
	@Column (name = "zatvoreno")
	public boolean zatvoreno;
	@Column (name = "kategorija")
	public String kategorija;
	@Column (name = "partner_id")
	public Partner partner_id;
	@Column(name = "datum")
	public String datum;
	@Column(name = "tip_id")
	public int tip_id;
	@Column(name = "mjesec")
	public int mjesec;

	public Transakcija () {
		super();
	}

	public Transakcija (long remote_id, String naziv, String opis, Double iznos, boolean zatvoreno, String kategorija, Partner partner_id, String datum, int mjesec, int tip) {
		this.remote_id = remote_id;
		this.naziv = naziv;
		this.iznos = iznos;
		this.zatvoreno = zatvoreno;
		this.kategorija = kategorija;
		this.partner_id = partner_id;
		this.datum = datum;
		this.tip_id = tip;
		this.opis=opis;
		this.mjesec = mjesec;
	}

	public long getRemote_id () {
		return remote_id;
	}

	public String getDatum () {
		return datum;
	}
	
	public String getOpis () {
		return opis;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public Double getIznos() {
		return iznos;
	}
	
	public boolean getZatvoreno() {
		return zatvoreno;
	}
	
	public String getKategorija() {
		return kategorija;
	}
	
	public Partner getPartner_id() {
		return partner_id;
	}
	
	public int getTip(){
		return tip_id;
	}
	
	public int getMjesec()
	{
		
		return mjesec;
	}
}


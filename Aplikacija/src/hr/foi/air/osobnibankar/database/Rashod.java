package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Rashod")
public class Rashod extends Model {
	@Column(name = "remote_id")
	public long remote_id;
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

	public Rashod(long remote_id, String naziv, String opis, Double iznos, String datum) {
		this.remote_id = remote_id;
		this.naziv = naziv;
		this.opis = opis;
		this.iznos = iznos;
		this.datum = datum;
	}

	public long getRemote_id() {
		return remote_id;
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

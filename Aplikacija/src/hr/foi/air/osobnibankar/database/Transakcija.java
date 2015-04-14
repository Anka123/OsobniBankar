package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Transakcija")
public class Transakcija extends Model{
	@Column (name = "remote__id")
	public long remote_id;
	@Column (name = "iznos")
	public double iznos;
	@Column (name = "zatvoreno")
	public boolean zatvoreno;
	@Column (name = "kategorija")
	public String kategorija;
	@Column (name = "partner_id")
	public Partner partner_id;

	public Transakcija () {
		super();
	}

	public Transakcija (long remote_id, double iznos, boolean zatvoreno, String kategorija, Partner partner_id) {
		this.remote_id = remote_id;
		this.iznos = iznos;
		this.zatvoreno = zatvoreno;
		this.kategorija = kategorija;
		this.partner_id = partner_id;
	}
	
	public long getRemote_id () {
		return remote_id;
	}
	
	public double getIznos() {
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
}


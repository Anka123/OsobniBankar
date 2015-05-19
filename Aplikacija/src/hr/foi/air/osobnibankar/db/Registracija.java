package hr.foi.air.osobnibankar.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Registracija")
public class Registracija extends Model{
	@Column (name = "lozinka")
	public String lozinka;
	@Column (name = "pitanje")
	public String pitanje;
	@Column (name = "odgovor")
	public String odgovor;
	
	public Registracija() {
		super();
	}

	public Registracija(String lozinka, String pitanje, String odgovor){
		super();
		this.lozinka = lozinka;
		this.pitanje = pitanje;
		this.odgovor = odgovor;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	
	public String getPitanje() {
		return pitanje;
	}
	
	public String getOdgovor() {
		return odgovor;
	}
}

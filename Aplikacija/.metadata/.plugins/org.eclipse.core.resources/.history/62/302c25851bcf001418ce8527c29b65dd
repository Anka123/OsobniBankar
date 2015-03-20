package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Login")
public class Login extends Model{
	@Column (name = "lozinka")
	private String lozinka;
	@Column (name = "pitanje")
	private String pitanje;
	@Column (name = "odgovor")
	private String odgovor;
	
	public Login() {
		super();
	}

	public Login(String lozinka, String pitanje, String odgovor){
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

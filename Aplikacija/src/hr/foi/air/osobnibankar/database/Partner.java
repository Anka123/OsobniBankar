package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Partner")
public class Partner extends Model{
	@Column (name = "remote_id")
	private long remote_id;
	@Column (name = "naziv")
	private String naziv;
	@Column (name = "kontakt")
	private String kontakt;
	
	public Partner() {
		super();
	}

	public Partner(long remote_id, String naziv, String kontakt){
		super();
		this.remote_id = remote_id;
		this.naziv = naziv;
		this.kontakt = kontakt;
	}
	
	public long getRemote_id() {
		return remote_id;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public String getKontakt() {
		return kontakt;
	}
}

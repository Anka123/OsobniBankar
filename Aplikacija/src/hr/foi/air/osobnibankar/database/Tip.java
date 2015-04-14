package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Tip")
public class Tip extends Model{
	@Column (name = "remote_id")
	public int remote_id;
	@Column (name = "naziv")
	public String naziv;
	
	public Tip() {
		super();
	}

	public Tip(int remote_id, String naziv){
		this.remote_id = remote_id;
		this.naziv = naziv;
	}
	
	public int getRemote_id() {
		return remote_id;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
}

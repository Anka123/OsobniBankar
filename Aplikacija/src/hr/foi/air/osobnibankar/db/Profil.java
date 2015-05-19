package hr.foi.air.osobnibankar.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Profil")
public class Profil extends Model{
	@Column (name = "remote_id")
	public long remote_id;
	@Column (name = "ime")
	public String ime;
	@Column (name = "prezime")
	public String prezime;
	@Column (name = "ogranicenje")
	public Double limit;
	
	public Profil(){
		super();
	}
	
	public Profil(long remote_id, String ime, String prezime, Double limit){
		this.remote_id = remote_id;
		this.ime = ime;
		this.prezime = prezime;
		this.limit = limit;
	}
	
	public long getRemoteId(){
		return remote_id;
	}
	
	public String getIme(){
		return ime;
	}
	
	public String getPrezime(){
		return prezime;
	}
	
	public Double getLimit() {
		return limit;
	}
}



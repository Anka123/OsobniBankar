package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Kategorija")
public class Kategorija extends Model {
	@Column(name = "remote_id")
	public long remote_id;
	@Column(name = "naziv")
	public String naziv;
	@Column(name = "tip_id")
	public Tip tip_id;
	

	public Kategorija() {
		super();
	}

	public Kategorija(long remote_id, String naziv, Tip tip_id) {
		this.remote_id = remote_id;
		this.naziv = naziv;
		this.tip_id = tip_id;
		
	}

	public long getRemote_id() {
		return remote_id;
	}
	
	public String getNaziv() {
		return naziv;
	}

	public Tip getTip_id() {
		return tip_id;
	}

	

}

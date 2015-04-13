package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Transakcija")
public class Transakcija extends Model{
	@Column (name = "remote__id")
	public long remote_id;
	@Column (name = "prihod_id")
	public Prihod prihod;
	@Column (name = "rashod_id")
	public Rashod rashod;

	public Transakcija () {
		super();
	}

	public Transakcija (long remote_id, Prihod prihod, Rashod rashod) {
		this.remote_id = remote_id;
		this.prihod = prihod;
		this.rashod = rashod;
	}
	
	public long getRemote_id () {
		return remote_id;
	}
	
	public Prihod getPrihod() {
		return prihod;
	}
	
	public Rashod getRashod() {
		return rashod;
	}
}


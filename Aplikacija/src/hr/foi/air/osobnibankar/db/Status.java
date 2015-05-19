package hr.foi.air.osobnibankar.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Status")
public class Status extends Model{
	@Column (name = "trans_id")
	private Transakcija trans_id;
	@Column (name = "datum")
	private String datum;
	@Column (name = "tip_id")
	private Tip tip_id;
	@Column (name = "opis")
	private String opis;
	
	public Status() {
		super();
	}

	public Status(Transakcija trans_id, String datum, Tip tip_id ,String opis){
		super();
		this.trans_id = trans_id;
		this.datum = datum;
		this.tip_id = tip_id;
		this.opis = opis;
	}
	
	public Transakcija getTrans_id() {
		return trans_id;
	}
	
	public String getDatum() {
		return datum;
	}
	
	public Tip getTip_id() {
		return tip_id;
	}
	
	public String opis() {
		return opis;
	}
}

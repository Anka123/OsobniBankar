package hr.foi.air.osobnibankar.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Limit")
public class Limit extends Model {
	
	@Column (name = "iznos")
	public double iznos;
	
	public Limit () {
		
		super();
	}
	
	public Limit (double iznos){
		
		this.iznos = iznos;
		
	}
	
	public double getIznos(){
		
		return iznos;
	}
	

}

package hr.foi.air.osobnibankar.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tecaj")
public class Tecaj extends Model {

	@Column(name = "valuta")
	private String valuta;
	@Column(name = "kupovni")
	private long kupovni;
	@Column(name = "srednji")
	private long srednji;
	@Column(name = "prodajni")
	private long prodajni;

	public Tecaj() {
		super();
	}

	public Tecaj(String valuta, long kupovni, long srednji, long prodajni) {
		super();
		this.valuta = valuta;
		this.kupovni = kupovni;
		this.srednji = srednji;
		this.prodajni = prodajni;
	}

	public String getValuta() {
		return valuta;
	}

	public long getKupovni() {
		return kupovni;
	}

	public long getSrednji() {
		return srednji;
	}

	public long getProdajni() {
		return prodajni;
	}

}

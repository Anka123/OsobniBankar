package hr.foi.air.osobnibankar.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tecaj")
public class Tecaj extends Model {

	@Column(name = "valuta")
	private String valuta;
	@Column(name = "kupovni")
	private String kupovni;
	@Column(name = "srednji")
	private String srednji;
	@Column(name = "prodajni")
	private String prodajni;

	public Tecaj() {
		super();
	}

	public Tecaj(String valuta, String kupovni, String srednji, String prodajni) {
		super();
		this.valuta = valuta;
		this.kupovni = kupovni;
		this.srednji = srednji;
		this.prodajni = prodajni;
	}

	public String getValuta() {
		return valuta;
	}

	public String getKupovni() {
		return kupovni;
	}

	public String getSrednji() {
		return srednji;
	}

	public String getProdajni() {
		return prodajni;
	}

}

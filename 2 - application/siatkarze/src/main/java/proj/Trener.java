package proj;

//import java.util.Arrays;

public class Trener {
	
	private int id;
	private String imie;
	private String nazwisko;
	private Integer rokUrodzenia;
	private Integer nrTelefonu;
	private Adres adres_id;
	private Klub klub_id;
	private String login_;
	private String haslo_;
	//private byte[] haslo_;
	
	public Trener() {}
	public Trener(String imie, String nazwisko, Integer rokUrodzenia, Integer nrTelefonu, Adres adres_id, Klub klub_id,
			String login_, String haslo_) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.rokUrodzenia = rokUrodzenia;
		this.nrTelefonu = nrTelefonu;
		this.adres_id = adres_id;
		this.klub_id = klub_id;
		this.login_ = login_;
		this.haslo_ = haslo_;
	}
	
	@Override
	public String toString() {
		return "Trener: id=" + id + ", " + imie + " " + nazwisko + ", rokUrodzenia=" + rokUrodzenia;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public Integer getRokUrodzenia() {
		return rokUrodzenia;
	}
	public void setRokUrodzenia(Integer rokUrodzenia) {
		this.rokUrodzenia = rokUrodzenia;
	}
	public Integer getNrTelefonu() {
		return nrTelefonu;
	}
	public void setNrTelefonu(Integer nrTelefonu) {
		this.nrTelefonu = nrTelefonu;
	}
	public Adres getAdres_id() {
		return adres_id;
	}
	public void setAdres_id(Adres adres_id) {
		this.adres_id = adres_id;
	}
	public Klub getKlub_id() {
		return klub_id;
	}
	public void setKlub_id(Klub klub_id) {
		this.klub_id = klub_id;
	}
	public String getLogin_() {
		return login_;
	}
	public void setLogin_(String login_) {
		this.login_ = login_;
	}
	public String getHaslo_() {
		return haslo_;
	}
	public void setHaslo_(String haslo_) {
		this.haslo_ = haslo_;
	}
	
}

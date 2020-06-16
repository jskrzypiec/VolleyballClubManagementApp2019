package proj;

//import java.util.Arrays;

public class Zawodnik {
	
	private int id;
	private String imie;
	private String nazwisko;
	private Integer rokUrodzenia;
	private Integer wzrost;
	private Integer waga;
	private Integer nrTelefonu;
	private Pozycja pozycja_id;
	private Adres adres_id;
	private Klub klub_id;
	private Grupa grupa_id;
	private String login_;
	private String haslo_;
	//private byte[] haslo_;
	
	public Zawodnik() {}
	public Zawodnik(String imie, String nazwisko, Integer rokUrodzenia, Integer wzrost, Integer waga, Integer nrTelefonu,
			Pozycja pozycja_id, Adres adres_id, Klub klub_id, Grupa grupa_id, String login_, String haslo_) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.rokUrodzenia = rokUrodzenia;
		this.wzrost = wzrost;
		this.waga = waga;
		this.nrTelefonu = nrTelefonu;
		this.pozycja_id = pozycja_id;
		this.adres_id = adres_id;
		this.klub_id = klub_id;
		this.grupa_id = grupa_id;
		this.login_ = login_;
		this.haslo_ = haslo_;
	}
	
	@Override
	public String toString() {
		return "Zawodnik: id=" + id + ", " + imie + ", " + nazwisko + ", " + rokUrodzenia;
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
	public int getRokUrodzenia() {
		return rokUrodzenia;
	}
	public void setRokUrodzenia(Integer rokUrodzenia) {
		this.rokUrodzenia = rokUrodzenia;
	}
	public Integer getWzrost() {
		return wzrost;
	}
	public void setWzrost(Integer wzrost) {
		this.wzrost = wzrost;
	}
	public Integer getWaga() {
		return waga;
	}
	public void setWaga(Integer waga) {
		this.waga = waga;
	}
	public Integer getNrTelefonu() {
		return nrTelefonu;
	}
	public void setNrTelefonu(Integer nrTelefonu) {
		this.nrTelefonu = nrTelefonu;
	}
	public Pozycja getPozycja_id() {
		return pozycja_id;
	}
	public void setPozycja_id(Pozycja pozycja_id) {
		this.pozycja_id = pozycja_id;
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
	public Grupa getGrupa_id() {
		return grupa_id;
	}
	public void setGrupa_id(Grupa grupa_id) {
		this.grupa_id = grupa_id;
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

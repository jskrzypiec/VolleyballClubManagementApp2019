package proj;

//import java.util.Arrays;

public class Administrator {
	
	private int id;
	private String imie;
	private String nazwisko;
	private String login_;
	private String haslo_;
	//private byte[] haslo_;
	
	public Administrator() {}
	public Administrator(String imie, String nazwisko, String login_, String haslo_) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.login_ = login_;
		this.haslo_ = haslo_;
	}
	
	@Override
	public String toString() {
		return "Administrator [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", login_=" + login_
				+ ", haslo_=" + haslo_ + "]";
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

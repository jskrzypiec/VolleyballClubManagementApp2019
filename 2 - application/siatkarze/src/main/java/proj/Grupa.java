package proj;

public class Grupa {
	
	private int id;
	private String nazwa;
	private Kategoria kategoria_id;
	private Trener trener_id;
	
	public Grupa() {}
	public Grupa(String nazwa, Kategoria kategoria_id, Trener trener_id) {
		this.nazwa = nazwa;
		this.kategoria_id = kategoria_id;
		this.trener_id = trener_id;
	}
	
	@Override
	public String toString() {
		return "Grupa: id=" + id + ", " + nazwa;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public Kategoria getKategoria_id() {
		return kategoria_id;
	}
	public void setKategoria_id(Kategoria kategoria_id) {
		this.kategoria_id = kategoria_id;
	}
	public Trener getTrener_id() {
		return trener_id;
	}
	public void setTrener_id(Trener trener_id) {
		this.trener_id = trener_id;
	}
		
}

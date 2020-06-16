package proj;

public class Kategoria {
	
	private int id;
	private String nazwa;
	
	public Kategoria() {}
	public Kategoria(String nazwa) {
		this.nazwa = nazwa;
	}
	
	@Override
	public String toString() {
		return "Kategoria: id=" + id + ", " + nazwa;
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
}

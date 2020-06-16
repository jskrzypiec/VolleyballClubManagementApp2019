package proj;

public class Pozycja {
	
	private int id;
	private String nazwa;
	
	public Pozycja() {}
	public Pozycja(String nazwa) {
		this.nazwa = nazwa;
	}
	
	@Override
	public String toString() {
		return "Pozycja: id=" + id + ", " + nazwa;
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

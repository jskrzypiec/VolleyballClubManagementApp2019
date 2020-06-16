package proj;

public class DzienTygodnia {
	
	private int id;
	private String nazwa;
	
	public DzienTygodnia() {}
	public DzienTygodnia(String nazwa) {
		this.nazwa = nazwa;
	}
	
	@Override
	public String toString() {
		return "DzienTygodnia: id=" + id + ", " + nazwa;
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

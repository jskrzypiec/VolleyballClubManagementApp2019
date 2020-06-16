package proj;

public class Klub {
	
	private int id; 
	private String nazwa;
	private Integer rokZalozenia;
	private Integer rokRozwiazania;
	
	public Klub() {}
	public Klub(String nazwa, Integer rokZalozenia) {
		this.nazwa = nazwa;
		this.rokZalozenia = rokZalozenia;
	}
	public Klub(String nazwa, Integer rokZalozenia, Integer rokRozwiazania) {
		this.nazwa = nazwa;
		this.rokZalozenia = rokZalozenia;
		this.rokRozwiazania = rokRozwiazania;
	}
	
	@Override
	public String toString() {
		return "Klub: id=" + id + ", " + nazwa;
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
	public Integer getRokZalozenia() {
		return rokZalozenia;
	}
	public void setRokZalozenia(Integer rokZalozenia) {
		this.rokZalozenia = rokZalozenia;
	}
	public Integer getRokRozwiazania() {
		return rokRozwiazania;
	}
	public void setRokRozwiazania(Integer rokRozwiazania) {
		this.rokRozwiazania = rokRozwiazania;
	}	
	
}

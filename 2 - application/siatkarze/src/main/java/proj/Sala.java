package proj;

public class Sala {
	
	private int id;
	private String nazwa;
	private Adres adres_id;
	
	public Sala() {}
	public Sala(String nazwa, Adres adres_id) {
		this.nazwa = nazwa;
		this.adres_id = adres_id;
	}
	
	@Override
	public String toString() {
		return "Sala: id=" + id + ", " + nazwa + ", adres: " + adres_id.getUlica() + " " + adres_id.getNrDomu() + " (" + 
				adres_id.getMiasto_id().getNazwa() + ")";
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
	public Adres getAdres_id() {
		return adres_id;
	}
	public void setAdres_id(Adres adres_id) {
		this.adres_id = adres_id;
	}
	
	
}

package proj;

public class Adres {
	
	private int id;
	private String ulica;
	private String nrDomu;
	private Integer nrMieszkania;
	private String kodPocztowy;
	private Miasto miasto_id;
	//private List<Miasto> miasto_id = new ArrayList<Miasto>();
	
	public Adres() {}
	public Adres(int id, String ulica, String nrDomu, Integer nrMieszkania, String kodPocztowy, Miasto miasto_id) {
		this.id = id;
		this.ulica = ulica;
		this.nrDomu = nrDomu;
		this.nrMieszkania = nrMieszkania;
		this.kodPocztowy = kodPocztowy;
		this.miasto_id = miasto_id;
	}
	public Adres(String ulica, String nrDomu, Integer nrMieszkania, String kodPocztowy, Miasto miasto_id) {
		this.ulica = ulica;
		this.nrDomu = nrDomu;
		this.nrMieszkania = nrMieszkania;
		this.kodPocztowy = kodPocztowy;
		this.miasto_id = miasto_id;
	}
	
	@Override
	public String toString() {
		if(this.getNrMieszkania()!=null)
			return "Adres: id=" + id + ", " + ulica + " " + nrDomu + "/" + nrMieszkania +" (" + miasto_id.getNazwa() + ")";
		return "Adres: id=" + id + ", " + ulica + " " + nrDomu + " (" + miasto_id.getNazwa() + ")";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getNrDomu() {
		return nrDomu;
	}
	public void setNrDomu(String nrDomu) {
		this.nrDomu = nrDomu;
	}
	public Integer getNrMieszkania() {
		return nrMieszkania;
	}
	public void setNrMieszkania(Integer nrMieszkania) {
		this.nrMieszkania = nrMieszkania;
	}
	public String getKodPocztowy() {
		return kodPocztowy;
	}
	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}
	public Miasto getMiasto_id() {
		return miasto_id;
	}
	public void setMiasto_id(Miasto miasto_id) {
		this.miasto_id = miasto_id;
	}
		
}

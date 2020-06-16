package proj;

import java.sql.Time;

public class Zajecia {
	
	private int id;
	private Time godzRozpoczecia;
	private Time godzZakonczenia;
	private DzienTygodnia dzienTygodnia_id;
	private Grupa grupa_id;
	private Sala sala_id;
	
	public Zajecia() {}
	public Zajecia(Time godzRozppoczecia, Time godzZakonczenia, DzienTygodnia dzienTygodnia_id, Grupa grupa_id, Sala sala_id) {
		this.godzRozpoczecia = godzRozppoczecia;
		this.godzZakonczenia = godzZakonczenia;
		this.dzienTygodnia_id = dzienTygodnia_id;
		this.grupa_id = grupa_id;
		this.sala_id = sala_id;
	}
	
	@Override
	public String toString() {
		return "Zajecia: id=" + id + ", " + dzienTygodnia_id + "(" + godzRozpoczecia + "-" + godzZakonczenia + "), " + grupa_id.getNazwa(); 
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Time getGodzRozpoczecia() {
		return godzRozpoczecia;
	}
	public void setGodzRozpoczecia(Time godzRozpoczecia) {
		this.godzRozpoczecia = godzRozpoczecia;
	}
	public Time getGodzZakonczenia() {
		return godzZakonczenia;
	}
	public void setGodzZakonczenia(Time godzZakonczenia) {
		this.godzZakonczenia = godzZakonczenia;
	}
	public DzienTygodnia getDzienTygodnia_id() {
		return dzienTygodnia_id;
	}
	public void setDzienTygodnia_id(DzienTygodnia dzienTygodnia_id) {
		this.dzienTygodnia_id = dzienTygodnia_id;
	}
	public Grupa getGrupa_id() {
		return grupa_id;
	}
	public void setGrupa_id(Grupa grupa_id) {
		this.grupa_id = grupa_id;
	}
	public Sala getSala_id() {
		return sala_id;
	}
	public void setSala_id(Sala sala_id) {
		this.sala_id = sala_id;
	}

}

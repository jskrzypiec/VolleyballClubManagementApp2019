package proj;

/*import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;*/

//@Entity
//@Table(name = "miasto")
public class Miasto {

	//@Id
	//@Column(name = "id")
	//@GeneratedValue
	private int id;
	
	//@Column(name = "nazwa")
	private String nazwa;
	
	public Miasto() {}
	public Miasto(String nazwa) {
		this.nazwa = nazwa;
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
	
	@Override
	public String toString() {
		return "Miasto: id=" + id + ", " + nazwa;
	}
	
	
}

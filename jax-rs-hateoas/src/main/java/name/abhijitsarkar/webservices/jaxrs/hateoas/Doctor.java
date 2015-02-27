package name.abhijitsarkar.webservices.jaxrs.hateoas;

public class Doctor {
    private final int id;

    public Doctor(int id) {
	this.id = id;
    }
    
    public int getId() {
	return id;
    }
}

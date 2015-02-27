package name.abhijitsarkar.microservices.appointment;

public class Appointment {
    private final int id;
    private final int slotId;

    public Appointment(int id, int slotId) {
	this.id = id;
	this.slotId = slotId;
    }

    public int getId() {
	return id;
    }

    public int getSlotId() {
	return slotId;
    }
}

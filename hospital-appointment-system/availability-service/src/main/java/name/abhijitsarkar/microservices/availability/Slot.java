package name.abhijitsarkar.microservices.availability;

public class Slot {
    private final int id;
    private final String startDateTime;
    private final String endDateTime;
    private final int doctorId;

    public Slot(int id, String startDateTime, String endDateTime, int doctorId) {
	this.id = id;
	this.startDateTime = startDateTime;
	this.endDateTime = endDateTime;
	this.doctorId = doctorId;
    }

    public int getId() {
	return id;
    }

    public String getStartDateTime() {
	return startDateTime;
    }

    public String getEndDateTime() {
	return endDateTime;
    }

    public int getDoctorId() {
	return doctorId;
    }
}

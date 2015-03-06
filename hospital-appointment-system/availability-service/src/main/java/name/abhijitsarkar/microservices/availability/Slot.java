package name.abhijitsarkar.microservices.availability;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.time.LocalDateTime;

public class Slot {
    private final int id;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String doctorId;

    private Slot(Slot other) {
	this(other.id, other.startDateTime, other.endDateTime, other.doctorId);
    }

    private Slot(int id, LocalDateTime startDateTime,
	    LocalDateTime endDateTime, String doctorId) {
	this.id = id;
	this.startDateTime = startDateTime;
	this.endDateTime = endDateTime;
	this.doctorId = doctorId;
    }

    public static Slot from(Slot s) {
	return new Slot(s);
    }

    public static Slot of(int id, LocalDateTime startDateTime,
	    LocalDateTime endDateTime, String doctorId) {
	return new Slot(id, startDateTime, endDateTime, doctorId);
    }

    public int getId() {
	return id;
    }

    public LocalDateTime getStartDateTime() {
	return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
	return endDateTime;
    }

    public String getDoctorId() {
	return doctorId;
    }

    @Override
    public String toString() {
	return "Slot [startDateTime="
		+ ISO_LOCAL_DATE_TIME.format(startDateTime) + ", endDateTime="
		+ ISO_LOCAL_DATE_TIME.format(endDateTime) + ", doctorId="
		+ doctorId + "]";
    }
}

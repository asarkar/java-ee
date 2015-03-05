package name.abhijitsarkar.microservices.availability;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.time.LocalDateTime;

public class Slot {
    private final int id;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String doctorId;

    private boolean reserved;

    public Slot(Slot other) {
	this(other.id, other.startDateTime, other.endDateTime, other.doctorId,
		other.reserved);
    }

    public Slot(int id, LocalDateTime startDateTime, LocalDateTime endDateTime,
	    String doctorId, boolean reserved) {
	this.id = id;
	this.startDateTime = startDateTime;
	this.endDateTime = endDateTime;
	this.doctorId = doctorId;
	this.reserved = reserved;
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

    public boolean isReserved() {
	return reserved;
    }

    @Override
    public String toString() {
	return "Slot [startDateTime="
		+ ISO_LOCAL_DATE_TIME.format(startDateTime) + ", endDateTime="
		+ ISO_LOCAL_DATE_TIME.format(endDateTime) + ", doctorId="
		+ doctorId + ", isReserved=" + reserved + "]";
    }
}

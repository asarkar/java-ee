package name.abhijitsarkar.javaee.microservices.availability.domain;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.time.LocalDateTime;

import name.abhijitsarkar.javaee.microservices.availability.representation.SlotDateTimeDeserializer;
import name.abhijitsarkar.javaee.microservices.availability.representation.SlotDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
	return of(s.id, s.startDateTime, s.endDateTime, s.doctorId);
    }

    @JsonCreator
    public static Slot of(
	    @JsonProperty("id") int id,
	    @JsonProperty("startDateTime") @JsonSerialize(using = SlotDateTimeSerializer.class) @JsonDeserialize(using = SlotDateTimeDeserializer.class) LocalDateTime startDateTime,
	    @JsonProperty("endDateTime") @JsonSerialize(using = SlotDateTimeSerializer.class) @JsonDeserialize(using = SlotDateTimeDeserializer.class) LocalDateTime endDateTime,
	    @JsonProperty("doctorId") String doctorId) {
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
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;

	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!(obj instanceof Slot)) {
	    return false;
	}

	Slot other = (Slot) obj;

	return id == other.id;
    }

    @Override
    public String toString() {
	return "Slot [startDateTime="
		+ ISO_LOCAL_DATE_TIME.format(startDateTime) + ", endDateTime="
		+ ISO_LOCAL_DATE_TIME.format(endDateTime) + ", doctorId="
		+ doctorId + "]";
    }
}

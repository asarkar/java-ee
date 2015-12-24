package name.abhijitsarkar.javaee.appointment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Appointment {
    private final int id;
    private final int slotId;
    private final String patientId;

    private Appointment(int id, int slotId, String patientId) {
	this.id = id;
	this.slotId = slotId;
	this.patientId = patientId;
    }

    @JsonCreator
    public static Appointment of(@JsonProperty("id") int id,
	    @JsonProperty("slotId") int slotId,
	    @JsonProperty("patientId") String patientId) {
	return new Appointment(id, slotId, patientId);
    }

    public int getId() {
	return id;
    }

    public int getSlotId() {
	return slotId;
    }

    public String getPatientId() {
	return patientId;
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
	if (!(obj instanceof Appointment)) {
	    return false;
	}

	Appointment other = (Appointment) obj;

	return id == other.id;
    }

    @Override
    public String toString() {
	return "Appointment [id=" + id + ", slotId=" + slotId + ", patientId="
		+ patientId + "]";
    }
}

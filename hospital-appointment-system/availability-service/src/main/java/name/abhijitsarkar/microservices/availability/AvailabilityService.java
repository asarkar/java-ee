package name.abhijitsarkar.microservices.availability;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import name.abhijitsarkar.microservices.extension.Consumes;
import name.abhijitsarkar.microservices.user.Doctor;

@ApplicationScoped
public class AvailabilityService {
    private List<Slot> availableSlots;

    @Consumes(value = Doctor.class)
    private List<Doctor> doctors;

    public Optional<List<Slot>> findSlotsByDate(String date) {
	return null;
    }

    public Optional<Slot> reserveSlot(int id) {
	return null;
    }

    public Optional<Slot> relinquishSlot(int id) {
	return null;
    }
}

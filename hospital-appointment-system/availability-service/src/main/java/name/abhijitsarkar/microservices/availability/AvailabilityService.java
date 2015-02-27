package name.abhijitsarkar.microservices.availability;

import java.util.List;
import java.util.Optional;

public class AvailabilityService {
    private List<Slot> availableSlots;

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

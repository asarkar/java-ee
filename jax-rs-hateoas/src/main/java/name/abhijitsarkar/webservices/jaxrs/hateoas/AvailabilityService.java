package name.abhijitsarkar.webservices.jaxrs.hateoas;

import java.util.List;
import java.util.Optional;

public class AvailabilityService {
    private List<Slot> availableSlots;

    public Optional<List<Slot>> findSlotsByDate(String date) {
	return null;
    }

    public Optional<Slot> bookSlot(int id) {
	return null;
    }

    public Optional<Slot> relinquishSlot(int id) {
	return null;
    }
}

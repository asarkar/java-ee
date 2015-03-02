package name.abhijitsarkar.microservices.availability;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AvailabilityService {
    @Inject
    private SlotsManager slotsManager;

    private List<Slot> availableSlots;

    public void initSlots() {
	Objects.nonNull(slotsManager);

	availableSlots = slotsManager.getAvailableSlots();

	Objects.nonNull(availableSlots);
    }

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

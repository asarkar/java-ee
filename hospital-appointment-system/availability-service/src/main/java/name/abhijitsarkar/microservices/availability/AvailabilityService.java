package name.abhijitsarkar.microservices.availability;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AvailabilityService {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AvailabilityService.class);

    @Inject
    private SlotsManager slotsManager;

    private List<Slot> availableSlots;

    @PostConstruct
    public void initSlots() {
	Objects.nonNull(slotsManager);

	availableSlots = slotsManager.getAvailableSlots();

	Objects.nonNull(availableSlots);

	LOGGER.info("Available slots: {}.", availableSlots);
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

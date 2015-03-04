package name.abhijitsarkar.microservices.availability;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import name.abhijitsarkar.microservices.extension.Consumes;
import name.abhijitsarkar.microservices.user.Doctor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AvailabilityService {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AvailabilityService.class);

    @Consumes(value = Doctor.class)
    private List<Doctor> doctors;

    private List<Slot> availableSlots;

    @PostConstruct
    public void initSlots() {
	availableSlots = getSlots();

	LOGGER.info("Initialized all slots: {}.", availableSlots);
    }

    private List<Slot> getSlots() {
	Objects.nonNull(doctors);

	LocalDateTime now = LocalDateTime.now();

	LocalDateTime aWeekFromNow = LocalDateTime.now().plusDays(7);

	int hours = (int) Duration.between(now, aWeekFromNow).toHours();

	Random r = new Random();

	List<Slot> slots = IntStream
		.range(1, hours)
		.mapToObj(
			index -> new Slot(index, now, now.plusHours(1), doctors
				.get(r.nextInt(doctors.size())).getUserId()))
		.collect(toList());

	return unmodifiableList(slots);
    }

    public Optional<List<Slot>> findSlotsByDate(String date) {
	LOGGER.info("Looking for a slot on the date: {}.", date);

	return Optional.of(availableSlots
		.stream()
		.filter(slot -> {
		    LocalDateTime requestedDate = LocalDateTime.parse(date,
			    ISO_LOCAL_DATE);

		    return slot.getStartDateTime().isBefore(requestedDate)
			    && slot.getEndDateTime().isAfter(requestedDate);
		}).collect(toList()));
    }

    public Optional<Slot> reserveSlot(int id) {
	return updateSlotAvailability(id, true);
    }

    public Optional<Slot> relinquishSlot(int id) {
	return updateSlotAvailability(id, false);
    }

    private Optional<Slot> updateSlotAvailability(int id, boolean isReserved) {
	Optional<Slot> s = availableSlots.stream()
		.filter(slot -> slot.getId() == id).findFirst();

	if (s.isPresent()) {
	    s.get().setReserved(isReserved);
	}

	return s;
    }
}

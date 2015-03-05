package name.abhijitsarkar.microservices.availability;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.TemporalAdjusters.next;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static name.abhijitsarkar.microservices.availability.AvailabilityService.WorkingHourAdjuster.endHour;
import static name.abhijitsarkar.microservices.availability.AvailabilityService.WorkingHourAdjuster.startHour;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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

    public static final int START_WORKING_HOUR = 9;
    public static final int START_LUNCH_HOUR = 12;
    public static final int END_WORKING_HOUR = 17;

    @Consumes(value = Doctor.class)
    private List<Doctor> doctors;

    private List<Slot> availableSlots;

    @PostConstruct
    public void initSlots() {
	availableSlots = getSlots();

	LOGGER.info("Initialized all slots: {}.", availableSlots);
    }

    /*
     * Initialize slots starting 9 AM today through 5 PM next Friday, with 1
     * hour for each slot. 12 PM to 1 PM is lunch, no slots are scheduled for
     * that hour.
     */
    private List<Slot> getSlots() {
	Objects.nonNull(doctors);

	Random r = new Random();

	final LocalDateTime startDateTime = LocalDateTime.now().with(
		startHour(START_WORKING_HOUR));
	final LocalDateTime nextSaturday = startDateTime.with(next(SATURDAY));

	List<Slot> slots = new ArrayList<Slot>();
	LocalDateTime end = null;
	Slot s = null;
	int i = 1;

	for (LocalDateTime start = startDateTime; start.isBefore(nextSaturday); start = start
		.plusDays(1).with(startHour(START_WORKING_HOUR))) {
	    end = start.with(endHour(END_WORKING_HOUR));

	    for (; start.isBefore(end); start = start.plusHours(1), i++) {
		if (start.getHour() == START_LUNCH_HOUR) {
		    continue;
		}

		s = new Slot(i, start, start.plusHours(1), doctors.get(
			r.nextInt(doctors.size())).getUserId(), false);

		slots.add(s);
	    }
	}

	return unmodifiableList(slots);
    }

    public Optional<Slot> findSlotById(int id) {
	LOGGER.info("Looking for a slot with id: {}.", id);

	return availableSlots.stream().filter(slot -> slot.getId() == id)
		.findFirst();
    }

    public Optional<List<Slot>> findSlotsByDate(String date) {
	LOGGER.info("Looking for a slot on the date: {}.", date);

	LocalDateTime requestedDate = LocalDate.parse(date, ISO_LOCAL_DATE)
		.atTime(START_WORKING_HOUR, 0);

	return Optional
		.of(availableSlots
			.stream()
			.filter(slot -> {
			    LocalDateTime slotStartDateTime = slot
				    .getStartDateTime();

			    return slotStartDateTime.getDayOfWeek() == requestedDate
				    .getDayOfWeek()
				    && (slotStartDateTime
					    .isEqual(requestedDate) || slotStartDateTime
					    .isAfter(requestedDate))
				    && slot.getEndDateTime().isAfter(
					    slotStartDateTime);
			}).collect(toList()));
    }

    public Optional<Slot> reserveSlot(int id) {
	return updateSlotAvailability(id, true);
    }

    public Optional<Slot> relinquishSlot(int id) {
	return updateSlotAvailability(id, false);
    }

    private Optional<Slot> updateSlotAvailability(int id, boolean isReserved) {
	Optional<Slot> s = findSlotById(id);

	if (s.isPresent() && s.get().isReserved() != isReserved) {
	    return Optional.of(new Slot(s.get()));
	}

	return Optional.empty();
    }

    public List<Slot> getAvailableSlots() {
	return this.availableSlots;
    }

    void setDoctors(List<Doctor> doctors) {
	this.doctors = doctors;
    }

    public static class WorkingHourAdjuster implements TemporalAdjuster {
	private final int hour;

	private WorkingHourAdjuster(int hour) {
	    this.hour = hour;
	}

	public static WorkingHourAdjuster startHour(int hour) {
	    return new WorkingHourAdjuster(hour);
	}

	public static WorkingHourAdjuster endHour(int hour) {
	    return new WorkingHourAdjuster(hour);
	}

	@Override
	public Temporal adjustInto(Temporal input) {
	    return LocalDateTime.from(input).with(HOUR_OF_DAY, hour)
		    .with(MINUTE_OF_HOUR, 0);
	}
    }
}

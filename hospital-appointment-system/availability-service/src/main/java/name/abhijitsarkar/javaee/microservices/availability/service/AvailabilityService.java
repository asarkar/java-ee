package name.abhijitsarkar.javaee.microservices.availability.service;

import static java.io.File.separator;
import static java.lang.Boolean.FALSE;
import static java.lang.String.join;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static name.abhijitsarkar.javaee.microservices.user.domain.User.Type.Doctor;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.availability.domain.Slot;
import name.abhijitsarkar.javaee.microservices.representation.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.user.domain.Doctor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

@ApplicationScoped
public class AvailabilityService {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AvailabilityService.class);

    public static final int START_WORKING_HOUR = 9;
    public static final int START_LUNCH_HOUR = 12;
    public static final int END_WORKING_HOUR = 17;

    private static final String SERVICE_NAME = "user-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "user");

    // @Inject
    // private UserServiceClientFactory clientFactory;
    //
    // void setClientFactory(UserServiceClientFactory clientFactory) {
    // this.clientFactory = clientFactory;
    // }

    @Inject
    @name.abhijitsarkar.javaee.microservices.client.Client
    private Client client;

    @Inject
    private ObjectMapperFactory mapperFactory;

    void setMapperFactory(ObjectMapperFactory mapperFactory) {
	this.mapperFactory = mapperFactory;
    }

    private List<Doctor> doctors;

    void setDoctors(List<Doctor> doctors) {
	this.doctors = doctors;
    }

    private ConcurrentMap<Integer, SimpleImmutableEntry<Slot, Boolean>> slotMap;

    /*
     * Initialize slots starting 9 AM today through 5 PM next Friday, with 1
     * hour for each slot. 12 PM to 1 PM is lunch, no slots are scheduled for
     * that hour.
     */
    @PostConstruct
    public void initSlots() {
	/* This is true except for unit tests. */
	if (doctors == null) {
	    doctors = getDoctors();
	}

	slotMap = new ConcurrentHashMap<>();

	Random r = new Random();

	final LocalDateTime startDateTime = LocalDateTime.now()
		.with(nextOrSame(MONDAY))
		.with(WorkingHourAdjuster.startHour(START_WORKING_HOUR));
	final LocalDateTime nextSaturday = startDateTime.with(next(SATURDAY));

	LocalDateTime end = null;
	Slot s = null;
	SimpleImmutableEntry<Slot, Boolean> entry = null;
	int i = 1;

	for (LocalDateTime start = startDateTime; start.isBefore(nextSaturday); start = start
		.plusDays(1).with(
			WorkingHourAdjuster.startHour(START_WORKING_HOUR))) {
	    end = start.with(WorkingHourAdjuster.endHour(END_WORKING_HOUR));

	    for (; start.isBefore(end); start = start.plusHours(1), i++) {
		if (start.getHour() == START_LUNCH_HOUR) {
		    --i;
		    continue;
		}

		s = Slot.of(i, start, start.plusHours(1),
			doctors.get(r.nextInt(doctors.size())).getUserId());
		entry = new SimpleImmutableEntry<>(s, FALSE);

		slotMap.put(i, entry);
	    }
	}

	LOGGER.info("Initialized slots: {}.", slotMap);
    }

    private List<Doctor> getDoctors() {
	// Objects.requireNonNull(clientFactory);
	Objects.requireNonNull(mapperFactory);

	Response response = newDoctorsTargetBuilder().get();

	if (response.getStatus() != OK.getStatusCode()) {
	    throw new InternalServerErrorException(
		    "Unable to retrieve list of registered doctors. Availability service response code: "
			    + response.getStatus());
	}

	try {
	    return mapperFactory.getObjectMapper().readValue(
		    response.readEntity(String.class),
		    new TypeReference<List<Doctor>>() {
		    });
	} catch (IOException e) {
	    throw new InternalServerErrorException(
		    "Unable to retrieve list of registered doctors. ", e);
	}
    }

    public Optional<Slot> findSlotById(int id) {
	LOGGER.info("Looking for a slot with id: {}.", id);

	SimpleImmutableEntry<Slot, Boolean> e = slotMap.get(id);

	return e != null ? Optional.of(e.getKey()) : Optional.empty();
    }

    public Optional<List<Slot>> findSlotsByDate(String date) {
	LocalDateTime requestedDate = LocalDate.parse(date, ISO_LOCAL_DATE)
		.atTime(START_WORKING_HOUR, 0);

	LOGGER.info("Looking for a slot on the date: {}.", date);

	EqualOrAfter equalOrAfter = new EqualOrAfter(requestedDate);

	List<Slot> slots = slotMap
		.values()
		.stream()
		.filter(entry -> equalOrAfter.test(entry.getKey())
			&& !entry.getValue()).map(SimpleImmutableEntry::getKey)
		.collect(toList());

	return slots.isEmpty() ? Optional.empty() : Optional.of(slots);
    }

    public boolean isSlotReserved(int id) {
	return slotMap.containsKey(id) && slotMap.get(id).getValue();
    }

    public Optional<Slot> updateSlotAvailability(int id, boolean reserved) {
	SimpleImmutableEntry<Slot, Boolean> entry = null;

	if (slotMap.containsKey(id)
		&& (entry = slotMap.get(id)).getValue() != reserved) {
	    slotMap.put(id,
		    new SimpleImmutableEntry<Slot, Boolean>(entry.getKey(),
			    reserved));

	    return Optional.of(slotMap.get(id).getKey());
	}

	return Optional.empty();
    }

    private Builder newDoctorsTargetBuilder() {
	return client.target(SERVICE_URL).matrixParam("type", Doctor.name())
		.request().accept(APPLICATION_JSON);
    }

    final static class WorkingHourAdjuster implements TemporalAdjuster {
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
	    if (!(input instanceof LocalDateTime)) {
		throw new DateTimeException("Cannot adjust using: "
			+ input.getClass().getName());
	    }

	    return LocalDateTime.of(((LocalDateTime) input).toLocalDate(),
		    LocalTime.of(hour, 0, 0, 0));
	}
    }

    static class EqualOrAfter implements Predicate<Slot> {
	private final LocalDateTime dateTime;

	private EqualOrAfter(LocalDateTime dateTime) {
	    this.dateTime = dateTime;
	}

	@Override
	public boolean test(Slot s) {
	    LocalDate slotStartDate = s.getStartDateTime().toLocalDate();
	    LocalTime slotStartTime = s.getStartDateTime().toLocalTime();
	    LocalTime slotEndTime = s.getEndDateTime().toLocalTime();

	    return slotStartDate.equals(dateTime.toLocalDate())
		    && (slotStartTime.equals(dateTime.toLocalTime()) || slotStartTime
			    .isAfter(dateTime.toLocalTime()))
		    && slotEndTime.isAfter(slotStartTime);
	}
    }
}

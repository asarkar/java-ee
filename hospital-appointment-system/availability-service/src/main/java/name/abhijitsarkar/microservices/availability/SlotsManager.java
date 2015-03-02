package name.abhijitsarkar.microservices.availability;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import name.abhijitsarkar.microservices.extension.Consumes;
import name.abhijitsarkar.microservices.user.Doctor;

@ApplicationScoped
public class SlotsManager {
    @Consumes(value = Doctor.class)
    private List<Doctor> doctors;

    private final List<Slot> availableSlots;

    public SlotsManager() {
	availableSlots = getSlots();
    }

    private List<Slot> getSlots() {
	Objects.nonNull(doctors);

	LocalDateTime now = LocalDateTime.now();

	LocalDateTime aWeekFromNow = LocalDateTime.now().plusDays(7);

	int hours = (int) Duration.between(now, aWeekFromNow).toHours();

	Random r = new Random();

	return IntStream
		.range(1, hours)
		.mapToObj(
			index -> new Slot(index, now
				.format(ISO_OFFSET_DATE_TIME), now.plusHours(1)
				.format(ISO_OFFSET_DATE_TIME), doctors.get(
				r.nextInt(doctors.size())).getUserId()))
		.collect(toList());
    }

    public List<Slot> getAvailableSlots() {
	return availableSlots;
    }
}

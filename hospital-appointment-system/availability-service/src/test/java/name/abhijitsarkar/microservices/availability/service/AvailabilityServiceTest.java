package name.abhijitsarkar.microservices.availability.service;

import static java.time.DayOfWeek.MONDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.util.stream.Collectors.toList;
import static name.abhijitsarkar.microservices.availability.service.AvailabilityService.END_WORKING_HOUR;
import static name.abhijitsarkar.microservices.availability.service.AvailabilityService.START_WORKING_HOUR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import name.abhijitsarkar.microservices.availability.domain.Slot;
import name.abhijitsarkar.microservices.availability.service.AvailabilityService;
import name.abhijitsarkar.microservices.user.Doctor;
import name.abhijitsarkar.microservices.user.Users;

import org.junit.Before;
import org.junit.Test;

public class AvailabilityServiceTest {
    private AvailabilityService service;
    private Users users;

    @Before
    public void init() {
	service = new AvailabilityService();

	List<Doctor> doctors = IntStream.range(1, 3)
		.mapToObj(i -> String.format("doctor%d", i))
		.map(docId -> new Doctor(docId, "Good", "Doctor"))
		.collect(toList());

	users = mock(Users.class);
	when(users.getDoctors()).thenReturn(doctors);

	service.setUsers(users);
	service.initSlots();
    }

    @Test
    public void testFindSLotsForNextMonday() {
	String nextMonday = getNextMonday();

	Optional<List<Slot>> slots = service.findSlotsByDate(nextMonday);

	assertEquals(END_WORKING_HOUR - START_WORKING_HOUR - 1, slots.get()
		.size());
    }

    private String getNextMonday() {
	return ISO_LOCAL_DATE.format(LocalDate.now().with(nextOrSame(MONDAY)));
    }

    @Test
    public void testReserveSlot() {
	int firstSlotId = getFirstSlotForNextMonday();

	Optional<Slot> s = service.reserveSlot(firstSlotId);

	assertTrue(service.isSlotReserved(s.get().getId()));
    }

    private int getFirstSlotForNextMonday() {
	String nextMonday = getNextMonday();

	return service.findSlotsByDate(nextMonday).get().get(0).getId();
    }

    @Test
    public void testReserveSlotTwice() {
	int firstSlotId = getFirstSlotForNextMonday();

	Optional<Slot> s = service.reserveSlot(firstSlotId);
	s = service.reserveSlot(firstSlotId);

	assertFalse(s.isPresent());
    }

    @Test
    public void testRelinquishSlot() {
	int firstSlotId = getFirstSlotForNextMonday();

	Optional<Slot> s = service.reserveSlot(firstSlotId);
	s = service.relinquishSlot(firstSlotId);

	assertFalse(service.isSlotReserved(s.get().getId()));
    }

    @Test
    public void testRelinquishSlotTwice() {
	int firstSlotId = getFirstSlotForNextMonday();

	Optional<Slot> s = service.relinquishSlot(firstSlotId);
	s = service.relinquishSlot(firstSlotId);

	assertFalse(s.isPresent());
    }

    @Test
    public void testRelinquishSlotWhenNotReserved() {
	int firstSlotId = getFirstSlotForNextMonday();

	Optional<Slot> s = service.reserveSlot(firstSlotId);
	s = service.relinquishSlot(Integer.MIN_VALUE);

	assertFalse(s.isPresent());
    }

    @Test
    public void testSlotIsNotFoundAfterReserved() {
	String nextMonday = getNextMonday();
	Slot firstSlot = service.findSlotsByDate(nextMonday).get().get(0);

	service.reserveSlot(firstSlot.getId());

	assertFalse(service.findSlotsByDate(nextMonday).get()
		.contains(firstSlot));
    }
}

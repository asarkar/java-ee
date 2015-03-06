package name.abhijitsarkar.microservices.availability;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.toList;
import static name.abhijitsarkar.microservices.availability.AvailabilityService.END_WORKING_HOUR;
import static name.abhijitsarkar.microservices.availability.AvailabilityService.START_WORKING_HOUR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
    public void testAvailabilityForToday() {
	String today = ISO_LOCAL_DATE.format(LocalDate.now());

	Optional<List<Slot>> slots = service.findSlotsByDate(today);

	assertEquals(END_WORKING_HOUR - START_WORKING_HOUR - 1, slots.get()
		.size());
    }

    @Test
    public void testReserveSlot() {
	String today = ISO_LOCAL_DATE.format(LocalDate.now());

	int firstSlotId = service.findSlotsByDate(today).get().get(0).getId();

	Optional<Slot> s = service.reserveSlot(firstSlotId);

	assertTrue(service.isSlotReserved(s.get().getId()));
    }

    @Test
    public void testReserveSlotTwice() {
	String today = ISO_LOCAL_DATE.format(LocalDate.now());

	int firstSlotId = service.findSlotsByDate(today).get().get(0).getId();

	Optional<Slot> s = service.reserveSlot(firstSlotId);
	s = service.reserveSlot(firstSlotId);

	assertFalse(s.isPresent());
    }

    @Test
    public void testRelinquishSlot() {
	String today = ISO_LOCAL_DATE.format(LocalDate.now());

	int firstSlotId = service.findSlotsByDate(today).get().get(0).getId();

	Optional<Slot> s = service.reserveSlot(firstSlotId);
	s = service.relinquishSlot(firstSlotId);

	assertFalse(service.isSlotReserved(s.get().getId()));
    }

    @Test
    public void testRelinquishSlotTwice() {
	String today = ISO_LOCAL_DATE.format(LocalDate.now());

	int firstSlotId = service.findSlotsByDate(today).get().get(0).getId();

	Optional<Slot> s = service.relinquishSlot(firstSlotId);
	s = service.relinquishSlot(firstSlotId);

	assertFalse(s.isPresent());
    }

    @Test
    public void testRelinquishSlotWhenNotReserved() {
	String today = ISO_LOCAL_DATE.format(LocalDate.now());

	int firstSlotId = service.findSlotsByDate(today).get().get(0).getId();

	Optional<Slot> s = service.reserveSlot(firstSlotId);
	s = service.relinquishSlot(Integer.MIN_VALUE);

	assertFalse(s.isPresent());
    }
}

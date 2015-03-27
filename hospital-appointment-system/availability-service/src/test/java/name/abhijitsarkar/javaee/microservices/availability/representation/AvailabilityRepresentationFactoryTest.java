package name.abhijitsarkar.javaee.microservices.availability.representation;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.COALESCE_ARRAYS;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.PRETTY_PRINT;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.UriBuilder;

import name.abhijitsarkar.javaee.microservices.availability.domain.Slot;
import name.abhijitsarkar.javaee.microservices.availability.representation.AvailabilityRepresentationFactory;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.Representation;

public class AvailabilityRepresentationFactoryTest {
    private static final String BASE_URI = "http://localhost:8080/availability-service/";

    private AvailabilityRepresentationFactory factory;
    private UriBuilder slotUriBuilder;
    private UriBuilder slotsUriBuilder;

    public AvailabilityRepresentationFactoryTest() {
	factory = new AvailabilityRepresentationFactory();
	factory.setBaseUri(BASE_URI);

	slotUriBuilder = factory.newSlotUriBuilder();
	slotsUriBuilder = factory.newSlotsUriBuilder();
    }

    @Test
    public void testSlotRepresentationWithSelfLink() {
	Slot thisSlot = newSlotStub(2);

	Representation rep = factory.newSlotRepresentation(thisSlot,
		Optional.<Slot> empty(), Optional.<Slot> empty());

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("self");
	assertEquals(slotUriBuilder.build(thisSlot.getId()).toString(),
		link.getHref());
    }

    @Test
    public void testSlotRepresentationWithEditLinks() {
	Slot thisSlot = newSlotStub(2);

	Representation rep = factory.newSlotRepresentation(thisSlot,
		Optional.<Slot> empty(), Optional.<Slot> empty());

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	List<Link> links = rep.getLinksByRel("edit");

	links.stream().forEach(
		link -> {
		    String query = URI.create(link.getHref()).getQuery();

		    assertTrue("reserve=true".equals(query)
			    || "reserve=false".equals(query));
		});
    }

    @Test
    public void testSlotRepresentationWithNextAndPrevLinks()
	    throws JsonProcessingException {
	Slot thisSlot = newSlotStub(2);
	Slot prevSlot = newSlotStub(1);
	Slot nextSlot = newSlotStub(3);

	Representation rep = factory.newSlotRepresentation(thisSlot,
		Optional.of(prevSlot), Optional.of(nextSlot));

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("prev");
	assertEquals(slotUriBuilder.build(prevSlot.getId()).toString(),
		link.getHref());

	link = rep.getLinkByRel("next");
	assertEquals(slotUriBuilder.build(nextSlot.getId()).toString(),
		link.getHref());

	String doctorId = rep.getValue("doctorId", "").toString();
	assertEquals(thisSlot.getDoctorId(), doctorId);
    }

    @Test
    public void testSlotRepresentationWithPrevLink()
	    throws JsonProcessingException {
	Slot thisSlot = newSlotStub(2);
	Slot prevSlot = newSlotStub(1);

	Representation rep = factory.newSlotRepresentation(thisSlot,
		Optional.of(prevSlot), Optional.<Slot> empty());

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("prev");
	assertEquals(slotUriBuilder.build(prevSlot.getId()).toString(),
		link.getHref());

	link = rep.getLinkByRel("next");
	assertNull(link);

	String doctorId = rep.getValue("doctorId", "").toString();
	assertEquals(thisSlot.getDoctorId(), doctorId);
    }

    @Test
    public void testSlotRepresentationWithNextLink()
	    throws JsonProcessingException {
	Slot thisSlot = newSlotStub(1);
	Slot nextSlot = newSlotStub(2);

	Representation rep = factory.newSlotRepresentation(thisSlot,
		Optional.<Slot> empty(), Optional.of(nextSlot));

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("next");
	assertEquals(slotUriBuilder.build(nextSlot.getId()).toString(),
		link.getHref());

	link = rep.getLinkByRel("prev");
	assertNull(link);

	String doctorId = rep.getValue("doctorId", "").toString();
	assertEquals(thisSlot.getDoctorId(), doctorId);
    }

    @Test
    public void testSlotsRepresentation() {
	List<Slot> slots = newSlotsStub();
	Representation rep = factory.newSlotsRepresentation(slots);

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("self");

	assertEquals(slotsUriBuilder.build().toString(), link.getHref());

	link = rep.getLinkByRel("item");
	String href = link.getHref();

	assertTrue(slots.stream().anyMatch(
		slot -> href.endsWith(String.valueOf(slot.getId()))));

	link = rep.getLinkByRel("start");
	assertEquals(slotUriBuilder.build(slots.get(0).getId()).toString(),
		link.getHref());
    }

    private List<Slot> newSlotsStub() {
	return asList(newSlotStub(1), newSlotStub(2));
    }

    private Slot newSlotStub(int id) {
	LocalDateTime startDateTime = LocalDateTime.now().plusHours(id);
	LocalDateTime endDateTime = startDateTime.plusHours(1);

	return Slot.of(id, startDateTime, endDateTime, "doctor1");
    }
}

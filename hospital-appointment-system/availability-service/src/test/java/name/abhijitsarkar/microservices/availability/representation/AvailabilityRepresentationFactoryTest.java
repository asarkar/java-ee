package name.abhijitsarkar.microservices.availability.representation;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.COALESCE_ARRAYS;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.PRETTY_PRINT;
import static java.util.Arrays.asList;

import java.time.LocalDateTime;
import java.util.List;

import name.abhijitsarkar.microservices.availability.domain.Slot;
import name.abhijitsarkar.microservices.representation.ObjectMapperFactory;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theoryinpractise.halbuilder.api.Representation;

public class AvailabilityRepresentationFactoryTest {
    private static final String BASE_URI = "http://localhost:8080/availability-service";

    private AvailabilityRepresentationFactory factory = new AvailabilityRepresentationFactory(
	    BASE_URI);

    @Test
    public void testSlotRepresentationWhenOnlyNext() throws JsonProcessingException {
	Representation rep = factory.newSlotRepresentation(newSlotStub(1), -1,
		2);

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));
    }

    @Test
    public void testSlotsRepresentation() {
	Representation rep = factory.newSlotsRepresentation(newSlotsStub(), 1);

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));
    }

    private List<Slot> newSlotsStub() {
	return asList(newSlotStub(1), newSlotStub(2));
    }

    private Slot newSlotStub(int id) {
	LocalDateTime startDateTime = LocalDateTime.now().plusHours(id);
	LocalDateTime endDateTime = startDateTime.plusHours(1);

	return Slot.of(id, startDateTime, endDateTime, "asarkar");
    }
}

package name.abhijitsarkar.javaee.microservices.availability.representation;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.UriBuilder;

import name.abhijitsarkar.javaee.microservices.availability.domain.Slot;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

@Dependent
public class AvailabilityRepresentationFactory extends
	StandardRepresentationFactory {
    private static final URI[] HAL_JSON_FLAGS = new URI[] { PRETTY_PRINT,
	    COALESCE_ARRAYS };

    public static final String BASE_PATH = "slot";
    public static final String SLOT_PATH = "{id}";

    private String baseUri;

    public void setBaseUri(String baseUri) {
	this.baseUri = baseUri;
    }

    public Representation newSlotsRepresentation(List<Slot> slots) {
	UriBuilder slotsUriBuilder = newSlotsUriBuilder();

	Representation rep = newRepresentation(slotsUriBuilder.build());
	rep = rep.withBean(slots);

	int numSlots = slots.size();
	UriBuilder slotUriBuilder = newSlotUriBuilder();

	for (int i = 0; i < numSlots; i++) {
	    Optional<Slot> previousSlot = Optional.empty();
	    Optional<Slot> nextSlot = Optional.empty();

	    if (i - 1 >= 0) {
		previousSlot = Optional.of(slots.get(i - 1));
	    }
	    if (i + 1 < numSlots) {
		nextSlot = Optional.of(slots.get(i + 1));
	    }

	    rep.withRepresentation("slots",
		    newSlotRepresentation(slots.get(i), previousSlot, nextSlot));

	    rep.withLink("item", slotUriBuilder.build(slots.get(i).getId())
		    .toString());
	}

	if (numSlots > 0) {
	    rep.withLink("start", slotUriBuilder.build(slots.get(0).getId())
		    .toString());
	}

	return rep;
    }

    UriBuilder newSlotUriBuilder() {
	return newSlotsUriBuilder().path(BASE_PATH).path(SLOT_PATH);
    }

    UriBuilder newSlotsUriBuilder() {
	Objects.requireNonNull(baseUri);

	return UriBuilder.fromUri(baseUri);
    }

    public Representation newSlotRepresentation(Slot slot,
	    Optional<Slot> previousSlot, Optional<Slot> nextSlot) {
	UriBuilder slotUriBuilder = newSlotUriBuilder();

	Representation rep = newRepresentation(slotUriBuilder.build(
		slot.getId()).toString());

	rep.withLink("edit", newSlotUriBuilder().queryParam("reserve", "true")
		.build(slot.getId()).toString(), "reserve", "reserve", null,
		null);
	rep.withLink("edit", newSlotUriBuilder().queryParam("reserve", "false")
		.build(slot.getId()).toString(), "relinquish", "relinquish",
		null, null);
	/*
	 * I've yet to investigate deeper but looks like the
	 * 'JsonRepresentationWriter' that the 'StandardRepresentationFactory'
	 * is initialized with uses Bean introspection to read the properties
	 * and then converts them to JSON one at a time. It doesn't look at the
	 * annotations, if any, on these properties and hence ignores them
	 * completely. We don't want the default serialization for
	 * 'LcoalDateTime', so we write out each field separately. Of course,
	 * this couples the representation with the 'Slot' class such that any
	 * time the class changes, the representation will have to change to.
	 */
	rep.withProperty("id", slot.getId());
	rep.withProperty("startDateTime",
		ISO_LOCAL_DATE_TIME.format(slot.getStartDateTime()));
	rep.withProperty("endDateTime",
		ISO_LOCAL_DATE_TIME.format(slot.getEndDateTime()));
	rep.withProperty("doctorId", slot.getDoctorId());

	if (previousSlot.isPresent()) {
	    rep.withLink("prev",
		    slotUriBuilder.build(previousSlot.get().getId()).toString());
	}

	if (nextSlot.isPresent()) {
	    rep.withLink("next", slotUriBuilder.build(nextSlot.get().getId())
		    .toString());
	}

	return rep;
    }

    public String convertToString(Representation rep) {
	return rep.toString(HAL_JSON, HAL_JSON_FLAGS);
    }
}

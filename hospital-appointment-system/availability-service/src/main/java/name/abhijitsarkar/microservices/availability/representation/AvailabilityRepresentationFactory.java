package name.abhijitsarkar.microservices.availability.representation;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;

import name.abhijitsarkar.microservices.availability.domain.Slot;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

@Dependent
public class AvailabilityRepresentationFactory extends
	StandardRepresentationFactory {
    public static final String BASE_PATH = "slot";
    public static final String SLOT_PATH = "{id}";

    private String baseUri;

    public void setBaseUri(String baseUri) {
	this.baseUri = baseUri;
    }

    public Representation newSlotsRepresentation(List<Slot> slots) {
	StringBuilder str = null;

	try {
	    str = new StringBuilder(new URI(baseUri).resolve(BASE_PATH)
		    .toString());
	} catch (URISyntaxException e) {
	    throw new IllegalArgumentException("Base URI: " + baseUri
		    + " is not valid.");
	}

	Representation rep = newRepresentation(str.toString());
	rep = rep.withBean(slots);

	int numSlots = slots.size();

	/* Put a placeholder id that'll soon be replaced by actual slot id. */
	str.append("/").append(1);
	int slotIdStartIndex = str.lastIndexOf("/") + 1;

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

	    str.replace(slotIdStartIndex, str.length(),
		    String.valueOf(slots.get(i).getId()));
	    rep.withLink("item", str.toString());
	}

	if (numSlots > 0) {
	    str.replace(slotIdStartIndex, str.length(),
		    String.valueOf(slots.get(0).getId()));
	    rep.withLink("start", str.toString());
	}

	return rep;
    }

    public Representation newSlotRepresentation(Slot slot,
	    Optional<Slot> previousSlot, Optional<Slot> nextSlot) {
	StringBuilder str = null;

	try {
	    str = new StringBuilder(new URI(baseUri).resolve(BASE_PATH)
		    .resolve(String.valueOf(slot.getId())).toString());
	} catch (URISyntaxException e) {
	    throw new IllegalArgumentException("Base URI: " + baseUri
		    + " is not valid.");
	}

	Representation rep = newRepresentation(str.toString());

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

	int slotIdStartIndex = str.lastIndexOf("/") + 1;

	if (previousSlot.isPresent()) {
	    rep.withLink(
		    "prev",
		    str.replace(slotIdStartIndex, str.length(),
			    String.valueOf(previousSlot.get().getId()))
			    .toString());
	}

	if (nextSlot.isPresent()) {
	    rep.withLink(
		    "next",
		    str.replace(slotIdStartIndex, str.length(),
			    String.valueOf(nextSlot.get().getId())).toString());
	}

	return rep;
    }
}

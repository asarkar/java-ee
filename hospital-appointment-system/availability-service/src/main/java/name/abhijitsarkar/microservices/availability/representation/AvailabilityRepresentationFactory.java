package name.abhijitsarkar.microservices.availability.representation;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.util.List;

import name.abhijitsarkar.microservices.availability.domain.Slot;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

public class AvailabilityRepresentationFactory extends
	StandardRepresentationFactory {
    public static final String BASE_PATH = "slot";
    public static final String SLOT_PATH = "{id}";

    private final String baseUri;

    public AvailabilityRepresentationFactory(String baseUri) {
	this.baseUri = baseUri;
    }

    public Representation newSlotsRepresentation(List<Slot> slots,
	    int firstSlotId) {
	StringBuilder str = new StringBuilder(baseUri).append("/").append(
		BASE_PATH);

	Representation rep = newRepresentation(str.toString());
	rep = rep.withBean(slots);

	/* Put a placeholder id that'll soon be replaced by actual slot id. */
	str.append("/").append(1);
	int slotIdStartIndex = str.lastIndexOf("/") + 1;

	int numSlots = slots.size();

	for (int i = 0; i < numSlots; i++) {
	    int previousSlotId = -1;
	    int nextSlotId = -1;

	    if (i - 1 >= 0) {
		previousSlotId = slots.get(i - 1).getId();
	    }
	    if (i + 1 < numSlots) {
		nextSlotId = slots.get(i + 1).getId();
	    }

	    rep.withRepresentation(
		    "slots",
		    newSlotRepresentation(slots.get(i), previousSlotId,
			    nextSlotId));

	    str.replace(slotIdStartIndex, str.length(),
		    String.valueOf(slots.get(i).getId()));
	    rep.withLink("item", str.toString());
	}

	if (firstSlotId > 0) {
	    str.replace(slotIdStartIndex, str.length(),
		    String.valueOf(firstSlotId));
	    rep.withLink("start", str.toString());
	}

	return rep;
    }

    public Representation newSlotRepresentation(Slot slot, int previousSlotId,
	    int nextSlotId) {
	StringBuilder str = new StringBuilder(baseUri).append("/")
		.append(BASE_PATH).append("/").append(slot.getId());

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

	if (previousSlotId > 0) {
	    rep.withLink(
		    "prev",
		    str.replace(slotIdStartIndex, str.length(),
			    String.valueOf(previousSlotId)).toString());
	}

	if (nextSlotId > 0) {
	    rep.withLink(
		    "next",
		    str.replace(slotIdStartIndex, str.length(),
			    String.valueOf(nextSlotId)).toString());
	}

	return rep;
    }
}

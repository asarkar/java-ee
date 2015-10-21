package name.abhijitsarkar.javaee.microservices.salon.appointment;

import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UncheckedIOException;
import java.time.OffsetDateTime;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;

import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.salon.test.ObjectMapperFactory;

public class AppointmentTestUtil {
	public static String getAppointmentAsJson(OffsetDateTime startTime, OffsetDateTime endTime) {
		Appointment testAppt = new Appointment().withUserId(1l).withStartTime(startTime).withEndTime(endTime);

		try {
			return ObjectMapperFactory.getInstance().writeValueAsString(testAppt);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static ResultActions createNewAppointment(MockMvc mockMvc, String jsonAppt) throws Exception {
		return mockMvc.perform(post("/appointments").content(jsonAppt).contentType(APPLICATION_JSON).accept(HAL_JSON))
				.andExpect(content().contentType(HAL_JSON)).andExpect(status().isCreated());
	}
}

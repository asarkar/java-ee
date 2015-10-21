package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import static java.util.Arrays.asList;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import name.abhijitsarkar.javaee.microservices.salon.appointment.AppointmentAppConfig;
import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.salon.test.ContentMatcher;
import name.abhijitsarkar.javaee.microservices.salon.test.DeleteAndCompare;
import name.abhijitsarkar.javaee.microservices.salon.test.GetAndCompare;
import name.abhijitsarkar.javaee.microservices.salon.test.IdExtractor;
import name.abhijitsarkar.javaee.microservices.salon.test.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.salon.test.Pair;
import name.abhijitsarkar.javaee.microservices.salon.test.UpdateAndCompare;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppointmentAppConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AppointmentRepositoryCrudTest {
	private String jsonAppt;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@PostConstruct
	void init() throws JsonProcessingException {
		mockMvc = webAppContextSetup(webApplicationContext).build();

		OffsetDateTime startTime = OffsetDateTime.now();
		OffsetDateTime endTime = startTime.plusHours(1);

		Appointment testAppt = new Appointment().withUserId(1l).withStartTime(startTime).withEndTime(endTime);

		jsonAppt = ObjectMapperFactory.getInstance().writeValueAsString(testAppt);
	}

	@Before
	public void setUp() {
		appointmentRepository.deleteAllInBatch();
	}

	@After
	public void tearDown() {
		appointmentRepository.deleteAllInBatch();
	}

	@Test
	public void testCreateAppointment() throws Exception {
		Pair pair = new Pair(asList("_links", "self", "href"), ".*/appointments/\\d$");

		createNewAppointment().andExpect(content().string(new ContentMatcher(pair)));
	}

	@Test
	public void testGetAppointment() throws Exception {
		Pair pair = new Pair(asList("_links", "self", "href"), ".*/appointments/\\d$");

		createNewAppointment().andDo(new GetAndCompare(pair, mockMvc, "/appointments/%s"));
	}

	@Test
	public void testUpdateAppointment() throws Exception {
		OffsetDateTime startTime = OffsetDateTime.of(LocalDateTime.of(2015, 01, 01, 11, 00), ZoneOffset.of("-08:30"));

		String startTimeText = ObjectMapperFactory.getInstance().writeValueAsString(startTime).replaceAll("\"", "");

		AppointmentExtractor AppointmentExtractor = new AppointmentExtractor(startTime);
		Pair pair = new Pair(asList("startTime"), startTimeText);

		createNewAppointment()
				.andDo(new UpdateAndCompare<Appointment>(pair, mockMvc, "/appointments/%s", AppointmentExtractor));
	}

	@Test
	public void testDeleteAppointment() throws Exception {
		createNewAppointment().andDo(new DeleteAndCompare(mockMvc, "/appointments/%s"));
	}

	private ResultActions createNewAppointment() throws Exception {
		return mockMvc.perform(post("/appointments").content(jsonAppt).contentType(APPLICATION_JSON).accept(HAL_JSON))
				.andExpect(content().contentType(HAL_JSON)).andExpect(status().isCreated());
	}

	private final class AppointmentExtractor implements Function<MvcResult, Appointment> {
		private final OffsetDateTime newStartTime;

		private AppointmentExtractor(OffsetDateTime newStartTime) {
			this.newStartTime = newStartTime;
		}

		@Override
		public Appointment apply(MvcResult result) {
			try {
				String appointmentId = new IdExtractor().apply(result);

				String body = result.getResponse().getContentAsString();
				JsonNode getTree = ObjectMapperFactory.getInstance().readTree(body);
				long userId = getTree.path("userId").asLong();

				return new Appointment().withId(Long.valueOf(appointmentId)).withStartTime(newStartTime)
						.withEndTime(newStartTime.plusHours(2)).withUserId(userId);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
}

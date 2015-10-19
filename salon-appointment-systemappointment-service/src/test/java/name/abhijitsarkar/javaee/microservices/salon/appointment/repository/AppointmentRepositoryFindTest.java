package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.OffsetDateTime;

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
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import name.abhijitsarkar.javaee.microservices.salon.appointment.AppointmentAppConfig;
import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.salon.common.OffsetDateTimeConverter;
import name.abhijitsarkar.javaee.microservices.salon.test.ContentMatcher;
import name.abhijitsarkar.javaee.microservices.salon.test.IdExtractor;
import name.abhijitsarkar.javaee.microservices.salon.test.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.salon.test.Pair;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppointmentAppConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AppointmentRepositoryFindTest {
	private String jsonAppt;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private AppointmentRepository appointmentRepository;

	private OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

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
	public void testFindByUserId() throws Exception {
		createNewAppointment().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String createBody = createResult.getResponse().getContentAsString();

				String userId = ObjectMapperFactory.getInstance().readTree(createBody).path("userId").asText();

				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(String.format("/appointments/search/findByUserId"))
						.param("userId", userId).accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));

			}
		});
	}

	@Test
	public void testFindByStartTimeGreaterThanEqual() throws Exception {
		createNewAppointment().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String createBody = createResult.getResponse().getContentAsString();

				String startTime = ObjectMapperFactory.getInstance().readTree(createBody).path("startTime").asText();

				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(
						String.format("/appointments/search/findByStartTimeGreaterThanEqual"))
								.param("startTime", startTime).accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}

	@Test
	public void testFindByStartTimeLessThanEqual() throws Exception {
		createNewAppointment().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String createBody = createResult.getResponse().getContentAsString();

				String startTime = ObjectMapperFactory.getInstance().readTree(createBody).path("startTime").asText();

				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(
						String.format("/appointments/search/findByStartTimeLessThanEqual"))
								.param("startTime", startTime).accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}

	@Test
	public void testFindByStartTimeBetween() throws Exception {
		createNewAppointment().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String createBody = createResult.getResponse().getContentAsString();

				String startTimeText = ObjectMapperFactory.getInstance().readTree(createBody).path("startTime")
						.asText();

				OffsetDateTime startTime = converter.convertToEntityAttribute(startTimeText);

				String begin = converter.convertToDatabaseColumn(startTime.minusMinutes(30));
				String end = converter.convertToDatabaseColumn(startTime.plusMinutes(30));

				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(
						String.format("/appointments/search/findByStartTimeBetween")).param("begin", begin)
								.param("end", end).accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}

	private ResultActions createNewAppointment() throws Exception {
		return mockMvc.perform(post("/appointments").content(jsonAppt).contentType(APPLICATION_JSON).accept(HAL_JSON))
				.andExpect(content().contentType(HAL_JSON)).andExpect(status().isCreated());
	}

	@RequiredArgsConstructor
	private final class VerifyFindResult implements ResultHandler {
		private final String id;

		@Override
		public void handle(MvcResult findResult) throws Exception {
			status().isOk().match(findResult);
			content().contentType(HAL_JSON).match(findResult);

			String findBody = findResult.getResponse().getContentAsString();

			JsonNode tree = ObjectMapperFactory.getInstance().readTree(findBody);
			String appointment = tree.path("_embedded").path("appointments").get(0).toString();

			Pair pair = new Pair(asList("_links", "self", "href"), String.format(".*/appointments/%s$", id));

			assertTrue(new ContentMatcher(pair).matches(appointment));
		}
	}
}

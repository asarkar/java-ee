package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import static name.abhijitsarkar.javaee.microservices.salon.appointment.AppointmentTestUtil.createNewAppointment;
import static name.abhijitsarkar.javaee.microservices.salon.appointment.AppointmentTestUtil.getAppointmentAsJson;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import name.abhijitsarkar.javaee.microservices.salon.appointment.AppointmentAppConfig;
import name.abhijitsarkar.javaee.microservices.salon.appointment.VerifyFindResult;
import name.abhijitsarkar.javaee.microservices.salon.appointment.web.BeginningOfDayAdjuster;
import name.abhijitsarkar.javaee.microservices.salon.common.domain.OffsetDateTimeConverter;
import name.abhijitsarkar.javaee.microservices.salon.test.IdExtractor;
import name.abhijitsarkar.javaee.microservices.salon.test.ObjectMapperFactory;

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

		jsonAppt = getAppointmentAsJson(startTime, endTime);
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
	public void testFindByUserIdIn() throws Exception {
		createNewAppointment(mockMvc, jsonAppt).andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String createBody = createResult.getResponse().getContentAsString();

				String userId = ObjectMapperFactory.getInstance().readTree(createBody).path("userId").asText();

				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(String.format("/appointments/search/findByUserIdIn"))
						.param("userIds", userId).accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));

			}
		});
	}

	@Test
	public void testFindByStartTimeGreaterThanEqual() throws Exception {
		createNewAppointment(mockMvc, jsonAppt).andDo(new ResultHandler() {
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
		createNewAppointment(mockMvc, jsonAppt).andDo(new ResultHandler() {
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
		createNewAppointment(mockMvc, jsonAppt).andDo(new ResultHandler() {
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

	@Test
	public void testFindTodaysSchedule() throws Exception {
		createNewAppointment(mockMvc, jsonAppt).andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String createBody = createResult.getResponse().getContentAsString();

				String startTimeText = ObjectMapperFactory.getInstance().readTree(createBody).path("startTime")
						.asText();

				OffsetDateTime startTime = converter.convertToEntityAttribute(startTimeText);
				
				String begin = converter.convertToDatabaseColumn(startTime.with(new BeginningOfDayAdjuster()));
				String end = converter.convertToDatabaseColumn(startTime.plusDays(1));

				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(
						String.format("/appointments/search/findByStartTimeBetween")).param("begin", begin)
								.param("end", end).accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}
}

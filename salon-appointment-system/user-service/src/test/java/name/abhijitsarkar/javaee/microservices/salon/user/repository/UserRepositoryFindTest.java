package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Optional;

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
import name.abhijitsarkar.javaee.microservices.salon.test.ContentMatcher;
import name.abhijitsarkar.javaee.microservices.salon.test.IdExtractor;
import name.abhijitsarkar.javaee.microservices.salon.test.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.salon.test.Pair;
import name.abhijitsarkar.javaee.microservices.salon.user.UserAppConfig;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserAppConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserRepositoryFindTest {
	private String jsonUser;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	void init() throws JsonProcessingException {
		mockMvc = webAppContextSetup(webApplicationContext).build();

		User testUser = new User().withFirstName("john").withLastName("doe").withPhoneNum("111-111-1111");

		jsonUser = ObjectMapperFactory.getInstance().writeValueAsString(testUser);
	}

	@Before
	public void setUp() {
		userRepository.deleteAllInBatch();
	}

	@After
	public void tearDown() {
		userRepository.deleteAllInBatch();
	}

	@Test
	public void testFindByPhoneNum() throws Exception {
		createNewUser().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(String.format("/users/search/findByPhoneNum"))
						.param("phoneNum", "111-111-1111").accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));

			}
		});
	}

	@Test
	public void testFindByBadPhoneNum() throws Exception {
		createNewUser().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				MockHttpServletRequestBuilder findRequest = get(String.format("/users/search/findByPhoneNum"))
						.param("phoneNum", "111-111").accept(HAL_JSON);

				mockMvc.perform(findRequest).andExpect(jsonPath("$._embedded").isEmpty());
			}
		});
	}

	@Test
	public void testFindByPhoneNumEndingWith() throws Exception {
		createNewUser().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(String.format("/users/search/findByPhoneNumEndingWith"))
						.param("phoneNum", "1111").accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}

	@Test
	public void testFindByFirstAndLastNamesIgnoreCase() throws Exception {
		createNewUser().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(
						String.format("/users/search/findByFirstNameAndLastName")).param("firstName", "JOHN")
								.param("lastName", "dOE").accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}

	@Test
	public void testFindByEmail() throws Exception {
		User testUser = new User().withFirstName("john").withLastName("doe").withPhoneNum("111-111-1111")
				.withEmail(Optional.of("test@test.com"));
		String user = ObjectMapperFactory.getInstance().writeValueAsString(testUser);

		mockMvc.perform(post("/users").content(user).contentType(APPLICATION_JSON)).andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				String id = new IdExtractor().apply(createResult);

				MockHttpServletRequestBuilder findRequest = get(String.format("/users/search/findByEmail"))
						.param("email", "test@test.com").accept(HAL_JSON);

				mockMvc.perform(findRequest).andDo(new VerifyFindResult(id));
			}
		});
	}

	@Test
	public void testFindByEmailWhenThereIsNone() throws Exception {
		createNewUser().andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult createResult) throws Exception {
				MockHttpServletRequestBuilder findRequest = get(String.format("/users/search/findByEmail"))
						.param("email", "test@test.com").accept(HAL_JSON);

				mockMvc.perform(findRequest).andExpect(jsonPath("$._embedded").isEmpty());
			}
		});
	}

	private ResultActions createNewUser() throws Exception {
		return mockMvc.perform(post("/users").content(jsonUser).contentType(APPLICATION_JSON).accept(HAL_JSON))
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
			JsonNode users = tree.path("_embedded").path("users");

			assertFalse(users.isMissingNode());
			assertTrue(users.has(0));

			String usersText = users.get(0).toString();

			Pair pair = new Pair(asList("_links", "self", "href"), String.format(".*/users/%s$", id));

			assertTrue(new ContentMatcher(pair).matches(usersText));
		}
	}
}

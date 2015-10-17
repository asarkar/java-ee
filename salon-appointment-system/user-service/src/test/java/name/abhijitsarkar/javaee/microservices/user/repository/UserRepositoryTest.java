package name.abhijitsarkar.javaee.microservices.user.repository;

import static java.util.Arrays.asList;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import name.abhijitsarkar.javaee.microservices.user.UserApp;
import name.abhijitsarkar.javaee.microservices.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserApp.class)
@WebAppConfiguration
public class UserRepositoryTest {
	private String jsonUser;

	private MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	void init() throws JsonProcessingException {
		mapper.registerModule(new Jdk8Module());

		User testUser = new User().withFirstName("John").withLastName("Doe").withPhoneNum("111-111-1111");

		jsonUser = mapper.writeValueAsString(testUser);
	}

	@Before
	public void setUp() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		userRepository.deleteAllInBatch();
	}

	@Test
	public void testCreateUser() throws Exception {
		Pair pair = new Pair(asList("_links", "self", "href"), ".*/users/\\d$");

		createNewUser().andExpect(content().string(new ContentMatcher(pair)));
	}

	@Test
	public void testGetUser() throws Exception {
		Pair pair = new Pair(asList("_links", "self", "href"), ".*/users/\\d$");

		createNewUser().andDo(new GetAndCompare(pair));
	}

	@Test
	public void testUpdateUser() throws Exception {
		createNewUser().andDo(new UpdateAndCompare());
	}

	@Test
	public void testDeleteUser() throws Exception {
		createNewUser().andDo(new DeleteAndCompare());
	}

	private ResultActions createNewUser() throws Exception {
		return mockMvc.perform(post("/users").content(jsonUser).contentType(APPLICATION_JSON).accept(HAL_JSON))
				.andExpect(content().contentType(HAL_JSON)).andExpect(status().isCreated());
	}

	private static final class Pair {
		private List<String> paths;
		private String regex;

		public Pair(List<String> paths, String regex) {
			this.paths = paths;
			this.regex = regex;
		}
	}

	private final class ContentMatcher extends BaseMatcher<String> {
		private Pair pair;

		private ContentMatcher(Pair pair) {
			this.pair = pair;
		}

		@Override
		public boolean matches(Object object) {
			if (!(object instanceof String)) {
				return false;
			}

			String content = object.toString();

			try {
				JsonNode tree = mapper.readTree(content);

				for (String path : pair.paths) {
					tree = tree.path(path);
				}

				return tree.asText().matches(pair.regex);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}

		@Override
		public void describeTo(Description desc) {
			// TODO Auto-generated method stub
		}
	}

	private final class GetAndCompare implements ResultHandler {
		private Pair pair;

		private GetAndCompare(Pair pair) {
			this.pair = pair;
		}

		@Override
		public void handle(MvcResult createResult) throws Exception {
			String userId = extractUserId(createResult);

			mockMvc.perform(get(String.format("/users/%s", userId)).accept(HAL_JSON))
					.andExpect(content().contentType(HAL_JSON)).andExpect(status().isOk())
					.andExpect(content().string(new ContentMatcher(pair)));
		}
	};

	private final class UpdateAndCompare implements ResultHandler {
		@Override
		public void handle(MvcResult createResult) throws Exception {
			String userId = extractUserId(createResult);

			mockMvc.perform(get(String.format("/users/%s", userId)).accept(HAL_JSON)).andExpect(status().isOk())
					.andDo(new ResultHandler() {
						@Override
						public void handle(MvcResult getResult) throws Exception {
							String getBody = getResult.getResponse().getContentAsString();

							JsonNode getTree = mapper.readTree(getBody);

							String lastName = getTree.path("lastName").asText();
							String phoneNum = getTree.path("phoneNum").asText();

							User user = new User().withUserId(Long.valueOf(userId)).withFirstName("Johnny")
									.withLastName(lastName).withPhoneNum(phoneNum);

							Pair pair = new Pair(asList("firstName"), "Johnny");

							mockMvc.perform(patch(String.format("/users/%s", userId))
									.content(mapper.writeValueAsString(user)).accept(HAL_JSON))
									.andExpect(content().contentType(HAL_JSON)).andExpect(status().isOk())
									.andDo(new GetAndCompare(pair));
						}
					});
		}
	};

	private final class DeleteAndCompare implements ResultHandler {
		@Override
		public void handle(MvcResult createResult) throws Exception {
			String userId = extractUserId(createResult);

			mockMvc.perform(delete(String.format("/users/%s", userId)).accept(HAL_JSON))
					.andExpect(status().isNoContent()).andDo(new ResultHandler() {
						@Override
						public void handle(MvcResult deleteResult) throws Exception {
							mockMvc.perform(get(String.format("/users/%s", userId)).accept(HAL_JSON))
									.andExpect(status().isNotFound());
						}
					});
		}
	}

	private String extractUserId(MvcResult result) throws Exception {
		String body = result.getResponse().getContentAsString();

		String uri = mapper.readTree(body).path("_links").path("self").path("href").asText();

		return uri.substring(uri.lastIndexOf('/') + 1);
	}
}

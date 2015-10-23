package name.abhijitsarkar.javaee.microservices.salon.user.web;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.abhijitsarkar.javaee.microservices.salon.common.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.salon.user.UserAppConfig;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.Authority;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.Role;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;
import name.abhijitsarkar.javaee.microservices.salon.user.repository.AuthorityRepository;
import name.abhijitsarkar.javaee.microservices.salon.user.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserAppConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserControllerTest {
	private static final ObjectMapper OBJECT_MAPPER = ObjectMapperFactory.newObjectMapper();

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	private User testUser;
	private Authority authority;

	@PostConstruct
	void init() throws JsonProcessingException {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Before
	public void setUp() {
		userRepository.deleteAllInBatch();

		testUser = new User().withFirstName("John").withLastName("Doe").withPhoneNum("111-111-1111")
				.withEmail(Optional.of("johndoe@test.com"));

		User newUser = userRepository.save(testUser);
		testUser.withId(newUser.getId());

		authority = new Authority().withRole(Role.ROLE_USER).withUser(newUser);

		Authority newAuthority = authorityRepository.save(authority);

		authority.withId(newAuthority.getId());
	}

	@After
	public void tearDown() {
		authorityRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	public void testFindUserDetailsByUsername() throws Exception {
		mockMvc.perform(get("/users/search/findUserDetailsByUsername").param("username", testUser.getEmail().get())
				.accept(HAL_JSON)).andDo(new ResultHandler() {
					@Override
					public void handle(MvcResult findResult) throws Exception {
						String body = findResult.getResponse().getContentAsString();

						org.springframework.security.core.userdetails.User user = OBJECT_MAPPER.readValue(body,
								org.springframework.security.core.userdetails.User.class);

						assertEquals(authority.getRole().name(),
								user.getAuthorities().iterator().next().getAuthority());
					}
				});
	}
}

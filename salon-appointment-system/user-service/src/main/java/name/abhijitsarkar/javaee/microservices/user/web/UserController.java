package name.abhijitsarkar.javaee.microservices.user.web;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.abhijitsarkar.javaee.microservices.user.domain.User;
import name.abhijitsarkar.javaee.microservices.user.repository.UserRepository;

//@RestController
//@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
//public class UserController {
//	@Autowired
//	private UserRepository userRepository;
//
//	@RequestMapping(method = POST)
//	public ResponseEntity<String> create(@RequestBody User user, HttpServletRequest request) {
//		User newUser = userRepository.save(user);
//
//		String uri = request.getRequestURL().append("/").append(newUser.getUserId()).toString();
//
//		return new ResponseEntity<String>(String.format("{ uri : %s }", uri), CREATED);
//	}
//}

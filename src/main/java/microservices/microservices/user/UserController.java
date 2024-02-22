package microservices.microservices.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	private final UserService userService;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.topic-followUser}")
	private String topicFollowUser;

	@GetMapping("/all")
	@CrossOrigin
	public ResponseEntity<List<User>> getAll(Principal principal){
		String email = principal.getName();
		kafkaTemplate.send(topicFollowUser, email + " is on /user/all");
		return ResponseEntity.ok().body(userService.getAll());
	}

	@GetMapping("/email")
	@CrossOrigin
	public ResponseEntity<User> getUser(Principal principal){
		String email = principal.getName();
		try {
			User user = userService.getUserByEmail(email);
			kafkaTemplate.send(topicFollowUser, email + " is on /user/email");
			return ResponseEntity.ok().body(user);
		} catch (RuntimeException e){
			return ResponseEntity.notFound().build();
		}
	}
}

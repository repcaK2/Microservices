package microservices.microservices.demo;

import lombok.RequiredArgsConstructor;
import microservices.microservices.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DemoController {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.topic-followUser}")
	private String topicFollowUser;

	@GetMapping("/secured")
	public ResponseEntity<String> secured(
			Principal principal
	){
		String username = principal.getName();
		kafkaTemplate.send(topicFollowUser, username + ": is on secured endpoint");
		return ResponseEntity.ok("You have access to secured endpoint");
	}

	@PostMapping("/testAPI")
	@CrossOrigin
	public ResponseEntity<String> testAPI(
			@RequestBody String testMessage
	) {
		return ResponseEntity.ok().body("Message taken: " + testMessage);
	}
}
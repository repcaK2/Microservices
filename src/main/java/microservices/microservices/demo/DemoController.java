package microservices.microservices.demo;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {

	@GetMapping("/secured")
	public ResponseEntity<String> secured(){
		return ResponseEntity.ok("You have access to secured endpoint");
	}
}

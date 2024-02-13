package microservices.microservices.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/all")
	@CrossOrigin
	public List<User> getAll(){
		return userService.getAll();
	}

	@GetMapping("/email")
	@CrossOrigin
	public ResponseEntity<User> getUser(Principal principal){
		String email = principal.getName();
		try {
			User user = userService.getUserByEmail(email);
			return ResponseEntity.ok().body(user);
		} catch (RuntimeException e){
			return ResponseEntity.notFound().build();
		}
	}
}

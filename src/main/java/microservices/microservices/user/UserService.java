package microservices.microservices.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

	private final UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return userRepository.findAll().stream().map(user -> {
			User userNoSensiviteData = new User();
			userNoSensiviteData.setEmail(user.getEmail());
			userNoSensiviteData.setFirstName(user.getFirstName());
			userNoSensiviteData.setLastName(user.getLastName());
			userNoSensiviteData.setRole(user.getRole());
			userNoSensiviteData.setPassword("No access");
			return userNoSensiviteData;
		}).collect(Collectors.toList());
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
}

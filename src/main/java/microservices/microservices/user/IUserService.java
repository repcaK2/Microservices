package microservices.microservices.user;

import java.util.List;

public interface IUserService {

	List<User> getAll();
	User getUserByEmail(String email);
}

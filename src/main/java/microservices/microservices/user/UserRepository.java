package microservices.microservices.user;

import microservices.microservices.store.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("SELECT ci FROM User u JOIN u.cart ci WHERE u.email = :email")
	Optional<List<Cart>> findCartByEmail(String email);
}

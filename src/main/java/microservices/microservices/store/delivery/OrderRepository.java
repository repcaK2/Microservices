package microservices.microservices.store.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderRequest, Long> {
	Optional<OrderRequest> findOrderByEmail(String email);
}

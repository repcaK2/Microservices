package microservices.microservices.OrderServerReceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepositoryReceiver extends MongoRepository<Order, String> {
}

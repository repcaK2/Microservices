package microservices.microservices.store.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final OrderRepository orderRepository;
	private final ObjectMapper objectMapper;
	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${spring.kafka.topic-order}")
	private String topicOrder;

	@Override
	public List<OrderRequest> getAll() {
		return orderRepository.findAll();
	}

	@Override
	public OrderRequest findOrderByEmail(String email) {
		return orderRepository.findOrderByEmail(email)
				.orElseThrow(() -> new RuntimeException("Order not found with email:" + email));
	}

	@Override
	public OrderRequest createOrder(
			OrderRequest orderRequest,
			Principal principal
	) {
		String email = principal.getName();
		OrderRequest newOrderRequest = new OrderRequest();
		newOrderRequest.setEmail(email);
		newOrderRequest.setProductName(orderRequest.getProductName());
		newOrderRequest.setAdress(orderRequest.getAdress());
		newOrderRequest.setCreationDate(Instant.now().toString());
		ObjectMapper objectMapper = new ObjectMapper();
		String orderRequestString;
		try {
			orderRequestString = new ObjectMapper().writeValueAsString(newOrderRequest);
			kafkaTemplate.send(topicOrder, orderRequestString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return orderRepository.save(newOrderRequest);
	}
}

package microservices.microservices.OrderServerReceiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaOrderListener {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final OrderRepositoryReceiver orderRepositoryReceiver;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${spring.kafka.topic-logs}")
	private String logs;

	@KafkaListener(topics = "orderRequest", groupId = "stock-service")
	public void listenOrder(String orderJson) {
		try{
			Order order = objectMapper.readValue(orderJson, Order.class);
			orderRepositoryReceiver.save(order);
			kafkaTemplate.send(logs, "Data saved on database: "+ orderJson);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
}
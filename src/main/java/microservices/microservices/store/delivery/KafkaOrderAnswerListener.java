package microservices.microservices.store.delivery;

import lombok.RequiredArgsConstructor;
import microservices.microservices.OrderServerReceiver.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaOrderAnswerListener {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${spring.kafka.topic-logsAnswer}")
	private String orderServerAnswer;

	@KafkaListener(topics = "logs", groupId = "stock-service")
	public void listenOrder(String orderServerAnswerMessage) {
		try{
			kafkaTemplate.send(orderServerAnswer, "Server Answer: "+ orderServerAnswerMessage);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
}

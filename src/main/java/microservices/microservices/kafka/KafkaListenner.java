package microservices.microservices.kafka;

import lombok.RequiredArgsConstructor;
import microservices.microservices.Store.StoreRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenner {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@KafkaListener(topics = "new-order", groupId = "stock-service")
	public void listenStoreRequest(StoreRequest storeRequest){
		System.out.println("Received order " + storeRequest);
		kafkaTemplate.send("new-order", String.valueOf(storeRequest));
		//storage update logic
	}
}

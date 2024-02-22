package microservices.microservices.kafka.kafkaStartTest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessagePublisher implements CommandLineRunner{

	private final KafkaService kafkaService;

	@Override
	public void run(String... args) throws Exception {
		kafkaService.sendMessage("Kafka has started");
	}
}

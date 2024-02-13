package microservices.microservices.kafka.logsHibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class HibernateEventInterceptor implements PostInsertEventListener {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.topic-hibernateLogs}")
	private String topicHibernateLogs;

	@Override
	public void onPostInsert(PostInsertEvent postInsertEvent) {
		String serverTime = Instant.now().toString();
		String message = serverTime + " Inserted: " + postInsertEvent.getEntity().toString();
		kafkaTemplate.send(topicHibernateLogs, message);
	}

	@Override
	public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
		return false;
	}
}

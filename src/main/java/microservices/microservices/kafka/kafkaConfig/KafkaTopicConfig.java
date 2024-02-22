package microservices.microservices.kafka.kafkaConfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Value("${spring.kafka.topic-order}")
	private String topicOrder;

	@Value("${spring.kafka.topic-authenticatedUser}")
	private String topicAuthenticatedUser;

	@Value("${spring.kafka.topic-followUser}")
	private String topicFollowUser;

	@Value("${spring.kafka.topic-logs}")
	private String logs;

	@Value("${spring.kafka.topic-logsAnswer}")
	private String orderServerAnswer;

	@Value("${spring.kafka.topic-hibernateLogs}")
	private String topicHibernateLogs;

	@Value("${spring.kafka.topic-openai}")
	private String topicOpenai;

	@Bean
	public NewTopic testTopic() {
		return TopicBuilder
				.name("start")
				.build();
	}

	@Bean
	public NewTopic newOrder() {
		return TopicBuilder
				.name(topicOrder)
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic authenticatedUser() {
		return TopicBuilder
				.name(topicAuthenticatedUser)
				.build();
	}

	@Bean
	public NewTopic followUser() {
		return TopicBuilder
				.name(topicFollowUser)
				.build();
	}

	@Bean
	public NewTopic logs() {
		return TopicBuilder
				.name(logs)
				.build();
	}

	//returns created USER
	@Bean
	public NewTopic hibernateLogs() {
		return TopicBuilder
				.name(topicHibernateLogs)
				.build();
	}

	@Bean
	public NewTopic openai() {
		return TopicBuilder
				.name(topicOpenai)
				.build();
	}

	@Bean
	public NewTopic logAnswer() {
		return TopicBuilder
				.name(orderServerAnswer)
				.build();
	}
}

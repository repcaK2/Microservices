package microservices.microservices.chatGPT.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.microservices.chatGPT.dto.ChatGPTRequest;
import microservices.microservices.chatGPT.dto.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@RestController
@RequestMapping("/openai")
public class OpenaiController {

	@Value("${spring.kafka.topic-openai}")
	private String topicOpenai;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private RestTemplate template;

	@Value("${openai.url}")
	private String url;

	@Value("${openai.model}")
	private String model;

	@PostMapping("/chat")
	public String chat(@RequestBody String prompt) throws JsonProcessingException {
		ChatGPTRequest request = new ChatGPTRequest(model, prompt);
		ChatGptResponse chatGptResponse = template.postForObject(url, request, ChatGptResponse.class);
		String serverTime = Instant.now().toString();
		String jsonResponse = serverTime + " " + new ObjectMapper().writeValueAsString(chatGptResponse);
		kafkaTemplate.send(topicOpenai, jsonResponse);
		return chatGptResponse.getChoices().get(0).getMessage().getContent();
	}
}

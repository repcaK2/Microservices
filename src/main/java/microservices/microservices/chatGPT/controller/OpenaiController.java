package microservices.microservices.chatGPT.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import microservices.microservices.chatGPT.dto.ChatGPTRequest;
import microservices.microservices.chatGPT.dto.ChatGptResponse;
import microservices.microservices.store.product.Product;
import microservices.microservices.store.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/openai")
@RequiredArgsConstructor
public class OpenaiController {

	@Value("${spring.kafka.topic-openai}")
	private String topicOpenai;

	private final ProductRepository productRepository;

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
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(prompt);
		List<Product> productList = productRepository.findAll();
		// Products without id
		String productListNoId = productList.stream()
				.map(product -> String.format("Produkt: %s, Opis: %s, Cena: %d",
						product.getProductName(), product.getProductDescription(), product.getPrice()))
				.collect(Collectors.joining(", "));
		String changedPrompt = "przekarze ci liste moich wszystkich prodoktów z bazy danych " +
				"użytkownik zapyta żeby mu doradzić jakiś produkt " +
				"wtedy musisz sugerować i podpowiadać mu produkty które ci wysłałem " +
				"po zobaczeniu tej listy będzie widoczne odrazu zapytanie uzytkownika " +
				"ponieważ tekst który pisze obecnie jest generowany przed każdym zapytaniem " +
				"użytkownika pamiętaj że walutą jest PLN, pamiętaj również że nie możesz " +
				"wymyślać rzeczy np. mówić coś o fotelu czego nie ma w bazie danych np." +
				"że ma wysokie oceny" +
				productListNoId + prompt;
		ChatGPTRequest request = new ChatGPTRequest(model, changedPrompt);
		ChatGptResponse chatGptResponse = template.postForObject(url, request, ChatGptResponse.class);
		String serverTime = Instant.now().toString();
		String jsonResponse = serverTime + " " + new ObjectMapper().writeValueAsString(chatGptResponse);
		kafkaTemplate.send(topicOpenai, jsonResponse);
		assert chatGptResponse != null;
		return chatGptResponse.getChoices().get(0).getMessage().getContent();
	}
}

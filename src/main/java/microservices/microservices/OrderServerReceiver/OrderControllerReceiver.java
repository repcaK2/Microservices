package microservices.microservices.OrderServerReceiver;

import lombok.RequiredArgsConstructor;
import microservices.microservices.store.delivery.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class OrderControllerReceiver {

	private final OrderServiceReceiver orderServiceReceiver;

	@GetMapping("/orders")
	public List<Order> getAll() {
		return orderServiceReceiver.getAll();
	}
}

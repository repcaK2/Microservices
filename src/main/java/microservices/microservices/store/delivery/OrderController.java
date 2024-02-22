package microservices.microservices.store.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sendOrder")
public class OrderController {

	private final OrderService orderService;

	@GetMapping("/all")
	public List<OrderRequest> getAll() {
		return orderService.getAll();
	}

	@GetMapping("/email")
	public ResponseEntity<OrderRequest> findOrderByEmail(
			Principal principal
	) {
		String email = principal.getName();
		OrderRequest orderRequestByEmail = orderService.findOrderByEmail(email);
		return ResponseEntity.ok().body(orderRequestByEmail);
	}

	@PostMapping("/newOrder")
	public ResponseEntity<String> createNewOrder(
			@RequestBody OrderRequest orderRequest,
			Principal principal
	) {
		OrderRequest newOrder = orderService.createOrder(orderRequest, principal);
		return ResponseEntity.ok().body("Created new order: " + newOrder);
	}
}

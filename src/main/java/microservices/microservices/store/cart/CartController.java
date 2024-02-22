package microservices.microservices.store.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;

	@PostMapping("/email")
	public ResponseEntity<List<Cart>> getCartWithEmail(
			Principal principal
	) {
		String email = principal.getName();
		List<Cart> cart = cartService.getCartByEmail(email);
		return ResponseEntity.ok().body(cart);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addProductToCart(
			@RequestBody Long productId,
			Principal principal
	) {
		String email = principal.getName();
		cartService.addProductToCart(productId, email);
		return ResponseEntity.ok().body("Product has been added to cart");
	}
}

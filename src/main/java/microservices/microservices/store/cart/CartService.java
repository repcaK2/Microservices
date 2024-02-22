package microservices.microservices.store.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import microservices.microservices.store.product.Product;
import microservices.microservices.store.product.ProductRepository;
import microservices.microservices.user.User;
import microservices.microservices.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;

	@Override
	public List<Cart> getCartByEmail(String email) {
		return userRepository.findCartByEmail(email)
				.orElseThrow(
						() -> new RuntimeException("No cart found for email: " + email)
				);
	}

	@Override
	public void addProductToCart(Long productId, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(
						() -> new RuntimeException("User not found with email: " + email)
				);

		Product product = productRepository.findById(productId)
				.orElseThrow(
						() -> new RuntimeException("Product not found with id: " + productId)
				);

		Cart cart = new Cart();
		cart.setProduct(product);
		cart.setUser(user);
		cartRepository.save(cart);
		user.getCart().add(cart);
		userRepository.save(user);
	}
}

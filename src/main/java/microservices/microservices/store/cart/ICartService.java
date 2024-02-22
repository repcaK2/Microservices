package microservices.microservices.store.cart;

import java.util.List;

public interface ICartService {
	List<Cart> getCartByEmail(String email);
	void addProductToCart(Long productId, String email);
}

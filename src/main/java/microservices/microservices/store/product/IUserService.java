package microservices.microservices.store.product;

import java.util.List;

public interface IUserService {
	List<Product> getAll();
	Product getProductById(Long id);
	Product addProduct(Product product);
}

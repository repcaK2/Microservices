package microservices.microservices.store.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IUserService {

	private final ProductRepository productRepository;

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(
						() -> new EntityNotFoundException("Product not found")
				);
	}

	@Override
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
}

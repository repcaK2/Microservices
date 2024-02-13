package microservices.microservices.store.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAll() {
		List<Product> products = productService.getAll();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/id")
	public ResponseEntity<Product> getProduct(
			@RequestBody Long id
	) {
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@PostMapping("/add")
	public ResponseEntity<Product> saveProduct(
			@RequestBody Product product
	) {
		Product newProduct = productService.addProduct(product);
		return ResponseEntity.ok(newProduct);
	}
}

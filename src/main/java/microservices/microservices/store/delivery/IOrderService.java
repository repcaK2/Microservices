package microservices.microservices.store.delivery;

import java.security.Principal;
import java.util.List;

public interface IOrderService {
	List<OrderRequest> getAll();
	OrderRequest findOrderByEmail(String email);
	OrderRequest createOrder(OrderRequest orderRequest, Principal principal);
}

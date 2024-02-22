package microservices.microservices.OrderServerReceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceReceiver implements IOrderServiceReceiver {

	private final OrderRepositoryReceiver orderRepository;

	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
	}
}

package microservices.microservices.kafka.logsHibernate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {

	private final LogService logService;

	@GetMapping("/all")
	public List<Log> getLogs() {
		return logService.getLogs();
	}
}

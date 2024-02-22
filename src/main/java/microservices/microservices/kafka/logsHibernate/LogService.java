package microservices.microservices.kafka.logsHibernate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService implements ILogService{

	private final LogRepository logRepository;

	@Override
	public List<Log> getLogs() {
		return logRepository.findAll();
	}
}

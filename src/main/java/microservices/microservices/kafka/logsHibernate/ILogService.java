package microservices.microservices.kafka.logsHibernate;

import java.util.List;

public interface ILogService {
	List<Log> getLogs();
}

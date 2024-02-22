package microservices.microservices.kafka.logsHibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log")
public class Log {

	@Id
	@GeneratedValue
	private Long id;
	private String content;
	private String action;
	private Instant dateCreation;
}

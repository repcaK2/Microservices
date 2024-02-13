package microservices.microservices.kafka.logsHibernate;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HibernateListenerConfig {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void registerListeners() {
		HibernateEventInterceptor hibernateEventInterceptor = applicationContext.getBean(HibernateEventInterceptor.class);
		SessionFactoryImpl sessionFactoryImpl = entityManagerFactory.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(hibernateEventInterceptor);
	}
}
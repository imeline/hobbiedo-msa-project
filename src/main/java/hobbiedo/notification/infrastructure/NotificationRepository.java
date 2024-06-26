package hobbiedo.notification.infrastructure;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hobbiedo.notification.domain.Notification;

public interface NotificationRepository extends CrudRepository<Notification, String> {
	List<Notification> findByUuid(String uuid);
}

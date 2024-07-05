package hobbiedo.notification.application;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import hobbiedo.notification.dto.NotificationDTO;

public interface NotificationService {
	SseEmitter subscribe(String uuid);

	void sendNotification(String uuid, String crewName, String content, String crewProfileUrl);

	List<NotificationDTO> getNotificationList(String uuid);

	void deleteNotification(String notificationId);
}

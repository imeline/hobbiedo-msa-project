package hobbiedo.notification.dto;

import java.util.Comparator;
import java.util.List;

import hobbiedo.notification.domain.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDTO {
	private String notificationId;
	private String content;
	private String crewProfileUrl;

	public static NotificationDTO toDto(Notification notification) {
		return NotificationDTO.builder()
			.notificationId(notification.getId())
			.content(notification.getContent())
			.crewProfileUrl(notification.getCrewProfileUrl())
			.build();
	}

	public static List<NotificationDTO> toDtoList(List<Notification> notifications) {
		return notifications.stream()
			.sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
			.map(NotificationDTO::toDto)
			.toList();
	}
}

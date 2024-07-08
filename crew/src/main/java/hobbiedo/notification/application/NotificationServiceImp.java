package hobbiedo.notification.application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import hobbiedo.notification.domain.Notification;
import hobbiedo.notification.dto.NotificationDTO;
import hobbiedo.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {
	public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	private final NotificationRepository notificationRepository;

	@Override
	public SseEmitter subscribe(String uuid) {
		// 현재 클라이언트를 위한 sseEmitter 생성
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			// 연결
			sseEmitter.send(SseEmitter.event().name("connect"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// uuid 를 key 값으로 해서 sseEmitter 를 저장
		sseEmitters.put(uuid, sseEmitter);

		sseEmitter.onCompletion(() -> sseEmitters.remove(uuid));
		sseEmitter.onTimeout(() -> sseEmitters.remove(uuid));
		sseEmitter.onError((e) -> sseEmitters.remove(uuid));

		return sseEmitter;
	}

	@Override
	public void sendNotification(String uuid, String crewName, String content, String crewProfileUrl) {
		Notification notification = notificationRepository.save(Notification.builder()
				.uuid(uuid)
				.crewName(crewName)
				.content(content)
				.crewProfileUrl(crewProfileUrl)
				.createdAt(LocalDateTime.now())
				.build());

		if (sseEmitters.containsKey(uuid)) {
			SseEmitter sseEmitter = sseEmitters.get(uuid);
			try {
				sseEmitter.send(
					SseEmitter.event().data(NotificationDTO.toDto(notification)));

			} catch (Exception e) {
				sseEmitters.remove(uuid);
			}
		}
	}

	@Override
	public List<NotificationDTO> getNotificationList(String uuid) {
		List<Notification> notifications = notificationRepository.findByUuid(uuid);
		return NotificationDTO.toDtoList(notifications);
	}

	@Override
	public void deleteNotification(String notificationId) {
		notificationRepository.deleteById(notificationId);
	}
}

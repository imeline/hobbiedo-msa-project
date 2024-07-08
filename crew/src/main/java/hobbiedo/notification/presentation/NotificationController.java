package hobbiedo.notification.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import hobbiedo.global.base.BaseResponse;
import hobbiedo.global.status.SuccessStatus;
import hobbiedo.notification.application.NotificationService;
import hobbiedo.notification.dto.NotificationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "알림", description = "Notification API")
@RequestMapping("/v1/users/notification")
public class NotificationController {

	private final NotificationService notificationService;

	@Operation(summary = "실시간 알림 조회", description = "sse를 구독하고, api를 요청한 이후의 한 회원 알림들을 조회한다.")
	@GetMapping("/subscribe")
	public SseEmitter subscribe(@RequestHeader(name = "Uuid") String uuid) {
		return notificationService.subscribe(uuid);
	}

	@Operation(summary = "알림 목록 조회", description = "한 회원의 기존 저장되어 있던 알림 목록들을 조회한다.")
	@GetMapping("/list")
	public BaseResponse<List<NotificationDTO>> getNotificationList(
		@RequestHeader(name = "Uuid") String uuid) {
		return BaseResponse.onSuccess(SuccessStatus.FIND_NOTIFICATION_LIST,
			notificationService.getNotificationList(uuid));
	}

	@Operation(summary = "알림 삭제", description = "알림 id를 통해 알림을 삭제한다.")
	@DeleteMapping("/{notificationId}")
	public BaseResponse<Void> deleteNotification(@PathVariable String notificationId) {
		notificationService.deleteNotification(notificationId);
		return BaseResponse.onSuccess(SuccessStatus.DELETE_NOTIFICATION, null);
	}
}

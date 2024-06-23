package hobbiedo.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

	private Long commentId; // 댓글 번호
	private String writerUuid; // 작성자 uuid
	private String writerName; // 작성자 이름
	private String writerProfileImageUrl; // 작성자 프로필 이미지 url
	private String content; // 댓글 내용
	private Boolean isInCrew; // 소모임 회원 여부
	private LocalDateTime createdAt; // 작성일
}

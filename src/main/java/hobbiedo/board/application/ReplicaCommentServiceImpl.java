package hobbiedo.board.application;

import org.springframework.stereotype.Service;

import hobbiedo.board.domain.ReplicaComment;
import hobbiedo.board.infrastructure.ReplicaCommentRepository;
import hobbiedo.board.kafka.dto.BoardCommentDeleteDto;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.member.application.ReplicaMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplicaCommentServiceImpl implements ReplicaCommentService {

	private final ReplicaCommentRepository replicaCommentRepository;

	// 회원 서비스 추가
	private final ReplicaMemberService replicaMemberService;

	/**
	 * 게시글 댓글 CQRS 패턴을 통해 복제
	 * @param eventDto
	 */
	@Override
	public void createReplicaComment(BoardCommentUpdateDto eventDto) {

		replicaCommentRepository.save(
			ReplicaComment.builder()
				.boardId(eventDto.getBoardId())
				.commentId(eventDto.getCommentId())
				.writerUuid(eventDto.getWriterUuid())
				.content(eventDto.getContent())
				.isInCrew(eventDto.getIsInCrew())
				.createdAt(eventDto.getCreatedAt())
				.build()
		);
	}

	/**
	 * 게시글 댓글 CQRS 패턴을 통해 삭제
	 * @param eventDto
	 */
	@Override
	public void deleteReplicaComment(BoardCommentDeleteDto eventDto) {

		replicaCommentRepository.deleteByCommentId(eventDto.getCommentId());
	}
}

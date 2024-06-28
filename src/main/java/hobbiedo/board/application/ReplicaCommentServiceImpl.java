package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hobbiedo.board.domain.ReplicaBoard;
import hobbiedo.board.domain.ReplicaComment;
import hobbiedo.board.dto.CommentListResponseDto;
import hobbiedo.board.dto.CommentResponseDto;
import hobbiedo.board.infrastructure.ReplicaBoardRepository;
import hobbiedo.board.infrastructure.ReplicaCommentRepository;
import hobbiedo.board.kafka.dto.BoardCommentDeleteDto;
import hobbiedo.board.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.crew.infrastructure.ReplicaCrewRepository;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import hobbiedo.member.application.ReplicaMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplicaCommentServiceImpl implements ReplicaCommentService {

	private final ReplicaCommentRepository replicaCommentRepository;
	private final ReplicaBoardRepository replicaBoardRepository;
	private final ReplicaCrewRepository replicaCrewRepository;

	// 회원 서비스 추가
	private final ReplicaMemberService replicaMemberService;

	/**
	 * 게시글 댓글 CQRS 패턴을 통해 복제
	 * @param eventDto
	 */
	@Override
	public void createReplicaComment(BoardCommentUpdateDto eventDto) {

		// 기존 LocalDateTime 객체
		LocalDateTime localDateTime = eventDto.getCreatedAt();

		// LocalDateTime 을 ZonedDateTime 으로 변환
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));

		// ZonedDateTime 을 Instant 로 변환
		Instant instant = zonedDateTime.toInstant();

		replicaCommentRepository.save(
			ReplicaComment.builder()
				.boardId(eventDto.getBoardId())
				.commentId(eventDto.getCommentId())
				.writerUuid(eventDto.getWriterUuid())
				.content(eventDto.getContent())
				.isInCrew(eventDto.getIsInCrew())
				.createdAt(instant)
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

	/**
	 * 게시글 댓글 목록 조회
	 * @param boardId
	 * @param page
	 * @return
	 */
	@Override
	public CommentListResponseDto getCommentList(Long boardId, Pageable page) {

		// 게시글이 존재하는지 확인
		ReplicaBoard board = replicaBoardRepository.findByBoardId(boardId)
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		Page<ReplicaComment> comments = replicaCommentRepository.findByBoardId(boardId, page);

		// 댓글 리스트가 비어있어도 예외 처리하지 않음
		List<CommentResponseDto> commentResponseDtoList = comments.stream()
			.sorted(Comparator.comparing(ReplicaComment::getCreatedAt))
			.map(comment -> CommentResponseDto.builder()
				.commentId(comment.getCommentId())
				.writerUuid(comment.getWriterUuid())
				.writerName(
					replicaMemberService.getMemberName(comment.getWriterUuid()))
				.writerProfileImageUrl(
					replicaMemberService.getMemberProfileImageUrl(comment.getWriterUuid()))
				.content(comment.getContent())
				.isInCrew(isInCrew(comment.getWriterUuid(), board.getCrewId()))
				.createdAt(comment.getCreatedAt())
				.build())
			.toList();

		return CommentListResponseDto.commentDtoToCommentListResponseDto(comments.isLast(),
			commentResponseDtoList);
	}

	// 댓글 작성자가 해당 소모임에 속한 회원인지 확인
	private boolean isInCrew(String writerUuid, Long crewId) {

		if (crewId == null) {
			throw new ReadOnlyExceptionHandler(NOT_FOUND_CREW);
		}

		return replicaCrewRepository.findCrewByCrewIdAndMemberUuid(crewId, writerUuid).isPresent();
	}
}

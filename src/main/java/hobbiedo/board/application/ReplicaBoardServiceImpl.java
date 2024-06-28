package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hobbiedo.board.domain.ReplicaBoard;
import hobbiedo.board.dto.BoardDetailsResponseDto;
import hobbiedo.board.infrastructure.ReplicaBoardRepository;
import hobbiedo.board.infrastructure.ReplicaCommentRepository;
import hobbiedo.board.kafka.dto.BoardCommentCountUpdateDto;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardLikeCountUpdateDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;
import hobbiedo.crew.application.ReplicaCrewService;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import hobbiedo.member.application.ReplicaMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplicaBoardServiceImpl implements ReplicaBoardService {

	private final ReplicaBoardRepository replicaBoardRepository;
	private final ReplicaCommentRepository replicaCommentRepository;

	// 회원 서비스 추가
	private final ReplicaMemberService replicaMemberService;

	// 소모임 서비스 추가
	private final ReplicaCrewService replicaCrewService;

	/**
	 * 게시글 CQRS 패턴을 통해 복제
	 * @param eventDto
	 */
	@Override
	public void createReplicaBoard(BoardCreateEventDto eventDto) {

		// 기존 LocalDateTime 객체
		LocalDateTime localDateTime = eventDto.getCreatedAt();

		// LocalDateTime 을 ZonedDateTime 으로 변환하여 UTC로 변환
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
			.withZoneSameInstant(ZoneId.of("UTC"));

		// ZonedDateTime 을 Instant 로 변환
		Instant instant = zonedDateTime.toInstant();

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.boardId(eventDto.getBoardId())
				.crewId(eventDto.getCrewId())
				.content(eventDto.getContent())
				.writerUuid(eventDto.getWriterUuid())
				.pinned(eventDto.isPinned())
				.createdAt(instant)
				.updated(eventDto.isUpdated())
				.imageUrls(eventDto.getImageUrls())
				.commentCount(0)
				.likeCount(0)
				.build()
		);
	}

	/**
	 * 게시글 삭제
	 * @param eventDto
	 */
	@Override
	public void deleteReplicaBoard(BoardDeleteEventDto eventDto) {

		replicaBoardRepository.deleteByBoardId(eventDto.getBoardId());

		// 삭제된 게시글의 댓글 삭제
		replicaCommentRepository.deleteByBoardId(eventDto.getBoardId());
	}

	/**
	 * 게시글 수정
	 * @param eventDto
	 */
	@Override
	public void updateReplicaBoard(BoardUpdateEventDto eventDto) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.id(replicaBoard.getId())
				.boardId(replicaBoard.getBoardId())
				.crewId(replicaBoard.getCrewId())
				.content(eventDto.getContent())
				.writerUuid(replicaBoard.getWriterUuid())
				.pinned(replicaBoard.isPinned())
				.createdAt(replicaBoard.getCreatedAt())
				.updated(true)
				.imageUrls(eventDto.getImageUrls())
				.commentCount(replicaBoard.getCommentCount())
				.likeCount(replicaBoard.getLikeCount())
				.build()
		);
	}

	/**
	 * 게시글 고정
	 * @param eventDto
	 */
	@Override
	public void pinReplicaBoard(BoardPinEventDto eventDto) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.id(replicaBoard.getId())
				.boardId(replicaBoard.getBoardId())
				.crewId(replicaBoard.getCrewId())
				.content(replicaBoard.getContent())
				.writerUuid(replicaBoard.getWriterUuid())
				.pinned(true)
				.createdAt(replicaBoard.getCreatedAt())
				.updated(replicaBoard.isUpdated())
				.imageUrls(replicaBoard.getImageUrls())
				.commentCount(replicaBoard.getCommentCount())
				.likeCount(replicaBoard.getLikeCount())
				.build()
		);
	}

	/**
	 * 게시글 고정 해제
	 * @param eventDto
	 */
	@Override
	public void unPinReplicaBoard(BoardUnPinEventDto eventDto) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.id(replicaBoard.getId())
				.boardId(replicaBoard.getBoardId())
				.crewId(replicaBoard.getCrewId())
				.content(replicaBoard.getContent())
				.writerUuid(replicaBoard.getWriterUuid())
				.pinned(false)
				.createdAt(replicaBoard.getCreatedAt())
				.updated(replicaBoard.isUpdated())
				.imageUrls(replicaBoard.getImageUrls())
				.commentCount(replicaBoard.getCommentCount())
				.likeCount(replicaBoard.getLikeCount())
				.build()
		);
	}

	/**
	 * 게시글 조회
	 * @param boardId
	 * @return
	 */
	@Override
	public BoardDetailsResponseDto getBoard(Long boardId) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findByBoardId(boardId)
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		String writerName = replicaMemberService.getMemberName(replicaBoard.getWriterUuid());
		String writerProfileImageUrl = replicaMemberService.getMemberProfileImageUrl(
			replicaBoard.getWriterUuid());

		boolean hostStatus = replicaCrewService.isHost(replicaBoard.getCrewId(),
			replicaBoard.getWriterUuid());

		return BoardDetailsResponseDto.builder()
			.boardId(replicaBoard.getBoardId())
			.crewId(replicaBoard.getCrewId())
			.content(replicaBoard.getContent())
			.writerUuid(replicaBoard.getWriterUuid())
			.pinned(replicaBoard.isPinned())
			.createdAt(replicaBoard.getCreatedAt())
			.updated(replicaBoard.isUpdated())
			.imageUrls(replicaBoard.getImageUrls())
			.commentCount(replicaBoard.getCommentCount())
			.likeCount(replicaBoard.getLikeCount())
			.writerName(writerName)
			.writerProfileImageUrl(writerProfileImageUrl)
			.hostStatus(hostStatus)
			.build();
	}

	/**
	 * 한 소모임의 최신 게시글 조회
	 * @param crewId
	 */
	@Override
	public BoardDetailsResponseDto getLatestBoard(Long crewId) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findTopByCrewIdOrderByBoardIdDesc(crewId)
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND_IN_CREW));

		String writerName = replicaMemberService.getMemberName(replicaBoard.getWriterUuid());
		String writerProfileImageUrl = replicaMemberService.getMemberProfileImageUrl(
			replicaBoard.getWriterUuid());

		return BoardDetailsResponseDto.builder()
			.boardId(replicaBoard.getBoardId())
			.crewId(replicaBoard.getCrewId())
			.content(replicaBoard.getContent())
			.writerUuid(replicaBoard.getWriterUuid())
			.pinned(replicaBoard.isPinned())
			.createdAt(replicaBoard.getCreatedAt())
			.updated(replicaBoard.isUpdated())
			.imageUrls(replicaBoard.getImageUrls())
			.commentCount(replicaBoard.getCommentCount())
			.likeCount(replicaBoard.getLikeCount())
			.writerName(writerName)
			.writerProfileImageUrl(writerProfileImageUrl)
			.build();
	}

	/**
	 * 고정된 게시글 조회
	 * @param crewId
	 * @return
	 */
	@Override
	public BoardDetailsResponseDto getPinnedBoard(Long crewId) {

		Optional<ReplicaBoard> replicaBoard = replicaBoardRepository.findByPinnedTrueAndCrewId(
			crewId);

		if (!replicaBoard.isPresent()) {
			return null;
		}

		String writerName = replicaMemberService.getMemberName(replicaBoard.get().getWriterUuid());
		String writerProfileImageUrl = replicaMemberService.getMemberProfileImageUrl(
			replicaBoard.get().getWriterUuid());

		boolean hostStatus = replicaCrewService.isHost(replicaBoard.get().getCrewId(),
			replicaBoard.get().getWriterUuid());

		return BoardDetailsResponseDto.builder()
			.boardId(replicaBoard.get().getBoardId())
			.crewId(replicaBoard.get().getCrewId())
			.content(replicaBoard.get().getContent())
			.writerUuid(replicaBoard.get().getWriterUuid())
			.pinned(replicaBoard.get().isPinned())
			.createdAt(replicaBoard.get().getCreatedAt())
			.updated(replicaBoard.get().isUpdated())
			.imageUrls(replicaBoard.get().getImageUrls())
			.commentCount(replicaBoard.get().getCommentCount())
			.likeCount(replicaBoard.get().getLikeCount())
			.writerName(writerName)
			.writerProfileImageUrl(writerProfileImageUrl)
			.hostStatus(hostStatus)
			.build();
	}

	/**
	 * 게시글 댓글 수 변경
	 * @param eventDto
	 * @return
	 */
	@Override
	public void changeCommentCount(BoardCommentCountUpdateDto eventDto) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.id(replicaBoard.getId())
				.boardId(replicaBoard.getBoardId())
				.crewId(replicaBoard.getCrewId())
				.content(replicaBoard.getContent())
				.writerUuid(replicaBoard.getWriterUuid())
				.pinned(replicaBoard.isPinned())
				.createdAt(replicaBoard.getCreatedAt())
				.updated(replicaBoard.isUpdated())
				.imageUrls(replicaBoard.getImageUrls())
				.commentCount(replicaBoard.getCommentCount() + eventDto.getCommentCount())
				.likeCount(replicaBoard.getLikeCount())
				.build()
		);
	}

	/**
	 * 게시글 좋아요 수 변경
	 * @param eventDto
	 * @return
	 */
	@Override
	public void changeLikeCount(BoardLikeCountUpdateDto eventDto) {

		ReplicaBoard replicaBoard = replicaBoardRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new ReadOnlyExceptionHandler(BOARD_NOT_FOUND));

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.id(replicaBoard.getId())
				.boardId(replicaBoard.getBoardId())
				.crewId(replicaBoard.getCrewId())
				.content(replicaBoard.getContent())
				.writerUuid(replicaBoard.getWriterUuid())
				.pinned(replicaBoard.isPinned())
				.createdAt(replicaBoard.getCreatedAt())
				.updated(replicaBoard.isUpdated())
				.imageUrls(replicaBoard.getImageUrls())
				.commentCount(replicaBoard.getCommentCount())
				.likeCount(replicaBoard.getLikeCount() + eventDto.getLikeCount())
				.build()
		);
	}
}

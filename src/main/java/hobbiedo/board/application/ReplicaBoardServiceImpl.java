package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import org.springframework.stereotype.Service;

import hobbiedo.board.domain.ReplicaBoard;
import hobbiedo.board.infrastructure.ReplicaBoardRepository;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplicaBoardServiceImpl implements ReplicaBoardService {

	private final ReplicaBoardRepository replicaBoardRepository;

	/**
	 * 게시글 CQRS 패턴을 통해 복제
	 * @param eventDto
	 */
	@Override
	public void createReplicaBoard(BoardCreateEventDto eventDto) {

		replicaBoardRepository.save(
			ReplicaBoard.builder()
				.boardId(eventDto.getBoardId())
				.crewId(eventDto.getCrewId())
				.content(eventDto.getContent())
				.writerUuid(eventDto.getWriterUuid())
				.pinned(eventDto.isPinned())
				.createdAt(eventDto.getCreatedAt())
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
}

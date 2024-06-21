package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import org.springframework.stereotype.Service;

import hobbiedo.board.domain.ReplicaBoard;
import hobbiedo.board.infrastructure.ReplicaBoardRepository;
import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;
import hobbiedo.global.api.exception.handler.ReadOnlyExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplicaBoardServiceImpl implements ReplicaBoardService {

	private final ReplicaBoardRepository replicaBoardRepository;

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

	@Override
	public void deleteReplicaBoard(BoardDeleteEventDto eventDto) {

		replicaBoardRepository.deleteByBoardId(eventDto.getBoardId());
	}

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
}

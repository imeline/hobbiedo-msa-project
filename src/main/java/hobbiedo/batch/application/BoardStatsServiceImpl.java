package hobbiedo.batch.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.batch.domain.BoardStats;
import hobbiedo.batch.infrastructure.BoardStatsRepository;
import hobbiedo.batch.kafka.dto.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.BoardLikeUpdateDto;
import hobbiedo.global.api.exception.handler.BatchExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardStatsServiceImpl implements BoardStatsService {

	private final BoardStatsRepository boardStatsRepository;

	/**
	 * 게시글 통계 생성
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void createBoardStats(BoardCreateEventDto eventDto) {

		try {

			BoardStats boardStats = BoardStats.builder()
				.boardId(eventDto.getBoardId())
				.commentCount(0)
				.likeCount(0)
				.build();

			boardStatsRepository.save(boardStats);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 게시글 통계 삭제
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void deleteBoardStats(BoardDeleteEventDto eventDto) {

		try {

			boardStatsRepository.deleteByBoardId(eventDto.getBoardId());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 게시글 통계 댓글 수 업데이트
	 * 댓글 수는 증가 또는 감소 가능
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void updateBoardCommentStats(BoardCommentUpdateDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(eventDto.getBoardId())
				.commentCount(boardStats.getCommentCount() + eventDto.getCommentCount())
				.likeCount(boardStats.getLikeCount())
				.build()
		);
	}

	/**
	 * 게시글 통계 좋아요 수 업데이트
	 * 좋아요 수는 증가 또는 감소 가능
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void updateBoardLikeStats(BoardLikeUpdateDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(eventDto.getBoardId())
				.commentCount(boardStats.getCommentCount())
				.likeCount(boardStats.getLikeCount() + eventDto.getLikeCount())
				.build()
		);
	}
}

package hobbiedo.batch.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.batch.domain.BoardStats;
import hobbiedo.batch.dto.response.BoardCommentDto;
import hobbiedo.batch.dto.response.BoardLikeDto;
import hobbiedo.batch.dto.response.BoardStatsResponseDto;
import hobbiedo.batch.infrastructure.BoardStatsRepository;
import hobbiedo.batch.kafka.application.KafkaProducerService;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentDeleteDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCommentUpdateDto;
import hobbiedo.batch.kafka.dto.consumer.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardDeleteEventDto;
import hobbiedo.batch.kafka.dto.consumer.BoardLikeUpdateDto;
import hobbiedo.batch.kafka.dto.producer.BoardCommentCountUpdateDto;
import hobbiedo.batch.kafka.dto.producer.BoardLikeCountUpdateDto;
import hobbiedo.global.api.exception.handler.BatchExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardStatsServiceImpl implements BoardStatsService {

	private final BoardStatsRepository boardStatsRepository;

	// kafka producer service 추가
	private final KafkaProducerService kafkaProducerService;

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
	 * 게시글 통계 댓글 수 증가
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void increaseBoardCommentStats(BoardCommentUpdateDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(eventDto.getBoardId())
				.commentCount(boardStats.getCommentCount() + 1)
				.likeCount(boardStats.getLikeCount())
				.build()
		);

		// 게시글의 댓글 수 업데이트 이벤트 메시지 전송
		BoardCommentCountUpdateDto commentCountUpdateDto = BoardCommentCountUpdateDto.builder()
			.boardId(eventDto.getBoardId())
			.commentCount(boardStats.getCommentCount() + 1)
			.build();

		kafkaProducerService.sendUpdateCommentCountMessage(commentCountUpdateDto);
	}

	/**
	 * 게시글 통계 댓글 수 삭제
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void decreaseBoardCommentStats(BoardCommentDeleteDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(eventDto.getBoardId())
				.commentCount(boardStats.getCommentCount() - 1)
				.likeCount(boardStats.getLikeCount())
				.build()
		);

		// 게시글의 댓글 수 삭제 이벤트 메시지 전송
		BoardCommentCountUpdateDto commentCountUpdateDto = BoardCommentCountUpdateDto.builder()
			.boardId(eventDto.getBoardId())
			.commentCount(boardStats.getCommentCount() - 1)
			.build();

		kafkaProducerService.sendDeleteCommentCountMessage(commentCountUpdateDto);
	}

	/**
	 * 게시글 통계 좋아요 수 증가
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void increaseBoardLikeStats(BoardLikeUpdateDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(eventDto.getBoardId())
				.commentCount(boardStats.getCommentCount())
				.likeCount(boardStats.getLikeCount() + 1)
				.build()
		);

		// 게시글의 좋아요 수 업데이트 이벤트 메시지 전송
		BoardLikeCountUpdateDto likeCountUpdateDto = BoardLikeCountUpdateDto.builder()
			.boardId(eventDto.getBoardId())
			.likeCount(boardStats.getLikeCount() + 1)
			.build();

		kafkaProducerService.sendUpdateLikeCountMessage(likeCountUpdateDto);
	}

	/**
	 * 게시글 통계 좋아요 수 삭제
	 * @param eventDto
	 */
	@Override
	@Transactional
	public void decreaseBoardLikeStats(BoardLikeUpdateDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(eventDto.getBoardId())
				.commentCount(boardStats.getCommentCount())
				.likeCount(boardStats.getLikeCount() - 1)
				.build()
		);

		// 게시글의 좋아요 수 삭제 이벤트 메시지 전송
		BoardLikeCountUpdateDto likeCountUpdateDto = BoardLikeCountUpdateDto.builder()
			.boardId(eventDto.getBoardId())
			.likeCount(boardStats.getLikeCount() - 1)
			.build();

		kafkaProducerService.sendDeleteLikeCountMessage(likeCountUpdateDto);
	}

	/**
	 * 게시글 통계 조회
	 * @param boardId
	 * @return
	 */
	@Override
	public BoardStatsResponseDto getBoardStats(Long boardId) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(boardId)
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		return BoardStatsResponseDto.builder()
			.commentCount(boardStats.getCommentCount())
			.likeCount(boardStats.getLikeCount())
			.build();
	}

	/**
	 * 게시글 통계 댓글 수 업데이트
	 */
	@Override
	@Transactional
	public void updateBoardCommentStats(BoardCommentDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		// 게시글 통계 댓글 수 업데이트
		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(boardStats.getBoardId())
				.commentCount(
					boardStats.getCommentCount() + eventDto.getCommentCount()) // 댓글 수 업데이트
				.likeCount(boardStats.getLikeCount())
				.build()
		);

		// 게시글의 댓글 수 변경 이벤트 메시지 전송
		BoardCommentCountUpdateDto commentCountUpdateDto = BoardCommentCountUpdateDto.builder()
			.boardId(eventDto.getBoardId())
			.commentCount(eventDto.getCommentCount())
			.build();

		kafkaProducerService.sendChangeCommentCountMessage(commentCountUpdateDto);
	}

	/**
	 * 게시글 통계 좋아요 수 업데이트
	 */
	@Override
	@Transactional
	public void updateBoardLikeStats(BoardLikeDto eventDto) {

		BoardStats boardStats = boardStatsRepository.findByBoardId(eventDto.getBoardId())
			.orElseThrow(() -> new BatchExceptionHandler(BOARD_STATS_NOT_FOUND));

		// 게시글 통계 좋아요 수 업데이트
		boardStatsRepository.save(
			BoardStats.builder()
				.id(boardStats.getId())
				.boardId(boardStats.getBoardId())
				.commentCount(boardStats.getCommentCount())
				.likeCount(
					boardStats.getLikeCount() + eventDto.getLikeCount()) // 좋아요 수 업데이트
				.build()
		);

		// 게시글의 좋아요 수 변경 이벤트 메시지 전송
		BoardLikeCountUpdateDto likeCountUpdateDto = BoardLikeCountUpdateDto.builder()
			.boardId(eventDto.getBoardId())
			.likeCount(eventDto.getLikeCount())
			.build();

		kafkaProducerService.sendChangeLikeCountMessage(likeCountUpdateDto);
	}
}

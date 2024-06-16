package hobbiedo.batch.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.batch.domain.BoardStats;
import hobbiedo.batch.infrastructure.BoardStatsRepository;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
import hobbiedo.batch.kafka.dto.BoardDeleteEventDto;
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
}

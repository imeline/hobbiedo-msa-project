package hobbiedo.batch.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.batch.domain.BoardStats;
import hobbiedo.batch.infrastructure.BoardStatsRepository;
import hobbiedo.batch.kafka.dto.BoardCreateEventDto;
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
				.commentCount(0L)
				.likeCount(0L)
				.build();

			boardStatsRepository.save(boardStats);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}

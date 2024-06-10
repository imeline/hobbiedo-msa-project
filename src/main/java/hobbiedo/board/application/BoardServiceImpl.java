package hobbiedo.board.application;

import static hobbiedo.global.api.code.status.ErrorStatus.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hobbiedo.board.domain.Board;
import hobbiedo.board.domain.BoardImage;
import hobbiedo.board.dto.request.BoardUploadRequestDto;
import hobbiedo.board.infrastructure.BoardImageRepository;
import hobbiedo.board.infrastructure.BoardRepository;
import hobbiedo.global.api.exception.handler.BoardExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;

	/**
	 * 게시글 생성
	 * 이미지 url 리스트가 비어있을 경우 게시글만 생성
	 * @param crewId
	 * @param uuid
	 * @param boardUploadRequestDto
	 */
	@Override
	@Transactional
	public void createPostWithImages(Long crewId, String uuid,
			BoardUploadRequestDto boardUploadRequestDto) {

		Board createdBoard = createPost(crewId, uuid, boardUploadRequestDto);

		// 이미지 업로드 기능
		List<String> imageUrls = boardUploadRequestDto.getImageUrls();

		if (imageUrls != null && !imageUrls.isEmpty()) {

			for (int i = 0; i < imageUrls.size(); i++) {

				String imageUrl = imageUrls.get(i);

				boardImageRepository.save(
						BoardImage.builder()
								.board(createdBoard) // 게시글 엔티티를 설정
								.imageUrl(imageUrl) // URL 사용
								.orderIndex(i) // 리스트 순서를 이미지의 순서로 설정
								.build()
				);
			}
		}
	}

	// 게시글 생성
	private Board createPost(Long crewId, String uuid,
			BoardUploadRequestDto boardUploadRequestDto) {

		String title = boardUploadRequestDto.getTitle();
		String content = boardUploadRequestDto.getContent();

		// 게시글 제목과 내용이 비어있을 경우 예외 처리
		if (title == null || title.trim().isEmpty()) {

			throw new BoardExceptionHandler(CREATE_POST_TITLE_EMPTY);
		} else if (content == null || content.trim().isEmpty()) {

			throw new BoardExceptionHandler(CREATE_POST_CONTENT_EMPTY);
		}

		return boardRepository.save(
				Board.builder()
						.crewId(crewId)
						.writerUuid(uuid)
						.title(boardUploadRequestDto.getTitle())
						.content(boardUploadRequestDto.getContent())
						.build()
		);
	}
}

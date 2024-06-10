package hobbiedo.board.application;

import hobbiedo.board.dto.request.BoardUploadRequestDto;

public interface BoardService {

	void createPostWithImages(Long crewId, String uuid,
			BoardUploadRequestDto boardUploadRequestDto);
}

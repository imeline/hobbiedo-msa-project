package hobbiedo.board.application;

import hobbiedo.board.kafka.dto.BoardCreateEventDto;
import hobbiedo.board.kafka.dto.BoardDeleteEventDto;
import hobbiedo.board.kafka.dto.BoardPinEventDto;
import hobbiedo.board.kafka.dto.BoardUnPinEventDto;
import hobbiedo.board.kafka.dto.BoardUpdateEventDto;

public interface ReplicaBoardService {

	void createReplicaBoard(BoardCreateEventDto eventDto);

	void deleteReplicaBoard(BoardDeleteEventDto eventDto);

	void updateReplicaBoard(BoardUpdateEventDto eventDto);

	void pinReplicaBoard(BoardPinEventDto eventDto);

	void unPinReplicaBoard(BoardUnPinEventDto eventDto);
}

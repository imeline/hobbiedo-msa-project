package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class BoardExceptionHandler extends GeneralException {
	public BoardExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class BobbyExceptionHandler extends GeneralException {
	public BobbyExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


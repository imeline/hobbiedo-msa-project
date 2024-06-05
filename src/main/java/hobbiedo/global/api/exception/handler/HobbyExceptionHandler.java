package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class HobbyExceptionHandler extends GeneralException {
	public HobbyExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


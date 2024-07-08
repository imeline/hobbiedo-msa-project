package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class ReadOnlyExceptionHandler extends GeneralException {
	public ReadOnlyExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


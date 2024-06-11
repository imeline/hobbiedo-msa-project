package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class BatchExceptionHandler extends GeneralException {
	public BatchExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


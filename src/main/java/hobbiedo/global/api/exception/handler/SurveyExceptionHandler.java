package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class SurveyExceptionHandler extends GeneralException {
	public SurveyExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


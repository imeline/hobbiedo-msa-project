package hobbiedo.user.auth.global.api.exception.handler;

import hobbiedo.user.auth.global.api.code.BaseErrorCode;
import hobbiedo.user.auth.global.api.exception.GeneralException;

public class UserExceptionHandler extends GeneralException {
	public UserExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}

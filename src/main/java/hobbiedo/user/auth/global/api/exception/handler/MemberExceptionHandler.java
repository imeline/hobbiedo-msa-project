package hobbiedo.user.auth.global.api.exception.handler;

import hobbiedo.user.auth.global.api.code.BaseErrorCode;
import hobbiedo.user.auth.global.api.exception.GeneralException;

public class MemberExceptionHandler extends GeneralException {
	public MemberExceptionHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}

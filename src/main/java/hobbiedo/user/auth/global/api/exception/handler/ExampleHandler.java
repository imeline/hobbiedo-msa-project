package hobbiedo.user.auth.global.api.exception.handler;

import hobbiedo.user.auth.global.api.code.BaseErrorCode;
import hobbiedo.user.auth.global.api.exception.GeneralException;

public class ExampleHandler extends GeneralException {
	public ExampleHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}

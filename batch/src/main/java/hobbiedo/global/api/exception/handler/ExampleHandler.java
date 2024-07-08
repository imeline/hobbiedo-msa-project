package hobbiedo.global.api.exception.handler;

import hobbiedo.global.api.code.BaseErrorCode;
import hobbiedo.global.api.exception.GeneralException;

public class ExampleHandler extends GeneralException {
	public ExampleHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}


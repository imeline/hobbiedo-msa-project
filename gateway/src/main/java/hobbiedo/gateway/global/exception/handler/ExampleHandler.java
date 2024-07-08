package hobbiedo.gateway.global.exception.handler;

import hobbiedo.gateway.global.code.BaseErrorCode;
import hobbiedo.gateway.global.exception.GeneralException;

public class ExampleHandler extends GeneralException {
	public ExampleHandler(BaseErrorCode errorCode) {
		super(errorCode);
	}
}

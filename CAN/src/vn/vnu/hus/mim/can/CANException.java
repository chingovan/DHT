package vn.vnu.hus.mim.can;

public class CANException extends Exception {

	public CANException() {
		super();
	}

	public CANException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CANException(String message, Throwable cause) {
		super(message, cause);
	}

	public CANException(String message) {
		super(message);
	}

	public CANException(Throwable cause) {
		super(cause);
	}

}

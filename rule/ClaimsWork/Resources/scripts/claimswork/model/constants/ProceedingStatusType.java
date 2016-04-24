package claimswork.model.constants;

/**
 * @author Kayra created 27-01-2016
 */

public enum ProceedingStatusType {
	UNKNOWN(0);

	private int code;

	ProceedingStatusType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ProceedingStatusType getType(int code) {
		for (ProceedingStatusType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		return UNKNOWN;
	}
}

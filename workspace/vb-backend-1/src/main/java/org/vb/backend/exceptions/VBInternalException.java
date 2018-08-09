package org.vb.backend.exceptions;

public class VBInternalException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5853198338132971487L;

	public VBInternalException(String message) {
		super(message);
	}
	
	public VBInternalException(String message, Throwable cause) {
		super(message, cause);
	}
}

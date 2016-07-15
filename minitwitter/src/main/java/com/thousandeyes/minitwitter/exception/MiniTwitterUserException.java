package com.thousandeyes.minitwitter.exception;

public class MiniTwitterUserException extends MiniTwitterException{
	
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public MiniTwitterUserException(String message)
	{
		super(message);
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}

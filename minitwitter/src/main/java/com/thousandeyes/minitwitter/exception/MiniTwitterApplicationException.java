package com.thousandeyes.minitwitter.exception;

public class MiniTwitterApplicationException extends MiniTwitterException{
		
		
		private static final long serialVersionUID = 1L;
		
		private String message;
		
		public MiniTwitterApplicationException(String message)
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

package br.com.secoti.activerecord.exception;

public class ActiveRecordException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ActiveRecordException() {
		super();
	}
	
	public ActiveRecordException(String msg) {
		super(msg);
	}
	
	public ActiveRecordException(Throwable e) {
		super(e);
	}
	
	public ActiveRecordException(String msg, Throwable e) {
		super(msg, e);
	}	
}
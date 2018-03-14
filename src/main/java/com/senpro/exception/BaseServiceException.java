package com.senpro.exception;

public class BaseServiceException extends Exception{
	
	private String code;
	private String errorMsg ;
	
	public BaseServiceException() {
		
	}
	
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}

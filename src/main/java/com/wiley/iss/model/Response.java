package com.wiley.iss.model;

/**
 * Response object sent to client upon fileupload
 *
 */
public class Response {
	private boolean success;
	private String message;
	private String details;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Response(){
	}
	public Response(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}

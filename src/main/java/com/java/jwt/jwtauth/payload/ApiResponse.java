/**
 * 
 */
package com.java.jwt.jwtauth.payload;

/**
 * @author Mangesh
 * @date 2 Sep 2018
 * @company self
 *
 */
public class ApiResponse {
	private Boolean success;
	private String message;

	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
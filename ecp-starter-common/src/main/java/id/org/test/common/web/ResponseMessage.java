package id.org.test.common.web;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
	private static final long serialVersionUID = -1956773799183380271L;

	public static String ERROR_MSG_TITLE = "Error";
	public static String SUCCESS_MSG_TITLE = "Success";
	String message;
	String status;
	Integer code;
	String messageBody;
	Object data;

	public ResponseMessage() {
	}

	public ResponseMessage(String status, Integer code, String message, Object data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public ResponseMessage(String status, Integer code, String message, String messageBody) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.messageBody = messageBody;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ResponseMessage showMessageSuccess(Integer statusCode, String message, Object data) {
		return new ResponseMessage(SUCCESS_MSG_TITLE, statusCode, message, data);
	}
	
	public static ResponseMessage showMessageError(Integer statusCode, String message, String messageBody) {
		return new ResponseMessage(ERROR_MSG_TITLE, statusCode, message, messageBody);
	}

}

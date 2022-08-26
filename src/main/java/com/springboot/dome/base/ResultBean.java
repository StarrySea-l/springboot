package com.springboot.dome.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class ResultBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3655295940128562278L;

	public static final String SUCCESS = "success";
	
	public static final String FAIL = "fail";		//用户错误
	
	public static final String ERROR = "error";		//系统错误
	
	private String status;
	
	private String errorCode;
	
	private String message;
	
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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

	@SuppressWarnings("unchecked")
	@JSONField(serialize = false)
	public <T> T getDataObject(){
		return (T) data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public boolean succeed(){
		return SUCCESS.equals(getStatus());
	}
	
	public boolean errored(){
		return ERROR.equals(getStatus());
	}
	
	public boolean failed(){
		return FAIL.equals(getStatus());
	}
	
	public static ResultBean success(String message,Object data){
		ResultBean result = new ResultBean();
		result.setStatus(SUCCESS);
		result.setData(data);
		result.setMessage(message);
		return result;
	}
	
	public static ResultBean success(Object data){
		return success(null,data);
	}
	
	public static ResultBean success(){
		return success(null);
	}

	public static ResultBean fail(String errorCode,String message, Object data){
		ResultBean result = new ResultBean();
		result.setStatus(FAIL);
		result.setErrorCode(errorCode);
		result.setData(data);
		result.setMessage(message);
		return result;
	}
	
	public static ResultBean fail(String errorCode,String msg){
		return fail(errorCode,msg,null);
	}
	
	public static ResultBean fail(String message){
		return fail(null,message,null);
	}
	
	public static ResultBean error(String errorCode, String message, Object data){
		ResultBean result = new ResultBean();
		result.setStatus(ERROR);
		result.setErrorCode(errorCode);
		result.setData(data);
		result.setMessage(message);
		return result;
	}
	
	public static ResultBean error(String errorCode, String msg){
		return error(errorCode, msg, null);
	}
	
	
	public static ResultBean error(String message){
		return error(null, message, null);
	}
	
}

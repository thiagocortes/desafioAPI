package com.cortes.desafio.solutis.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ApiError {
	
    private HttpStatus status;
    private String message;
    private Date dataErro;
    
    public ApiError(HttpStatus status, String message, Date dataErro) {
        super();
        this.status = status;
        this.message = message;
        this.dataErro = dataErro;
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDataErro() {
		return dataErro;
	}

	public void setDataErro(Date dataErro) {
		this.dataErro = dataErro;
	}
    
    
}

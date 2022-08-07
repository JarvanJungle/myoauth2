package com.doxa.oauth2.controllers;

import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.exceptions.AccessDeniedException;
import com.doxa.oauth2.exceptions.BadRequestException;
import com.doxa.oauth2.exceptions.DuplicateObjectException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			details.add(fieldName + " " + error.getDefaultMessage());
		}
		ApiResponse error = new ApiResponse();
		error.setMessage("Validation Failed");
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setData(details);
		LOG.error(ex.getMessage());
		error.setStatusCode(400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateObjectException.class)
	public ResponseEntity<ApiResponse> handleDuplicatedObject(DuplicateObjectException ex) {
		ApiResponse error = new ApiResponse();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(ex.getLocalizedMessage());
		LOG.error(ex.getMessage());
		error.setStatusCode(400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ObjectDoesNotExistException.class)
	public ResponseEntity<ApiResponse> handleObjectNotFound(ObjectDoesNotExistException ex) {
		ApiResponse error = new ApiResponse();
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setMessage(ex.getLocalizedMessage());
		LOG.error(ex.getMessage());
		error.setStatusCode(404);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex) {
		ApiResponse error = new ApiResponse();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(ex.getLocalizedMessage());
		LOG.error(ex.getMessage());
		error.setStatusCode(400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException ex) {
		ApiResponse error = new ApiResponse();
		error.setStatus(HttpStatus.FORBIDDEN);
		error.setMessage(ex.getLocalizedMessage());
		LOG.error(ex.getMessage());
		error.setStatusCode(403);
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleException(Exception ex) {
		ApiResponse error = new ApiResponse();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setMessage(Message.UNEXPECTED_ERROR.getValue());
		ex.printStackTrace();
		LOG.error(ex.getMessage());
		error.setStatusCode(500);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}

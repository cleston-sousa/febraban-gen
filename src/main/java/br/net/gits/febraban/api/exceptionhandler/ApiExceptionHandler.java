package br.net.gits.febraban.api.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		//// @formatter:off
		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.type("")
				.title("")
				.detail(ex.getMessage())
				.userMessage(ex.getMessage())
				.build();
		// @formatter:on

		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		//// @formatter:off
		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.type("")
				.title("")
				.detail(ex.getMessage())
				.userMessage(ex.getMessage())
				.build();
		// @formatter:on

		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

}

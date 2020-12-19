package com.juan.gamedevforums.web.error;

import java.sql.SQLException;
import com.juan.gamedevforums.web.util.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 400
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidOldPasswordException.class })
    public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Invalid Old Password", "InvalidOldPassword");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 409
    @ExceptionHandler({ UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        logger.error("409 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("An account for that username/email already exists. Please enter a different username.", "UserAlreadyExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({ UsernameAlreadyExistException.class })
    public ResponseEntity<Object> handleUsernameAlreadyExist(final RuntimeException ex, final WebRequest request) {
        logger.error("409 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("An account for that username/email already exists. Please enter a different username.", "UsernameAlreadyExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

// fix this
    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity<Object> handleUsernameNotFound(final RuntimeException ex, final WebRequest request) {
	logger.error("404 Status Code", ex);
	final GenericResponse bodyOfResponse = new GenericResponse("Username not found", "UsernameNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ CategoriesNotFoundException.class, SQLException.class, NullPointerException.class })
    public ResponseEntity<Object> handleCategoriesSQLNotFound(final RuntimeException ex, final WebRequest request) {
	logger.error("404 Status Code", ex);
	final GenericResponse bodyOfResponse = new GenericResponse("Categories not found", "CategoriesNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // @ExceptionHandler({ TopicNotFoundException.class, SQLException.class, NullPointerException.class })
    // public ResponseEntity<Object> handleTopicSQLNotFound(final RuntimeException ex, final WebRequest request) {
    // 	logger.error("404 Status Code", ex);
    // 	final GenericResponse bodyOfResponse = new GenericResponse("Topic not found", "TopicNotFound");
    //     return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    // }


// fix this
    @ExceptionHandler({ StorageFileNotFoundException.class })
    public ResponseEntity<Object> handleStorageFileNotFound(final RuntimeException ex, final WebRequest request) {
	logger.error("409 Status Code", ex);
	final GenericResponse bodyOfResponse = new GenericResponse("Could not read file", "StorageFileNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

// fix this
    @ExceptionHandler({ StorageException.class })
    public ResponseEntity<Object> handleStorageException(final RuntimeException ex, final WebRequest request) {
	logger.error("409 Status Code", ex);
	final GenericResponse bodyOfResponse = new GenericResponse("Failed to store file.", "StorageFailed");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 500
    @ExceptionHandler({ MailAuthenticationException.class })
    public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Email configuration Error", "MailError");
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("InternalError");
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

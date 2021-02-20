package com.solomka.libraryservice.exceptions.hahdlers;

import com.solomka.libraryservice.exceptions.BookNotFoundException;
import com.solomka.libraryservice.exceptions.BookUnavailableException;
import com.solomka.libraryservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = ErrorMessage.createErrorMessage(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorMessage error = ErrorMessage.createErrorMessage(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleBadIdException(BookNotFoundException ex) {
        ErrorMessage error = ErrorMessage.createErrorMessage(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            NumberFormatException.class,
            IllegalArgumentException.class,
            BookUnavailableException.class
    })
    public ResponseEntity<ErrorMessage> handleInvalidDataException(Exception ex) {
        ErrorMessage error = ErrorMessage.createErrorMessage(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

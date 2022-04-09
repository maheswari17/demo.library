package com.library.exceptions.GlobalExceptionHandler;
import com.library.exceptions.CustomNotFoundException.BookNotFoundException;
import com.library.exceptions.CustomNotFoundException.LoanNotFoundException;
import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BookNotFoundException.class,LoanNotFoundException.class,MemberNotFoundException.class})
         protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, HttpServletRequest req){
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}

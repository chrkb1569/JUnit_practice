package com.example.JUnitTest.Advice;

import com.example.JUnitTest.Exception.NotFoundBoardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(NotFoundBoardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> notFoundBoardException() {
        return new ResponseEntity<>("해당 아이디에 일치하는 도서를 찾지 못하였습니다.", HttpStatus.NOT_FOUND);
    }
}

package com.penghuang.home_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Catch all Exception.
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  all exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        return new ResponseEntity<>("System error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Catch SystemException.
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  SystemException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity<?> myExceptionHandler(HttpServletRequest httpServletRequest, SystemException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

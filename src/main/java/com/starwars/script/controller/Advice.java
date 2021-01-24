package com.starwars.script.controller;

import com.starwars.script.exception.DefaultErrorMessage;
import com.starwars.script.exception.ForbiddenMessage;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.util.JSONBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class Advice {

    @ExceptionHandler(ForbiddenMessage.class)
    @ResponseBody
    public ResponseEntity<JSONBasicInfo> handleForbiddenResourceException(
            ForbiddenMessage ex) {
        log.error(ex.getMessage(), ex);
        JSONBasicInfo info = new JSONBasicInfo();
        info.setMessage(ex.getMessage());
        return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundMessage.class)
    @ResponseBody
    public ResponseEntity<JSONBasicInfo> handleNotFoundResourceException(
            NotFoundMessage ex) {
        log.error(ex.getMessage(), ex);
        JSONBasicInfo info = new JSONBasicInfo();
        info.setMessage(ex.getMessage());
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<JSONBasicInfo> handleDefaultException(
            DefaultErrorMessage ex) {
        log.error(ex.getMessage(), ex);
        JSONBasicInfo info = new JSONBasicInfo();
        info.setMessage(ex.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }
}

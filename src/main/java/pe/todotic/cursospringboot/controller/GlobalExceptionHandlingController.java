package pe.todotic.cursospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandlingController {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(RuntimeException.class)
    ModelAndView handleRuntimeException(RuntimeException exception) {

        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            throw exception;
        }

        return new ModelAndView("error/exception")
                .addObject("exception", exception);
    }

}

package pe.todotic.cursospringboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HttpErrorController implements ErrorController {

    @GetMapping("/error")
    String handleError(HttpServletRequest request) {
        // si el codigo de respuesta de la solicitud es 404, retorno error-404.html
        // si el codigo de respuesta de la solicitud es 405, retorno error-405.html
        // si el codigo de respuesta de la solicitud es 400, retorno error.html

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "error";
            }
        }
        return "error";
    }

}

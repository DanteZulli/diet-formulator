package ar.dantezulli.diet_formulator.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (status != null) {
            httpStatus = HttpStatus.resolve(Integer.parseInt(status.toString()));
        }

        model.addAttribute("status", httpStatus != null ? httpStatus.value() : 500);
        model.addAttribute("error", httpStatus != null ? httpStatus.getReasonPhrase() : "Error");
        model.addAttribute("message", message != null ? message : "Ocurrió un error inesperado");
        model.addAttribute("exception", exception != null ? exception.toString() : null);
        model.addAttribute("uri", uri != null ? uri : request.getRequestURI());

        return "error";
    }
}

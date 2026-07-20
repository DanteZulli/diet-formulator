package ar.dantezulli.diet_formulator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String VIEW_ERROR = "error";
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        log.warn("Validation error: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/profiles";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        ModelAndView mav = new ModelAndView(VIEW_ERROR);
        mav.addObject("status", 500);
        mav.addObject("error", "Error interno");
        mav.addObject("message", ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado");
        mav.addObject("exception", ex.getClass().getName() + ": " + ex.getMessage());
        return mav;
    }
}

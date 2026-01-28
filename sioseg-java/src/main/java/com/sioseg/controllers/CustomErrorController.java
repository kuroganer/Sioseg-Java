package com.sioseg.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            if (statusCode == 500) {
                String errorMsg = "Erro interno do servidor. ";
                if (exception != null) {
                    Throwable ex = (Throwable) exception;
                    if (ex.getMessage() != null) {
                        errorMsg += ex.getMessage();
                    } else {
                        errorMsg += ex.getClass().getSimpleName();
                    }
                } else if (message != null) {
                    errorMsg += message.toString();
                }
                model.addAttribute("message", errorMsg);
            }
        }

        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}

package ru.misterparser.mlsbackendtest;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ExceptionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("Error processing...");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("<html><head><title>Exception/Error Details</title></head><body>");
        if (statusCode != 500) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong>Status Code</strong>: " + statusCode + "<br>");
            out.write("<strong>Requested URI</strong>: " + requestUri);
        } else {
            out.write("<h3>Exception Details</h3>");
            out.write("<ul>");
            out.write("<li>Servlet Name: " + servletName + "</li>");
            out.write("<li>Exception Name: " + throwable.getClass().getName() + "</li>");
            out.write("<li>Requested URI: " + requestUri + "</li>");
            out.write("<li>Exception Message: " + throwable.getMessage() + "</li>");
            out.write("</ul>");
        }
        out.write("</body></html>");
    }
}

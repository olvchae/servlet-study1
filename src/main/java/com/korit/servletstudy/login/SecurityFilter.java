package com.korit.servletstudy.login;

import com.korit.servletstudy.HelloServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;

        String uri = httpReq.getRequestURI();
        String projectNameIgnoreUri = uri.substring(uri.indexOf("/", 1));

        if (projectNameIgnoreUri.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpReq.getSession();
        Object authAttribute = session.getAttribute("authentication");
        if (authAttribute == null) {
            ResponseEntity.builder()
                    .status(403)
                    .body("로그인 후 이용가능합니다.")
                    .build()
                    .response(response);
            return;
        }

        chain.doFilter(request, response);
    }
}
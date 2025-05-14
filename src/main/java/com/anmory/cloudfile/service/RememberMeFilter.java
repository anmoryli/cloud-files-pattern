package com.anmory.cloudfile.service;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 下午7:26
 */

import com.anmory.cloudfile.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RememberMeFilter implements Filter {

    @Autowired
    private UsersService usersService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("session_user_key") == null) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("remember_me".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        Users user = usersService.findByUsername(token);
                        if (user != null) {
                            session.setAttribute("session_user_key", user);
                            break;
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}


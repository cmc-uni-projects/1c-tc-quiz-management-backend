package com.example.final_project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String frontendBase = "http://localhost:3000";
        String redirectUrl = frontendBase + "/"; // Mặc định chuyển về trang chủ

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = frontendBase + "/admin";
                break;
            } else if (role.equals("ROLE_TEACHER")) {
                redirectUrl = frontendBase + "/teacher/teacherhome";
                break;
            } else if (role.equals("ROLE_STUDENT")) {
                redirectUrl = frontendBase + "/student/studenthome";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}

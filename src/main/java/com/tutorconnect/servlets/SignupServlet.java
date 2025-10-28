package com.tutorconnect.servlets;

import com.tutorconnect.dao.impl.PasswordResetDaoImpl;
import com.tutorconnect.dao.impl.UserDaoImpl;
import com.tutorconnect.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SignupServlet extends HttpServlet {
    private AuthService auth;

    @Override
    public void init() {
        auth = new AuthService(new UserDaoImpl(), new PasswordResetDaoImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("signup.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");
        try {
            auth.signup(email, pass, name, phone, role);
            resp.sendRedirect("login");
        } catch (Exception ex) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Signup error: " + ex.getMessage());
        }
    }
}

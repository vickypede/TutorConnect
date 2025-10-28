package com.tutorconnect.servlets;

import com.tutorconnect.dao.impl.PasswordResetDaoImpl;
import com.tutorconnect.dao.impl.UserDaoImpl;
import com.tutorconnect.model.User;
import com.tutorconnect.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private AuthService auth;

    @Override
    public void init() {
        auth = new AuthService(new UserDaoImpl(), new PasswordResetDaoImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        try {
            User u = auth.login(email, pass);
            if (u == null) {
                resp.setContentType("text/plain");
                resp.getWriter().println("Invalid credentials.");
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("currentUser", u);
            resp.sendRedirect("app/bookings");
        } catch (Exception ex) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Login error: " + ex.getMessage());
        }
    }
}

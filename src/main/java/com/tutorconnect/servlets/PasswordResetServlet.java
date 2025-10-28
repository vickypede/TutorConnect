package com.tutorconnect.servlets;

import com.tutorconnect.dao.impl.PasswordResetDaoImpl;
import com.tutorconnect.dao.impl.UserDaoImpl;
import com.tutorconnect.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PasswordResetServlet extends HttpServlet {
    private AuthService auth;

    @Override
    public void init() {
        auth = new AuthService(new UserDaoImpl(), new PasswordResetDaoImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String token = req.getParameter("token");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<h2>Reset Password</h2>");
        out.println("<form method='post' action='reset'>");
        out.println("<input type='hidden' name='token' value='" + (token==null?"":token) + "'/>");
        out.println("New password <input type='password' name='password' required/>");
        out.println("<button type='submit'>Set new password</button>");
        out.println("</form>");
        out.println("<p><a href='" + req.getContextPath() + "/login'>Back to login</a></p>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String token = req.getParameter("token");
        String newPass = req.getParameter("password");
        try {
            boolean ok = auth.resetPassword(token, newPass);
            resp.setContentType("text/plain");
            resp.getWriter().println(ok ? "Password updated. Go to /login." : "Invalid/expired token.");
        } catch (Exception ex) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Reset error: " + ex.getMessage());
        }
    }
}

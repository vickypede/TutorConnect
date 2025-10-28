package com.tutorconnect.servlets;

import com.tutorconnect.dao.impl.PasswordResetDaoImpl;
import com.tutorconnect.dao.impl.UserDaoImpl;
import com.tutorconnect.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PasswordResetRequestServlet extends HttpServlet {
    private AuthService auth;

    @Override
    public void init() {
        auth = new AuthService(new UserDaoImpl(), new PasswordResetDaoImpl());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        try {
            String rawToken = auth.requestReset(email);
            // For demo, print the token so you can copy it into the browser:
            System.out.println("=== DEMO PASSWORD RESET TOKEN ===");
            System.out.println(rawToken);
            System.out.println("Open: " + req.getScheme() + "://" + req.getServerName() + ":" +
                    req.getServerPort() + req.getContextPath() + "/password/reset?token=" + rawToken);

            resp.setContentType("text/plain");
            resp.getWriter().println("If the account exists, a reset link was sent (see server console for demo).");
        } catch (Exception ex) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Request-reset error: " + ex.getMessage());
        }
    }
}

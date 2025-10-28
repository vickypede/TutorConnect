package com.tutorconnect.servlets;

import com.tutorconnect.dao.UserDao;
import com.tutorconnect.dao.impl.UserDaoImpl;
import com.tutorconnect.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect("profile.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null) { resp.sendRedirect("login"); return; }
        User u = (User) s.getAttribute("currentUser");
        if (u == null) { resp.sendRedirect("login"); return; }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        try {
            userDao.updateProfile(u.getId(), name, phone, email);
            // refresh in session
            User refreshed = userDao.findById(u.getId());
            s.setAttribute("currentUser", refreshed);
            resp.sendRedirect("profile");
        } catch (Exception ex) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Profile error: " + ex.getMessage());
        }
    }
}

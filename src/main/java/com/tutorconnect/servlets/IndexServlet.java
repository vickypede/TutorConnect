package com.tutorconnect.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!doctype html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"utf-8\"/>");
        out.println("    <title>TutorConnect — Stage 2</title>");
        out.println("    <link rel=\"stylesheet\" href=\"" + req.getContextPath() + "/css/style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"container\">");
        out.println("        <div class=\"header\">");
        out.println("            <h1>TutorConnect</h1>");
        out.println("            <p>Stage 2 - MVP Web Application</p>");
        out.println("        </div>");
        out.println("        <div class=\"content\">");
        out.println("            <ul class=\"nav-menu\">");
            out.println("                <li><a href=\"" + req.getContextPath() + "/signup\">Sign Up</a></li>");
            out.println("                <li><a href=\"" + req.getContextPath() + "/login\">Log In</a></li>");
            out.println("                <li><a href=\"" + req.getContextPath() + "/profile\">Profile</a></li>");
            out.println("                <li><a href=\"" + req.getContextPath() + "/app/bookings\">Bookings</a></li>");
        out.println("            </ul>");
        out.println("            <div class=\"alert alert-info\">");
        out.println("                <strong>Welcome!</strong> Choose an option above to get started. Profile and Bookings require login.");
        out.println("            </div>");
        out.println("        </div>");
        out.println("        <div class=\"footer\">");
        out.println("            <p>TutorConnect - Connecting Students with Expert Tutors</p>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
}

package com.tutorconnect.servlets;

import com.tutorconnect.dao.impl.BookingDaoImpl;
import com.tutorconnect.model.Booking;
import com.tutorconnect.model.Role;
import com.tutorconnect.model.User;
import com.tutorconnect.service.BookingService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

public class BookingServlet extends HttpServlet {
    private BookingService bookingService;

    @Override
    public void init() {
        bookingService = new BookingService(new BookingDaoImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        User u = (User) s.getAttribute("currentUser");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!doctype html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"utf-8\"/>");
        out.println("    <title>Bookings - TutorConnect</title>");
        out.println("    <link rel=\"stylesheet\" href=\"" + req.getContextPath() + "/css/style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"container\">");
        out.println("        <div class=\"header\">");
        out.println("            <h1>My Bookings</h1>");
        out.println("            <p>" + u.getName() + " (" + u.getRole() + ")</p>");
        out.println("        </div>");
        out.println("        <div class=\"content\">");
        out.println("            <ul class=\"nav-menu\">");
        out.println("                <li><a href=\"" + req.getContextPath() + "/\">Home</a></li>");
        out.println("                <li><a href=\"" + req.getContextPath() + "/profile\">Profile</a></li>");
        out.println("            </ul>");

        try {
            if (u.getRole() == Role.STUDENT) {
                List<Booking> mine = bookingService.listForStudent(u.getId());
                out.println("<div class=\"form-container\">");
                out.println("<h2>My Bookings</h2>");
                renderTable(out, mine);
                out.println("</div>");
            } else if (u.getRole() == Role.TUTOR) {
                List<Booking> incoming = bookingService.listForTutor(u.getId());
                out.println("<div class=\"form-container\">");
                out.println("<h2>Incoming Bookings</h2>");
                renderTable(out, incoming);
                out.println("</div>");
            } else {
                out.println("<div class=\"alert alert-info\">");
                out.println("<p>Admins: no view implemented for Stage 2.</p>");
                out.println("</div>");
            }
        } catch (Exception e) {
            out.println("<div class=\"alert alert-error\">");
            out.println("<pre>" + e.getMessage() + "</pre>");
            out.println("</div>");
        }

        out.println("<div class=\"text-center\">");
        out.println("<a href='" + req.getContextPath() + "/bookings.html' class=\"btn\">Open Forms Page</a>");
        out.println("</div>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void renderTable(PrintWriter out, List<Booking> list) {
        out.println("<div class=\"table-container\">");
        out.println("<table>");
        out.println("<thead><tr><th>ID</th><th>Student ID</th><th>Tutor ID</th><th>Subject</th><th>Start Time</th><th>Status</th></tr></thead>");
        out.println("<tbody>");
        for (Booking b : list) {
            String statusClass = "status-" + b.getStatus().toLowerCase();
            out.printf("<tr><td>%d</td><td>%d</td><td>%d</td><td>%s</td><td>%s</td><td><span class=\"status-badge %s\">%s</span></td></tr>%n",
                b.getId(), b.getStudentId(), b.getTutorId(), b.getSubject(), b.getStartAt(), statusClass, b.getStatus());
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        User u = (User) s.getAttribute("currentUser");

        String action = req.getParameter("action");
        try {
            if ("create".equals(action) && u.getRole() == Role.STUDENT) {
                long tutorId = Long.parseLong(req.getParameter("tutorId"));
                String subject = req.getParameter("subject");
                LocalDateTime startAt = LocalDateTime.parse(req.getParameter("startAt").replace(' ', 'T'));
                bookingService.create(u.getId(), tutorId, subject, startAt);
            } else if ("status".equals(action) && u.getRole() == Role.TUTOR) {
                long id = Long.parseLong(req.getParameter("id"));
                String status = req.getParameter("status");
                bookingService.setStatus(id, status, u);
            }
            resp.sendRedirect(req.getContextPath() + "/app/bookings");
        } catch (Exception ex) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Booking error: " + ex.getMessage());
        }
    }
}

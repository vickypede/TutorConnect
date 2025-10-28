package com.tutorconnect.dao.impl;

import com.tutorconnect.dao.BookingDao;
import com.tutorconnect.model.Booking;
import com.tutorconnect.util.JdbcUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    private Booking map(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getLong("id"));
        b.setStudentId(rs.getLong("student_id"));
        b.setTutorId(rs.getLong("tutor_id"));
        b.setSubject(rs.getString("subject"));
        b.setStartAt(rs.getTimestamp("start_at").toLocalDateTime());
        b.setStatus(rs.getString("status"));
        return b;
    }

    @Override
    public long create(Booking booking) throws Exception {
        String sql = "INSERT INTO bookings(student_id, tutor_id, subject, start_at, status) VALUES(?,?,?,?,?)";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, booking.getStudentId());
            ps.setLong(2, booking.getTutorId());
            ps.setString(3, booking.getSubject());
            ps.setTimestamp(4, Timestamp.valueOf(booking.getStartAt()));
            ps.setString(5, booking.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Failed to insert booking");
    }

    @Override
    public List<Booking> listForStudent(long studentId) throws Exception {
        String sql = "SELECT * FROM bookings WHERE student_id=? ORDER BY created_at DESC";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Booking> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        }
    }

    @Override
    public List<Booking> listForTutor(long tutorId) throws Exception {
        String sql = "SELECT * FROM bookings WHERE tutor_id=? ORDER BY created_at DESC";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, tutorId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Booking> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        }
    }

    @Override
    public void setStatus(long bookingId, String status) throws Exception {
        String sql = "UPDATE bookings SET status=? WHERE id=?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, bookingId);
            ps.executeUpdate();
        }
    }

    @Override
    public Booking findById(long id) throws Exception {
        String sql = "SELECT * FROM bookings WHERE id=?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }
}

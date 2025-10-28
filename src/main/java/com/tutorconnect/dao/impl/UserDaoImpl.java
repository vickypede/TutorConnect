package com.tutorconnect.dao.impl;

import com.tutorconnect.dao.UserDao;
import com.tutorconnect.model.Role;
import com.tutorconnect.model.User;
import com.tutorconnect.util.JdbcUtil;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setName(rs.getString("name"));
        u.setPhone(rs.getString("phone"));
        u.setRole(Role.valueOf(rs.getString("role")));
        return u;
    }

    @Override
    public User findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }

    @Override
    public User findById(long id) throws Exception {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }

    @Override
    public long create(User u) throws Exception {
        String sql = "INSERT INTO users(email, password_hash, name, phone, role) VALUES(?,?,?,?,?)";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getName());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getRole().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Failed to insert user");
    }

    @Override
    public void updateProfile(long id, String name, String phone, String email) throws Exception {
        String sql = "UPDATE users SET name=?, phone=?, email=? WHERE id=?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setLong(4, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updatePassword(long id, String newHash) throws Exception {
        String sql = "UPDATE users SET password_hash=? WHERE id=?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }
}

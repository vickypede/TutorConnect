package com.tutorconnect.dao.impl;

import com.tutorconnect.dao.PasswordResetDao;
import com.tutorconnect.model.PasswordReset;
import com.tutorconnect.util.JdbcUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class PasswordResetDaoImpl implements PasswordResetDao {

    @Override
    public long create(long userId, String tokenHash, LocalDateTime expiresAt) throws Exception {
        String sql = "INSERT INTO password_resets(user_id, token_sha256, expires_at) VALUES(?,?,?)";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.setString(2, tokenHash);
            ps.setTimestamp(3, Timestamp.valueOf(expiresAt));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Failed to insert password reset");
    }

    @Override
    public PasswordReset findValidBySha256(String tokenSha256) throws Exception {
        String sql = "SELECT * FROM password_resets " +
                     "WHERE token_sha256=? AND used_at IS NULL AND expires_at > CURRENT_TIMESTAMP LIMIT 1";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tokenSha256);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PasswordReset pr = new PasswordReset();
                    pr.setId(rs.getLong("id"));
                    pr.setUserId(rs.getLong("user_id"));
                    pr.setTokenHash(rs.getString("token_sha256"));
                    pr.setExpiresAt(rs.getTimestamp("expires_at").toLocalDateTime());
                    Timestamp used = rs.getTimestamp("used_at");
                    pr.setUsedAt(used == null ? null : used.toLocalDateTime());
                    return pr;
                }
                return null;
            }
        }
    }

    @Override
    public void markUsed(long id) throws Exception {
        String sql = "UPDATE password_resets SET used_at=CURRENT_TIMESTAMP WHERE id=?";
        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}

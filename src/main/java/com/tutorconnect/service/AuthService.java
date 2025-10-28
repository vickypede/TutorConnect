package com.tutorconnect.service;

import com.tutorconnect.dao.PasswordResetDao;
import com.tutorconnect.dao.UserDao;
import com.tutorconnect.model.Role;
import com.tutorconnect.model.User;
import com.tutorconnect.model.PasswordReset;
import com.tutorconnect.util.PasswordHasher;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class AuthService {
    private final UserDao userDao;
    private final PasswordResetDao resetDao;

    public AuthService(UserDao userDao, PasswordResetDao resetDao) {
        this.userDao = userDao;
        this.resetDao = resetDao;
    }

    public long signup(String email, String password, String name, String phone, String roleStr) throws Exception {
        if (userDao.findByEmail(email) != null) throw new IllegalArgumentException("Email already in use");
        Role role = Role.valueOf(roleStr);
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(PasswordHasher.hash(password));
        u.setName(name);
        u.setPhone(phone);
        u.setRole(role);
        return userDao.create(u);
    }

    public User login(String email, String password) throws Exception {
        User u = userDao.findByEmail(email);
        if (u == null) return null;
        return PasswordHasher.matches(password, u.getPasswordHash()) ? u : null;
    }

    public String requestReset(String email) throws Exception {
        User u = userDao.findByEmail(email);
        if (u == null) return null; // do not reveal account existence
        String raw = generateToken();
        String sha = sha256Hex(raw);
        LocalDateTime exp = LocalDateTime.now().plusMinutes(30);
        resetDao.create(u.getId(), sha, exp);
        return raw; // returned so servlet can print to console for demo
    }

    public boolean resetPassword(String rawToken, String newPassword) throws Exception {
        String sha = sha256Hex(rawToken);
        PasswordReset pr = resetDao.findValidBySha256(sha);
        if (pr == null) return false;
        User u = userDao.findById(pr.getUserId());
        if (u == null) return false;
        userDao.updatePassword(u.getId(), PasswordHasher.hash(newPassword));
        resetDao.markUsed(pr.getId());
        return true;
    }

    private static String sha256Hex(String raw) {
        try {
            var md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(d.length * 2);
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateToken() {
        byte[] buf = new byte[24];
        new SecureRandom().nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}

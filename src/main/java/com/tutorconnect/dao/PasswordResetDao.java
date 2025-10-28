package com.tutorconnect.dao;

import com.tutorconnect.model.PasswordReset;

public interface PasswordResetDao {
    long create(long userId, String tokenHash, java.time.LocalDateTime expiresAt) throws Exception;
    PasswordReset findValidBySha256(String tokenSha256) throws Exception; // returns only if not used and not expired
    void markUsed(long id) throws Exception;
}

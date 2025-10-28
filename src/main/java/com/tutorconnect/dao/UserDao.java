package com.tutorconnect.dao;

import com.tutorconnect.model.User;

public interface UserDao {
    User findByEmail(String email) throws Exception;
    User findById(long id) throws Exception;
    long create(User user) throws Exception;
    void updateProfile(long id, String name, String phone, String email) throws Exception;
    void updatePassword(long id, String newHash) throws Exception;
}

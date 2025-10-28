package com.tutorconnect.dao;

import java.util.List;
import com.tutorconnect.model.Booking;

public interface BookingDao {
    long create(Booking booking) throws Exception;
    List<Booking> listForStudent(long studentId) throws Exception;
    List<Booking> listForTutor(long tutorId) throws Exception;
    void setStatus(long bookingId, String status) throws Exception;
    Booking findById(long id) throws Exception;
}

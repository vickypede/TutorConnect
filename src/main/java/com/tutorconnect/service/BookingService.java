package com.tutorconnect.service;

import com.tutorconnect.dao.BookingDao;
import com.tutorconnect.model.Booking;
import com.tutorconnect.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final BookingDao bookingDao;

    public BookingService(BookingDao bookingDao) { this.bookingDao = bookingDao; }

    public long create(long studentId, long tutorId, String subject, LocalDateTime startAt) throws Exception {
        if (startAt.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Start time in the past");
        Booking b = new Booking();
        b.setStudentId(studentId);
        b.setTutorId(tutorId);
        b.setSubject(subject);
        b.setStartAt(startAt);
        b.setStatus("PENDING");
        return bookingDao.create(b);
    }

    public List<Booking> listForStudent(long studentId) throws Exception {
        return bookingDao.listForStudent(studentId);
    }

    public List<Booking> listForTutor(long tutorId) throws Exception {
        return bookingDao.listForTutor(tutorId);
    }

    public void setStatus(long bookingId, String status, User acting) throws Exception {
        Booking b = bookingDao.findById(bookingId);
        if (b == null) throw new IllegalArgumentException("Not found");
        if (acting.getId() != b.getTutorId()) throw new SecurityException("Only the assigned tutor can change status");
        bookingDao.setStatus(bookingId, status);
    }
}

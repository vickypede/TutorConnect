package com.tutorconnect.model;

import java.time.LocalDateTime;

public class Booking {
    private long id;
    private long studentId;
    private long tutorId;
    private String subject;
    private LocalDateTime startAt;
    private String status; // PENDING, APPROVED, REJECTED, CANCELLED

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public long getTutorId() { return tutorId; }
    public void setTutorId(long tutorId) { this.tutorId = tutorId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public LocalDateTime getStartAt() { return startAt; }
    public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

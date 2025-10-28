# TutorConnect Demo Script (1-2 minutes)
# Follow this exact sequence for the video demo

## Demo Steps:

### 1. Show Landing Page (5 seconds)
- Open browser to: http://localhost:8080/tutorconnect/
- Show "TutorConnect — Stage 2" with navigation links

### 2. Sign up Student (15 seconds)
- Click "Sign up"
- Fill form: email=student@demo.com, password=demo123, name=Demo Student, role=STUDENT
- Submit → redirects to login

### 3. Sign up Tutor (15 seconds)  
- Click "Sign up" again
- Fill form: email=tutor@demo.com, password=demo123, name=Demo Tutor, role=TUTOR
- Submit → redirects to login

### 4. Login as Student (10 seconds)
- Login with: student@demo.com / demo123
- Redirects to /app/bookings

### 5. Create Booking (20 seconds)
- Fill booking form: Tutor ID=2, Subject=Mathematics, Start=2025-11-05 18:00:00
- Submit → booking created

### 6. Switch to Tutor (10 seconds)
- Open incognito/private window
- Login with: tutor@demo.com / demo123
- Go to /app/bookings

### 7. Approve Booking (15 seconds)
- See incoming booking
- Fill form: Booking ID=1, Status=APPROVED
- Submit → booking approved

### 8. Show Status Update (10 seconds)
- Switch back to Student window
- Refresh page
- Show booking status changed to APPROVED

### 9. Password Reset Demo (20 seconds)
- Go to login page
- Click "Request reset link" with student@demo.com
- Check console for token
- Open: http://localhost:8080/tutorconnect/password/reset?token=[TOKEN_FROM_CONSOLE]
- Set new password: newpass123
- Login with new password

### 10. Profile Update (10 seconds)
- Go to /profile
- Change name to "Updated Student"
- Save → profile updated

## Total Time: ~2 minutes
## Key Points to Highlight:
- Two different browser sessions (Student vs Tutor)
- Real-time status updates
- Secure password reset with console token
- Profile persistence
- Role-based access control

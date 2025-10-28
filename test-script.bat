# TutorConnect Test Script
# Run these commands to test the application

echo "=== TutorConnect Testing Script ==="

# 1. Test Tomcat 9 is running
echo "1. Testing Tomcat 9..."
curl -s http://localhost:8080 | findstr "Apache Tomcat/9"

# 2. Sign up Student
echo "2. Creating Student account..."
curl -i -X POST http://localhost:8080/tutorconnect/signup -d "email=student@test.com&password=password123&name=Test Student&role=STUDENT"

# 3. Sign up Tutor  
echo "3. Creating Tutor account..."
curl -i -X POST http://localhost:8080/tutorconnect/signup -d "email=tutor@test.com&password=password123&name=Test Tutor&role=TUTOR"

# 4. Login as Student
echo "4. Logging in as Student..."
curl -i -c cookies.txt -X POST http://localhost:8080/tutorconnect/login -d "email=student@test.com&password=password123"

# 5. Create booking (assuming tutor ID is 2)
echo "5. Creating booking..."
curl -i -b cookies.txt -X POST http://localhost:8080/tutorconnect/app/bookings -d "action=create&tutorId=2&subject=Math&startAt=2025-11-05 18:00:00"

# 6. Login as Tutor
echo "6. Logging in as Tutor..."
curl -i -c tutor-cookies.txt -X POST http://localhost:8080/tutorconnect/login -d "email=tutor@test.com&password=password123"

# 7. Approve booking (assuming booking ID is 1)
echo "7. Approving booking..."
curl -i -b tutor-cookies.txt -X POST http://localhost:8080/tutorconnect/app/bookings -d "action=status&id=1&status=APPROVED"

# 8. Test password reset
echo "8. Testing password reset..."
curl -i -X POST http://localhost:8080/tutorconnect/password/request-reset -d "email=student@test.com"

echo "=== Test completed! Check console for password reset token ==="

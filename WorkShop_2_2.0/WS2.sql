CREATE DATABASE WS2_OnlineExamination;
USE WS2_OnlineExamination;

-- Bảng người dùng
CREATE TABLE tblUsers (
    Username VARCHAR(50) PRIMARY KEY, 
    Name VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL, 
    Role VARCHAR(20) NOT NULL CHECK (Role IN ('Instructor', 'Student')) -- Vai trò: Giáo viên (Instructor) hoặc Học viên (Student)
);

-- Bảng danh mục bài thi (Chứa thông tin các danh mục kỳ thi)
CREATE TABLE tblExamCategories (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL, -- Tên danh mục bài thi (Ví dụ: Quiz, Midterm, Final)
    description TEXT -- Mô tả chi tiết về danh mục bài thi
);

-- Bảng bài thi (Lưu thông tin các bài thi được tạo ra)
CREATE TABLE tblExams (
    exam_id INT PRIMARY KEY, 
    exam_title VARCHAR(100) NOT NULL, -- Tiêu đề bài thi
    subject VARCHAR(50) NOT NULL, -- Môn học của bài thi
    category_id INT NOT NULL, -- Mã danh mục bài thi, khóa ngoại tham chiếu tblExamCategories
    total_marks INT NOT NULL, -- Tổng điểm của bài thi
    duration INT NOT NULL, -- Thời gian làm bài thi (tính bằng phút)
    FOREIGN KEY (category_id) REFERENCES tblExamCategories(category_id) ON DELETE CASCADE -- Xóa danh mục sẽ xóa bài thi liên quan
);

-- Bảng câu hỏi (Lưu danh sách các câu hỏi trong từng bài thi)
CREATE TABLE tblQuestions (
    question_id INT PRIMARY KEY, -- Mã câu hỏi, khóa chính
    exam_id INT NOT NULL, -- Mã bài thi, khóa ngoại tham chiếu tblExams
    question_text TEXT NOT NULL, -- Nội dung câu hỏi
    option_a VARCHAR(100) NOT NULL, -- Lựa chọn A
    option_b VARCHAR(100) NOT NULL, -- Lựa chọn B
    option_c VARCHAR(100) NOT NULL, -- Lựa chọn C
    option_d VARCHAR(100) NOT NULL, -- Lựa chọn D
    correct_option CHAR(1) NOT NULL CHECK (correct_option IN ('A', 'B', 'C', 'D')), -- Đáp án đúng (A, B, C hoặc D)
    FOREIGN KEY (exam_id) REFERENCES tblExams(exam_id) ON DELETE CASCADE -- Xóa bài thi sẽ xóa câu hỏi liên quan
);


-- Chèn dữ liệu vào bảng tblUsers
INSERT INTO tblUsers (Username, Name, Password, Role) VALUES
('instructor01', 'Instructor_Alice Johnson', 'i123', 'Instructor'),
('student01', 'Bob Smith', 's123', 'Student'),
('student02', 'Charlie Brown', 's234', 'Student'),
('student03', 'David Wilson', 's345', 'Student');

-- Chèn dữ liệu vào bảng tblExamCategories
INSERT INTO tblExamCategories (category_id, category_name, description) VALUES
(1, 'Quiz', 'Short test'),
(2, 'Midterm', 'Midterm exam'),
(3, 'Final', 'Final exam');

-- Chèn dữ liệu vào bảng tblExams
INSERT INTO tblExams (exam_id, exam_title, Subject, category_id, total_marks, Duration) VALUES
(1, 'Basic Math Quiz', 'Mathematics', 1, 100, 60),
(2, 'Midterm Programming Exam', 'Programming', 2, 100, 90),
(3, 'Final Database Exam', 'Database', 3, 100, 120);

-- Chèn dữ liệu vào bảng tblQuestions
INSERT INTO tblQuestions (question_id, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
(1, 1, '1 + 1 = ?', '1', '2', '3', '4', 'B'),
(2, 1, '5 + 3 = ?', '6', '7', '8', '9', 'C'),
(3, 1, '12 - 5 = ?', '5', '6', '7', '8', 'C'),
(4, 1, '9 × 3 = ?', '27', '30', '24', '33', 'A'),
(5, 2, 'Which programming language is the most popular?', 'Python', 'Java', 'C++', 'Ruby', 'A'),
(6, 2, 'Which data structure follows FIFO?', 'Stack', 'Queue', 'Array', 'Linked List', 'B'),
(7, 2, 'Which is a fundamental data type in programming?', 'Integer', 'String', 'Boolean', 'All of the above', 'D'),
(8, 2, 'Which programming language is mainly used for Android app development?', 'Python', 'C#', 'Java', 'Swift', 'C'),
(9, 3, 'What does SQL stand for?', 'Structured Question Language', 'Structured Query Language', 'System Query Language', 'Sequential Query Language', 'B'),
(10, 3, 'Which clause is used to retrieve data in SQL?', 'SELECT', 'INSERT', 'DELETE', 'UPDATE', 'A'),
(11, 3, 'Which command is used to delete a table in SQL?', 'DROP TABLE', 'DELETE', 'REMOVE', 'CLEAR TABLE', 'A'),
(12, 3, 'Which clause is used to filter data in SQL?', 'ORDER BY', 'GROUP BY', 'WHERE', 'HAVING', 'C');
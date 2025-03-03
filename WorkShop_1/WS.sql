CREATE DATABASE WS1_StartupProjectManagement;
USE WS1_StartupProjectManagement;

-- Users Table
CREATE TABLE tblUsers (
    Username VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL CHECK (Role IN ('Founder', 'Team Member'))
);

-- Startup Projects Table
CREATE TABLE tblStartupProjects (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    Description TEXT,
    Status VARCHAR(20) NOT NULL CHECK (Status IN ('Ideation', 'Development', 'Launch', 'Scaling')),
    estimated_launch DATE NOT NULL
);


-- Insert data into tblUsers
INSERT INTO tblUsers (Username, Name, Password, Role) VALUES
('founder01', 'Alice Johnson', 'founder123', 'Founder'),
('teammember01', 'Bob Smith', 'teammember123', 'Team Member'),
('founder02', 'Charlie Brown', 'founder456', 'Founder'),
('teammember02', 'David Wilson', 'teammember456', 'Team Member');


-- Insert additional data into tblStartupProjects
INSERT INTO tblStartupProjects (project_id, project_name, Description, Status, estimated_launch) VALUES
('001', 'EduFuture', 'An e-learning platform for interactive courses.', 'Ideation', '2025-04-20'),
('002', 'AgriTech Solutions', 'A startup developing smart farming technology.', 'Development', '2025-07-15'),
('003', 'GreenEnergy', 'A renewable energy solutions company.', 'Launch', '2025-10-05'),
('004', 'AI Assistant', 'An AI-driven personal assistant for productivity.', 'Scaling', '2026-02-28'),
('005', 'SmartHome IoT', 'Developing IoT-based home automation solutions.', 'Ideation', '2025-05-10'),
('006', 'MedTech Innovations', 'AI-based medical diagnostics and health monitoring.', 'Development', '2025-08-22'),
('007', 'CyberSecure', 'A cybersecurity company offering data protection solutions.', 'Launch', '2025-11-30'),
('008', 'Blockchain Hub', 'A decentralized platform for secure transactions.', 'Scaling', '2026-03-10'),
('009', 'FoodTech Labs', 'Innovative food processing and delivery solutions.', 'Ideation', '2025-06-18'),
('010', 'AutoDrive', 'Self-driving car technology startup.', 'Development', '2025-09-25'),
('011', 'VR Universe', 'A VR-based gaming and training platform.', 'Launch', '2026-01-05'),
('012', 'BioHealth', 'A biotech startup focused on genetic research.', 'Scaling', '2026-04-12');

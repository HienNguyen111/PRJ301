<%-- 
    Document   : bookForm
    Created on : Feb 27, 2025, 10:33:39 AM
    Author     : Admin
--%>

<%@page import="dto.ExamCategoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.ExamDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Exam</title>
        
        <style>
            * {
                box-sizing: border-box;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            body {
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
                min-height: 100vh;
            }

            .page-content {
                padding: 40px 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: calc(100vh - 150px); /* Account for header and footer */
            }

            .form-container {
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 30px;
                width: 100%;
                max-width: 600px;
                margin: 0 auto;
            }

            h1 {
                color: #2c3e50;
                margin-top: 0;
                margin-bottom: 20px;
                text-align: center;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: 600;
                color: #333;
            }

            input[type="text"],
            input[type="number"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
                transition: border-color 0.3s;
            }

            input[type="text"]:focus,
            input[type="number"]:focus {
                border-color: #3498db;
                outline: none;
                box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
            }

            .error-message {
                color: #e74c3c;
                font-size: 14px;
                margin-top: 5px;
            }

            .button-group {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }

            button, input[type="submit"], input[type="reset"] {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            input[type="submit"] {
                background-color: #2ecc71;
                color: white;
                flex: 1;
                margin-right: 10px;
            }

            input[type="submit"]:hover {
                background-color: #27ae60;
            }

            input[type="reset"] {
                background-color: #e74c3c;
                color: white;
                flex: 1;
                margin-left: 10px;
            }

            input[type="reset"]:hover {
                background-color: #c0392b;
            }

            .error-container {
                background-color: #fff0f0;
                border-left: 4px solid #e74c3c;
                padding: 20px;
                border-radius: 4px;
                margin-bottom: 20px;
            }

            .error-container h1 {
                color: #e74c3c;
                margin-top: 0;
            }
            
            .error-container p {
                text-align: center;
            }
            
            .back-link {
                display: block;
                text-align: center;
                margin-top: 20px;
                color: #3498db;
                text-decoration: none;
            }
            
            .back-link:hover {
                text-decoration: underline;
            }
            
            select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
                background-color: white;
                transition: border-color 0.3s;
            }

            select:focus {
                border-color: #3498db;
                outline: none;
                box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
            }

            @media (max-width: 768px) {
                .form-container {
                    padding: 20px;
                }
                
                .button-group {
                    flex-direction: column;
                }
                
                input[type="submit"], input[type="reset"] {
                    margin: 5px 0;
                }
            }
        </style>
        
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="page-content">
         
            
        <% if (AuthUtils.isLoggedIn(session)) {
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (AuthUtils.isAdmin(session)) {
        %>
        
        
        <%
            ExamDTO newExam = new ExamDTO();
            try{
                newExam = (ExamDTO) request.getAttribute("newExam");
            } catch (Exception e) {
                newExam = new ExamDTO();
            }
            if(newExam == null) {
                newExam = new ExamDTO();
            }
            
            // In ra thông báo lỗi
            String txtExam_id_error = request.getAttribute("txtExam_id_error") + "";
            txtExam_id_error = txtExam_id_error.equals("null") ? "" : txtExam_id_error;
            
            String txtExam_title_error = request.getAttribute("txtExam_title_error") + "";
            txtExam_title_error = txtExam_title_error.equals("null") ? "" : txtExam_title_error;
            
            String txtSubject_error = request.getAttribute("txtSubject_error") + "";
            txtSubject_error = txtSubject_error.equals("null") ? "" : txtSubject_error;
            
            String txtCategory_id_error = request.getAttribute("txtCategory_id_error") + "";
            txtCategory_id_error = txtCategory_id_error.equals("null") ? "" : txtCategory_id_error;
            
            String txtTotal_marks_error = request.getAttribute("txtTotal_marks_error") + "";
            txtTotal_marks_error = txtTotal_marks_error.equals("null") ? "" : txtTotal_marks_error;
            
            String txtDuration_error = request.getAttribute("txtDuration_error") + "";
            txtDuration_error = txtDuration_error.equals("null") ? "" : txtDuration_error;
            
        %>
    
        <div class="form-container">
            <h1>New Exam Information</h1>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="add_exam"/>
                
                <div class="form-group">
                    <label for="txtExam_id">Exam ID:</label>
                    <input type="number" id="txtExam_id" name="txtExam_id" value="<%=newExam.getExam_id()%>"/>
                    <% if (!txtExam_id_error.isEmpty()) { %>
                        <div class="error-message"><%=txtExam_id_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtExam_title">Exam Title:</label>
                    <input type="text" id="txtExam_title" name="txtExam_title" value="<%=newExam.getExam_title()%>"/>
                    <% if (!txtExam_title_error.isEmpty()) { %>
                        <div class="error-message"><%=txtExam_title_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtSubject">Subject:</label>
                    <input type="text" id="txtSubject" name="txtSubject" value="<%=newExam.getSubject()%>"/>
                    <% if (!txtSubject_error.isEmpty()) { %>
                        <div class="error-message"><%=txtSubject_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtCategory_id">Category:</label>
                    <select name="txtCategory_id" id="txtCategory_id">
                        <option value="0"> Choose... </option>
                        <option value="1"> Quiz </option>
                        <option value="2"> Midterm </option>
                        <option value="3"> Final </option>
                    </select>
                    <% if (!txtCategory_id_error.isEmpty()) { %>
                        <div class="error-message"><%=txtCategory_id_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtTotal_marks">Total Marks:</label>
                    <input type="number" id="txtTotal_marks" name="txtTotal_marks" value="<%=newExam.getTotal_marks()%>"/>
                    <% if (!txtTotal_marks_error.isEmpty()) { %>
                        <div class="error-message"><%=txtTotal_marks_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtDuration">Duration:</label>
                    <input type="number" id="txtDuration" name="txtDuration" value="<%=newExam.getDuration()%>"/>
                    <% if (!txtDuration_error.isEmpty()) { %>
                        <div class="error-message"><%=txtDuration_error%></div>
                    <% } %>
                </div>
                
                
                
                <div class="button-group">
                    <input type="submit" value="Save" />
                    <input type="reset" value="Reset"/>
                </div>
            </form>

            <a href="MainController?action=backToDashBroad" class="back-link">Back to Dash Broad</a>
            </div>
            <%} else {%>
            <div class="form-container error-container">
                <h1>403 Error</h1>
                <p>You do not have permission to access this content!</p>
                <a href="MainController?action=backToDashBroad" class="back-link">Back to Dash Broad</a>
            </div>
            <%}
                } else {%>
            <div class="form-container error-container">
                <h1>Access Denied</h1>
                <p>Please log in to access this page.</p>
                <a href="login.jsp" class="back-link">Go to Login</a>
            </div>
            <%}%>
        </div>
       
        <jsp:include page="footer.jsp"/>
    </body>
</html>

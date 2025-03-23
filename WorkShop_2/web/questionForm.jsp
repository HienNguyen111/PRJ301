<%-- 
    Document   : bookForm
    Created on : Feb 27, 2025, 10:33:39 AM
    Author     : Admin
--%>

<%@page import="dto.QuestionDTO"%>
<%@page import="java.util.List"%>
<%@page import="utils.AuthUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Question</title>
        
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
            
            p {
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
            QuestionDTO newQuestion = new QuestionDTO();
            try{
                newQuestion = (QuestionDTO) request.getAttribute("newQuestion");
            } catch (Exception e) {
                newQuestion = new QuestionDTO();
            }
            if(newQuestion == null) {
                newQuestion = new QuestionDTO();
            }
            
            // In ra thông báo lỗi
            String txtQuestion_id_error = request.getAttribute("txtQuestion_id_error") + "";
            txtQuestion_id_error = txtQuestion_id_error.equals("null") ? "" : txtQuestion_id_error;
            
            String txtQuestion_text_error = request.getAttribute("txtQuestion_text_error") + "";
            txtQuestion_text_error = txtQuestion_text_error.equals("null") ? "" : txtQuestion_text_error;
            
            String txtOption_a_error = request.getAttribute("txtOption_a_error") + "";
            txtOption_a_error = txtOption_a_error.equals("null") ? "" : txtOption_a_error;
            
            String txtOption_b_error = request.getAttribute("txtOption_b_error") + "";
            txtOption_b_error = txtOption_b_error.equals("null") ? "" : txtOption_b_error;
            
            String txtOption_c_error = request.getAttribute("txtOption_c_error") + "";
            txtOption_c_error = txtOption_c_error.equals("null") ? "" : txtOption_c_error;
            
            String txtOption_d_error = request.getAttribute("txtOption_d_error") + "";
            txtOption_d_error = txtOption_d_error.equals("null") ? "" : txtOption_d_error;
            
            String txtCorrect_option_error = request.getAttribute("txtCorrect_option_error") + "";
            txtCorrect_option_error = txtCorrect_option_error.equals("null") ? "" : txtCorrect_option_error;
            

            String exam_id = request.getParameter("id"); // Lấy exam_id (ở dashBoard.jsp)
        %>
        
        
        <div class="form-container">
            <h1>New Question Information</h1>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="add_question"/>
                
                <div class="form-group">
                    <label for="txtExam_id_AddQ">Exam ID:</label>
                    <input type="number" id="txtExam_id_AddQ" name="txtExam_id_AddQ" value="<%= exam_id %>" readonly/>
                    <input type="hidden" name="txtExam_id_AddQ" value="<%= exam_id %>"/>
                </div>
                
                <div class="form-group">
                    <label for="txtQuestion_id">Question ID:</label>
                    <input type="number" id="txtQuestion_id" name="txtQuestion_id" value="<%=newQuestion.getQuestion_id()%>"/>
                    <% if (!txtQuestion_id_error.isEmpty()) { %>
                        <div class="error-message"><%=txtQuestion_id_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtQuestion_text">Question Text:</label>
                    <input type="text" id="txtQuestion_text" name="txtQuestion_text" value="<%=newQuestion.getQuestion_text()%>"/>
                    <% if (!txtQuestion_text_error.isEmpty()) { %>
                        <div class="error-message"><%=txtQuestion_text_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtOption_a">Option A:</label>
                    <input type="text" id="txtOption_a" name="txtOption_a" value="<%=newQuestion.getOption_a()%>"/>
                    <% if (!txtOption_a_error.isEmpty()) { %>
                        <div class="error-message"><%=txtOption_a_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtOption_b">Option B:</label>
                    <input type="text" id="txtOption_b" name="txtOption_b" value="<%=newQuestion.getOption_b()%>"/>
                    <% if (!txtOption_b_error.isEmpty()) { %>
                        <div class="error-message"><%=txtOption_b_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtOption_c">Option C:</label>
                    <input type="text" id="txtOption_c" name="txtOption_c" value="<%=newQuestion.getOption_c()%>"/>
                    <% if (!txtOption_c_error.isEmpty()) { %>
                        <div class="error-message"><%=txtOption_c_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtOption_d">Option D:</label>
                    <input type="text" id="txtOption_d" name="txtOption_d" value="<%=newQuestion.getOption_d()%>"/>
                    <% if (!txtOption_d_error.isEmpty()) { %>
                        <div class="error-message"><%=txtOption_d_error%></div>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="txtCorrect_option">Correct Option:</label>
                    <select name="txtCorrect_option" id="txtCorrect_option">
                        <option value=""> Choose... </option>
                        <option value="A" <%= (newQuestion.getCorrect_option() == 'A') ? "selected" : "" %>> A </option>
                        <option value="B" <%= (newQuestion.getCorrect_option() == 'B') ? "selected" : "" %>> B </option>
                        <option value="C" <%= (newQuestion.getCorrect_option() == 'C') ? "selected" : "" %>> C </option>
                        <option value="D" <%= (newQuestion.getCorrect_option() == 'D') ? "selected" : "" %>> D </option>
                    </select>
                    <% if (!txtCorrect_option_error.isEmpty()) { %>
                        <div class="error-message"><%=txtCorrect_option_error%></div>
                    <% } %>
                </div>

                <div class="button-group">
                    <input type="submit" value="Save" />
                    <input type="reset" value="Reset"/>
                </div>
            </form>

            <a href="MainController?action=backToDashBoard" class="back-link">Back to DashBoard</a>
            </div>
            <%} else {%>
            <div class="form-container error-container">
                <h1>403 Error</h1>
                <p>You do not have permission to access this content!</p>
                <a href="MainController?action=backToDashBoard" class="back-link">Back to DashBoard</a>
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

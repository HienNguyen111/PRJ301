<%@page import="dto.ExamDTO"%>
<%@page import="dto.ExamCategoryDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chính</title>
    </head>

    <style>
        /* Table Styles */
        .book-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin: 25px 0;
            font-size: 15px;
            font-family: 'Poppins', sans-serif;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
        }

        .book-table thead th {
            background: linear-gradient(90deg, #58C7F4, #47B0E1);
            color: #ffffff;
            text-align: left;
            font-weight: bold;
            padding: 14px 18px;
            text-transform: uppercase;
            border-bottom: 3px solid #3AAED8;
        }

        .book-table tbody tr {
            border-bottom: 1px solid #e0e0e0;
            transition: background 0.3s ease-in-out;
        }

        .book-table tbody tr:nth-of-type(even) {
            background-color: #eef9fe;
        }

        .book-table tbody tr:last-of-type {
            border-bottom: 3px solid #58C7F4;
        }

        .book-table tbody td {
            padding: 14px 18px;
            color: #333;
        }

        .book-table tbody tr:hover {
            background-color: #d9f2fc;
            transition: background 0.3s ease-in-out;
        }

        @media (max-width: 768px) {
            .book-table {
                font-size: 13px;
            }

            .book-table thead th,
            .book-table tbody td {
                padding: 12px 10px;
            }
        }


        /* view exam by category styles */
        h2 {
            color: #FAD105;
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            padding-top: 20px;
        }

        .selectCategory {
            background-color: #58C7F4;
            padding: 10px;
            border-radius: 10px;
            width: 30%;
            margin: 20px auto;
            text-align: center;
        }

        label {
            font-size: 18px;
            font-weight: bold;
            color: white;
        }

        select {
            background-color: white;
            color: #333;
            padding: 8px;
            border: 2px solid #FAD105;
            border-radius: 5px;
            font-size: 16px;
        }

        select:focus {
            outline: none;
            border-color: #FAD105;
            box-shadow: 0 0 5px #FAD105;
        }

        /* Responsive design */
        @media screen and (max-width: 600px) {
            .book-table {
                font-size: 12px;
            }

            .book-table thead th,
            .book-table tbody td {
                padding: 8px 10px;
            }
        }

    </style> 

    <body>

        <%@include file="header.jsp" %>
        <div style="min-height: 500px; padding: 10px">

            <% if (AuthUtils.isLoggedIn(session)) { %>


            <%
                if (request.getAttribute("listExamCate") != null) {
                    List<ExamCategoryDTO> listEC = (List<ExamCategoryDTO>) request.getAttribute("listExamCate");
            %>

            <h2>View All Exam Categories</h2>
            <table class="book-table">
                <thead>
                    <tr>
                        <th>Category ID</th>
                        <th>Category Name</th>
                        <th>Description</th>
                    </tr>
                </thead>

                <tbody>
                    <%
                        for (ExamCategoryDTO ec : listEC) {
                    %>
                    <tr>
                        <td> <%=ec.getCategory_id()%> </td>
                        <td> <%=ec.getCategory_name()%> </td>
                        <td> <%=ec.getDescription()%> </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>        
            <% } %>


            <% if (request.getAttribute("listExams") != null) {
                    List<ExamDTO> listExams = (List<ExamDTO>) request.getAttribute("listExams");
            %>

            <h2>View All Exams</h2>

            <table class="book-table">
                <thead>
                    <tr>
                        <th>Exam ID</th>
                        <th>Exam Title</th>
                        <th>Subject</th>
                        <th>Category</th>
                        <th>Total Marks</th>
                        <th>Duration (minutes)</th>
                        <% if(AuthUtils.isAdmin(session)){ %>
                            <th>Action</th>            
                        <% } %>
                    </tr>
                </thead>

                <tbody>
                    <% for (ExamDTO exam : listExams) {%>
                    <tr>
                        <td><%= exam.getExam_id()%></td>
                        <td><%= exam.getExam_title()%></td>
                        <td><%= exam.getSubject()%></td>
                        <td><%= exam.getCategory_id()%></td>
                        <td><%= exam.getTotal_marks()%></td>
                        <td><%= exam.getDuration()%></td>
                        <% if(AuthUtils.isAdmin(session)){ %>
                            <td> <a href="MainController?action=add_question&id=<%=exam.getExam_id()%>">
                                <img src="assets/images/add_quetstion.png" style="height: 25px; display: block; margin: auto;" />
                            </a> </td>
                        <% } %>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <% } %>




            <% if (AuthUtils.isStudent(session)) { %>

            <div class="selectCategory">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="viewExamsByCategory"/>
                    <label for="category">Select Category:</label>
                    <select name="txtCategory_id_viewExam" id="txtCategory_id_viewExam" required onchange="this.form.submit()">
                        <option value=""> Choose... </option>
                        <% if (request.getAttribute("listExamCate") != null) {
                        List<ExamCategoryDTO> listEC = (List<ExamCategoryDTO>) request.getAttribute("listExamCate");
                        for (ExamCategoryDTO ec : listEC) {%>
                        <option value="<%= ec.getCategory_id()%>"
                                <%= (request.getAttribute("txtCategory_id_viewExam") != null 
                                    && Integer.parseInt(request.getAttribute("txtCategory_id_viewExam").toString()) == ec.getCategory_id()) ? "selected" : "" %>>
                            <%= ec.getCategory_name()%>
                        </option>
                        <% } %>
                        <% } %>
                    </select>
                </form>
            </div>



            <% if (request.getAttribute("listExamByCate") != null) {
                    List<ExamDTO> listExamByCate = (List<ExamDTO>) request.getAttribute("listExamByCate");
            %>

            <h2>View Exams by Category</h2>

            <table class="book-table">
                <thead>
                    <tr>
                        <th>Exam ID</th>
                        <th>Exam Title</th>
                        <th>Subject</th>
                        <th>Total Marks</th>
                        <th>Duration (minutes)</th>
                    </tr>
                </thead>

                <tbody>
                    <% for (ExamDTO exam : listExamByCate) {%>
                    <tr>
                        <td><%= exam.getExam_id()%></td>
                        <td><%= exam.getExam_title()%></td>
                        <td><%= exam.getSubject()%></td>
                        <td><%= exam.getTotal_marks()%></td>
                        <td><%= exam.getDuration()%></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <% } %>
            <% } %>




            <% if (AuthUtils.isAdmin(session)) { %>
            <a href="examForm.jsp" style="display: inline-flex; align-items: center; text-decoration: none; padding: 8px 12px; background-color: #FAD105; color: white; border-radius: 5px; font-weight: bold; transition: 0.3s;">
                <img src="assets/images/Add.png" alt="Add" style="height: 20px; margin-right: 8px;">
                Add New Exam
            </a>
            <% } %>




            <% } else { %>
            You do not have permission to access this content.
            <%}%>
        </div>

        <jsp:include page="footer.jsp"/>

    </body>
</html>

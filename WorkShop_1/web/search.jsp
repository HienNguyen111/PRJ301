<%@page import="utils.AuthUtils"%>
<%@page import="dto.SUp_ProjectDTO"%>
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


            /* Search section styles */
            .search-section {
                background-color: #fff;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                display: flex;
                align-items: center;
            }

            .search-section form {
                display: flex;
                align-items: center;
                flex-grow: 1;
            }

            .search-section label {
                margin-right: 10px;
                font-weight: bold;
                color: #58C7F4;
            }

            .search-input {
                flex-grow: 1;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
                margin-right: 10px;
                transition: border-color 0.3s;
            }

            .search-input:focus {
                border-color: #58C7F4;
                outline: none;
                box-shadow: 0 0 0 2px rgba(0, 152, 121, 0.2);
            }

            .search-btn {
                background-color: #58C7F4;
                color: white;
                border: none;
                border-radius: 4px;
                padding: 10px 15px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .search-btn:hover {
                background-color: #0590C9;
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
 
            <% if (session.getAttribute("user") != null) {
                    UserDTO user = (UserDTO) session.getAttribute("user");
            %>
            
            <%
                String searchTerm = request.getAttribute("searchTerm") + "";
                searchTerm = searchTerm.equals("null") ?"" :searchTerm;
            %>
            
            <% if(AuthUtils.isAdmin(session)){ %>
            <div class="search-section">
                <form action="MainController">
                    <input type="hidden" name="action" value="search"/>
                    <label for="searchInput">Search Books:</label>
                    <input type="text" id="searchInput" name="searchTerm" value="<%=searchTerm%>" class="search-input" placeholder="Enter book title, author or ID..."/>
                    <input type="submit" value="Search" class="search-btn"/>
                </form>
            </div>
            <% } %>
             
            <% if(AuthUtils.isAdmin(session)){ %>
                <a href="projectForm.jsp" style="display: inline-flex; align-items: center; text-decoration: none; padding: 8px 12px; background-color: #FAD105; color: white; border-radius: 5px; font-weight: bold; transition: 0.3s;">
                    <img src="assets/images/Add.png" alt="Add" style="height: 20px; margin-right: 8px;">
                    Add New Project
                </a>
            <% } %>

            
            <%
                if(request.getAttribute("search_pj") != null){
                    List<SUp_ProjectDTO> projects = (List<SUp_ProjectDTO>) request.getAttribute("search_pj");
            %>
            
                    <table class="book-table">
                        <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Project Name</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Estimated Launch</th>
                                <% if(AuthUtils.isAdmin(session)){ %>
                                    <th>Update Status
                                    <%
                                        String messageUpdate = request.getAttribute("messageUpdate") + "";
                                    %>
                                    <span style="color: #FAD105; font-weight: bold; margin-left: 10px;">
                                        <%= messageUpdate.equals("null") ? "" : messageUpdate %>
                                    </span>

                                    </th>
                                <% } %>
                            </tr>
                        </thead>
                        
                        <tbody>
                            <%
                                for(SUp_ProjectDTO p : projects){
                            %>
                                    <tr>
                                        <td> <%=p.getProject_id()%> </td>
                                        <td> <%=p.getProject_name()%> </td>
                                        <td> <%=p.getDescription()%> </td>
                                        <td> <%=p.getStatus()%> </td>
                                        <td> <%= (p.getEstimated_launch() != null) ? p.getEstimated_launch().toString() : "N/A" %> </td>
                                        <% if(AuthUtils.isAdmin(session)){ %>
                                        <td>
                                            <form action="MainController" method="post" style="display: flex; align-items: center; gap: 5px;">
                                            <input type="hidden" name="action" value="updateStatus">
                                            <input type="hidden" name="project_id" value="<%= p.getProject_id() %>">
                        
                                            <select name="status_update" style="padding: 5px;">
                                                <option value="Ideation" <%= p.getStatus().equals("Ideation") ? "selected" : "" %>>Ideation</option>
                                                <option value="Development" <%= p.getStatus().equals("Development") ? "selected" : "" %>>Development</option>
                                                <option value="Launch" <%= p.getStatus().equals("Launch") ? "selected" : "" %>>Launch</option>
                                                <option value="Scaling" <%= p.getStatus().equals("Scaling") ? "selected" : "" %>>Scaling</option>
                                            </select>

                                            <button type="submit" style="border: none; background: none; cursor: pointer;">
                                                <img src="assets/images/fix.png" style="height: 25px; display: block; margin: auto;">
                                            </button>

                                            </form>
                                        </td>
                                        <% } %>
                                    </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                        
            <%    }
            } else { %>
            You do not have permission to access this content.
            <%}%>
        </div>
            
        <jsp:include page="footer.jsp"/>
            
    </body>
</html>

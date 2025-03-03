<%-- 
    Document   : search
    Created on : Feb 24, 2025, 7:38:45 PM
    Author     : Admin
--%>

<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%@include file="header.jsp" %>
        <div style="min-height: 500px; padding: 10px">
            <%
                UserDTO user = (UserDTO)request.getAttribute("user");
            %>
        
            <h1> Welcome <%=user.getFullName()%></h1>
            <a href="MainController?action=logout">Log out</a>
        
            <form>
                Search Value : <input type="text" name="txtSearchValue"/>
                <input type="submit" name="Search"/>
            </form>
        </div>
            
        <jsp:include page="footer.jsp"/>
            
    </body>
</html>

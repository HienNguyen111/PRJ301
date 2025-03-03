<%-- 
    Document   : search
    Created on : Feb 24, 2025, 7:38:45 PM
    Author     : Admin
--%>

<%@page import="dto.BookDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chính</title>
    </head>
    <body>
        
        <%@include file="header.jsp" %>
        <div style="min-height: 500px; padding: 10px">
            
            <%
                if(session.getAttribute("user") != null){
                    
                UserDTO user = (UserDTO)session.getAttribute("user");
                
            %>
        
            <h1> Welcome <%=user.getFullName()%></h1>
            
            <form action="MainController">
                <input type="hidden" name="action" value="logout"/>
                <input type="submit" value="Logout"/>
            </form>
            
            <br/>
            <hr/>
            <br/>
            
            <form action="MainController">
                <input type="hidden" name="action" value="search"/>
                Search Books : <input type="text" name="searchTerm"/>
                <input type="submit" name="Search"/>
            </form>
            <br/>
            <br/>
            
            <%
                if(request.getAttribute("books") != null){
                    List<BookDTO> books = (List<BookDTO>) request.getAttribute("books");
                    %>
                    <table border="1">
                        
                        <tr>
                            <td>BookID</td>
                            <td>Title</td>
                            <td>Author</td>
                            <td>PublishYear</td>
                            <td>Price</td>
                            <td>Quantity</td>
                        </tr>
                        
                        <%
                            for(BookDTO b : books){
                                %>
                                <tr>
                                    <td> <%=b.getBookID()%> </td>
                                    <td> <%=b.getTitle()%> </td>
                                    <td> <%=b.getAuthor()%> </td>
                                    <td> <%=b.getPublishYear()%> </td>
                                    <td> <%=b.getPrice()%> </td>
                                    <td> <%=b.getQuantity()%> </td>
                                </tr>
                                <%
                            }
                        %>
                        
                    </table>
            <%
                }
            %>
            
            <% }else{%>
                You do not have permission to ascess this content.
            <% } %>
            
        </div>
            
        <jsp:include page="footer.jsp"/>
            
    </body>
</html>

<%-- 
    Document   : example04
    Created on : Feb 10, 2025, 10:31:28 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
            for(int i=2 ; i<=9 ; i++){
                %>
                <hr/>
                <h1> Bảng cửu chương <%=i%> </h1>
                <%
                for(int j=1 ; j<=10 ; j++){
                %>
                <%=i%> x <%=j%> = <%=i*j%> </br>
                <%
                }
        }
        %>
            
            
    </body>
</html>

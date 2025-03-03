<%-- 
    Document   : output2
    Created on : Feb 10, 2025, 11:23:00 AM
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
            int x = (int)request.getAttribute("x");
            
            if(x%2 == 0){
            %>
            <h1> x là số chẵn </h1>
                <%
                }else{
                %>
                    <h1> x là số lẻ </h1>
                <%
                }
                %>
        
        
        
    </body>
</html>

<%-- 
    Document   : input
    Created on : Feb 10, 2025, 10:53:51 AM
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
        Tạo bảng cửu chương:
        <form action="PrintServlet">
            n = <input type="text" name="strN"/>
            <input type="submit" value="submit"/>
        </form>
        <br/>
        <hr/>
        
        Kiểm tra chẵn lẻ:
        <form action="ChanLeServlet">
            x = <input type="text" name="strX"/>
            <input type="submit" value="submit"/>
        </form>
        
    </body>
</html>

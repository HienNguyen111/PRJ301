<%-- 
    Document   : bookForm
    Created on : Feb 27, 2025, 10:33:39 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm sách mới</title>
    </head>
    <body>
        
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="add_book"/>
            
            BookID: <input type="text" name="txtBookID" /> 
            <span style="red"> </span> <br/>
            Title: <input type="text" name="txtTitle" /> <br/>
            Author: <input type="text" name="txtAuthor" /> <br/>
            PublishYear: <input type="text" name="txtPublishYear" /> <br/>
            Price: <input type="text" name="txtPrice" /> <br/>
            Quantity: <input type="text" name="txtQuantity" /> <br/>
            
            <input type="submit" value="Save"/>
            <input type="reset" value="Reset"/>
        </form>
        
        
    </body>
</html>

<%-- 
    Document   : boardgame-home
    Created on : Mar 29, 2021, 10:19:57 PM
    Author     : vanni
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>

<sql:query var="boardgameList" dataSource="jdbc/boardgame_database">
    select id, name, release_date, designer, price from boardgame_table
</sql:query>
    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Boardgame home page</title>
    </head>
    <body>
         <div align="center">
            <table border="1" cellpadding="5">
                <caption><h2>Boardgame list</h2></caption>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Release date</th>
                    <th>Designer</th>
                    <th>Price</th>
                </tr>
                <c:forEach var="boardgame" items="${boardgameList.rows}">
                    <tr>
                        <td><c:out value="${boardgame.id}" /></td>
                        <td><c:out value="${boardgame.name}" /></td>
                        <td><c:out value="${boardgame.release_date}" /></td>
                        <td><c:out value="${boardgame.designer}" /></td>
                        <td><c:out value="${boardgame.price}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
<%-- 
    Document   : boardgame-home
    Created on : Mar 29, 2021, 10:19:57 PM
    Author     : vanni
--%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
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
                <tr>
                    <form method="GET" action="${pageContext.request.contextPath}/BoardgameServlet">
                        <td><input type="text" name="boardgameId" /></td>
                        <td><input type="text" name="boardgameName" /></td>
                        <td><input type="datetime-local" name="boardgameReleaseDate" /> </td>
                        <td><input type="text" name="boardgameDesigner" /> </td>
                        <td><input type="text" name="boardgamePrice" /> </td>
                        <td></td>
                        <td colspan ="2">
                            <input type="submit" value= "Submit" />
                            <a href="${pageContext.request.contextPath}/">Cancel</a>
                        </td>
                    </form>
                </tr>
                <c:forEach items="${boardgameList}" var="boardgame">
                    <tr>
                        <td>${boardgame.getId()}</td>
                        <td>${boardgame.getName()}</td>
                        <td>${boardgame.getReleaseDate()}</td>
                        <td>${boardgame.getDesigner()}</td>
                        <td>${boardgame.getPrice()}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
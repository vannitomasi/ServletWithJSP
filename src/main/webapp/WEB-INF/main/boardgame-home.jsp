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
        <jsp:include page="shared/header.jsp"></jsp:include> 
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
                            <input type="submit" value= "Filter" />
                            <a href="${pageContext.request.contextPath}/">Cancel</a>
                        </td>
                    </form>
                </tr>
                <c:forEach items="${boardgameList}" var="boardgame">
                    <tr>
                        <form method="POST" action="${pageContext.request.contextPath}/BoardgameServlet">
                            <td><input type="text" name="boardgameId" value="${boardgame.getId()}" /></td>
                            <td><input type="text" name="boardgameName" value="${boardgame.getName()}" /></td>
                            <td><input type="datetime-local" name="boardgameReleaseDate" value="${boardgame.getReleaseDate()}" /></td>
                            <td><input type="text" name="boardgameDesigner" value="${boardgame.getDesigner()}" /></td>
                            <td><input type="text" name="boardgamePrice" value="${boardgame.getPrice()}" /></td>
                            <td></td>
                            <td colspan ="2">
                                <input type="submit" value= "Edit" />
                                <a href="${pageContext.request.contextPath}/">Cancel</a>
                            </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <jsp:include page="shared/footer.jsp"></jsp:include>
    </body>
</html>
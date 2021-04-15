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
            <caption><h2>Boardgame list</h2></caption>
            <table border="1" cellpadding="5">
                <c:if test="${errorMessage == null}">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Release date</th>
                        <th>Designer</th>
                        <th>Price</th>
                        <th colspan="2">Actions</th>
                    </tr>
                    <tr>
                        <form method="GET" action="${pageContext.request.contextPath}/boardgameList">
                            <td><input type="text" name="boardgameId" /></td>
                            <td><input type="text" name="boardgameName" /></td>
                            <td><input type="datetime-local" name="boardgameReleaseDate" /> </td>
                            <td><input type="text" name="boardgameDesigner" /> </td>
                            <td><input type="text" name="boardgamePrice" /> </td>
                            <td colspan="2">
                                <input type="submit" value="Filter" />
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
                            <td>
                                <a href="${pageContext.request.contextPath}/boardgameEdit?id=${boardgame.getId()}">Edit</a>
                            </td>
                            <td>
                                <form method="POST" action="${pageContext.request.contextPath}/boardgameDelete">
                                    <input type="hidden" name="id" value="${boardgame.getId()}" />
                                    <input type="submit" value="Delete" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                
            </table>
            <jsp:include page="shared/error.jsp"></jsp:include>
            <a href="${pageContext.request.contextPath}/boardgameList">Cancel</a>
        </div>
        <jsp:include page="shared/footer.jsp"></jsp:include>
    </body>
</html>  
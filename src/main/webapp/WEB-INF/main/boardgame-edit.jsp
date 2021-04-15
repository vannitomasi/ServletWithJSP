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
            <caption><h2>Edit boardgame</h2></caption>
                <c:if test="${errorMessage == null}">
                    <form method="POST" action="${pageContext.request.contextPath}/boardgameEdit">
                        <input type="hidden" name="boardgameId" value="${boardgame.getId()}" />
                        <table border="0">
                            <tr>
                                <td>Id</td>
                                <td>${boardgame.getId()}</td>
                            </tr>
                            <tr>
                                <td>Name</td>
                                <td><input type="text" name="boardgameName" value="${boardgame.getName()}" /></td>
                            </tr>
                            <tr>
                                <td>Release date</td>
                                <td><input type="datetime-local" name="boardgameReleaseDate" value="${boardgame.getReleaseDate()}" /></td>
                            </tr>
                            <tr>
                                <td>Designer</td>
                                <td><input type="text" name="boardgameDesigner" value="${boardgame.getDesigner()}" /></td>
                            </tr>
                            <tr>
                                <td>Price</td>
                                <td><input type="text" name="boardgamePrice" value="${boardgame.getPrice()}" /></td>
                            </tr>
                            <tr>
                                <td colspan ="2">
                                    <input type="submit" value= "Save" />
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:if>
            <jsp:include page="shared/error.jsp"></jsp:include>
            <a href="${pageContext.request.contextPath}/boardgameList">Cancel</a>
        </div>
        <jsp:include page="shared/footer.jsp"></jsp:include>
    </body>
</html>

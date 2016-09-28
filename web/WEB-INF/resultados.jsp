
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado Final</title>
    </head>
    <body link="black" vlink="black" alink="#003399">
    <center>
        <fieldset style="width:500px">
            <legend>Resultado Final</legend>
        <jsp:include page="fragments/menu.jspf" />
        <c:if test="${erro != null}">
            <div style="border: 1px solid red;">${erro}</div>
        </c:if>
        <h2>Listar Alunos</h2>
        <table style="border: 1px solid black;">
            <thead>
                <tr>
                    <th style="border: 1px solid black;">Grupo</th>
                    <th style="border: 1px solid black;">Pontos</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pontos}" var="ponto">
                    <tr>
                        <td style="border: 1px solid black;">Grupo ${ponto.grupo}</td>
                        <td style="border: 1px solid black;">${ponto.pontos}</td>
                    </tr>
                </c:forEach>                
            </tbody>
        </table>
        </fieldset>
    </center>
    </body>
</html>

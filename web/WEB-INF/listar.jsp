
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar Alunos</title>
    </head>
    <body link="black" vlink="black" alink="#003399">
    <center>
        <fieldset style="width:500px">
            <legend>Listar alunos</legend>
        <jsp:include page="fragments/menu.jspf" />
        <c:if test="${erro != null}">
            <div style="border: 1px solid red;">${erro}</div>
        </c:if>
        <h2>Listar Alunos</h2>
        <table>
            <thead>
                <tr>
                    <th>Nome Completo</th>
                    <th>Grupo</th>
                    <th>Pontos</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${alunos}" var="aluno">
                    <tr>
                        <td>${aluno.nome}</td>
                        <td><center>${aluno.grupo}</center></td>
                        <td><center>${aluno.pontos}</center></td>
                    </tr>
                </c:forEach>                
            </tbody>
        </table>
        </fieldset>
    </center>
    </body>
</html>

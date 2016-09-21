
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Grupo</title>
    </head>
    <body link="black" vlink="black" alink="#003399">
    <center>
        <fieldset style="width:500px">
            <legend>Grupo</legend>
        <jsp:include page="fragments/menu.jspf" />
        <c:if test="${erro != null}">
            <div style="border: 1px solid red;">${erro}</div>
        </c:if>
        <h2>Grupo  ${idGrupo}</h2>
        <table style="border: 1px solid black;">
            <thead>
                <tr>
                    <th style="border: 1px solid black;">Nome Completo</th>
                    <th style="border: 1px solid black;">Pontos</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${alunos}" var="aluno">
                    <tr>
                        <td style="border: 1px solid black;">${aluno.nome}</td>
                        <td style="border: 1px solid black;"><center>${aluno.pontos}</center></td>
                    </tr>
                </c:forEach>                
            </tbody>
            Total de pontos do grupo: ${totalPontos}
        </table>
        </fieldset>
    </center>
    </body>
</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar Alunos</title>
    </head>
    <body>
        <jsp:include page="fragments/menu.jspf" />
        <c:if test="${erro != null}">
            <div style="border: 1px solid red;">${erro}</div>
        </c:if>
        <h1>Listar Alunos</h1>
        <table>
            <thead>
                <tr>
                    <th>Nome Completo</th>
                    <th>Idade</th>
                    <th>Sexo</th>
                    <th>Grupo</th>
                    <th>Pontos</th>
                    <th>Pontuar</th>
                </tr>
            </thead>
            <tbody>

                <c:forEach items="${alunos}" var="aluno">
                    <tr>
                        <td>${aluno.nomeCompleto}</td>
                        <td>${aluno.idade}</td>
                        <td>
                            <c:if test="${aluno.sexo == true}">
                                <label>Feminino</label>
                            </c:if>
                            <c:if test="${aluno.sexo == false}">
                                <label>Masculino</label>
                            </c:if>
                        </td>
                        <td><center>${aluno.grupo}</center></td>
                        <td><center></center></td>
                        <td><center>+  -</center></td>
                    </tr>
                </c:forEach>
                
            </tbody>
        </table>
    </body>
</html>

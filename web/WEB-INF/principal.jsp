<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bem vindo!</title>
    </head>
    <body link="black" vlink="black" alink="#003399">
    <center>
        <fieldset style="width:500px">
            <legend>Selecione uma opção</legend>
        <jsp:include page="fragments/menu.jspf" />
        <p>
        <form method="post" action="">
            <div>
                <a href="novo.html" style="text-decoration:none">Cadastrar novo aluno</a>
            </div>
            <p>
            <div>
                <a href="listar.html" style="text-decoration:none">Listar alunos cadastrados</a>
            </div>
                <fildset style="width:50px">
                    <legend>Visualizar gurpos</legend>
                <a href="listar.html?id=1" style="text-decoration:none">Grupo 01</a><br>
                <a href="listar.html?id=2" style="text-decoration:none">Grupo 02</a><br>
                <a href="listar.html?id=3" style="text-decoration:none">Grupo 03</a><br>
                <a href="listar.html?id=4" style="text-decoration:none">Grupo 04</a><br>
                </fildset>
            <p>
        </form>
        </fieldset>
    </center>
    </body>
</html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novo aluno</title>
    </head>
    <body>
        <jsp:include page="fragments/menu.jspf" />
        <h1>Novo aluno</h1>
        <form method="post" action="">
            <div>
                <label>Nome completo:
                <input type="text" name="nomeCompleto" />
                </label>
            </div>
            <div>
                <label>Idade:
                <input type="number" name="idade" />
                </label>
            </div>
            <div>
                <label>Sexo:
                <label>Masculino<input type="radio" name="sexo" value="false" />
                <label>Feminino<input type="radio" name="sexo" value="true" />
                </label>
            </div>
            <div>
                <input type="submit" value="cadastrar" />
            </div>
        </form>
    </body>
</html>

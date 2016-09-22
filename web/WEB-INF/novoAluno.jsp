<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novo aluno</title>
    </head>
    <body link="black" vlink="black" alink="#003399">
    <center>
        <fieldset style="width:500px">
            <legend>Cadastro de Aluno</legend>
        <jsp:include page="fragments/menu.jspf" />
        <h2>Novo aluno</h2>
        <form method="post" action="">
            <div>
                <label>Nome:
                    <input type="text" name="nomeCompleto" placeholder="Nome completo"/>
                </label>
            </div>
            <p>
            <div>
                <label>Grupo 1 <input type="radio" name="grupo" value="1" />
                <label>Grupo 2 <input type="radio" name="grupo" value="2" />
                    <label>Grupo 3 <input type="radio" name="grupo" value="3" />
                        <label>Grupo 4<input type="radio" name="grupo" value="4" />
                </label>
            </div><p>
            <div>
                <input type="submit" value="Cadastrar" /> 
                <input type="button" value="Cancelar" onClick="nova()"/>
                <script type="text/javascript">
                function nova(){
                location.hreaf="listar.html"
                }
                </script>
                <input type="reset" value="Limpar" />
            </div>
        </form>
        </fieldset>
    </center>
    </body>
</html>
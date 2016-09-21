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
            <legend>Cadastro de Professor</legend>
        <jsp:include page="fragments/menu.jspf" />
        <h2>Novo Professor</h2>
        <form method="post" action="">
            <div>
                <label>Nome:
                    <input type="text" name="nomeProfessor" placeholder="Nome completo"/>
                </label>
            </div>
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
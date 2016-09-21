<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nova ocorrência</title>
    </head>
    <body link="black" vlink="black" alink="#003399">
    <center>
        <fieldset style="width:500px">
            <legend>Ocorrência</legend>
        <jsp:include page="fragments/menu.jspf" />
        <h2>Nova Ocorrência</h2>
        <form method="post" action="">
            <div>
                <label>Aluno:
                    <select name="aaaa"> 
                        <option value=""></option>
                        <c:forEach var="aluno" items="${alunos}" >
                          <option value="aaa">${aluno.nomeCompleto}</option>  
                        </c:forEach>  
                    </select>
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
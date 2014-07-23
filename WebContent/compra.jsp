<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pagina de finalização de compra</title>
</head>
<body>

	<c:if test="${not empty usuarioLogado}">
	Logado como ${usuarioLogado.email}<br />
	</c:if>
	
<div align="center"><h3><a href="http://localhost:8080/lojaVirtual/home">Lobo Marinho</a></h3></div>

<h3>Compra efetuada com Sucesso!</h3>



<div align="center"><a href="http://localhost:8080/lojaVirtual/home">Inicio</a></div>
	<div align="right">
		<form action="http://localhost:8080/lojaVirtual/deslogar" method="POST">
			<input type="hidden" name="logout" value="Logout" /> 
			<input type="submit" value="Deslogar" />
		</form>
	</div>

</body>
</html>
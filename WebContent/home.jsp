<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lobo Marinho</title>
</head>
<body>
	<c:if test="${not empty usuarioLogado}">
	Logado como ${usuarioLogado.email}<br />
	</c:if>
	
	<div align="center"><h3><a href="http://localhost:8080/lojaVirtual/home">Lobo Marinho</a></h3></div>
	
	<c:if test="${not empty produtos}">

<table border=1 cellspacing=0 cellpadding=2 bordercolor="000">
<tr><td>

	<ul>
		<c:forEach var="produto" items="${produtos}">
			<br />
			<li><h3>${produto.nome}</h3> <br />
				<h3>R$ ${produto.valor}</h3> <br />
				${produto.descricao}, ${produto.marca},
				<br /> Setor ${produto.categoria } <br />
		<input type="submit" value="Adicionar no Carrinho de Compras" onclick="location. href= 'http://localhost:8080/lojaVirtual/addProdutoNoCarrinhoDeCompra?idDoproduto=${produto.id}' "> 
				
				
		</c:forEach>
	</ul>
</td></tr>
</table>
</c:if>
 <br />

	<div align="left"><a href="http://localhost:8080/lojaVirtual/formularioCadastrarProduto">Quero Vender</a></div>
	<div align="right"><a href="http://localhost:8080/lojaVirtual/carrinhoDeCompras">Carrinho de Compras</a></div>
	<div align="center"><a href="http://localhost:8080/lojaVirtual/home">Inicio</a></div>

	<div align="right">
		<form action="http://localhost:8080/lojaVirtual/deslogar" method="POST">
			<input type="hidden" name="logout" value="Logout" /> <input
				type="submit" value="Deslogar" />
		</form>
	</div>
	
</body>
</html>
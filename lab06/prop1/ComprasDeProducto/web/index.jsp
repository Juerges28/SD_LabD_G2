<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Compra de Productos</title>
</head>
<body>
<h1>Compra de Productos</h1>
<form action="CompraServlet" method="post">
    Producto A ($10.00): <input type="number" name="cantidad0" value="0"/><br><br>
    Producto B ($20.00): <input type="number" name="cantidad1" value="0"/><br><br>
    Producto C ($15.50): <input type="number" name="cantidad2" value="0"/><br><br>
    <input type="submit" value="Calcular Total"/>
</form>
</body>
</html>

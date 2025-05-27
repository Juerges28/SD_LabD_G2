<%-- 
    Document   : bienvenida
    Created on : May 27, 2025, 7:54:35 AM
    Author     : peposinho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bienvenido</title>
    <style>
        body {
            background-color: #e6f7ff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #ffffff;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0, 123, 255, 0.2);
            text-align: center;
        }
        h1 {
            color: #007bff;
            font-size: 2.2rem;
            margin-bottom: 20px;
        }
        p {
            font-size: 1.1rem;
            color: #333;
        }
        a {
            display: inline-block;
            margin-top: 25px;
            text-decoration: none;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border-radius: 6px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>¡Bienvenido usuario!</h1>
        <p>Has iniciado sesión correctamente.</p>
        <a href="login.jsp">Cerrar sesión</a>
    </div>
</body>
</html>

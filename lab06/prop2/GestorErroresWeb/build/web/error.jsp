<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - Página de Error</title>
    <style>
        body {
            background-color: #fdecea;
            color: #611a15;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
        }
        .error-container {
            background-color: #fff1f0;
            border: 1px solid #f5c6cb;
            border-radius: 10px;
            padding: 30px 40px;
            max-width: 600px;
            box-shadow: 0 6px 15px rgba(198, 60, 60, 0.2);
            text-align: center;
        }
        h1 {
            color: #b71c1c;
            margin-bottom: 15px;
            font-size: 2.2rem;
        }
        p {
            font-size: 1.1rem;
            margin: 12px 0;
        }
        .technical-details {
            margin-top: 30px;
            padding: 20px;
            background-color: #f9d6d5;
            border: 1px solid #e99a9a;
            border-radius: 8px;
            text-align: left;
            color: #4a1a16;
            font-family: monospace;
            font-size: 0.95rem;
            line-height: 1.4;
        }
        b {
            color: #7a1616;
        }
        .back-button {
            display: inline-block;
            margin-top: 25px;
            padding: 10px 20px;
            background-color: #c62828;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .back-button:hover {
            background-color: #8e0000;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>¡Ha ocurrido un error!</h1>
        <p>Mensaje para el usuario: <%= request.getAttribute("mensajeUsuario") != null ? request.getAttribute("mensajeUsuario") : "Acción no válida" %></p>

        <%
            if (exception != null) {
        %>
        <div class="technical-details">
            <h3>Detalles Técnicos (para depuración):</h3>
            <p><b>Tipo:</b> <%= exception.getClass().getName() %></p>
            <p><b>Mensaje:</b> <%= exception.getMessage() %></p>
        </div>
        <%
            }
        %>

        <a href="login.jsp" class="back-button">Volver al inicio</a>
    </div>
</body>
</html>

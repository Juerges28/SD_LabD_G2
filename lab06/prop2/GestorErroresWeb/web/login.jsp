<%-- 
    Document   : login
    Created on : May 27, 2025, 7:16:41 AM
    Author     : peposinho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <style>
            body {
                background: #f0f2f5;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            form {
                background: white;
                padding: 30px 40px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                width: 320px;
            }
            h2 {
                text-align: center;
                margin-bottom: 25px;
                color: #333;
            }
            label {
                display: block;
                margin-bottom: 6px;
                color: #555;
                font-weight: bold;
            }
            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 10px 12px;
                margin-bottom: 20px;
                border: 1.8px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
                transition: border-color 0.3s ease;
            }
            input[type="text"]:focus,
            input[type="password"]:focus {
                border-color: #007bff;
                outline: none;
            }
            input[type="submit"] {
                width: 100%;
                padding: 12px;
                background-color: #007bff;
                border: none;
                border-radius: 6px;
                color: white;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <form action="LoginServlet" method="post">
            <h2>Iniciar Sesión</h2>
            <label for="usuario">Usuario:</label>
            <input type="text" id="usuario" name="usuario" required />

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required />

            <input type="submit" value="Ingresar" />
        </form>
    </body>
</html>



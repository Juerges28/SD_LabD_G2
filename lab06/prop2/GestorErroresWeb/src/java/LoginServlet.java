import java.io.IOException;
import javax.servlet.ServletException;import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");
            System.out.println("ola"+usuario+"oa"+password);
            if (usuario == null || password == null || usuario.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Debe completar todos los campos.");
            }

            if (usuario.equals(USER) && password.equals(PASSWORD)) {
                response.sendRedirect("bienvenido.jsp");
            } else {
                request.setAttribute("mensajeUsuario", "Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("mensajeUsuario", "Error en la autenticación.");
            throw new ServletException(ex); // Será capturado por web.xml
        }
    }
}

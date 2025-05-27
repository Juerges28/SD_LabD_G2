package compra.productos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "CompraServlet", urlPatterns = {"/CompraServlet"})
public class CompraServlet extends HttpServlet {

    private static final double[] precios = {10.0, 20.0, 15.5};

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        double total = 0.0;
        for (int i = 0; i < precios.length; i++) {
            String param = request.getParameter("cantidad" + i);
            int cantidad;
            try {
                cantidad = Integer.parseInt(param);
                if (cantidad < 0) {
                    request.setAttribute("error", "Lo siento, ingrese una cantidad positiva.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Cantidad inválida.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            total += cantidad * precios[i];
        }

        request.setAttribute("total", total);
        request.getRequestDispatcher("resultado.jsp").forward(request, response);
    }
}

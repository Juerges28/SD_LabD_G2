package empresamysql;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Desplazamiento extends JFrame {
    JLabel lblId, lblNombre, lblDescripcion;
    JTextField txtId, txtNombre, txtDescripcion;
    JButton btnPrimero, btnSiguiente, btnAnterior, btnUltimo;
    ResultSet resultado;

    public Desplazamiento(String titulo) {
        super(titulo);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblId = new JLabel("Id Categoria");
        lblNombre = new JLabel("Nombre");
        lblDescripcion = new JLabel("Descripcion");
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtDescripcion = new JTextField();

        btnPrimero = new JButton("Primero");
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnUltimo = new JButton("Último");

        // Eventos
        btnPrimero.addActionListener(new EventoBoton());
        btnAnterior.addActionListener(new EventoBoton());
        btnSiguiente.addActionListener(new EventoBoton());
        btnUltimo.addActionListener(new EventoBoton());

        // Layout
        JPanel panel1 = new JPanel(new GridLayout(3, 2));
        panel1.add(lblId);
        panel1.add(txtId);
        panel1.add(lblNombre);
        panel1.add(txtNombre);
        panel1.add(lblDescripcion);
        panel1.add(txtDescripcion);

        JPanel panel2 = new JPanel();
        panel2.add(btnPrimero);
        panel2.add(btnAnterior);
        panel2.add(btnSiguiente);
        panel2.add(btnUltimo);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel1, BorderLayout.CENTER);
        getContentPane().add(panel2, BorderLayout.SOUTH);

        Conexion();
    }

    private void Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/empresamysql", "root", "1234");

            Statement sentencia = conexion.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery("SELECT * FROM Categorias");

            if (resultado.next()) {
                mostrarResultados();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    private void mostrarResultados() throws SQLException {
        txtId.setText(resultado.getString("IdCategoria"));
        txtNombre.setText(resultado.getString("Nombre"));
        txtDescripcion.setText(resultado.getString("Descripcion"));
    }

    public static void main(String[] args) {
        Desplazamiento ventana = new Desplazamiento("Desplazamiento de Registros");
        ventana.setVisible(true);
    }

    private class EventoBoton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                switch (e.getActionCommand()) {
                    case "Primero":
                        resultado.first();
                        break;
                    case "Anterior":
                        if (!resultado.previous()) resultado.first();
                        break;
                    case "Siguiente":
                        if (!resultado.next()) resultado.last();
                        break;
                    case "Último":
                        resultado.last();
                        break;
                }
                mostrarResultados();
            } catch (SQLException ex) {
                System.out.println("Error en navegación de registros: " + ex.getMessage());
            }
        }
    }
}

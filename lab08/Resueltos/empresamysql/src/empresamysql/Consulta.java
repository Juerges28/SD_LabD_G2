package empresamysql;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Consulta extends JFrame {
    JTextField txtSql;
    JTextArea areaResultados;
    JButton btnConsulta;

    public Consulta(String titulo) {
        super(titulo);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtSql = new JTextField(20);
        areaResultados = new JTextArea(10, 30);
        btnConsulta = new JButton("Ejecutar SQL");
        JScrollPane scrollPanel = new JScrollPane(areaResultados);

        // Vincula el botón al evento directamente dentro de la clase
        btnConsulta.addActionListener(new EventoBoton());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(new JLabel("Escribe tu consulta SQL:"));
        panel1.add(txtSql);
        panel1.add(btnConsulta);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(new JLabel("Resultados:"));
        panel2.add(scrollPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel2, BorderLayout.CENTER);

        getContentPane().add(panel);
    }

    void verBaseDatos() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresamysql", "root", "1234");

            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(txtSql.getText());

            mostrarResultados(resultado);
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    void mostrarResultados(ResultSet r) throws SQLException {
        ResultSetMetaData res = r.getMetaData();
        int numCampos = res.getColumnCount();
        StringBuilder texto = new StringBuilder();

        for (int i = 1; i <= numCampos; i++) {
            texto.append(res.getColumnName(i)).append("\t");
        }
        texto.append("\n");

        while (r.next()) {
            for (int j = 1; j <= numCampos; j++) {
                texto.append(r.getString(j)).append("\t");
            }
            texto.append("\n");
        }

        areaResultados.setText(texto.toString());
    }

    public static void main(String[] args) {
        Consulta ventana = new Consulta("Consulta MySQL");
        ventana.setVisible(true);
    }

    // Clase interna para manejar el evento del botón
    private class EventoBoton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            verBaseDatos();
        }
    }
}
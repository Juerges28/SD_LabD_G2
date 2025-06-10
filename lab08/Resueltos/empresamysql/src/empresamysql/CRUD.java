package empresamysql;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class Ventana extends JFrame {
    JLabel LblId;
    JLabel LblNombre;
    JLabel LblDescripcion;
    JTextField TxtId;
    JTextField TxtNombre;
    JTextField TxtDescripcion;
    JButton BtnPrimero;
    JButton BtnSiguiente;
    JButton BtnAnterior;
    JButton BtnUltimo;
    JButton BtnInsertar;
    JButton BtnModificar;
    JButton BtnEliminar;
    JButton BtnActualizar;
    ResultSet resultado;
    Statement sentencia;

    public Ventana(String titulo) {
        super(titulo);

        LblId = new JLabel("Id Categoria");
        LblNombre = new JLabel("Nombre");
        LblDescripcion = new JLabel("Descripcion");
        TxtId = new JTextField();
        TxtNombre = new JTextField();
        TxtDescripcion = new JTextField();

        BtnPrimero = new JButton("Primero");
        BtnPrimero.addActionListener(new EventoBoton(this));
        BtnAnterior = new JButton("Anterior");
        BtnAnterior.addActionListener(new EventoBoton(this));
        BtnSiguiente = new JButton("Siguiente");
        BtnSiguiente.addActionListener(new EventoBoton(this));
        BtnUltimo = new JButton("Ultimo");
        BtnUltimo.addActionListener(new EventoBoton(this));

        BtnInsertar = new JButton("Insertar");
        BtnInsertar.addActionListener(new EventoBoton(this));
        BtnModificar = new JButton("Modificar");
        BtnModificar.addActionListener(new EventoBoton(this));
        BtnEliminar = new JButton("Eliminar");
        BtnEliminar.addActionListener(new EventoBoton(this));
        BtnActualizar = new JButton("Actualizar");
        BtnActualizar.addActionListener(new EventoBoton(this));

        JPanel Panel1 = new JPanel();
        JPanel Panel11 = new JPanel();
        Panel11.setLayout(new BoxLayout(Panel11, BoxLayout.Y_AXIS));
        Panel11.setAlignmentY(BOTTOM_ALIGNMENT);
        Panel11.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel11.add(LblId);
        Panel11.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel11.add(LblNombre);
        Panel11.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel11.add(LblDescripcion);
        Panel11.add(Box.createRigidArea(new Dimension(10, 20)));

        JPanel Panel12 = new JPanel();
        Panel12.setLayout(new BoxLayout(Panel12, BoxLayout.Y_AXIS));
        Panel12.setAlignmentY(BOTTOM_ALIGNMENT);
        Panel12.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel12.add(TxtId);
        Panel12.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel12.add(TxtNombre);
        Panel12.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel12.add(TxtDescripcion);
        Panel12.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel12.add(Box.createVerticalGlue());

        Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.X_AXIS));
        Panel1.setAlignmentY(TOP_ALIGNMENT);
        Panel1.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel1.add(Panel11);
        Panel1.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel1.add(Panel12);

        JPanel Panel2 = new JPanel();
        Panel2.setLayout(new BoxLayout(Panel2, BoxLayout.X_AXIS));
        Panel2.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel2.add(BtnInsertar);
        Panel2.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel2.add(BtnModificar);
        Panel2.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel2.add(BtnActualizar);
        Panel2.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel2.add(BtnEliminar);
        Panel2.add(Box.createRigidArea(new Dimension(5, 10)));
        Panel2.setBackground(new Color(0, 0, 255));

        JPanel Panel3 = new JPanel();
        Panel3.setLayout(new BoxLayout(Panel3, BoxLayout.X_AXIS));
        Panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel3.add(BtnPrimero);
        Panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel3.add(BtnAnterior);
        Panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel3.add(BtnSiguiente);
        Panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel3.add(BtnUltimo);
        Panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        Panel3.setBackground(Color.orange);

        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        Panel.setAlignmentY(TOP_ALIGNMENT);
        Panel.add(Panel1);
        Panel.add(Box.createRigidArea(new Dimension(0, 20)));
        Panel.add(Panel2);
        Panel.add(Box.createRigidArea(new Dimension(0, 20)));
        Panel.add(Panel3);
        Panel.setBackground(new Color(255, 0, 0));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Panel);

        addWindowListener(new EventosVentana(this));
        Conexion();
    }

    private void Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresamysql", "root", "1234");
            sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            boolean tieneResultados = sentencia.execute("Select * from Categorias");
            if (tieneResultados) {
                resultado = sentencia.getResultSet();
                if (resultado != null) {
                    mostrarResultados(resultado);
                }
            }
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void mostrarResultados(ResultSet r) throws SQLException {
        ResultSetMetaData res = r.getMetaData();
        int numCampos = res.getColumnCount();
        String texto = "";
    }

    public void IrPrimero() {
        try {
            resultado.first();
            TxtId.setText(resultado.getString("IdCategoria"));
            TxtNombre.setText(resultado.getString("Nombre"));
            TxtDescripcion.setText(resultado.getString("Descripcion"));
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrAnterior() {
        try {
            if (!resultado.previous()) resultado.first();
            TxtId.setText(resultado.getString("IdCategoria"));
            TxtNombre.setText(resultado.getString("Nombre"));
            TxtDescripcion.setText(resultado.getString("Descripcion"));
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrSiguiente() {
        try {
            if (!resultado.next()) resultado.last();
            TxtId.setText(resultado.getString("IdCategoria"));
            TxtNombre.setText(resultado.getString("Nombre"));
            TxtDescripcion.setText(resultado.getString("Descripcion"));
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrUltimo() {
        try {
            resultado.last();
            TxtId.setText(resultado.getString("IdCategoria"));
            TxtNombre.setText(resultado.getString("Nombre"));
            TxtDescripcion.setText(resultado.getString("Descripcion"));
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrInsertar() {
        try {
            String var1 = TxtId.getText();
            String var2 = TxtNombre.getText();
            String var3 = TxtDescripcion.getText();
            sentencia.executeUpdate("INSERT INTO Categorias(Nombre, Descripcion) VALUES ('" + var2 + "', '" + var3 + "')");
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrEliminar() {
        try {
            String var1 = TxtId.getText();
            sentencia.executeUpdate("DELETE FROM Categorias WHERE IdCategoria = " + var1);
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrModificar() {
        try {
            String var1 = TxtId.getText();
            String var2 = TxtNombre.getText();
            String var3 = TxtDescripcion.getText();
            sentencia.executeUpdate("UPDATE Categorias SET Nombre = '" + var2 + "', Descripcion = '" + var3 + "' WHERE IdCategoria = " + var1);
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }

    public void IrActualizar() {
        try {
            sentencia.execute("SELECT * FROM Categorias");
            resultado = sentencia.getResultSet();
        } catch (Exception e) {
            System.out.println("Error de Conexión: " + e);
        }
    }
}

class EventoBoton implements ActionListener {
    Ventana ventana;

    public EventoBoton(Ventana v) {
        ventana = v;
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Primero":
                ventana.IrPrimero();
                break;
            case "Anterior":
                ventana.IrAnterior();
                break;
            case "Siguiente":
                ventana.IrSiguiente();
                break;
            case "Ultimo":
                ventana.IrUltimo();
                break;
            case "Insertar":
                ventana.IrInsertar();
                break;
            case "Modificar":
                ventana.IrModificar();
                break;
            case "Eliminar":
                ventana.IrEliminar();
                break;
            case "Actualizar":
                ventana.IrActualizar();
                break;
        }
    }
}

class EventosVentana extends WindowAdapter {
    Ventana ventana;

    public EventosVentana(Ventana v) {
        ventana = v;
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

class CRUD {
    public static void main(String[] args) {
        Ventana v = new Ventana("CRUD de Categorias");
        v.setSize(600, 400);
        v.setVisible(true);
    }
}

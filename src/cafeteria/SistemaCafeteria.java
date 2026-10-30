package cafeteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SistemaCafeteria extends JFrame {
    private List<Producto> listaProductos;
    private DefaultTableModel modeloTabla;
    private JTable tablaProductos;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock;

    public SistemaCafeteria() {
        // Configuración de la ventana principal (JFrame)
        setTitle("Sistema de Cafetería - Práctica 2");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar productos del archivo o inicializar unos por defecto
        listaProductos = ArchivoDatos.cargarProductos();
        if (listaProductos.isEmpty()) {
            listaProductos.add(new Producto("P01", "Café Americano", 1.50, 50));
            listaProductos.add(new Producto("P02", "Sanduche de Pollo", 2.50, 20));
            ArchivoDatos.guardarProductos(listaProductos);
        }

        initComponents();
        cargarDatosTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel Superior: Formulario para ingresar productos
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Productos"));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        JButton btnAgregar = new JButton("Guardar / Agregar Producto");
        panelFormulario.add(btnAgregar);

        JButton btnGuardarArchivo = new JButton("Guardar en Archivo");
        panelFormulario.add(btnGuardarArchivo);

        add(panelFormulario, BorderLayout.NORTH);

        // Panel Central: Tabla visual para mostrar los productos
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Precio ($)", "Stock"}, 0);
        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // Eventos de los botones
        btnAgregar.addActionListener(e -> agregarProducto());
        btnGuardarArchivo.addActionListener(e -> {
            ArchivoDatos.guardarProductos(listaProductos);
            JOptionPane.showMessageDialog(this, "Datos guardados en el archivo correctamente.");
        });
    }

    private void agregarProducto() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Producto p = new Producto(codigo, nombre, precio, stock);
            listaProductos.add(p);
            cargarDatosTabla();

            // Limpiar campos de texto
            txtCodigo.setText("");
            txtNombre.setText("");
            txtPrecio.setText("");
            txtStock.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, verifique que los campos numéricos sean correctos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : listaProductos) {
            modeloTabla.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio(), p.getStock()});
        }
    }

    // Método principal para ejecutar la aplicación directamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaCafeteria().setVisible(true);
        });
    }
}

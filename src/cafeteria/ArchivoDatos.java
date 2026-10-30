package cafeteria;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoDatos {
    private static final String ARCHIVO_PRODUCTOS = "productos.txt";

    public static void guardarProductos(List<Producto> productos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            for (Producto p : productos) {
                pw.println(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        File archivo = new File(ARCHIVO_PRODUCTOS);
        if (!archivo.exists()) return productos;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto p = Producto.fromString(linea);
                if (p != null) {
                    productos.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
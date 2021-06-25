package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.*;


public class ProductosDAO {

    private int id_producto;
    private String nombre_producto;
    private int existencia;
    private String disponible;

    private byte[] imagen;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    //Manda datos
    public void INSERT() {
        try {
            String query = "INSERT INTO tbl_productos (nombre_producto, existencia, disponible, imagen) " +
                    "VALUES('" + nombre_producto + "'," + existencia + ",'" + disponible + "','" + imagen + "')";

            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Manda datos (Actualiza la DB)
    public void UPDATE() {
        try {
            String query = "UPDATE tbl_productos SET nombre_producto = '" + nombre_producto + "', existencia = " + existencia + ", " +
                    "disponible = '" + disponible + "', imagen =  '" + imagen + "' WHERE id_producto = " + id_producto;

            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Borra datos
    public void DELETE() {
        try {
            String query = "DELETE FROM tbl_productos WHERE id_producto = " + id_producto;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Trae datos
    public ObservableList<ProductosDAO> SELECT() {

        ObservableList<ProductosDAO> listaC = FXCollections.observableArrayList();
        try {
            ProductosDAO objC;

            String query = "SELECT * FROM tbl_productos ORDER BY nombre_producto ASC";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                objC = new ProductosDAO();
                objC.id_producto = res.getInt("id_producto");
                objC.nombre_producto = res.getString("nombre_producto");
                objC.existencia = res.getInt("existencia");
                objC.disponible = res.getString("disponible");
                objC.imagen = res.getBytes("imagen");

                listaC.add(objC);
            }
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaC;
    }

    public Image renderImagen() throws IOException {
        Image i= new Image(getClass().getResourceAsStream(this.getImagen().toString()));

        return i;
    }

}
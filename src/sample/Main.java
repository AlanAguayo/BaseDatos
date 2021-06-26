package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sample.components.CellCustome;
import sample.models.ProductosDAO;
import sample.models.Conexion;
import sample.view.frmProductos;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Main extends Application implements EventHandler<WindowEvent> {

    @Override
    public void start(Stage primaryStage) throws Exception{
        objCDAO = new ProductosDAO();

        Conexion.getConexion();
        CrearUI();

        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,this);
        primaryStage.setTitle("Base de Datos");
        primaryStage.setScene(escena);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private TableView tbvProductos;
    private Button btnAgregar;
    private VBox vBox;
    private Scene escena;
    private ProductosDAO objCDAO;
    private frmProductos productos;

    private void CrearUI() {
        vBox = new VBox();

        tbvProductos = new TableView<>();
        btnAgregar=new Button("Agregar");

        btnAgregar.setOnAction(event -> {
            productos = new frmProductos(tbvProductos, null);
        });

        vBox.getChildren().addAll(tbvProductos, btnAgregar);

        CrearTabla();
        escena = new Scene(vBox, 500, 250);
    }

    private void CrearTabla() {
        TableColumn<ProductosDAO, Integer> tbcIdProducto = new TableColumn<>("ID");
        tbcIdProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));

        TableColumn<ProductosDAO, String> tbcNombre = new TableColumn<>("Producto");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));

        TableColumn<ProductosDAO, Integer> tbcExistencia = new TableColumn<>("Existencia");
        tbcExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        TableColumn<ProductosDAO, String> tbcDisponible = new TableColumn<>("Disponibilidad");
        tbcDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        TableColumn<ProductosDAO, byte[]> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        /*tbcImagen.setCellFactory(
                new Callback<TableColumn<ProductosDAO, byte[]>, TableCell<ProductosDAO, byte[]>>() {
                    @Override
                    public TableCell<ProductosDAO, byte[]> call(TableColumn<ProductosDAO, byte[]> param) {
                        return new CellCustome(3);
                    }
                }
        );
*/
        TableColumn<ProductosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ProductosDAO, String>, TableCell<ProductosDAO, String>>() {
                    @Override
                    public TableCell<ProductosDAO, String> call(TableColumn<ProductosDAO, String> param) {
                        return new CellCustome(1);
                    }
                }
        );

        TableColumn<ProductosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(
                new Callback<TableColumn<ProductosDAO, String>, TableCell<ProductosDAO, String>>() {
                    @Override
                    public TableCell<ProductosDAO, String> call(TableColumn<ProductosDAO, String> param) {
                        return new CellCustome(2);
                    }
                }
        );

        tbvProductos.getColumns().addAll(tbcIdProducto, tbcNombre, tbcExistencia, tbcDisponible, tbcImagen, tbcEditar, tbcBorrar);
        tbvProductos.setItems(objCDAO.SELECT());
    }


    @Override
    public void handle(WindowEvent event) {
        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setHeaderText("Gracias por usar la aplicacion :D");
        alerta.setContentText("Vuelva Pronto");
        alerta.showAndWait();
    }
}

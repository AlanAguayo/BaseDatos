package sample.components;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.models.ProductosDAO;
import sample.view.frmProductos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;


public class CellCustome extends TableCell<ProductosDAO, String> {
    private Button btnCelda;
    private ProductosDAO objCDAO;
    private ImageView lblImagen;

    public CellCustome(int opc){

        if(opc==1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objCDAO = CellCustome.this.getTableView().getItems().get(CellCustome.this.getIndex());
                new frmProductos(CellCustome.this.getTableView(),objCDAO);
            });
        }
        if(opc == 2) {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema :)");
                alerta.setHeaderText("Confirmacion de la accion");
                alerta.setContentText("Realmente deseas borrar el registro?");
                Optional<ButtonType> result = alerta.showAndWait();
                if (result.get() == ButtonType.OK) {
                    objCDAO = CellCustome.this.getTableView().getItems().get(CellCustome.this.getIndex());
                    objCDAO.DELETE();

                    CellCustome.this.getTableView().setItems(objCDAO.SELECT());
                    CellCustome.this.getTableView().refresh();
                }
            });
        }
        if(opc==3){
            objCDAO = CellCustome.this.getTableView().getItems().get(12);
            ByteArrayInputStream bis = new ByteArrayInputStream(objCDAO.getImagen());
            BufferedImage read = null;
            {
                try {
                    read = ImageIO.read(bis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Image i = SwingFXUtils.toFXImage(read, null);
            CellCustome.this.getTableView().setItems(objCDAO.SELECT());
            CellCustome.this.getTableView().refresh();
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if( !empty)
            setGraphic(btnCelda);
    }
}
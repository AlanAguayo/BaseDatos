package sample.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.ProductosDAO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class frmProductos extends Stage {
    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtExistencia, txtDisponible, txtImagen;
    private Button btnGuardar;
    private Button btnImagen;
    private RadioButton rbDisponible;
    private RadioButton rbDescontinuado;
    private ImageIcon imgi;
    private ProductosDAO objCDAO;
    private TableView<ProductosDAO> tbvProductos;
    private TextField txtruta;

    public frmProductos(TableView<ProductosDAO> tbvCanciones, ProductosDAO objCDAO){
        this.tbvProductos = tbvCanciones;
        if(objCDAO != null)
            this.objCDAO = objCDAO;

        else
            this.objCDAO = new ProductosDAO();


        CrearUI();
        this.setTitle("Productos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        final ToggleGroup grupo = new ToggleGroup();
        vBox=new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));

        txtNombre = new TextField();
        txtNombre.setText(objCDAO.getNombre_producto());
        txtNombre.setPromptText("Nombre");
        txtExistencia =new TextField();
        txtExistencia.setText(String.valueOf(objCDAO.getExistencia()));
        txtExistencia.setPromptText("Existencia");
        rbDisponible = new RadioButton("Disponible");
        rbDisponible.setToggleGroup(grupo);
        rbDisponible.setSelected(true);
        rbDescontinuado = new RadioButton("Descontinuado");
        rbDescontinuado.setToggleGroup(grupo);

        if(objCDAO.getDisponible()==null){

        }else{
            if(objCDAO.getDisponible().equals("Descontinuado")){
            rbDescontinuado.setSelected(true);
            }
        }

        txtDisponible =new TextField();
        txtDisponible.setText(objCDAO.getDisponible());
        txtDisponible.setPromptText("Disponibilidad");
        txtruta = new TextField();
        btnImagen = new Button ("Imagen");
        btnImagen.setOnAction(event -> {
            JFileChooser j = new JFileChooser();
            FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
            j.setFileFilter(fil);

            int s = j.showOpenDialog(null);
            if(s == JFileChooser.APPROVE_OPTION){
                String ruta = j.getSelectedFile().getAbsolutePath();
                txtruta.setText(ruta);
            }
        });

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objCDAO.setNombre_producto(txtNombre.getText());
            objCDAO.setExistencia(Integer.parseInt(txtExistencia.getText()));
            if(rbDisponible.isSelected()){
                objCDAO.setDisponible("Disponible");
            }
            else{
                objCDAO.setDisponible("Descontinuado");
            }

            ////////////////////
            try{
                File ruta = new File(txtruta.getText());
                InputStream input = new FileInputStream(ruta);
                byte[] icono = new byte[(int) ruta.length()];

                input.read(icono);

                objCDAO.setImagen(icono);
            }catch(Exception ex){
                objCDAO.setImagen(null);
            }

            /////////////////////
            if(objCDAO.getId_producto()>0) {
                objCDAO.UPDATE();
            }else {
                objCDAO.INSERT();
            }

            tbvProductos.setItems(objCDAO.SELECT());
            tbvProductos.refresh();

            this.close();
        });
        vBox.getChildren().addAll(txtNombre, txtExistencia, rbDisponible,rbDescontinuado, btnImagen,btnGuardar);
        escena=new Scene(vBox,250,300);
    }
}

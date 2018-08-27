package application.vistas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.extras.botones;
import application.tablas.tablaFacturaDet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class facturaGenera {
	public TableView<tablaFacturaDet> tableFacturacion = new TableView();

	public TableColumn<tablaFacturaDet, String> idTable = new TableColumn<>("Cantidad");
	public TableColumn<tablaFacturaDet, String> Nombre = new TableColumn<>("Articulo");
	public TableColumn<tablaFacturaDet, String> Desc = new TableColumn<>("Valor unitario");
	public TableColumn<tablaFacturaDet, String> Precio = new TableColumn<>("Valor total");

	public Stage ventanaActual;
	public String usuarioGlobal="";
	public TextField txtRuc, txtCliente, txtDireccion, txtApellidos, txtTelefono, txtCorreo, txtStock;
	public TextField txtConsulta, txtCantidad, txtSubtotal, txtIva, txtIvaDoce, txtTotal, txtCantidadString;
	public Button btnAdd, btnExit, btnAddProducto;
	public float totalFacturar = 0.2f;
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Seleccione un producto");;
	public ComboBox<String> comboProductos = new ComboBox<String>(Contenido);

	public void formularioFactura(Stage stgFactura, String usuario) 
	{
		usuarioGlobal = usuario;
		//ventanaActual = stgFactura;
		ventanaActual = new Stage();
		Text scenetitle = new Text("Facturación");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(250);
		scenetitle.setY(40);
		// scenetitle.setFont(new Font("Arial",20));

		Label lblRuc = new Label("CEDULA/RUC: ");
		lblRuc.setLayoutX(20);
		lblRuc.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(55);
		Label lblFecha = new Label("FECHA: ");
		lblFecha.setLayoutX(300);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(380);
		txtFecha.setLayoutY(60);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: " + dateFormat.format(date));
		txtFecha.setText(dateFormat.format(date).toString());

		Label lblCliente = new Label("NOMBRES: ");
		lblCliente.setLayoutX(20);
		lblCliente.setLayoutY(90);
		txtCliente = new TextField();
		txtCliente.setEditable(false);
		txtCliente.setLayoutX(100);
		txtCliente.setLayoutY(85);
		Label lblApellidos = new Label("APELLIDOS: ");
		lblApellidos.setLayoutX(300);
		lblApellidos.setLayoutY(90);
		txtApellidos = new TextField();
		txtApellidos.setLayoutX(380);
		txtApellidos.setLayoutY(85);
		txtApellidos.setEditable(false);

		Label lblDireccion = new Label("DIRECCIÓN: ");
		lblDireccion.setLayoutX(20);
		lblDireccion.setLayoutY(120);
		txtDireccion = new TextField();
		txtDireccion.setEditable(false);
		txtDireccion.setLayoutX(100);
		txtDireccion.setLayoutY(115);
		Label lblTelefono = new Label("TELÉFONO: ");
		lblTelefono.setLayoutX(300);
		lblTelefono.setLayoutY(120);
		txtTelefono = new TextField();
		txtTelefono.setEditable(false);
		txtTelefono.setLayoutX(380);
		txtTelefono.setLayoutY(115);

		Label lblCorreo = new Label("CORREO: ");
		lblCorreo.setLayoutX(20);
		lblCorreo.setLayoutY(150);
		txtCorreo = new TextField();
		txtCorreo.setLayoutX(100);
		txtCorreo.setLayoutY(145);
		txtCorreo.setPrefSize(200, 25);
		txtCorreo.setEditable(false);

		AnchorPane fondo = new AnchorPane();
		botones bot = new botones();
		fondo.getChildren().add(bot.fondoPantalla());

		Group panel = new Group();
		AnchorPane grupo = new AnchorPane();
		grupo.getChildren().addAll(// lblRuc,
				txtRuc, lblDireccion, lblTelefono, lblCorreo);
		grupo.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		grupo.setTranslateX(20);
		grupo.setTranslateY(20);
		grupo.setMaxSize(560, 200);

		AnchorPane grupo2 = new AnchorPane();
		grupo2.getChildren().addAll(// lblRuc,
				txtCorreo, txtDireccion);
		grupo2.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		grupo2.setTranslateX(20);
		grupo2.setTranslateY(200);
		grupo2.setMaxSize(560, 200);

		panel.getChildren().addAll(fondo, grupo, grupo2);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(panel, 700, 650);
		ventanaActual.setTitle("Control de ventas");
		ventanaActual.setScene(escenaConsulta);
		// ventanaActual.setResizable(false);
		ventanaActual.show();

	}

}

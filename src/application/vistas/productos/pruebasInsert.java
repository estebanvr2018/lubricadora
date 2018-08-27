package application.vistas.productos;

import java.util.List;

import application.Principal;
import application.com.DTOS.ProductosDTO;
import application.tablas.tablaFacturaDet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class pruebasInsert implements EventHandler<ActionEvent> {
	public TextField txtProducto, txtValorUni, txtStock, txtDescripcion;
	public Button btnBuscar, btnExit;
	public Stage ventanaActual;

	public TableView<tablaFacturaDet> tableProductos = new TableView();
	public TableColumn<tablaFacturaDet, String> idTable = new TableColumn<>("Cod. Artículo");
	public TableColumn<tablaFacturaDet, String> Nombre = new TableColumn<>("Descripción");
	public TableColumn<tablaFacturaDet, String> Desc = new TableColumn<>("Valor unitario");
	public TableColumn<tablaFacturaDet, String> Stock = new TableColumn<>("Stock");
	public List<ProductosDTO> productos = null;

	/*** ingreso de productos ***/
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Aceite", "Filtro", "Retenedores");;
	public ComboBox<String> comboProductos = new ComboBox<String>(Contenido);

	/*** FIn ingreso de productois ***/

	public void ingresoProductos(Stage ventanaIngreso) {

		ventanaActual = ventanaIngreso;
		Text scenetitle = new Text("Ingreso Seccion Productos.");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(250);
		scenetitle.setY(40);
		// scenetitle.setFont(new Font("Arial",20));
		/*** Nuevos Cambios para el ingreso del producto ***/

		Label lblTipo = new Label("Tipo producto");
		lblTipo.setLayoutX(30);
		lblTipo.setLayoutY(60);
		comboProductos.setLayoutX(120);
		comboProductos.setLayoutY(60);

		Label lblProducto = new Label("Nuevo");
		lblProducto.setLayoutX(240);
		lblProducto.setLayoutY(60);
		TextField txtProducto = new TextField();
		txtProducto.setLayoutX(300);
		txtProducto.setLayoutY(60);
		// comboProductos.setPrefSize(200, 25);

		comboProductos.valueProperty().addListener((ov, p1, p2) -> {

			System.out.println("Producto --> " + p2);
			System.out.println("================================================================================");
			System.out.println(" Agregando producto a la tabla...");
			System.out.println("================================================================================");

		});

		/*** Fin nuevos cambios apra el ingreso del producto ***/

		Image image = new Image("application/buscar.png", 20, 20, true, true);
		ImageView imgVie = new ImageView(image);
		btnBuscar = new Button("");
		btnBuscar.setGraphic(imgVie);
		btnBuscar.setLayoutX(472);
		btnBuscar.setLayoutY(53);
		btnBuscar.setPrefSize(25, 25);
		btnBuscar.setOnAction(this);

		btnExit = new Button("Ir a Inicio");
		btnExit.setLayoutX(520);
		btnExit.setLayoutY(370);
		// btnExit.setPrefSize(25, 25);
		btnExit.setOnAction(this);

		/**/
		idTable.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
		Nombre.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		Desc.setCellValueFactory(new PropertyValueFactory<>("ValorUnitario"));
		Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));

		idTable.setMinWidth(50);
		Nombre.setMinWidth(200);
		Desc.setMinWidth(150);
		Stock.setMinWidth(50);

		tableProductos.getColumns().addAll(idTable, Nombre, Desc, Stock);

		tableProductos.setLayoutX(40);
		tableProductos.setLayoutY(130);
		tableProductos.setPrefSize(510, 200);
		/**/

		Group rootIngreso = new Group();

		Image imgCarga = new Image("application/madmenmag-fondo-verano-agua.jpg");
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp = new BorderPane();
		rootIngreso.getChildren().addAll(bp, scenetitle, btnBuscar, btnExit, comboProductos, lblTipo, lblProducto,
				txtProducto);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 590, 400);

		// VentanaConsultasProductos = new Stage();
		ventanaActual.setTitle("Ingreso de productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.show();

	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnExit) {
			System.out.println("==================================================");
			System.out.println("Redirigiendose al menú principal...");
			System.out.println("==================================================");
			ventanaActual.toBack();
			ventanaActual.close();
			Principal prin = new Principal();
			//prin.panelPrincipal();
		}
	}
}

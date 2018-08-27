package application.vistas.productos;

import java.sql.SQLException;
import java.util.List;
import application.Principal;
import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.productoDTO;
import application.com.DTOS.productosCategoriaDTO;
import application.com.DTOS.productosDescripcionDTO;
import application.extras.botones;
import application.tablas.tablaFacturaDet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class productosPrincipal implements EventHandler<ActionEvent> {
	public TextField txtNProducto, txtDescripcion, txtPrecioCompra, txtValorPorcentual, txtPrecioVta, txtStock,
			txtValorU, txtActStock;

	public TextField txtProNew, txtCatNew, numProductos;

	public Button btnGuardar, btnBuscar, btnExit, btnPrecio, btnGuardarCategoria, btnEnvia, btnLimpiar, btnActualizar;
	public Stage ventanaActual;
	public Stage VentanaConsulta;

	public boolean verifica = false;

	public productoDTO productoRec = new productoDTO();

	public TableView<tablaFacturaDet> tableProductos = new TableView();

	public TableView<productosDescripcionDTO> tableDescProductos = new TableView();
	public TableColumn<productosDescripcionDTO, String> descripcion = new TableColumn<>("Descripción");

	public TableColumn<tablaFacturaDet, String> idTable = new TableColumn<>("Cod. Artículo");
	public TableColumn<tablaFacturaDet, String> Nombre = new TableColumn<>("Descripción");
	public TableColumn<tablaFacturaDet, String> Desc = new TableColumn<>("Valor unitario");
	public TableColumn<tablaFacturaDet, String> Stock = new TableColumn<>("Stock");

	/*** ***/
	public TableView<productoDTO> productosIngresados = new TableView();
	public TableColumn<productoDTO, String> idProducto = new TableColumn<>("Cod");
	public TableColumn<productoDTO, String> nomProducto = new TableColumn<>("Nombre");
	public TableColumn<productoDTO, String> descProducto = new TableColumn<>("Descripción");
	public TableColumn<productoDTO, String> nombDescProducto = new TableColumn<>("Nombre Producto");
	public TableColumn<productoDTO, String> valorCompra = new TableColumn<>("V. Compra");
	public TableColumn<productoDTO, String> disponibles = new TableColumn<>("Stock");
	public TableColumn<productoDTO, String> valorVenta = new TableColumn<>("V. Venta");
	public TableColumn<productoDTO, String> categoria = new TableColumn<>("Categoría");
	public TableColumn<productoDTO, String> subcategoria = new TableColumn<>("Sub");
	/*** ***/

	public List<productoDTO> productos = null;
	public List<productoDTO> listaProductos = null;
	public List<productosCategoriaDTO> categoriaProductos = null;
	public List<productosDescripcionDTO> subCategoriaProductos = null;

	public String productoGeneral = null, productoEspecifico = null;
	/*** ingreso de productos ***/
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Nuevo producto");;
	public ObservableList<String> ContenidoDesc = FXCollections.observableArrayList(

	);;

	public ComboBox<String> comboProductosCat = new ComboBox<String>(Contenido);

	public ComboBox<String> comboProCatDescripcion = new ComboBox<String>(ContenidoDesc);

	// Bandera para controlar que ingreso por primera vez
	public String strBanderaControlaMensaje = "N";
	// permite actualizar la lista al actualizar o guardar un producto
	public String strSubCategoria = "";
	public String usuarioGlobal = "";
	/*** FIn ingreso de productois ***/

	public void ingresoProductos(Stage ventanaIngreso, String usuario) 
	{
		usuarioGlobal = usuario;
		// Bandera para controlar que ingreso por primera vez
		strBanderaControlaMensaje = "S";

		ventanaActual = ventanaIngreso;
		Label scenetitle = new Label("Sección productos");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(150);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");
		
		Label scenetitleT = new Label("Detalles");
		scenetitleT.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitleT.setLayoutX(150);
		scenetitleT.setLayoutY(5);
		scenetitleT.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));
		/*** Nuevos Cambios para el ingreso del producto ***/
		botones b = new botones();
		btnGuardarCategoria = new Button("Guardar");
		btnGuardarCategoria.setLayoutX(120);
		btnGuardarCategoria.setLayoutY(140);
		btnGuardarCategoria.setGraphic(b.botonGuardar());
		btnGuardarCategoria.setOnAction(this);
		btnGuardarCategoria.setVisible(false);

		Label lblNProducto = new Label("Nombre producto");
		lblNProducto.setLayoutX(30);
		lblNProducto.setLayoutY(40);
		lblNProducto.setVisible(false);
		txtNProducto = new TextField();
		txtNProducto.setLayoutX(150);
		txtNProducto.setLayoutY(40);
		txtNProducto.setPrefSize(100, 25);
		txtNProducto.setVisible(false);

		Label lblDescripcion = new Label("Descripción");
		lblDescripcion.setLayoutX(260);
		lblDescripcion.setLayoutY(40);
		lblDescripcion.setVisible(false);
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(340);
		txtDescripcion.setLayoutY(40);
		txtDescripcion.setPrefSize(220, 25);
		txtDescripcion.setVisible(false);

		Label lblPCompra = new Label("Precio compra");
		lblPCompra.setLayoutX(30);
		lblPCompra.setLayoutY(80);
		lblPCompra.setVisible(false);
		txtPrecioCompra = new TextField();
		txtPrecioCompra.setLayoutX(150);
		txtPrecioCompra.setLayoutY(75);
		txtPrecioCompra.setPrefSize(60, 25);
		txtPrecioCompra.setVisible(false);
		txtPrecioCompra.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
					txtPrecioCompra.setText(oldValue);
				}
			}
		});

		Label lblStock = new Label("Número de productos");
		lblStock.setLayoutX(260);
		lblStock.setLayoutY(80);
		lblStock.setVisible(false);
		txtStock = new TextField();
		txtStock.setLayoutX(390);
		txtStock.setLayoutY(75);
		txtStock.setPrefSize(60, 25);
		txtStock.setVisible(false);
		txtStock.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
					txtStock.setText(oldValue);
				}
			}
		});

		Label lblPorcentaje = new Label("Porcentaje[%]");
		lblPorcentaje.setLayoutX(260);
		lblPorcentaje.setLayoutY(110);
		lblPorcentaje.setVisible(false);
		txtValorPorcentual = new TextField();
		txtValorPorcentual.setLayoutX(390);
		txtValorPorcentual.setLayoutY(105);
		txtValorPorcentual.setPrefSize(60, 25);
		txtValorPorcentual.setVisible(false);
		txtValorPorcentual.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
					txtValorPorcentual.setText(oldValue);
				}
			}
		});

		btnPrecio = new Button("Calcular",b.botonCalcular());
		btnPrecio.setLayoutX(500);
		btnPrecio.setLayoutY(95);
		//btnPrecio.setPrefSize(15, 20);
		btnPrecio.setVisible(false);
		btnPrecio.setTextFill(Color.GREEN);
		btnPrecio.setContentDisplay(ContentDisplay.BOTTOM);
		btnPrecio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Calculando...");
				System.out.println("=======================================================");
				if (txtPrecioCompra.getText().toString().trim().isEmpty()
						&& txtValorPorcentual.getText().toString().trim().isEmpty()) {
					alertasMensajes alerta = new alertasMensajes();
					String srtError = "Faltan datos por ingresar.. ";
					alerta.alertaGeneral(srtError);
				} else {
					float precioCompra = 0;
					precioCompra = Float.parseFloat(txtPrecioCompra.getText().toString().trim());
					float calculoPorcentaje = 0, porcentaje = 0;
					porcentaje = Float.parseFloat(txtValorPorcentual.getText().toString().trim());
					calculoPorcentaje = ((precioCompra * porcentaje) / 100) + precioCompra;
					String resultado = null;
					resultado = String.valueOf(calculoPorcentaje);
					txtPrecioVta.setText(resultado);
				}
			}

		});

		Label lblActStock = new Label("Actualiza Stock");
		lblActStock.setLayoutX(210);
		lblActStock.setLayoutY(270);
		lblActStock.setVisible(false);
		txtActStock = new TextField();
		txtActStock.setLayoutX(310);
		txtActStock.setLayoutY(265);
		txtActStock.setPrefSize(60, 25);
		txtActStock.setVisible(false);
		txtActStock.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
					txtActStock.setText(oldValue);
				}
			}
		});

		Label lblPVta = new Label("Precio Venta");
		lblPVta.setLayoutX(260);
		lblPVta.setLayoutY(140);
		lblPVta.setVisible(false);
		txtPrecioVta = new TextField();
		txtPrecioVta.setLayoutX(390);
		txtPrecioVta.setLayoutY(135);
		txtPrecioVta.setPrefSize(65, 25);
		txtPrecioVta.setVisible(false);
		txtPrecioVta.setEditable(false);

		/*** ***/
		Label lblCat = new Label("Categoría");
		lblCat.setLayoutX(250);
		lblCat.setLayoutY(60);
		lblCat.setVisible(false);
		txtProNew = new TextField();
		txtProNew.setLayoutX(320);
		txtProNew.setLayoutY(55);
		txtProNew.setVisible(false);

		Label lblSubProd = new Label("SubCategoria");
		lblSubProd.setLayoutX(250);
		lblSubProd.setLayoutY(60);
		lblSubProd.setVisible(false);
		comboProCatDescripcion.setLayoutX(330);
		comboProCatDescripcion.setLayoutY(55);
		comboProCatDescripcion.setVisible(false);

		/*** ***/
		idProducto.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
		idProducto.setMinWidth(10);
		nomProducto.setCellValueFactory(new PropertyValueFactory<>("NombreProducto"));
		nomProducto.setMinWidth(30);
		descProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		descProducto.setMinWidth(145);
		valorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
		valorCompra.setMinWidth(10);
		disponibles.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		disponibles.setMinWidth(10);
		valorVenta.setCellValueFactory(new PropertyValueFactory<>("valorVenta"));
		valorVenta.setMinWidth(10);
		productosIngresados.getColumns().addAll(idProducto,nomProducto, descProducto, valorCompra, disponibles, valorVenta);
		productosIngresados.setLayoutX(25);
		productosIngresados.setLayoutY(100);
		productosIngresados.setPrefSize(540, 101);
		productosIngresados.setVisible(false);
		/*** ***/
		productosIngresados.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				try {
					btnGuardar.setDisable(true);
					btnActualizar.setDisable(false);
					btnLimpiar.setDisable(false);
					txtActStock.setEditable(true);
					txtStock.setEditable(false);

					txtNProducto.setText(productosIngresados.getSelectionModel().getSelectedItem().getNombreProducto().toString());
					txtDescripcion.setText(productosIngresados.getSelectionModel().getSelectedItem().getDescripcion());
					Float valorCompra = productosIngresados.getSelectionModel().getSelectedItem().getValorCompra();
					String compra = String.valueOf(valorCompra);
					txtPrecioCompra.setText(compra);

					int Stock = productosIngresados.getSelectionModel().getSelectedItem().getStock();
					String stock = String.valueOf(Stock);
					txtStock.setText(stock);

					Float valorVta = productosIngresados.getSelectionModel().getSelectedItem().getValorVenta();
					String venta = String.valueOf(valorVta);
					txtPrecioVta.setText(venta);

				} catch (Exception exs) {
					// btnAdd.setDisable(true);
					System.out.println("Error");
				}
			}
		});
		
		/*** ***/
		
		/*** ***/
		Label lblCatMin = new Label("Medida");
		lblCatMin.setLayoutX(20);
		lblCatMin.setLayoutY(90);
		lblCatMin.setVisible(false);
		txtCatNew = new TextField();
		txtCatNew.setLayoutX(90);
		txtCatNew.setLayoutY(85);
		txtCatNew.setVisible(false);

		botones btna = new botones();
		Button btnAddMedida = new Button();
		btnAddMedida.setLayoutX(260);
		btnAddMedida.setLayoutY(90);
		btnAddMedida.setVisible(false);
		btnAddMedida.setGraphic(btna.botonAgregarLista());
		btnAddMedida.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Guardando en la tabla...");
				System.out.println("=======================================================");
				productosDescripcionDTO producto = new productosDescripcionDTO();

				if (!txtCatNew.getText().isEmpty()) {
					btnGuardarCategoria.setVisible(true);
					producto.setDescripcion(txtCatNew.getText().toString().trim());
					tableDescProductos.getItems().add(producto);
					txtCatNew.setText("");
				} else {
					String srtError = "Debe ingresar la unidade de medida";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				}
			}

		});

		btnGuardar = new Button("Guardar");
		btnGuardar.setLayoutX(150);
		btnGuardar.setLayoutY(300);
		btnGuardar.setGraphic(btna.botonGuardar());
		btnGuardar.setVisible(false);
		btnGuardar.setDisable(false);
		btnGuardar.setOnAction(this);

		btnActualizar = new Button("Actualizar");
		btnActualizar.setLayoutX(250);
		btnActualizar.setLayoutY(300);
		btnActualizar.setGraphic(btna.botonActualizaProducto());
		btnActualizar.setVisible(false);
		btnActualizar.setDisable(true);
		btnActualizar.setOnAction(this);

		btnLimpiar = new Button("Limpiar");
		btnLimpiar.setLayoutX(350);
		btnLimpiar.setLayoutY(300);
		btnLimpiar.setGraphic(btna.botonLimpiarJPG());
		btnLimpiar.setVisible(false);
		btnLimpiar.setDisable(false);
		btnLimpiar.setOnAction(this);

		Label lblDe = new Label("Producto");
		lblDe.setLayoutX(20);
		lblDe.setLayoutY(60);
		comboProductosCat.setLayoutX(90);
		comboProductosCat.setLayoutY(55);

		comboProductosCat.valueProperty().addListener((ov, p1, p2) -> {

			System.out.println("Producto --> " + p2);
			productoGeneral = null;
			productoGeneral = p2;
			productosIngresados.getItems().removeAll();
			productosIngresados.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Agregando producto a la tabla...");
			System.out.println("================================================================================");
			if (p2 != null) {
				if (p2 == "Nuevo producto") {
					lblCat.setVisible(true);
					txtProNew.setText("");
					txtProNew.setVisible(true);
					tableDescProductos.setVisible(true);
					lblCatMin.setVisible(true);
					txtCatNew.setVisible(true);
					btnAddMedida.setVisible(true);
					tableDescProductos.getItems().removeAll();
					tableDescProductos.getItems().clear();
					btnGuardarCategoria.setVisible(true);

					lblSubProd.setVisible(false);
					comboProCatDescripcion.setVisible(false);

					lblNProducto.setVisible(false);
					lblDescripcion.setVisible(false);
					lblPCompra.setVisible(false);
					lblStock.setVisible(false);
					lblPorcentaje.setVisible(false);
					lblPVta.setVisible(false);
					lblActStock.setVisible(false);
					txtNProducto.setVisible(false);
					txtDescripcion.setVisible(false);
					txtPrecioCompra.setVisible(false);
					txtStock.setVisible(false);
					txtValorPorcentual.setVisible(false);
					btnPrecio.setVisible(false);
					txtPrecioVta.setVisible(false);
					txtActStock.setVisible(false);
					btnGuardar.setVisible(false);
					btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);

					productosIngresados.setVisible(false);

				} else {

					lblCat.setVisible(false);
					txtProNew.setVisible(false);
					tableDescProductos.setVisible(false);
					lblCatMin.setVisible(false);
					txtCatNew.setVisible(false);
					btnAddMedida.setVisible(false);
					btnGuardarCategoria.setVisible(false);
					lblSubProd.setVisible(true);
					comboProCatDescripcion.setVisible(true);

					btnGuardar.setVisible(false);
					btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);

					traeProdSubCat(p2);

					// ContenidoDesc.add("Nuevo subproducto");
					System.out.println("Cantidad de combos; " + comboProCatDescripcion.getItems().size());

					
				}
			}
		});

		comboProCatDescripcion.valueProperty().addListener((ov, p1, p2) -> {
			productoEspecifico = null;
			productoEspecifico = p2;
			btnGuardar.setVisible(false);
			btnActualizar.setVisible(false);
			btnLimpiar.setVisible(false);
			txtActStock.setEditable(false);
			System.out.println("Nuevo subproducto " + p2);
			if (p2 != null) {
				if (p2 == "Nuevo subproducto") {
					System.out.println("Nuevo subproducto");
					lblCatMin.setVisible(true);
					txtCatNew.setText("");
					txtCatNew.setVisible(true);
					btnAddMedida.setVisible(true);
					tableDescProductos.setVisible(true);
					tableDescProductos.getItems().removeAll();
					tableDescProductos.getItems().clear();
					btnGuardarCategoria.setVisible(true);

					lblNProducto.setVisible(false);
					lblDescripcion.setVisible(false);
					lblPCompra.setVisible(false);
					lblStock.setVisible(false);
					lblPorcentaje.setVisible(false);
					lblPVta.setVisible(false);
					lblActStock.setVisible(false);
					txtNProducto.setVisible(false);
					txtDescripcion.setVisible(false);
					txtPrecioCompra.setVisible(false);
					txtStock.setVisible(false);
					txtValorPorcentual.setVisible(false);
					btnPrecio.setVisible(false);
					txtPrecioVta.setVisible(false);
					txtActStock.setVisible(false);
					btnGuardar.setVisible(false);
					btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);

					productosIngresados.setVisible(false);
				} else if (p2.isEmpty() || comboProCatDescripcion.getItems().size() < 0) {
					lblCatMin.setVisible(false);
					txtCatNew.setVisible(false);
					btnAddMedida.setVisible(false);
					tableDescProductos.setVisible(false);
					btnGuardarCategoria.setVisible(false);

					System.out.println("SubProducto --> " + p2);
					lblNProducto.setVisible(false);
					lblDescripcion.setVisible(false);
					lblPCompra.setVisible(false);
					lblStock.setVisible(false);
					lblPorcentaje.setVisible(false);
					lblPVta.setVisible(false);
					lblActStock.setVisible(false);
					txtNProducto.setVisible(false);
					txtDescripcion.setVisible(false);
					txtPrecioCompra.setVisible(false);
					txtStock.setVisible(false);
					txtValorPorcentual.setVisible(false);
					btnPrecio.setVisible(false);
					txtPrecioVta.setVisible(false);
					txtActStock.setVisible(false);
					btnGuardar.setVisible(false);
					btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);

					try {
						cargaProductos(p2);
						// permite actualizar la lista al actualizar o guardar
						// un producto
						strSubCategoria = p2;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					productosIngresados.setVisible(true);

				} else {
					lblNProducto.setVisible(true);
					lblDescripcion.setVisible(true);
					lblPCompra.setVisible(true);
					lblStock.setVisible(true);
					lblPorcentaje.setVisible(true);
					lblPVta.setVisible(true);
					lblActStock.setVisible(true);
					txtNProducto.setVisible(true);
					txtDescripcion.setVisible(true);
					txtPrecioCompra.setVisible(true);
					txtStock.setVisible(true);
					txtValorPorcentual.setVisible(true);
					btnPrecio.setVisible(true);
					txtPrecioVta.setVisible(true);
					txtActStock.setVisible(true);
					btnGuardar.setVisible(true);
					btnActualizar.setVisible(true);
					btnLimpiar.setVisible(true);

					lblCatMin.setVisible(false);
					txtCatNew.setVisible(false);
					btnAddMedida.setVisible(false);
					tableDescProductos.getItems().removeAll();
					tableDescProductos.getItems().clear();
					tableDescProductos.setVisible(false);
					btnGuardarCategoria.setVisible(false);
					try {
						cargaProductos(p2);
						// permite actualizar la lista al actualizar o guardar
						// un producto
						strSubCategoria = p2;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					productosIngresados.setVisible(true);

				}
			}

		});

		traeProductosCategoria();
		descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		descripcion.setMinWidth(130);
		tableDescProductos.getColumns().add(descripcion);
		tableDescProductos.setLayoutX(320);
		tableDescProductos.setLayoutY(90);
		tableDescProductos.setPrefSize(130, 90);
		tableDescProductos.setVisible(false);

		/*** ***/

		/*** Fin nuevos cambios apra el ingreso del producto ***/
		btnBuscar = new Button("");
		btnBuscar.setLayoutX(472);
		btnBuscar.setLayoutY(53);
		btnBuscar.setPrefSize(25, 25);
		btnBuscar.setOnAction(this);

		btnExit = new Button("Ir a Inicio");
		btnExit.setLayoutX(450);
		btnExit.setLayoutY(600);
		btnExit.setGraphic(btna.botonRegresar());
		btnExit.setOnAction(this);

		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();
		/*** ***/
		AnchorPane datosPrincipales = new AnchorPane();
		datosPrincipales.getChildren().addAll(scenetitle,
											  btnGuardarCategoria,
											  lblCat,											  
											  txtProNew,
											  lblSubProd,
											  comboProCatDescripcion,
											  productosIngresados,
											  lblCatMin,
											  txtCatNew,
											  btnAddMedida,
											  lblDe,
											  comboProductosCat,
											  tableDescProductos
											  
			
		);
		
		datosPrincipales.setPadding(new Insets(5));
		datosPrincipales.setTranslateX(20);
		datosPrincipales.setTranslateY(20);
		datosPrincipales.setTranslateZ(20);
		datosPrincipales.setMaxSize(750, 200);
		datosPrincipales.setId("colorMarco");
		/*** ***/
		
		/*** ***/
		AnchorPane datosIngresoProductos = new AnchorPane();
		datosIngresoProductos.getChildren().addAll(scenetitleT,
													lblNProducto,
													txtNProducto,
													lblDescripcion,
													txtDescripcion,
													lblPCompra,
													txtPrecioCompra,
													lblStock,
													txtStock,
													lblPorcentaje,
													txtValorPorcentual,
													lblActStock,
													txtActStock,
													lblPVta,
													txtPrecioVta,
													btnPrecio,
													btnGuardar,
													btnActualizar,
													btnLimpiar
											  
			
		);
		
		datosIngresoProductos.setPadding(new Insets(5));
		datosIngresoProductos.setTranslateX(20);
		datosIngresoProductos.setTranslateY(250);
		datosIngresoProductos.setTranslateZ(20);
		datosIngresoProductos.setMaxSize(750, 200);
		datosIngresoProductos.setId("colorMarco");
		/*** ***/
		
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		rootIngreso.getChildren().addAll(bp, btnExit, datosPrincipales,datosIngresoProductos

		);

		/*** rootIngreso.getChildren().addAll(bp, scenetitle, lblDe, lblCat, txtProNew, btnExit, tableDescProductos,
				lblCatMin, txtCatNew, btnAddMedida, btnGuardar, btnGuardarCategoria, comboProductosCat, lblSubProd,
				comboProCatDescripcion, btnActualizar, btnLimpiar

		);

		rootIngreso.getChildren().addAll(lblNProducto, lblDescripcion, lblPCompra, lblStock, lblPorcentaje, lblPVta,
				lblActStock, txtNProducto, txtDescripcion, txtPrecioCompra, txtStock, txtValorPorcentual, btnPrecio,
				txtPrecioVta, txtActStock, productosIngresados);***/

		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 630, 700);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual.setTitle("Ingreso de productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.getIcons().add(b.iconoLaren());
		ventanaActual.show();

	}

	/*** ***/
	public void creacionCategoria() {

		txtProNew.setVisible(true);
	}

	public void traeProductosCategoria() {
		System.out.println("==================================================");
		System.out.println(" Agregando productos al combo...");
		System.out.println("==================================================");
		try {
			categoriaProductos = null;
			categoriaProductos = new ProductosBO().extraeProductosCAT();
			// comboProductosCat.getItems().clear();
			// comboProductosCat.getItems().removeAll();
			comboProductosCat.getItems().clear();
			Contenido.add("Nuevo producto");
			if (categoriaProductos != null && !categoriaProductos.isEmpty()) {

				for (productosCategoriaDTO obje : categoriaProductos) {

					if (obje != null) {

						comboProductosCat.getItems().add(obje.getDescripcion());
						System.out.println("Id de la categoria: " + obje.getId_producto_cat());
					}
				}
			} else if (!strBanderaControlaMensaje.equals("S")) {
				// MOSTAR MENSAJE POR PANTALLA
				String srtError = "Producto no existe en stock, por favor ingrese otra descripción : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*** INICIO CONSULTA DE PRODUCTOS SUB ***/

	public void traeProdSubCat(String idCategoria) {
		System.out.println("==================================================");
		System.out.println(" Agregando productos al comboSubCategoria...");
		System.out.println("==================================================");

		try {
			subCategoriaProductos = null;
			subCategoriaProductos = new ProductosBO().extraeProdCatSub(idCategoria);

			if (subCategoriaProductos != null && !subCategoriaProductos.isEmpty()) {
				ContenidoDesc.clear();
				ContenidoDesc.add("Nuevo subproducto");
				for (productosDescripcionDTO obje : subCategoriaProductos) {

					if (obje != null) {

						comboProCatDescripcion.getItems().add(obje.getDescripcion());
						System.out.println(obje.getDescripcion() + " - " + obje.getId_producto_categoria() + " - "
								+ obje.getId_des_producto());

					}
				}

			} else {
				// MOSTAR MENSAJE POR PANTALLA
				String srtError = "Producto no existe en stock, por favor ingrese otra descripción : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*** FIN CONSULTA DE PRODUCTOS SUB ***/

	/*** INICIO guardando categoria del producto ***/
	public void insertaProductosCategoria(String Descripcion) {
		System.out.println("==================================================");
		System.out.println(" Guardando categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		try {
			resInsert = new ProductosBO().insertaCategoriaProd(Descripcion,usuarioGlobal);
			if (resInsert != 0 || resInsert != -1) {
				insertaProductosDetalle(resInsert);
			} else {
				// MOSTAR MENSAJE POR PANTALLA
				String srtError = "Categoría no se pudo insertar : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*** INICIO guardando categoria del producto ***/
	public void insertaProductosDetalle(int idCat) {
		System.out.println("==================================================");
		System.out.println(" Guardando Sub-categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		try {
			for (int i = 0; i < tableDescProductos.getItems().size(); i++) {
				String descripcion = null;
				descripcion = tableDescProductos.getItems().get(i).getDescripcion();
				resInsert = new ProductosBO().insertaSubCatProd(idCat, descripcion,usuarioGlobal);
				System.out.println(tableDescProductos.getItems().get(i).getDescripcion());
				if (resInsert != 1) {
					String srtError = "La subcategoría " + descripcion + "no se pudo ingresar : ";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
					System.out.println("NO HAY DATOS");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*** FIN guardando categoria del producto ***/

	/*** INICIO guardando solo subcategoria del producto ***/
	public void insertaProductosDet(String nomCat) {
		System.out.println("==================================================");
		System.out.println(" Guardando solo Sub-categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		try {
			for (int i = 0; i < tableDescProductos.getItems().size(); i++) {
				String descripcion = null;
				descripcion = tableDescProductos.getItems().get(i).getDescripcion();
				resInsert = new ProductosBO().insertaSubProducto(descripcion, nomCat, usuarioGlobal );
				System.out.println(tableDescProductos.getItems().get(i).getDescripcion());
				if (resInsert != 1) {
					String srtError = "La subcategoría " + descripcion + "no se pudo ingresar : ";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
					System.out.println("NO HAY DATOS");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*** FIN guardando solo subcategoria del producto ***/

	
	/***
	 * se añadio esta clase para cuando el usuario desee agregar un producto
	 * 
	 * @return
	 ***/
	public productoDTO consultaProductoCliente() {
		VentanaConsulta = new Stage();
		VentanaConsulta.setTitle("Principal");

		Label lblDescripcion = new Label("Ingrese el producto");
		lblDescripcion.setLayoutX(40);
		lblDescripcion.setLayoutY(60);
		lblDescripcion.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(220);
		txtDescripcion.setLayoutY(60);
		txtDescripcion.setPrefSize(200, 25);
		productoRec = null;
		/*
		 * INICIO CARGA DE PRODUCTOS A LA TABLA
		 */
		txtDescripcion.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						tableProductos.getItems().removeAll();
						tableProductos.getItems().clear();
						System.out.println(
								"================================================================================");
						System.out.println(" CARGA DE PRODUCTOS A LA TABLA");
						System.out.println(
								"================================================================================");
						// cargaProductoTabla(txtConsulta.getText().toString());
						/*
						 * Metodo que traera de base todos los productos
						 * existentes [INICIO]
						 */
						String strCondicion = "";
						strCondicion = txtDescripcion.getText().toString().trim();
						cargaProductoTabla(strCondicion);
					} catch (Exception exs) {
						System.out.println("====================================");
					}
					/*
					 * Metodo que traera de base todos los productos existentes
					 * [FIN]
					 */
					// }

				}
			}
		});
		/*
		 * FIN CARGA DE PRODUCTOS A LA TABLA
		 */
		botones b = new botones();
		Group rootConsulta = new Group();
		BorderPane sur = new BorderPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(b.fondoPantalla());

		/*** ***/
		idProducto.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
		idProducto.setMinWidth(5);
		nomProducto.setCellValueFactory(new PropertyValueFactory<>("NombreProducto"));
		nomProducto.setMinWidth(80);
		descProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		descProducto.setMinWidth(100);
		valorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
		valorCompra.setMinWidth(60);
		disponibles.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		disponibles.setMinWidth(40);
		valorVenta.setCellValueFactory(new PropertyValueFactory<>("valorVenta"));
		valorVenta.setMinWidth(60);
		categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		categoria.setMinWidth(40);
		subcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
		subcategoria.setMinWidth(40);
		productosIngresados.getColumns().addAll(idProducto, categoria, subcategoria, nomProducto, descProducto,
				valorCompra, disponibles);
		productosIngresados.setLayoutX(30);
		productosIngresados.setLayoutY(110);
		productosIngresados.setPrefSize(600, 220);
		productosIngresados.setVisible(true);
		Label lblProd = new Label("Número productos");
		lblProd.setLayoutX(50);
		lblProd.setLayoutY(350);
		lblProd.setVisible(false);
		numProductos = new TextField();
		numProductos.setVisible(false);
		btnEnvia = new Button("Agregar");
		btnEnvia.setVisible(false);
		productosIngresados.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 || event.getClickCount() == 1) {
				try {
					System.out.println("1 o 2 click");
					lblProd.setVisible(true);
					numProductos.setVisible(true);
					numProductos.setLayoutX(165);
					numProductos.setLayoutY(345);
					numProductos.setPrefSize(50, 20);
					numProductos.textProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
							if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
								numProductos.setText(oldValue);

							}
						}
					});
					botones btna = new botones();
					btnEnvia.setLayoutX(230);
					btnEnvia.setLayoutY(345);
					btnEnvia.setGraphic(btna.botonAgregarLista());
					btnEnvia.setVisible(true);
					// btnEnvia.setOnAction(this);
					btnEnvia.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							System.out.println("=======================================================");
							System.out.println("Enviando a la factura...");
							System.out.println("=======================================================");
							int prodIngreso = 0;
							prodIngreso = Integer.valueOf(numProductos.getText().toString());
							int stock = 0;
							stock = Integer
									.valueOf(productosIngresados.getSelectionModel().getSelectedItem().getStock());
							System.out.println("=Stock " + stock);
							if (prodIngreso > 0 && prodIngreso < stock) {
								System.out.println("OK cumple...");
								productoDTO productoT = new productoDTO();
								productoT.setIdProducto(
										productosIngresados.getSelectionModel().getSelectedItem().getIdProducto());
								productoT.setDescripcion(
										productosIngresados.getSelectionModel().getSelectedItem().getDescripcion());
								productoT
										.setStock(productosIngresados.getSelectionModel().getSelectedItem().getStock());
								productoT.setValorVenta(
										productosIngresados.getSelectionModel().getSelectedItem().getValorVenta());
								productoRec = null;
								productoRec = productoT;
								verifica = true;
							} else {
								String srtError = "El número de productos a comprar es erróneo, por favor verifique";
								alertasMensajes alerta = new alertasMensajes();
								alerta.alertaGeneral(srtError);

							}
						}

					});

				} catch (Exception exs) {
					System.out.println("Error");
				}
			}
		});

		rootConsulta.getChildren().addAll(bp, lblDescripcion, txtDescripcion, productosIngresados, lblProd,
				numProductos, btnEnvia);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootConsulta, 640, 450);
		VentanaConsulta.setTitle("Productos");
		VentanaConsulta.setScene(escenaProductos);
		VentanaConsulta.setResizable(false);
		VentanaConsulta.initModality(Modality.APPLICATION_MODAL);
		VentanaConsulta.show();
		return productoRec;
	}

	/*** fin de clase ***/


	public void cargaProductos(String strProducto) throws SQLException {
		try {
			productosIngresados.getItems().removeAll();
			productosIngresados.getItems().clear();
			listaProductos = new ProductosBO().traeProductos(strProducto);
			if (listaProductos != null && !listaProductos.isEmpty()) {

				for (productoDTO obje : listaProductos) {

					if (obje != null) {
						productoDTO llena = new productoDTO();
						llena.setIdProducto(obje.getIdProducto());
						llena.setNombreProducto(obje.getNombreProducto());
						llena.setDescripcion(obje.getDescripcion());
						llena.setNombreDescripcion(obje.getNombreProducto() + " - " + obje.getDescripcion());
						llena.setValorCompra(obje.getValorCompra());
						llena.setStock(obje.getStock());
						llena.setValorVenta(obje.getValorVenta());
						productosIngresados.getItems().add(llena);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cargaProductoTabla(String strProducto) throws SQLException {
		try {
			productosIngresados.getItems().removeAll();
			productosIngresados.getItems().clear();
			productos = new ProductosBO().extraeProductos(strProducto);

			if (productos != null && !productos.isEmpty()) {

				for (productoDTO obje : productos) {

					if (obje != null) {
						productoDTO objeto = new productoDTO();
						objeto.setIdProducto(obje.getIdProducto());
						objeto.setNombreProducto(obje.getNombreProducto());
						objeto.setDescripcion(obje.getDescripcion());
						objeto.setValorCompra(obje.getValorCompra());
						objeto.setStock(obje.getStock());
						objeto.setCategoria(obje.getCategoria());
						objeto.setSubcategoria(obje.getSubcategoria());
						productosIngresados.getItems().add(objeto);
					}
				}
			} else {
				String srtError = "Producto no existe en stock ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertaProducto(String prodEsp, String nomProd, String Descirpcion, float fltValorUni, int intStock,
			float fltPrecioVta) {
		System.out.println("================================================================================");
		System.out.println(" Ingreso de productos...");
		System.out.println("================================================================================");
		ProductosBO objInsertar = new ProductosBO();
		int resInsert = 0;
		try {
			resInsert = objInsertar.insertaProductos(1,prodEsp, nomProd, Descirpcion, fltValorUni, intStock,
					fltPrecioVta, usuarioGlobal);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha insertado el producto:" + nomProd;
				alertas.alertaOK(strMensaje);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No entro");
		}
	}

	public void actualizaProducto(int intIdProd, String prodEsp, String nomProd, String Descirpcion, float fltValorUni,
			int intStock, float fltPrecioVta) {
		System.out.println("================================================================================");
		System.out.println(" Actualizacion de productos...");
		System.out.println("================================================================================");
		ProductosBO objActualizar = new ProductosBO();
		int resActualizar = 0;
		try {
			resActualizar = objActualizar.actualizarProductos(intIdProd, prodEsp, nomProd, Descirpcion, fltValorUni,
					intStock, fltPrecioVta,usuarioGlobal);
			if (resActualizar == 1) {
				System.out.println("Resultado del query: " + resActualizar);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha actualizado el producto:" + nomProd;
				alertas.alertaOK(strMensaje);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No entro");
		}
	}

	public void limpiarTemporal() {
		try {
			comboProductosCat.getItems().clear();
			comboProductosCat.getItems().clear();
			Contenido.add("Nuevo producto");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void limpiarTemporalDescripcion() {
		try {
			comboProCatDescripcion.getItems().clear();
			comboProCatDescripcion.getItems().removeAll();
			// ContenidoDesc.add("Nuevo subproducto");
			System.out.println("Que tiene:  +" + txtCatNew.getText().toString().trim());
			traeProdSubCat(productoGeneral);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void limpiarPantalla() {
		try {
			txtNProducto.setText("");
			txtDescripcion.setText("");
			txtPrecioCompra.setText("");
			txtStock.setText("");
			txtValorPorcentual.setText("");
			txtActStock.setText("");
			txtPrecioVta.setText("");
			txtStock.setEditable(true);
			txtActStock.setEditable(false);
			txtPrecioVta.setEditable(false);
			cargaProductos(strSubCategoria);
			btnGuardar.setDisable(false);
			btnActualizar.setDisable(true);
			btnLimpiar.setDisable(false);

			txtCatNew.setText("");
			txtProNew.setText("");
			tableDescProductos.getItems().clear();
			tableDescProductos.getItems().removeAll();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnBuscar) {
			System.out.println("==================================================");
			System.out.println("Buscando en la base...");
			System.out.println("==================================================");
			String strParametro = null;

			try {
				cargaProductoTabla(strParametro);
			} catch (SQLException e) {
				String srtError = "No se pudo conectar a la base de datos...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}

		} else if (event.getSource() == btnExit) {
			System.out.println("==================================================");
			System.out.println("Redirigiendose al menú principal...");
			System.out.println("==================================================");
			ventanaActual.toBack();
			ventanaActual.close();
			Principal prin = new Principal();
			prin.panelPrincipal(usuarioGlobal);

		} else if (event.getSource() == btnGuardar) {
			System.out.println("==================================================");
			System.out.println(" creando categorías...");
			System.out.println("==================================================");
			System.out.println(" Correcion primera..." + productoGeneral + " - " + productoEspecifico);

			if (!txtNProducto.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty()
					&& !txtPrecioCompra.getText().toString().isEmpty() && !txtStock.getText().toString().isEmpty()
					&& !txtPrecioVta.getText().toString().isEmpty()) {

				boolean campos = false;
				float fltPrecioCompra = 0, fltPrecioVta = 0;
				int Stock = 0;
				fltPrecioCompra = Float.parseFloat(txtPrecioCompra.getText().toString().trim());
				fltPrecioVta = Float.parseFloat(txtPrecioVta.getText().toString().trim());
				Stock = Integer.parseInt(txtStock.getText().toString().trim());
				System.out.println("Antes");
				insertaProducto(productoEspecifico, txtNProducto.getText().toString().trim(),
						txtDescripcion.getText().toString().trim(), fltPrecioCompra, Stock, fltPrecioVta);

				limpiarPantalla();

				// btnGuardar.setVisible(false);

			} else {
				String srtError = "Faltan datos de ingresar, por favor revise...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}
			traeProductosCategoria();

		} else if (event.getSource() == btnActualizar) {
			System.out.println("==================================================");
			System.out.println(" actualizando categorías...");
			System.out.println("==================================================");

			if (!txtNProducto.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty()
					&& !txtPrecioCompra.getText().toString().isEmpty() && !txtStock.getText().toString().isEmpty()
					&& !txtPrecioVta.getText().toString().isEmpty()) {

				boolean campos = false;
				float fltPrecioCompra = 0, fltPrecioVta = 0;
				int Stock = 0;
				fltPrecioCompra = Float.parseFloat(txtPrecioCompra.getText().toString().trim());
				fltPrecioVta = Float.parseFloat(txtPrecioVta.getText().toString().trim());
				if (!txtActStock.getText().toString().isEmpty()) {
					Stock = Integer.parseInt(txtStock.getText().toString().trim())
							+ Integer.parseInt(txtActStock.getText().toString().trim());
				} else {
					Stock = Integer.parseInt(txtStock.getText().toString().trim());
				}
				// actualizar
				int intIdProd = productosIngresados.getSelectionModel().getSelectedItem().getIdProducto();
				actualizaProducto(intIdProd, productoEspecifico, txtNProducto.getText().toString().trim(),
						txtDescripcion.getText().toString().trim(), fltPrecioCompra, Stock, fltPrecioVta);

				limpiarPantalla();
			} else {
				String srtError = "Faltan datos de ingresar, por favor revise...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}

		} else if (event.getSource() == btnGuardarCategoria) {
			System.out.println("k tiene " + txtProNew.getText().length());
			int validaCategoria = 0;
			validaCategoria = txtProNew.getText().length();
			System.out.println("valida: " + validaCategoria + " - " + tableDescProductos.getItems().size());
			productosIngresados.getItems().size();
			if (validaCategoria == 0) {
				if (tableDescProductos.getItems().size() == 0) {
					String srtError = "No ha ingresado la nueva categoría o subcategoría...";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				} else {
					System.out.println("==================================================");
					System.out.println(" Creando subcategoría...");
					System.out.println("==================================================");
					System.out.println(" Correcion primera..." + productoGeneral + " - " + productoEspecifico);
					insertaProductosDet(productoGeneral);
					limpiarTemporalDescripcion();
					limpiarPantalla();
				}
			} else {
				System.out.println("==================================================");
				System.out.println(" creando categoría y subcategoría...");
				System.out.println("==================================================");
				insertaProductosCategoria(txtProNew.getText().toString().trim());
				limpiarTemporal();
				traeProductosCategoria();
				limpiarPantalla();
			}

		} else if (event.getSource() == btnEnvia) {
			System.out.println("=======================================================");
			System.out.println("Enviando a la factura...");
			System.out.println("=======================================================");
			int prodIngreso = 0;
			prodIngreso = Integer.valueOf(numProductos.getText().toString());
			int stock = 0;
			stock = Integer.valueOf(productosIngresados.getSelectionModel().getSelectedItem().getStock());
			System.out.println("=Stock " + stock);
			if (prodIngreso > 0 && prodIngreso < stock) {
				System.out.println("OK cumple...");
				productoDTO productoT = new productoDTO();
				productoT.setIdProducto(productosIngresados.getSelectionModel().getSelectedItem().getIdProducto());
				productoT.setDescripcion(productosIngresados.getSelectionModel().getSelectedItem().getDescripcion());
				productoT.setStock(productosIngresados.getSelectionModel().getSelectedItem().getStock());
				productoT.setValorVenta(productosIngresados.getSelectionModel().getSelectedItem().getValorVenta());
				productoRec = null;
				productoRec = productoT;

			} else {
				String srtError = "El número de productos a comprar es erróneo, por favor verifique";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);

			}

		} else if (event.getSource() == btnLimpiar) {
			System.out.println("==================================================");
			System.out.println("Limpiar Pantalla...");
			System.out.println("==================================================");
			limpiarPantalla();
		}

	}
}

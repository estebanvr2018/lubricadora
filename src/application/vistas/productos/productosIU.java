package application.vistas.productos;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.productoDTO;
import application.com.DTOS.productosCategoriaDTO;
import application.com.DTOS.productosDescripcionDTO;
import application.extras.botones;
import application.tablas.tablaFacturaDet;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class productosIU 
{
	public TextField txtNProducto, txtDescripcion, txtPrecioCompra, txtValorPorcentual, txtPrecioVta, txtStock,
	txtValorU, txtActStock;

public TextField txtProNew, txtCatNew, numProductos;
public String usuarioGlobal = "";
public Button btnGuardar, btnBuscar, btnExit, btnPrecio, btnGuardarCategoria, btnEnvia, btnLimpiar, btnActualizar;
public Stage ventanaActual;
public Stage VentanaConsulta;

public boolean verifica = false;

Optional<ButtonType> optionRetorno=null;

public productoDTO productoRec = new productoDTO();

public TableView<tablaFacturaDet> tableProductos = new TableView();

public TableView<productosDescripcionDTO> tableDescProductos = new TableView();
public TableColumn<productosDescripcionDTO, String> descripcion = new TableColumn<>("Descripción");

public TableColumn<tablaFacturaDet, String> idTable = new TableColumn<>("Cod. Artículo");
public TableColumn<tablaFacturaDet, String> Nombre = new TableColumn<>("Descripción");
public TableColumn<tablaFacturaDet, String> Desc = new TableColumn<>("Valor unitario");
public TableColumn<tablaFacturaDet, String> Stock = new TableColumn<>("Stock");

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
	public Optional<ButtonType> ingresoProductos(int idProveedor, String usuario) 
	{
		usuarioGlobal = usuario;
		// Bandera para controlar que ingreso por primera vez
				
		Label scenetitle = new Label("Sección productos");
		scenetitle.setLayoutX(140);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");
		Label scenetitleT = new Label("Detalles del producto");
		scenetitleT.setLayoutX(190);
		scenetitleT.setLayoutY(5);
		scenetitleT.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));
		/*** Nuevos Cambios para el ingreso del producto ***/
		botones b = new botones();
		btnGuardarCategoria = new Button("Guardar");
		btnGuardarCategoria.setLayoutX(120);
		btnGuardarCategoria.setLayoutY(140);
		btnGuardarCategoria.setGraphic(b.botonGuardar());
		btnGuardarCategoria.setVisible(false);
		
		
		Label lblNProducto = new Label("Nombre producto.");
		lblNProducto.setLayoutX(30);
		lblNProducto.setLayoutY(40);
		lblNProducto.setVisible(false);
		txtNProducto = new TextField();
		txtNProducto.setLayoutX(160);
		txtNProducto.setLayoutY(40);
		txtNProducto.setPrefSize(220, 25);
		txtNProducto.setVisible(false);

		Label lblDescripcion = new Label("Descripción");
		lblDescripcion.setLayoutX(30);
		lblDescripcion.setLayoutY(80);
		lblDescripcion.setVisible(false);
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(160);
		txtDescripcion.setLayoutY(75);
		txtDescripcion.setPrefSize(220, 25);
		txtDescripcion.setVisible(false);

		Label lblPCompra = new Label("Precio compra");
		lblPCompra.setLayoutX(30);
		lblPCompra.setLayoutY(120);
		lblPCompra.setVisible(false);
		txtPrecioCompra = new TextField();
		txtPrecioCompra.setLayoutX(160);
		txtPrecioCompra.setLayoutY(115);
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
		lblStock.setLayoutY(160);
		lblStock.setVisible(false);
		txtStock = new TextField();
		txtStock.setLayoutX(410);
		txtStock.setLayoutY(155);
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
		lblPorcentaje.setLayoutY(120);
		lblPorcentaje.setVisible(false);
		txtValorPorcentual = new TextField();
		txtValorPorcentual.setLayoutX(410);
		txtValorPorcentual.setLayoutY(115);
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

		btnPrecio = new Button("Calcula");
		btnPrecio.setLayoutX(500);
		btnPrecio.setLayoutY(115);
		//btnPrecio.setPrefSize(15, 20);
		btnPrecio.setVisible(false);
//		btnPrecio.setContentDisplay(ContentDisplay.BOTTOM);
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


		Label lblPVta = new Label("Precio Venta");
		lblPVta.setLayoutX(260);
		lblPVta.setLayoutY(200);
		lblPVta.setVisible(false);
		txtPrecioVta = new TextField();
		txtPrecioVta.setLayoutX(410);
		txtPrecioVta.setLayoutY(195);
		txtPrecioVta.setPrefSize(60, 25);
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
		comboProCatDescripcion.setLayoutX(345);
		comboProCatDescripcion.setLayoutY(55);
		comboProCatDescripcion.setVisible(false);
		
		/*** ***/
		Label lblCatMin = new Label("Medida");
		lblCatMin.setLayoutX(20);
		lblCatMin.setLayoutY(90);
		lblCatMin.setVisible(false);
		txtCatNew = new TextField();
		txtCatNew.setLayoutX(90);
		txtCatNew.setLayoutY(85);
		txtCatNew.setPrefSize(100, 25);
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
		btnGuardar.setLayoutY(250);
		btnGuardar.setGraphic(btna.botonGuardar());
		btnGuardar.setVisible(false);
		btnGuardar.setDisable(false);
		btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Comenzando a guardar el producto...");
				System.out.println("=======================================================");
				/*** ***/
				alertasMensajes alertas = new alertasMensajes();
				if (!txtNProducto.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty()
						&& !txtPrecioCompra.getText().toString().isEmpty() && !txtStock.getText().toString().isEmpty()
						&& !txtPrecioVta.getText().toString().isEmpty()) {

					
					float fltPrecioCompra = 0, fltPrecioVta = 0;
					int Stock = 0;
					fltPrecioCompra = Float.parseFloat(txtPrecioCompra.getText().toString().trim());
					fltPrecioVta = Float.parseFloat(txtPrecioVta.getText().toString().trim());
					Stock = Integer.parseInt(txtStock.getText().toString().trim());
					
					int resultadoInsert = insertaProducto(idProveedor, productoEspecifico, txtNProducto.getText().toString().trim(),
							txtDescripcion.getText().toString().trim(), fltPrecioCompra, Stock, fltPrecioVta);
					comprasPrincipal abc = new comprasPrincipal();
					if ( resultadoInsert == 1 )
					{
						
						optionRetorno = alertas.opcionConfirmacion("Confirmación", "Se ha guardado el producto ");
						ventanaActual.close();
					}	
					else 
					{
						
						String  strMensaje="No se ha guardado el cliente, por favor vuelva a intentarlo";
						alertas.alertaError(strMensaje);
						ventanaActual.close();
					}	
					limpiarPantalla();
					abc.recibeParametro(idProveedor);
					// btnGuardar.setVisible(false);

				} else {
					System.out.println("Error");
					String srtError = "Faltan datos de ingresar, por favor revise...";
					
					alertas.alertaGeneral(srtError);
				}
				traeProductosCategoria();

			} 
				/*** ***/

		});
		/*btnActualizar = new Button("Actualizar");
		btnActualizar.setLayoutX(250);
		btnActualizar.setLayoutY(300);
		btnActualizar.setGraphic(btna.botonActualizaProducto());
		btnActualizar.setVisible(false);
		btnActualizar.setDisable(true);*/
		

		btnLimpiar = new Button("Limpiar");
		btnLimpiar.setLayoutX(350);
		btnLimpiar.setLayoutY(250);
		btnLimpiar.setGraphic(btna.botonLimpiarJPG());
		btnLimpiar.setVisible(false);
		btnLimpiar.setDisable(false);
		
		Label lblDe = new Label("Producto");
		lblDe.setLayoutX(20);
		lblDe.setLayoutY(60);
		comboProductosCat.setLayoutX(90);
		comboProductosCat.setLayoutY(55);

		comboProductosCat.valueProperty().addListener((ov, p1, p2) -> {

			System.out.println("Producto --> " + p2);
			productoGeneral = null;
			productoGeneral = p2;
			
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
					//lblActStock.setVisible(false);
					txtNProducto.setVisible(false);
					txtDescripcion.setVisible(false);
					txtPrecioCompra.setVisible(false);
					txtStock.setVisible(false);
					txtValorPorcentual.setVisible(false);
					btnPrecio.setVisible(false);
					txtPrecioVta.setVisible(false);
					//txtActStock.setVisible(false);
					btnGuardar.setVisible(false);
					//btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);
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
					//btnActualizar.setVisible(false);
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
			//btnActualizar.setVisible(false);
			btnLimpiar.setVisible(false);
			//txtActStock.setEditable(false);
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
					//lblActStock.setVisible(false);
					txtNProducto.setVisible(false);
					txtDescripcion.setVisible(false);
					txtPrecioCompra.setVisible(false);
					txtStock.setVisible(false);
					txtValorPorcentual.setVisible(false);
					btnPrecio.setVisible(false);
					txtPrecioVta.setVisible(false);
					//txtActStock.setVisible(false);
					btnGuardar.setVisible(false);
					//btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);
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
					//lblActStock.setVisible(false);
					txtNProducto.setVisible(false);
					txtDescripcion.setVisible(false);
					txtPrecioCompra.setVisible(false);
					txtStock.setVisible(false);
					txtValorPorcentual.setVisible(false);
					btnPrecio.setVisible(false);
					txtPrecioVta.setVisible(false);
					//txtActStock.setVisible(false);
					btnGuardar.setVisible(false);
					//btnActualizar.setVisible(false);
					btnLimpiar.setVisible(false);

					
						strSubCategoria = p2;
					
					
				} else {
					lblNProducto.setVisible(true);
					lblDescripcion.setVisible(true);
					lblPCompra.setVisible(true);
					lblStock.setVisible(true);
					lblPorcentaje.setVisible(true);
					lblPVta.setVisible(true);
					//lblActStock.setVisible(true);
					txtNProducto.setVisible(true);
					txtDescripcion.setVisible(true);
					txtPrecioCompra.setVisible(true);
					txtStock.setVisible(true);
					txtValorPorcentual.setVisible(true);
					btnPrecio.setVisible(true);
					txtPrecioVta.setVisible(true);
					//txtActStock.setVisible(true);
					btnGuardar.setVisible(true);
					//btnActualizar.setVisible(true);
					btnLimpiar.setVisible(true);

					lblCatMin.setVisible(false);
					txtCatNew.setVisible(false);
					btnAddMedida.setVisible(false);
					tableDescProductos.getItems().removeAll();
					tableDescProductos.getItems().clear();
					tableDescProductos.setVisible(false);
					btnGuardarCategoria.setVisible(false);
					
						strSubCategoria = p2;
					
				}
			}

		});

		traeProductosCategoria();
		descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		descripcion.setMinWidth(130);
		tableDescProductos.getColumns().add(descripcion);
		tableDescProductos.setLayoutX(340);
		tableDescProductos.setLayoutY(90);
		tableDescProductos.setPrefSize(130, 90);
		tableDescProductos.setVisible(false);

		/*** ***/

		/*** Fin nuevos cambios apra el ingreso del producto ***/
		btnBuscar = new Button("");
		btnBuscar.setLayoutX(472);
		btnBuscar.setLayoutY(53);
		btnBuscar.setPrefSize(25, 25);
		
		
		btnGuardarCategoria.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Calculando...");
				System.out.println("=======================================================");
				System.out.println("k tiene " + txtProNew.getText().length());
				int validaCategoria = 0;
				validaCategoria = txtProNew.getText().length();
				alertasMensajes alerta = new alertasMensajes();
				System.out.println("valida: " + validaCategoria + " - " + tableDescProductos.getItems().size());
				if (validaCategoria == 0) {
					if (tableDescProductos.getItems().size() == 0) {
						String srtError = "No ha ingresado la nueva categoría o subcategoría...";
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
				}
					 else {
							System.out.println("==================================================");
							System.out.println(" creando categoría y subcategoría...");
							System.out.println("==================================================");
							int existeCategoria = 0;
							try {
								String srtError="";
								existeCategoria = new ProductosBO().existeCategoriaProd(txtProNew.getText().toString().trim());
								if ( existeCategoria == 0 )
								{	
									insertaProductosCategoria(txtProNew.getText().toString().trim());
									srtError="";
									limpiarTemporal();
									traeProductosCategoria();
									limpiarPantalla();
								}
								else 
								{
									srtError = "La categoría " + txtProNew.getText().toString().trim() +  " ya se encuentra registrada.";
									alerta.alertaOK(srtError);
								}	
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}	
				}
			
		});
		

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
													//lblActStock,
													//txtActStock,
													lblPVta,
													txtPrecioVta,
													btnPrecio,
													btnGuardar,
													//btnActualizar,
													btnLimpiar
											  
			
		);
		datosIngresoProductos.setPadding(new Insets(5));
		datosIngresoProductos.setTranslateX(20);
		datosIngresoProductos.setTranslateY(250);
		datosIngresoProductos.setTranslateZ(20);
		datosIngresoProductos.setMaxSize(750, 200);
		datosIngresoProductos.setId("colorMarco");
		/*** ***/
		

		bp.setCenter(b.fondoPantalla());
		rootIngreso.getChildren().addAll( bp, datosPrincipales,datosIngresoProductos

		);

		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 630, 550);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual = new Stage();
		
		ventanaActual.setTitle("Ingreso de productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.initModality(Modality.APPLICATION_MODAL);
		ventanaActual.getIcons().add(b.iconoLaren());
		ventanaActual.showAndWait();;
		return optionRetorno;
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
	
	/*** INICIO guardando solo subcategoria del producto ***/
	public void insertaProductosDet(String nomCat) {
		System.out.println("==================================================");
		System.out.println(" Guardando solo Sub-categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		alertasMensajes alerta = new alertasMensajes();
		try {
			for (int i = 0; i < tableDescProductos.getItems().size(); i++) {
				String descripcion = null;
				descripcion = tableDescProductos.getItems().get(i).getDescripcion();
				
				int existeCategoria = 0;
				existeCategoria = new ProductosBO().existeSubCatProductos(nomCat, descripcion); 
				if ( existeCategoria ==  0 )
				{	
					resInsert = new ProductosBO().insertaSubProducto(descripcion, nomCat, usuarioGlobal );
					System.out.println(tableDescProductos.getItems().get(i).getDescripcion());
					if (resInsert != 1) {
						String srtError = "La subcategoría " + descripcion + " no se pudo ingresar : ";
						
						alerta.alertaGeneral(srtError);
						System.out.println("NO HAY DATOS");
					}
				}
				else 
				{
					String srtError = "La subcategoría " + descripcion + " ya existe, por favor revise... ";
					alerta.alertaOK(srtError); //.alertaGeneral(srtError);
				}	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*** FIN guardando solo subcategoria del producto ***/

	
	/*** INICIO guardando categoria del producto ***/
	public int insertaProductosCategoria(String Descripcion) {
		System.out.println("==================================================");
		System.out.println(" Guardando categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		alertasMensajes alerta = new alertasMensajes();
		String srtError="";
		try {
			resInsert = new ProductosBO().insertaCategoriaProd(Descripcion, usuarioGlobal);
			if (resInsert != 0 || resInsert != -1) 
			{
				srtError = "La categoría " + Descripcion + " se guardo satisfactoriamente en el sistema ";
				alerta.alertaOK(srtError);
				insertaProductosDetalle(resInsert);
			} else {
				// MOSTAR MENSAJE POR PANTALLA
				srtError = "Categoría no se pudo guardar en el sistema";
				alerta.alertaGeneral(srtError);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resInsert;
	}
	/*** INICIO guardando categoria del producto ***/
	public void insertaProductosDetalle(int idCat) {
		System.out.println("==================================================");
		System.out.println(" Guardando Sub-categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		alertasMensajes alerta = new alertasMensajes();
		String srtError = "";
		try {
			for (int i = 0; i < tableDescProductos.getItems().size(); i++) {
				String descripcion = null;
				descripcion = tableDescProductos.getItems().get(i).getDescripcion();
				resInsert = new ProductosBO().insertaSubCatProd(idCat, descripcion, usuarioGlobal);
				System.out.println(tableDescProductos.getItems().get(i).getDescripcion());
				if (resInsert != 1) {
					srtError = "La subcategoría " + descripcion + " no se pudo guardar";
					alerta.alertaGeneral(srtError);
					System.out.println("NO HAY DATOS");
				}
				else 
				{
					srtError = "La subcategoría " + descripcion + " se guardo satisfactoriamente ";
					alerta.alertaOK(srtError);
				}	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*** FIN guardando categoria del producto ***/

	
	comprasPrincipal stage1Controller;
	public void recibeParametros(comprasPrincipal cp1, String texto)
	{
		System.out.println("Esto llego "+texto);
		stage1Controller=cp1;
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
			if (categoriaProductos != null && !categoriaProductos.isEmpty() ) {

				for (productosCategoriaDTO obje : categoriaProductos) {

					if (obje != null) {

						comboProductosCat.getItems().add(obje.getDescripcion());
						System.out.println("Id de la categoria: " + obje.getId_producto_cat());
					}
				}
			} 
			/*else if (!strBanderaControlaMensaje.equals("S")) {
				// MOSTAR MENSAJE POR PANTALLA
				String srtError = "Producto no existe en stock, por favor ingrese otra descripción : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}*/

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
	public int insertaProducto(int idProveedor,String prodEsp, String nomProd, String Descirpcion, float fltValorUni, int intStock,
			float fltPrecioVta) 
	{
		System.out.println("================================================================================");
		System.out.println(" Ingreso de productos...");
		System.out.println("================================================================================");
		ProductosBO objInsertar = new ProductosBO();
		int resInsert = 0;
		try {
			resInsert = objInsertar.insertaProductos(idProveedor,prodEsp, nomProd, Descirpcion, fltValorUni, intStock,
					fltPrecioVta, usuarioGlobal);
			System.out.println("inserta  3 + "+ resInsert);
			if (resInsert == 1) {
				//System.out.println("Resultado del query: " + resInsert);
				//alertasMensajes alertas = new alertasMensajes();
				//String strMensaje = "Se ha insertado el producto:" + nomProd;
				//alertas.alertaOK(strMensaje);
				//return resInsert;
				
				//ventanaActual.toBack();
				//ventanaActual.close();
				return resInsert;
				
			}
			else
			{	
				System.out.println("inserta  NO");
				return resInsert;
				//return 0;
		}} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No entro");
		}
		return resInsert;
	}
	
	public void limpiarPantalla() 
	{
			txtNProducto.setText("");
			txtDescripcion.setText("");
			txtPrecioCompra.setText("");
			txtStock.setText("");
			txtValorPorcentual.setText("");
			txtPrecioVta.setText("");
			txtStock.setEditable(true);
			txtPrecioVta.setEditable(false);
			btnGuardar.setDisable(false);
			btnLimpiar.setDisable(false);

			txtCatNew.setText("");
			txtProNew.setText("");
	}
	
	productoDTO objActualizaProd = new productoDTO(); 
	/*** Actualiza el producto 
	 * @return ***/
	public Optional<ButtonType> actualizaProductos(int idProveedor, productoDTO cargarProducto) 
	{
		Label scenetitleT = new Label("Detalles del producto");
		scenetitleT.setLayoutX(180);
		scenetitleT.setLayoutY(5);
		scenetitleT.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));
		/*** Nuevos Cambios para el ingreso del producto ***/
		botones b = new botones();
		
		
		Label lblNProducto = new Label("Nombre producto.");
		lblNProducto.setLayoutX(30);
		lblNProducto.setLayoutY(40);
		
		txtNProducto = new TextField();
		txtNProducto.setLayoutX(160);
		txtNProducto.setLayoutY(40);
		txtNProducto.setPrefSize(220, 25);
		txtNProducto.setEditable(false);
 
		txtNProducto.setText(cargarProducto.getNombreProducto());
		Label lblDescripcion = new Label("Descripción");
		lblDescripcion.setLayoutX(30);
		lblDescripcion.setLayoutY(80);
		
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(160);
		txtDescripcion.setLayoutY(75);
		txtDescripcion.setPrefSize(220, 25);
		txtDescripcion.setEditable(false);
		txtDescripcion.setText(cargarProducto.getDescripcion());
		Label lblPCompra = new Label("Precio compra");
		lblPCompra.setLayoutX(30);
		lblPCompra.setLayoutY(120);
		
		txtPrecioCompra = new TextField();
		txtPrecioCompra.setLayoutX(160);
		txtPrecioCompra.setLayoutY(115);
		txtPrecioCompra.setPrefSize(60, 25);
		txtPrecioCompra.setEditable(false);
		String valorCompra="";
		valorCompra = String.valueOf(cargarProducto.getValorCompra());
		txtPrecioCompra.setText(valorCompra);
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
		lblStock.setLayoutY(160);
		
		txtStock = new TextField();
		txtStock.setLayoutX(410);
		txtStock.setLayoutY(155);
		txtStock.setPrefSize(60, 25);
		txtStock.setEditable(false);
		String Stock="";
		Stock = String.valueOf(cargarProducto.getStock());
		txtStock.setText(Stock);
		txtStock.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
					txtStock.setText(oldValue);
				}
			}
		});

		Label lblPorcentaje = new Label("Ganancia[%]");
		lblPorcentaje.setLayoutX(260);
		lblPorcentaje.setLayoutY(120);
		
		txtValorPorcentual = new TextField();
		txtValorPorcentual.setLayoutX(410);
		txtValorPorcentual.setLayoutY(115);
		txtValorPorcentual.setPrefSize(60, 25);
		txtValorPorcentual.setEditable(false);
		
		
		
		txtValorPorcentual.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
					txtValorPorcentual.setText(oldValue);
				}
			}
		});
		botones btna = new botones();
		
		Label lblPVta = new Label("Precio Venta");
		lblPVta.setLayoutX(260);
		lblPVta.setLayoutY(200);
		
		txtPrecioVta = new TextField();
		txtPrecioVta.setLayoutX(410);
		txtPrecioVta.setLayoutY(195);
		txtPrecioVta.setPrefSize(60, 25);
		String valorVta="";
		valorVta = String.valueOf(cargarProducto.getValorVenta());
		txtPrecioVta.setText(valorVta);
		txtPrecioVta.setEditable(false);

		
		//txtValorPorcentual;
		float resultado=0;
		String porcentaje="";
		resultado = ((cargarProducto.getValorVenta() - cargarProducto.getValorCompra())*100) / cargarProducto.getValorCompra();
		porcentaje = String.valueOf(resultado) ;
		DecimalFormat formato1 = new DecimalFormat("#.00");
		porcentaje = formato1.format(resultado);
		porcentaje= porcentaje.replaceAll(",", ".");
		System.out.println(" Resultado: "+porcentaje);
		txtValorPorcentual.setText(porcentaje.toString());	
		btnLimpiar = new Button("Limpiar");
		btnLimpiar.setLayoutX(350);
		btnLimpiar.setLayoutY(300);
		btnLimpiar.setGraphic(btna.botonLimpiarJPG());
		
		btnLimpiar.setDisable(false);
		
		Button btnPrecios = new Button("Calcular");
		btnPrecios.setLayoutX(500);
		btnPrecios.setLayoutY(50);
		btnPrecios.setVisible(false);
		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();
		
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
													lblPVta,
													txtPrecioVta,
													btnPrecios
													
											  
			
		);
		datosIngresoProductos.setPadding(new Insets(5));
		datosIngresoProductos.setTranslateX(20);
		datosIngresoProductos.setTranslateY(25);
		datosIngresoProductos.setTranslateZ(20);
		datosIngresoProductos.setMaxSize(750, 150);
		datosIngresoProductos.setId("colorMarco");
		/*** ***/
		
		
		/***Updtae***/
		
		ToggleGroup group = new ToggleGroup();
		// Radio 1: Male
		RadioButton button1 = new RadioButton("Modificar stock");
		button1.setToggleGroup(group);
		button1.setSelected(true);
		button1.setId("radioButton");
		// Radio 2: Female.
		RadioButton button2 = new RadioButton("Modificar precio y stock");
		button2.setToggleGroup(group);
		button2.setId("radioButton");
		HBox radioButtonCliente = new HBox();
		radioButtonCliente.setPadding(new Insets(10));
		radioButtonCliente.setSpacing(5);
		radioButtonCliente.setLayoutX(30);
		radioButtonCliente.setLayoutY(25);
		radioButtonCliente.getChildren().addAll( button1, button2);
		radioButtonCliente.setId("radioButton");
		//
		/*** FIUn update***/
		
		
		/*** Nuevos valores ***/
		
		Label scenetitle = new Label("Modificación del producto: "+cargarProducto.getNombreProducto());
		scenetitle.setLayoutX(100);
		scenetitle.setLayoutY(3);
		scenetitle.setId("texto");
		
		Label lblPCompra1 = new Label("Precio compra");
		lblPCompra1.setLayoutX(30);
		lblPCompra1.setLayoutY(70);
		lblPCompra1.setVisible(false);
		TextField txtPrecioCompra1 = new TextField();
		txtPrecioCompra1.setLayoutX(160);
		txtPrecioCompra1.setLayoutY(65);
		txtPrecioCompra1.setText("");
		txtPrecioCompra1.setPrefSize(60, 25);
		txtPrecioCompra1.setVisible(false);
		txtPrecioCompra1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
					txtPrecioCompra1.setText(oldValue);
				}
			}
		});

		Label lblStock1 = new Label("Nuevo # de productos");
		lblStock1.setLayoutX(260);
		lblStock1.setLayoutY(110);
		lblStock1.setVisible(false);
		TextField txtStock1 = new TextField();
		txtStock1.setLayoutX(410);
		txtStock1.setLayoutY(105);
		txtStock1.setText("");
		txtStock1.setVisible(false);
		txtStock1.setPrefSize(60, 25);
		txtStock1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
					txtStock1.setText(oldValue);
				}
			}
		});

		Label lblPorcentaje1 = new Label("Ganancia[%]");
		lblPorcentaje1.setLayoutX(260);
		lblPorcentaje1.setLayoutY(70);
		lblPorcentaje1.setVisible(false);
		TextField txtValorPorcentual1 = new TextField();
		txtValorPorcentual1.setLayoutX(410);
		txtValorPorcentual1.setLayoutY(65);
		txtValorPorcentual1.setPrefSize(60, 25);
		txtValorPorcentual1.setText("");
		txtValorPorcentual1.setVisible(false);
		txtValorPorcentual1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					txtValorPorcentual1.setText(oldValue);
				}
			}
		});
		btnPrecio = new Button("Calcular");
		btnPrecio.setLayoutX(500);
		btnPrecio.setLayoutY(70);
		//btnPrecio.setPrefSize(15, 20);
		btnPrecio.setVisible(false);
		Label lblPVta1 = new Label("Precio Venta");
		lblPVta1.setLayoutX(260);
		lblPVta1.setLayoutY(150);
		lblPVta1.setVisible(false);
		TextField txtPrecioVta1 = new TextField();
		txtPrecioVta1.setLayoutX(410);
		txtPrecioVta1.setLayoutY(145);
		txtPrecioVta1.setText("");
		txtPrecioVta1.setPrefSize(60, 25);
		txtPrecioVta1.setVisible(false);
		
		btnPrecio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Calculando...");
				System.out.println("=======================================================");
				if (txtPrecioCompra1.getText().toString().trim().isEmpty()
						|| txtValorPorcentual1.getText().toString().trim().isEmpty()
						|| txtStock1.getText().toString().trim().isEmpty()) {
					alertasMensajes alerta = new alertasMensajes();
					String srtError = "Faltan datos por ingresar.. ";
					alerta.alertaGeneral(srtError);
				} else {
					float precioCompra = 0;
					precioCompra = Float.parseFloat(txtPrecioCompra1.getText().toString().trim());
					float calculoPorcentaje = 0, porcentaje = 0;
					porcentaje = Float.parseFloat(txtValorPorcentual1.getText().toString().trim());
					calculoPorcentaje = ((precioCompra * porcentaje) / 100) + precioCompra;
					String resultado = null;
					resultado = String.valueOf(calculoPorcentaje);
					DecimalFormat formato1 = new DecimalFormat("#.00");
					resultado = formato1.format(calculoPorcentaje);
					txtPrecioVta1.setText(resultado);
				}
			}

		});
		
		txtPrecioVta1.setEditable(false);
		
		
		btnActualizar = new Button("Actualizar");
		btnActualizar.setLayoutX(160);
		btnActualizar.setLayoutY(200);
		btnActualizar.setGraphic(btna.botonActualizaProducto());
		btnActualizar.setVisible(false);
		btnActualizar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Calculando Actualiza..."+txtPrecioCompra1.getText().toString().trim());
				System.out.println("=======================================================");
				alertasMensajes alerta = new alertasMensajes();
				
				if (txtStock1.getText().toString().trim().isEmpty())
				{	
					String srtError = "No se ha ingresado la cantidad de productos comprados: ";
					alerta.alertaGeneral(srtError);
				}
				else
					{	
						if (txtPrecioCompra1.getText().toString().isEmpty() && txtValorPorcentual1.getText().toString().isEmpty() && txtPrecioVta1.getText().toString().isEmpty() && txtStock1.getText().toString().isEmpty())
						{	String srtError1 = "Faltan datos por ingresar, por favor revise.. ";
							alerta.alertaGeneral(srtError1);
						}
						else
						{	
							int stockNuevo = 0 ;
							stockNuevo = Integer.parseInt(txtStock1.getText().toString().trim());
							
							if (txtPrecioCompra1.getText().toString().isEmpty() && txtValorPorcentual1.getText().toString().isEmpty() && txtPrecioVta1.getText().toString().isEmpty() && !txtStock1.getText().toString().isEmpty())
							{	
								String srtError2 = "Se procederá a actualizar el Stock del producto: "+cargarProducto.getNombreProducto();
								alerta.alertaOK(srtError2);
								float pCompra = 0, pVenta = 0;
								
							    int resultadoInsert =actualizaProducto(cargarProducto, pCompra, stockNuevo, pVenta);
							    if ( resultadoInsert == 1 )
								{
									
									optionRetorno = alerta.opcionConfirmacion("Confirmación", "Se ha actualizado el producto ");
									ventanaActual.close();
								}	
								else 
								{
									
									String  strMensaje="No se ha actualizado el producto, por favor vuelva a intentarlo";
									alerta.alertaError(strMensaje);
									ventanaActual.close();
								}	
							}
							else
							{	if(!txtPrecioCompra1.getText().toString().isEmpty() && !txtValorPorcentual1.getText().toString().isEmpty() && !txtPrecioVta1.getText().toString().isEmpty() && !txtStock1.getText().toString().isEmpty())
								{
									float precioCompra = 0;
									precioCompra = Float.parseFloat(txtPrecioCompra1.getText().toString().trim());
									float calculoPorcentaje = 0, porcentaje = 0;
									
									porcentaje = Float.parseFloat(txtValorPorcentual1.getText().toString().trim());
									calculoPorcentaje = ((precioCompra * porcentaje) / 100) + precioCompra;
									String resultado = null;
									resultado = String.valueOf(calculoPorcentaje);
									DecimalFormat formato1 = new DecimalFormat("#.00");
									resultado = formato1.format(calculoPorcentaje);
									txtPrecioVta1.setText(resultado);
									System.out.println("OK");
									String srtError1 = "Se procederá a actualizar el producto: "+cargarProducto.getNombreProducto();
									alerta.alertaOK(srtError1);
									int resultadoInsert = actualizaProducto(cargarProducto, precioCompra, stockNuevo, calculoPorcentaje);
									 if ( resultadoInsert == 1 )
										{
											
											optionRetorno = alerta.opcionConfirmacion("Confirmación", "Se ha actualizado el producto ");
											ventanaActual.close();
										}	
										else 
										{
											
											String  strMensaje="No se ha actualizado el producto, por favor vuelva a intentarlo";
											alerta.alertaError(strMensaje);
											ventanaActual.close();
										}
								}
								else
								{
									if (txtPrecioCompra1.getText().toString().trim().isEmpty()
											|| txtValorPorcentual1.getText().toString().trim().isEmpty()
											|| txtPrecioVta1.getText().toString().trim().isEmpty()) {
										String srtError = "Faltan datos por ingresar.. ";
										alerta.alertaGeneral(srtError);
									}		
								}
							}
						}
				}					
			}

		});
		
		btnExit  = new Button("Cerrar");
		btnExit.setLayoutX(320);
		btnExit.setLayoutY(200);
		btnExit.setGraphic(btna.botonError());
		
		/*** ***/
		Label lblStock2 = new Label("cantidad comprada");
		lblStock2.setLayoutX(30);
		lblStock2.setLayoutY(70);
		TextField txtStock2 = new TextField();
		txtStock2.setLayoutX(160);
		txtStock2.setLayoutY(65);
		txtStock2.setText("");
		Button btnPrecioStock = new Button("Actualizar");
		btnPrecioStock.setLayoutX(160);
		btnPrecioStock.setLayoutY(200);
		btnPrecioStock.setGraphic(btna.botonActualizaProducto());
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				// Has selection.
				if (group.getSelectedToggle() != null) {
					RadioButton button = (RadioButton) group.getSelectedToggle();
					lblPCompra1.setVisible(false);
					txtPrecioCompra1.setVisible(false);
					lblStock1.setVisible(false);
					txtStock1.setVisible(false);
					lblPorcentaje1.setVisible(false);
					txtValorPorcentual1.setVisible(false);
					lblPVta1.setVisible(false);
					txtPrecioVta1.setVisible(false);
					txtStock2.setVisible(false);
					lblStock2.setVisible(false);
					btnPrecio.setVisible(false);
					btnActualizar.setVisible(false);
					btnPrecioStock.setVisible(false);
					if (button.getText().toString() == "Modificar stock") 
					{
						System.out.println("Modificando stock");
						lblStock2.setVisible(true);
						txtStock2.setVisible(true);
						btnPrecioStock.setVisible(true);
					} 
					else 
					{
						System.out.println("Modificando stock y precio");
						lblPCompra1.setVisible(true);
						txtPrecioCompra1.setVisible(true);
						lblStock1.setVisible(true);
						txtStock1.setVisible(true);
						lblPorcentaje1.setVisible(true);
						txtValorPorcentual1.setVisible(true);
						lblPVta1.setVisible(true);
						txtPrecioVta1.setVisible(true);
						btnPrecio.setVisible(true);
						btnActualizar.setVisible(true);
					}
				}
			}
		});
		/*** **/
		btnPrecioStock.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Actualizando Stock "+cargarProducto.getIdProducto());
				System.out.println("=======================================================");
				alertasMensajes alerta = new alertasMensajes();
				int Stock=0;

				if (txtStock2.getText().toString().isEmpty() )
				{	String srtError1 = "No ha ingresado el número de productos que compro.. ";
					alerta.alertaGeneral(srtError1);
				}
				
				else 
				{	
					Stock = Integer.parseInt(txtStock2.getText().toString().trim());
					ProductosBO objInsertar = new ProductosBO();
					if ( Stock > 0 )
					{	
						int resInsert = 0;
						try {
							
							resInsert = objInsertar.modifStockProductos(cargarProducto.getIdProducto(), Stock); 
							if (resInsert == 1) 
							{
									optionRetorno = alerta.opcionConfirmacion("Confirmación", "Se ha actualizado el producto ");
									ventanaActual.close();
							}	
							else 
							{
									
									String  strMensaje="No se ha actualizado el producto, por favor vuelva a intentarlo";
									alerta.alertaError(strMensaje);
									ventanaActual.close();
							}
							
						}catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("No entro");
						}
					}
					else 
					{
						String srtError1 = "El número de productos que ha ingresado no es válido ";
						alerta.alertaGeneral(srtError1);
					}	
				}
				
				
		}});
		
		
		
		AnchorPane datosModificarProductos = new AnchorPane();
		datosModificarProductos.getChildren().addAll(scenetitle,
													lblPCompra1,
													txtPrecioCompra1,
													lblPorcentaje1,
													txtValorPorcentual1,
													lblStock1,
													txtStock1,
													lblPVta1,
													txtPrecioVta1,
													lblStock2,
													txtStock2,
													btnPrecioStock,
													btnPrecio,
													btnActualizar,
													radioButtonCliente,
													btnExit
													
											  
			
		);
		datosModificarProductos.setPadding(new Insets(5));
		datosModificarProductos.setTranslateX(20);
		datosModificarProductos.setTranslateY(290);
		datosModificarProductos.setTranslateZ(20);
		datosModificarProductos.setMaxSize(750, 200);
		datosModificarProductos.setId("colorMarco");
		
		/*** FIN nuevos valores***/
		bp.setCenter(b.fondoPantalla());
		rootIngreso.getChildren().addAll( bp,datosIngresoProductos,datosModificarProductos

		);
		btnExit.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				System.out.println("=======================================================");
				System.out.println("	Saliendo...");
				System.out.println("=======================================================");
				ventanaActual.toBack();
				ventanaActual.close();
			}

		});
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 630, 550);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual = new Stage();
		
		ventanaActual.setTitle("Actualiza de productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.initModality(Modality.APPLICATION_MODAL);
		ventanaActual.getIcons().add(b.iconoLaren());
		ventanaActual.showAndWait();
		return optionRetorno;
		
		

	}
	/*** FIN actualiza el producto
	 * @param cargarProducto ***/
	
	/*Meotod para actuilizar el producto */
	public int actualizaProducto(productoDTO cargarProducto, Float precioCompra, int  stockNuevo, float resultado)
	{
		System.out.println("================================================================================");
		System.out.println(" Modificación de productos...");
		System.out.println("================================================================================");
		ProductosBO objInsertar = new ProductosBO();
		int resInsert = 0;
		try {
			
			resInsert = objInsertar.modificaProductosNew(cargarProducto.getIdProducto(), precioCompra, stockNuevo, resultado);
			
			if (resInsert == 1) 
			{
				//alertasMensajes alertas = new alertasMensajes();
				//String strMensaje = "Se ha actualizado el producto: " +cargarProducto.getNombreProducto();
				//alertas.alertaOK(strMensaje);
				//return resInsert;
				//ventanaActual.toBack();
				//ventanaActual.close();
				return resInsert;
			}
			else
			{	
				System.out.println("inserta  NO");
				return resInsert;
				//return 0;
		}} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No entro");
		}
		return resInsert;
	}
	/* FIn*/
}

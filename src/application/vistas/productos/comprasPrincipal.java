package application.vistas.productos;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.aquafx_project.AquaFx;

import application.Principal;
import application.BO.ComprasBO;
import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ProveedorDTO;
import application.com.DTOS.comprasDTO;
import application.com.DTOS.productoDTO;
import application.extras.botones;
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

public class comprasPrincipal implements EventHandler<ActionEvent>
{	public TextField txtIdentificacion;
	public Button  btnExit, btnAdd, btnUpdate, btnClear,btnClearT, btnNewProducto, btnModifProducto;
	
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Nuevo proveedor");
	public ComboBox<String> comboProveedores = new ComboBox<String>(Contenido);
	
	public List<ProveedorDTO> proveedoresSelect = null;
	public String proveedor = "";
	
	public List<productoDTO> listaProductos = null;
	
	public Stage ventanaActual;
	
	comprasPrincipal Stage1Controller;
	
	productoDTO cargarProducto = new productoDTO();
	
	public int idProveedorGeneral=0;
	/* tabla de productos */
	
	/*** ***/
	public TableView<productoDTO> productosIngresados = new TableView();
	public TableColumn<productoDTO, String> idProveedor = new TableColumn<>("id Prov.");
	public TableColumn<productoDTO, String> idProducto = new TableColumn<>("Cod");
	public TableColumn<productoDTO, String> nomProducto = new TableColumn<>("Nombre");
	public TableColumn<productoDTO, String> descProducto = new TableColumn<>("Descripción");
	public TableColumn<productoDTO, String> nombDescProducto = new TableColumn<>("Nombre Producto");
	public TableColumn<productoDTO, String> valorCompra = new TableColumn<>("P. Compra");
	public TableColumn<productoDTO, String> disponibles = new TableColumn<>("Stock");
	public TableColumn<productoDTO, String> valorVenta = new TableColumn<>("P. Venta");
	public TableColumn<productoDTO, String> categoria = new TableColumn<>("Categoría");
	public TableColumn<productoDTO, String> subcategoria = new TableColumn<>("Sub-categoría");
	/*** ***/
	/* FIN tabla de productos*/
	public String usuarioGlobal = "";
	
	public void comprasPrin(Stage ventanaIngreso, String usuario) 
	{
		usuarioGlobal = usuario;
		//ventanaActual = ventanaIngreso;
		ventanaActual = new Stage();
		// Bandera para controlar que ingreso por primera vez
				Label scenetitle = new Label("Compras");
				scenetitle.setLayoutX(250);
				scenetitle.setLayoutY(5);
				scenetitle.setId("texto");
				botones b = new botones();
				
				Stage1Controller=this;
				
				/*** INI Detalles de la compra***/
				Label lblFecha = new Label("Fecha ");
				lblFecha.setLayoutX(30);
				lblFecha.setLayoutY(50);
				TextField txtFecha = new TextField();
				txtFecha.setLayoutX(100);
				txtFecha.setLayoutY(45);
				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				System.out.println("Fecha actual: " + dateFormat.format(date));
				txtFecha.setText(dateFormat.format(date).toString());
				txtFecha.setEditable(false);
				
				Label lblProveedor = new Label("Proveedor");
				lblProveedor.setLayoutX(310);
				lblProveedor.setLayoutY(50);
				comboProveedores.setLayoutX(380);
				comboProveedores.setLayoutY(45);
				
				proveedor="";
				/*** INI metodo que sirve para cargar el combo con los proveedores***/
				comboProveedores.valueProperty().addListener((ov, p1, p2) -> {

					System.out.println("Proveedor --> " + p2);
					System.out.println("================================================================================");
					System.out.println(" Agregando Proveedor al combo...");
					System.out.println("================================================================================");
					proveedor="";
					proveedor=p2;
					btnModifProducto.setDisable(true);
					if (p2 != null) {
						if (p2 == "Nuevo proveedor")
						{
							proveedorUI insertaProveedor = new proveedorUI();
							insertaProveedor.insertaProveedor(null,usuarioGlobal);

						} 
						else 
						{

							System.out.println(" Agregando Proveedor...");
							try {
								cargaProductos(p2);
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							///***
							//productosIngresados
						}
					}
				});
				
				traeProveedores();
				/*** FIN metodo cargado con los proveedores***/
				Label lblFactura = new Label("N Factura ");
				lblFactura.setLayoutX(30);
				lblFactura.setLayoutY(90);
				TextField txtFactura = new TextField();
				txtFactura.setLayoutX(100);
				txtFactura.setLayoutY(85);
				txtFactura.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if (!newValue.matches("\\d{0,10}?") && !newValue.matches("\\d{0,13}?")) {
							txtFactura.setText(oldValue);
						}

					}
				});
				Label lblBase0 = new Label("Base 0% ");
				lblBase0.setLayoutX(310);
				lblBase0.setLayoutY(90);
				TextField txtBase0 = new TextField();
				txtBase0.setLayoutX(380);
				txtBase0.setLayoutY(85);
				txtBase0.setPrefSize(60, 25);
				txtBase0.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
							txtBase0.setText(oldValue);
						}
					}
				});
				
				Label lblBaseD = new Label("Base 12% ");
				lblBaseD.setLayoutX(310);
				lblBaseD.setLayoutY(130);
				TextField txtBaseD = new TextField();
				txtBaseD.setLayoutX(380);
				txtBaseD.setLayoutY(125);
				txtBaseD.setPrefSize(60, 25);
				txtBaseD.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
							txtBaseD.setText(oldValue);
						}
					}
				});
				
				Label lblIva = new Label("Iva");
				lblIva.setLayoutX(310);
				lblIva.setLayoutY(170);
				TextField txtIva = new TextField();
				txtIva.setLayoutX(380);
				txtIva.setLayoutY(165);
				txtIva.setPrefSize(60, 25);
				txtIva.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
							txtIva.setText(oldValue);
						}
					}
				});
				
				botones btna = new botones();
				
				Label lblTotal = new Label("Total");
				lblTotal.setLayoutX(310);
				lblTotal.setLayoutY(210);
				TextField txtTotal = new TextField();
				txtTotal.setLayoutX(380);
				txtTotal.setLayoutY(205);
				txtTotal.setText("");
				txtTotal.setPrefSize(60, 25);
				txtTotal.setEditable(false);
				txtTotal.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) 
						{	txtTotal.setText(oldValue);
						}
					}
				});
				
			  
				Button btnComprar = new Button ("Comprar");
			    btnComprar.setLayoutX(150);
			    btnComprar.setLayoutY(250);
			    btnComprar.setDisable(true);
			    btnComprar.setGraphic(btna.botonGuardar());
			    
			    Button btnRefrescarCombo = new Button ("Refrescar");
			    btnRefrescarCombo.setLayoutX(290);
			    btnRefrescarCombo.setLayoutY(250);
			    btnRefrescarCombo.setVisible(true);
			    btnRefrescarCombo.setGraphic(btna.botonActualizaProducto());
			    btnRefrescarCombo.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println("=======================================================");
						System.out.println("Refrescando...");
						System.out.println("=======================================================");
						traeProveedores();
						 txtFactura.setText("");
						 btnComprar.setDisable(true);
						 txtBase0.setText("");
						 txtBaseD.setText("");
						 txtIva.setText("");
						 txtTotal.setText("");
						
					}

				});
			    Button btnTotalCancelar = new Button("Calcular",b.botonCalcular());
			    btnTotalCancelar.setLayoutX(480);
			    btnTotalCancelar.setLayoutY(170);
				//btnPrecio.setPrefSize(15, 20);
			    btnTotalCancelar.setTextFill(Color.GREEN);
			   // btnTotalCancelar.setContentDisplay(ContentDisplay.BOTTOM);
			    btnTotalCancelar.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println("=======================================================");
						System.out.println("Calculando...");
						System.out.println("=======================================================");
						if (!txtFactura.getText().toString().isEmpty() && !txtBase0.getText().toString().isEmpty() && !txtBaseD.getText().toString().isEmpty() && 
								!txtIva.getText().toString().isEmpty() )
						{	
								
							float baseD = 0, base0 = 0, iva=0, totalCOmpra=0;
							
							String ivaDoceS = txtBaseD.getText().toString().trim().replace(",", ".");
							baseD = Float.parseFloat(ivaDoceS);
							
							String Base0 = txtBase0.getText().toString().trim().replace(",", ".");
							base0 = Float.parseFloat(Base0);
							
							String ivaTotal = txtIva.getText().toString().trim().replace(",", ".");
							iva = Float.parseFloat(ivaTotal);
							totalCOmpra= baseD+ base0+iva;
							
							String totalString = "";
							totalString = String.valueOf(totalCOmpra);
							txtTotal.setText(totalString);
							 btnComprar.setVisible(true);
							 btnComprar.setDisable(false);
						
						 }
						 else {
							String srtError = "Faltan datos por favor revise";
							alertasMensajes alerta = new alertasMensajes();
							alerta.alertaGeneral(srtError);
						}
						
					}

				});
			    txtIva.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (ke.getCode().equals(KeyCode.ENTER)) 
						{
							if (!txtFactura.getText().toString().isEmpty() && !txtBase0.getText().toString().isEmpty() && !txtBaseD.getText().toString().isEmpty() && 
									!txtIva.getText().toString().isEmpty() )
							{	
									
								float baseD = 0, base0 = 0, iva=0, totalCOmpra=0;
								
								String ivaDoceS = txtBaseD.getText().toString().trim().replace(",", ".");
								baseD = Float.parseFloat(ivaDoceS);
								
								String Base0 = txtBase0.getText().toString().trim().replace(",", ".");
								base0 = Float.parseFloat(Base0);
								
								String ivaTotal = txtIva.getText().toString().trim().replace(",", ".");
								iva = Float.parseFloat(ivaTotal);
								totalCOmpra= baseD+ base0+iva;
								
								String totalString = "";
								totalString = String.valueOf(totalCOmpra);
								txtTotal.setText(totalString);
								 btnComprar.setVisible(true);
							
							 }
							 else {
								String srtError = "Faltan datos por favor revise";
								alertasMensajes alerta = new alertasMensajes();
								alerta.alertaGeneral(srtError);
							}
							
						}
					}

				});
			    
			    
			    btnComprar.setOnAction(new EventHandler<ActionEvent>() {
				    
					@Override
					public void handle(ActionEvent event) {
						System.out.println("=======================================================");
						System.out.println("Guardando en la tabla...");
						System.out.println("=======================================================");
						idProveedorGeneral = 0;
						if ( !txtFactura.getText().toString().isEmpty() && !txtBase0.getText().toString().isEmpty() && !txtBaseD.getText().toString().isEmpty() && 
								!txtIva.getText().toString().isEmpty() && !txtTotal.getText().toString().isEmpty())
						{	
								
							if (proveedor.length() == 0)
							{
								String srtError = "No ha ingresado un proveedor";
								alertasMensajes alerta = new alertasMensajes();
								alerta.alertaGeneral(srtError);
							}
							else
							{	
								comprasDTO compraRealizada = new comprasDTO();
								for (ProveedorDTO obj: proveedoresSelect)
								{
									if (obj.getNombre() == proveedor)
									{
										//System.out.println("Id del proveedor escogido"+obj.getIdProveedor());
										idProveedorGeneral = obj.getIdProveedor();
									}	
								}
								System.out.println("Id del proveedor: "+idProveedorGeneral);
								int nFactura = 0;
								float baseD = 0, base0 = 0, iva=0, totalCOmpra=0;
								
								String nuFact = txtFactura.getText().toString().trim();
								nFactura = Integer.parseInt(nuFact); 
								
								String ivaDoceS = txtBaseD.getText().toString().trim().replace(",", ".");
								baseD = Float.parseFloat(ivaDoceS);
								
								String Base0 = txtBase0.getText().toString().trim().replace(",", ".");
								base0 = Float.parseFloat(Base0);
								
								String ivaTotal = txtIva.getText().toString().trim().replace(",", ".");
								iva = Float.parseFloat(ivaTotal);
								totalCOmpra= baseD+ base0+iva;
								guardaCompra(idProveedorGeneral, nFactura, base0, baseD, iva, totalCOmpra);
							}	
						
						 }
						 else {
							String srtError = "Faltan datos por favor revise";
							alertasMensajes alerta = new alertasMensajes();
							alerta.alertaGeneral(srtError);
						}
					}
				});
				/*** FIN Detalles de la compra***/
				
			    AnchorPane datosResultantes = new AnchorPane();
				datosResultantes.getChildren().addAll(
													  scenetitle,lblFecha, txtFecha, 
													  lblProveedor,
													  comboProveedores,
													  lblFactura,txtFactura, 
													  lblBase0,
													  txtBase0,
													  lblBaseD,
													  txtBaseD,
													  lblIva,
													  txtIva,
													  btnTotalCancelar,
													  lblTotal,
													  txtTotal,
													  btnComprar,
													  btnRefrescarCombo
													  
													  
													  // tableProductos,								
				);
				datosResultantes.setBorder(new Border(
						new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				datosResultantes.setPadding(new Insets(5));
				datosResultantes.setTranslateX(90);
				datosResultantes.setTranslateY(20);
				datosResultantes.setTranslateZ(20);
				datosResultantes.setMaxSize(700, 200);
				datosResultantes.setId("colorMarco");
				/*Table de productos de proveedores seleccionados*/
				/*** ***/
				idProveedor.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
				idProveedor.setMinWidth(10);
				idProducto.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
				idProducto.setMinWidth(10);
				nomProducto.setCellValueFactory(new PropertyValueFactory<>("NombreProducto"));
				nomProducto.setMinWidth(30);
				descProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
				descProducto.setMinWidth(145);
				valorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
				valorCompra.setMinWidth(140);
				disponibles.setCellValueFactory(new PropertyValueFactory<>("Stock"));
				disponibles.setMinWidth(10);
				valorVenta.setCellValueFactory(new PropertyValueFactory<>("valorVenta"));
				valorVenta.setMinWidth(10);
				
				categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
				categoria.setMinWidth(80); 
				subcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
				subcategoria.setMinWidth(120);
				
				productosIngresados.getColumns().addAll(categoria,subcategoria,nomProducto, descProducto, valorCompra, disponibles, valorVenta);
				productosIngresados.setLayoutX(25);
				productosIngresados.setLayoutY(45);
				productosIngresados.setPrefSize(740, 160);
				productosIngresados.setVisible(true);
				
				productosIngresados.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2) {
						try {
							cargarProducto = productosIngresados.getSelectionModel().getSelectedItem();
							btnModifProducto.setDisable(false);
							
						} catch (Exception exs) {
							// btnAdd.setDisable(true);
							System.out.println("Error");
						}
					}
					if (event.getClickCount() == 1) {
						cargarProducto = productosIngresados.getSelectionModel().getSelectedItem();
						btnModifProducto.setDisable(false);
					}
				});
				
				Label sceneTable = new Label("Productos existentes");
				sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
				sceneTable.setLayoutX(280);
				sceneTable.setLayoutY(5);
				sceneTable.setId("texto");
				btnNewProducto = new Button("Agregar ");
				btnNewProducto.setGraphic(b.botonAgregarProducto());
				btnNewProducto.setLayoutX(190);
				btnNewProducto.setLayoutY(220);
				//btnNewProducto.setPrefSize(100, 35);
				btnNewProducto.setTextFill(Color.GREEN);
				btnNewProducto.setContentDisplay(ContentDisplay.BOTTOM);
				btnNewProducto.setOnAction(this);
				
				btnModifProducto = new Button("Modificar");
				btnModifProducto.setGraphic(b.botonModifyPro());
				btnModifProducto.setLayoutX(310);
				btnModifProducto.setLayoutY(220);
				//btnModifProducto.setPrefSize(100, 38);
				btnModifProducto.setTextFill(Color.GREEN);
				btnModifProducto.setContentDisplay(ContentDisplay.BOTTOM);
				btnModifProducto.setOnAction(this);
				btnModifProducto.setDisable(true);
				
				/*  INI Refrescar tabla cuando ingreso un nuevo producto*/
				Button btnRefrescarTabla = new Button ("Actualizar");
				btnRefrescarTabla.setLayoutX(430);
				btnRefrescarTabla.setLayoutY(220);
				btnRefrescarTabla.setVisible(true);
				btnRefrescarTabla.setTextFill(Color.GREEN);
				btnRefrescarTabla.setContentDisplay(ContentDisplay.BOTTOM);
				//btnRefrescarTabla.setPrefSize(100, 35);
				btnRefrescarTabla.setGraphic(btna.botonActualizaProducto());
				btnRefrescarTabla.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println("=======================================================");
						System.out.println("Refrescando...");
						System.out.println("=======================================================");
						 try {
								cargaProductos(proveedor);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}

				});
				/* FIN Refrescar tabla cuando ingreso un nuevo producot*/
				
				AnchorPane datosProductosTable = new AnchorPane();
				
				datosProductosTable.getChildren().addAll(
														
														sceneTable,
														productosIngresados,
														btnNewProducto,
														btnModifProducto,
														btnRefrescarTabla
														
				);
				datosProductosTable.setPadding(new Insets(5));
				datosProductosTable.setTranslateX(20);
				datosProductosTable.setTranslateY(335);
				datosProductosTable.setTranslateZ(20);
				datosProductosTable.setMaxSize(720, 240);
				datosProductosTable.setId("colorMarco");
				/*** ***/
				/* FIn table de productos de proveedores seleccionados*/
				
				
				
				
				btnExit = new Button("Regresar");
				btnExit.setGraphic(b.botonRegresar());
				btnExit.setLayoutX(510);
				btnExit.setLayoutY(630);
				//btnExit.setPrefSize(100, 35);
				btnExit.setOnAction(this);
				Group rootIngreso = new Group();

				BorderPane bpP = new BorderPane();
				bpP.setCenter(b.fondoPantalla());
				rootIngreso.getChildren().addAll(bpP, 
												 datosResultantes,
												 datosProductosTable,
												 btnExit
						);
				Scene escenaProductos = null;
				
				escenaProductos = new Scene(rootIngreso, 815, 670);
				
				escenaProductos.getStylesheets().add("DarkTheme.css");
				ventanaActual.setTitle("LmLaren");
				ventanaActual.setScene(escenaProductos);
				ventanaActual.setResizable(false);
				ventanaActual.getIcons().add(b.iconoLaren());
				ventanaActual.initModality(Modality.APPLICATION_MODAL);
				ventanaActual.show();
	}
	
	/*** INI trae proveedores registrados***/
	
	/*** Inserta Compra ***/
	
	public void traeProveedores() 
	{
		System.out.println("==================================================");
		System.out.println(" Agregando productos al combo...");
		System.out.println("==================================================");
		try {
			proveedoresSelect = null;
			proveedoresSelect = new ComprasBO().extraeProveedores();
			
			if (proveedoresSelect != null && !proveedoresSelect.isEmpty())
			{
				comboProveedores.getItems().clear();
				Contenido.add("Nuevo proveedor");
				for (ProveedorDTO obje : proveedoresSelect) 
				{
					if (obje != null) 
					{
						comboProveedores.getItems().add(obje.getNombre());
						System.out.println("Id de la categoria: " + obje.getIdProveedor());
					}
				}
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*** ***/
	
	
	/* Guarda la compra*/
	public void guardaCompra(int idProveedor, int nFactura, float base0, float baseD, float iva, float totalCOmpra) 
	{
		System.out.println("================================================================================");
		System.out.println(" Ingreso de compra...");
		System.out.println("================================================================================");	
		ComprasBO objInsertar = new ComprasBO();
		int resInsert = 0;
		try {
			resInsert = objInsertar.insertaCompra(idProveedor, nFactura, base0, baseD, iva, totalCOmpra, usuarioGlobal);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha insertado la compra por el valor total de: "+totalCOmpra;
				alertas.alertaOK(strMensaje);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No entro");
		}
	}
	/* Fin guarda compra*/
	
	/* Carga productos a la tabla pilas*/
	
	public void cargaProductos(String strProducto) throws SQLException {
	try {
			
			productosIngresados.getItems().removeAll();
			productosIngresados.getItems().clear();
			
			listaProductos = new ProductosBO().traeProductosProveedor(strProducto);
			
			if (listaProductos != null && !listaProductos.isEmpty()) {
				for (productoDTO obje : listaProductos) {
					productoDTO llena = new productoDTO();
					if (obje != null) {
						
						System.out.println("Producto: "+obje.getNombreProducto());
						llena.setIdProducto(obje.getIdProducto());
						llena.setNombreProducto(obje.getNombreProducto());
						llena.setIdProveedor(obje.getIdProveedor());
						llena.setDescripcion(obje.getDescripcion());
						llena.setNombreDescripcion(obje.getNombreProducto() + " - " + obje.getDescripcion());
						llena.setValorCompra(obje.getValorCompra());
						llena.setStock(obje.getStock());
						llena.setValorVenta(obje.getValorVenta());
						llena.setCategoria(obje.getCategoria());
						llena.setSubcategoria(obje.getSubcategoria());
						productosIngresados.getItems().add(llena);
					}
				}
				/*** ***/
				
			}
			
		} catch (SQLException e) {
			System.out.println("Entro error");
			e.printStackTrace();
		}

	}
	/*FIn carga productos a la tabla*/
	
	public static <T,U> void refreshTableView(final TableView<T> tableView, final List<TableColumn<T,U>> columns, final List<T> rows) {

	    tableView.getColumns().clear();
	    tableView.getColumns().addAll(columns);

	    ObservableList<T> list = FXCollections.observableArrayList(rows);
	    tableView.setItems(list);
	}
	
	//Investigacion
	public void recibeParametro(int texto)
	{
		System.out.println(texto);
		System.out.println("=======================================================");
		System.out.println("Refrescando...");
		System.out.println("=======================================================");
		 try {
			 
			 ProductosBO traerProv = new ProductosBO(); 
			 		String proveedorS = traerProv.traeNombreProveedor(texto);
			    System.out.println("Nombre del proveedor"+proveedorS);
			 	cargaProductos(proveedorS);
				System.out.println("Cantidad en la tabla"+productosIngresados.getItems().size());
				productosIngresados.requestFocus();
				refreshTableView(productosIngresados, Arrays.asList(idProveedor,idProducto,nomProducto, descProducto, valorCompra, disponibles, valorVenta), listaProductos);
				
			} catch (SQLException e) {
				System.err.println("No se pudo "+proveedor);
			}
	}
	//
	
	public void handle(ActionEvent event) {
		if (event.getSource() == btnExit) 
		{
			System.out.println("==================================================");
			System.out.println("Redirigiendose al menú principal...");
			System.out.println("==================================================");
			ventanaActual.toBack();
			ventanaActual.close();
			//Principal menuInicio = new Principal();
			//menuInicio.panelPrincipal();

		}
		else if (event.getSource() ==btnNewProducto)
		{
			System.out.println("==================================================");
			System.out.println("Creando nuevo producto...");
			System.out.println("==================================================");
			productosIU ingresoPrdo = new productosIU();
			System.out.println(" id Proveedor: "+idProveedorGeneral);
			for (ProveedorDTO obj: proveedoresSelect)
			{
				if (obj.getNombre() == proveedor)
				{
					//System.out.println("Id del proveedor escogido"+obj.getIdProveedor());
					idProveedorGeneral = obj.getIdProveedor();
					System.out.println(" id Proveedorres: "+idProveedorGeneral);
				}	
			}
			Optional<ButtonType> result = ingresoPrdo.ingresoProductos(idProveedorGeneral, usuarioGlobal);
			try {
				if (result.get() == ButtonType.OK){
					cargaProductos(proveedor);
				} 
				else
				{
					cargaProductos(proveedor);
				}
			}
			catch(Exception e)
			{
				System.out.println("Error ");
			}
			
			/*Navegacion entre ventanas*/
			productosIU ControllerStage2 = new productosIU();
			//Stage1Controller
			ControllerStage2.recibeParametros(Stage1Controller, "hola mindo");

		}
		else if (event.getSource() ==btnModifProducto)
		{
			System.out.println("==================================================");
			System.out.println("Actualizando producto...");
			System.out.println("==================================================");
			productosIU ingresoPrdo = new productosIU();
			for (ProveedorDTO obj: proveedoresSelect)
			{
				if (obj.getNombre() == proveedor)
				{
					idProveedorGeneral = obj.getIdProveedor();
					System.out.println(" id Producto: "+idProveedorGeneral);
				}	
			}
			Optional<ButtonType> result = ingresoPrdo.actualizaProductos(idProveedorGeneral, cargarProducto);
			try {
				if (result.get() == ButtonType.OK){
					cargaProductos(proveedor);
				} 
				else
				{
					cargaProductos(proveedor);
				}
				
			}
			catch(Exception e)
			{
				System.out.println("Error ");
			}
		}
		// 
		
	}
}

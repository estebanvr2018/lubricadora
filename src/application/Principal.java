package application;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import application.BO.ClientesBO;
import application.BO.ProductosBO;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.ProductosDTO;
import application.extras.botones;
import application.vistas.facturaGenera;
import application.vistas.facturacion;
import application.vistas.productos.UsuariosPrincipal;
import application.vistas.productos.clientesPrincipal;
import application.vistas.productos.comprasPrincipal;
import application.vistas.productos.consultaFacturas;
import application.vistas.productos.factReporteGeneral;
import application.vistas.productos.productosPrincipal;
import application.vistas.productos.proveedoresPrincipal;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import reportes.ProductoReporte;

public class Principal extends Application implements EventHandler<ActionEvent> {
	Button btnLogin;
	Button btnExit;
	Button btnAgregar;
	MenuItem facturacionMenuItem, bodegaMenuItem, exMenuItem, pruebasCombos;
	MenuItem ingresoProductoMenuItem, reporteProductoMenuItem, CompraProductoMenuItem ;
	MenuItem ingresoClientesMenuItem;
	MenuItem facturasMenuItem, factConsulta, factReporteGeneral;
	MenuItem cargaUserMenuItem;
	MenuItem provMenuItem;
	boolean primera = false;
	boolean segunda = false;
	boolean boolFact = false;
	Stage VentanaT;

	public TableView<ProductosDTO> table = new TableView();
	public TableView<ProductosDTO> tableT = new TableView();
	public TableView<ProductosDTO> tableFacturacion = new TableView();
	public TableColumn<ProductosDTO, String> tableId = new TableColumn<>("Codigo");
	public TableColumn<ProductosDTO, String> tableNomp = new TableColumn<>("Nombre");
	public TableColumn<ProductosDTO, String> tableDesc = new TableColumn<>("Descripcion");
	public TableColumn<ProductosDTO, String> tableStock = new TableColumn<>("Precio");
	public TableColumn<ProductosDTO, String> idTable = new TableColumn<>("Codigo");
	public TableColumn<ProductosDTO, String> Nombre = new TableColumn<>("Nombre");
	public TableColumn<ProductosDTO, String> Desc = new TableColumn<>("Descripcion");
	public TableColumn<ProductosDTO, String> Precio = new TableColumn<>("Precio");

	@Override
	public void start(Stage primaryStage) {
		botones bot = new botones();
		primaryStage.setTitle("Login");
		GridPane grid = new GridPane();
		grid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Bienvenido");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		BorderPane bp = new BorderPane();
		bp.setCenter(bot.fondoPantalla());
		//grid.add(bp, 0, 0, 0, 0);
		grid.add(scenetitle, 0, 0, 2, 1);
		Label userName = new Label("Usuario:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		Label pw = new Label("Contraseña:");
		grid.add(pw, 0, 2);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		botones b = new botones();
		btnLogin = new Button("Entrar");
		btnLogin.setGraphic(b.botonSuccess());
		// btnAdd.setFont(new Font("Arial",15));
		btnLogin.setPrefSize(100, 30);
		btnLogin.setOnAction(this);
		btnExit = new Button("Salir");
		btnExit.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnExit.setPrefSize(100, 30);
		btnExit.setOnAction(this);
		HBox hButton = new HBox(10);
		hButton.setAlignment(Pos.BOTTOM_CENTER);
		hButton.getChildren().addAll(btnLogin, btnExit);
		grid.add(hButton, 1, 4);

		Scene scene = new Scene(grid, 410, 310);
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Entrando al menu principal...");
				System.out.println("=======================================================");
				primaryStage.toBack();
				primaryStage.close();
				panelPrincipal();
			}

		});
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		
		primaryStage.getIcons().add(bot.iconoLaren());
	}

	public void alertaGeneral(String strMensaje) {
		Alert diAlerta = new Alert(AlertType.WARNING);
		diAlerta.setTitle("Alerta");
		diAlerta.setHeaderText(null);
		diAlerta.setContentText(strMensaje);
		diAlerta.initStyle(StageStyle.UTILITY);
		diAlerta.showAndWait();
	}

	public void panelPrincipal() {
		VentanaT = new Stage();
		VentanaT.setTitle("Principal");
		Group raiz = new Group();
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(VentanaT.widthProperty());
		botones b = new botones();
		BorderPane bp = new BorderPane();
		
		bp.setCenter(b.fondoPantalla());

		raiz.getChildren().addAll(bp, menuBar);

		Menu fileMenu = new Menu("_Inicio");
		fileMenu.setGraphic(b.botonBuscarMax());
		
		exMenuItem = new MenuItem("Salir");
		exMenuItem.setOnAction(this);
		
		fileMenu.getItems().add(exMenuItem);
		
		Menu filePoliticas = new Menu("_Productos");
		filePoliticas.setGraphic(b.productosP());
		ingresoProductoMenuItem = new MenuItem("Ingreso de productos");
		ingresoProductoMenuItem.setOnAction(this);
		reporteProductoMenuItem = new MenuItem("Reporte producto");
		reporteProductoMenuItem.setOnAction(this);
		CompraProductoMenuItem = new MenuItem("Compras productos");
		CompraProductoMenuItem.setOnAction(this);
		
		filePoliticas.getItems().addAll(ingresoProductoMenuItem, reporteProductoMenuItem, CompraProductoMenuItem);

		/*** Proveedores***/
		Menu fileProveedores = new Menu("_Proveedores");
		fileProveedores.setGraphic(b.productosP());
		provMenuItem = new MenuItem("Consulta de proveedores");
		provMenuItem.setOnAction(this);
		
		fileProveedores.getItems().add(provMenuItem);
		/*** FIN proveedores ***/
		
		/*** Clientes ***/
		ingresoClientesMenuItem = new MenuItem("Ingreso de clientes");
		ingresoClientesMenuItem.setOnAction(this);
		Menu fileClientes = new Menu("_Clientes");
		fileClientes.setGraphic(b.ClientesP());
		fileClientes.getItems().add(ingresoClientesMenuItem);

		/*** FIN clientes ***/

		/*** INI Facturas ***/
		Menu fileFacturas = new Menu("_Facturación");
		fileFacturas.setGraphic(b.botonFacturaGrande());
		facturasMenuItem = new MenuItem("Facturar");
		facturasMenuItem.setOnAction(this);

		factConsulta = new MenuItem("Consulta de facturas");
		factConsulta.setOnAction(this);
		
		factReporteGeneral = new MenuItem("Reporte Facturas General");
		factReporteGeneral.setOnAction(this);
		

		fileFacturas.getItems().addAll(facturasMenuItem, factConsulta, factReporteGeneral);
		/*** FIN facturas ***/

		/*** INI usuarios ***/
		Menu fileUsuarios = new Menu("_Usuarios");
		cargaUserMenuItem = new MenuItem("Sumarizado de usuarios");
		cargaUserMenuItem.setOnAction(this);
		fileUsuarios.setGraphic(b.UsuariosP());
		fileUsuarios.getItems().add(cargaUserMenuItem);
		/*** FIN usuarios ***/

		menuBar.getMenus().addAll(fileMenu, filePoliticas, fileClientes, fileFacturas, fileUsuarios, fileProveedores);

		Scene escena = new Scene(raiz, 800, 400);
		VentanaT.setScene(escena);
		VentanaT.setResizable(false);
		VentanaT.setX(50);
		VentanaT.setY(20);
		VentanaT.show();
	}

	@SuppressWarnings("unchecked")
	public void panelFactura() {

		Text scenetitle = new Text("Facturación");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(250);
		scenetitle.setY(40);
		// scenetitle.setFont(new Font("Arial",20));

		Label lblRuc = new Label("CEDULA/RUC: ");
		lblRuc.setLayoutX(20);
		lblRuc.setLayoutY(60);
		TextField txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(60);
		/*
		 * Metodo que detecta ingreso de cedula o ruc y al presionar llamará a
		 * un prc
		 */
		txtRuc.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					tableT.getItems().removeAll();
					tableT.getItems().clear();
					System.out.println(
							"================================================================================");
					System.out.println(" Búsqueda en la base de datos");
					System.out.println(
							"================================================================================");

					String parametro = txtRuc.getText().toString();
					if (parametro.length() == 0) {
						String srtError = "Debe poner algo para buscar...";
						alertaGeneral(srtError);
					} else
						consultarCliente(parametro); // OJO OBTENER EL ID
														// INGRESADO
					segunda = true;

				}
			}
		});

		Label lblCliente = new Label("NOMBRES: ");
		lblCliente.setLayoutX(20);
		lblCliente.setLayoutY(90);
		TextField txtCliente = new TextField();
		txtCliente.setLayoutX(100);
		txtCliente.setLayoutY(90);
		Label lblApellidos = new Label("APELLIDOS: ");
		lblApellidos.setLayoutX(300);
		lblApellidos.setLayoutY(90);
		TextField txtApellidos = new TextField();
		txtApellidos.setLayoutX(380);
		txtApellidos.setLayoutY(90);
		Label lblFecha = new Label("FECHA: ");
		lblFecha.setLayoutX(300);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(380);
		txtFecha.setLayoutY(60);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		txtFecha.setText(dateFormat.format(date).toString());

		Label lblDireccion = new Label("DIRECCIÓN: ");
		lblDireccion.setLayoutX(20);
		lblDireccion.setLayoutY(120);
		TextField txtDireccion = new TextField();
		txtDireccion.setLayoutX(100);
		txtDireccion.setLayoutY(120);
		Label lblTelefono = new Label("TELÉFONO: ");
		lblTelefono.setLayoutX(300);
		lblTelefono.setLayoutY(120);
		TextField txtTelefono = new TextField();
		txtTelefono.setLayoutX(380);
		txtTelefono.setLayoutY(120);

		Label lblCorreo = new Label("CORREO: ");
		lblCorreo.setLayoutX(20);
		lblCorreo.setLayoutY(150);
		TextField txtCorreo = new TextField();
		txtCorreo.setLayoutX(100);
		txtCorreo.setLayoutY(150);
		txtCorreo.setPrefSize(200, 25);

		idTable.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
		Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
		Desc.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		Precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
		idTable.setMinWidth(50);
		Nombre.setMinWidth(200);
		Desc.setMinWidth(150);
		Precio.setMinWidth(50);
		/**/
		if (!boolFact)
			tableFacturacion.getColumns().addAll(idTable, Nombre, Desc, Precio);

		tableFacturacion.setLayoutX(40);
		tableFacturacion.setLayoutY(150);
		tableFacturacion.setPrefSize(510, 200);
		Group root = new Group();
		root.getChildren().addAll(lblDireccion, txtDireccion, lblTelefono, txtTelefono, lblCorreo, txtCorreo);
		root.getChildren().addAll(scenetitle, lblRuc, txtRuc, lblCliente, txtCliente, lblApellidos, txtApellidos,
				lblFecha, txtFecha);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 800, 500);
		// escenaConsulta.setFill(null);
		Stage VentanaConsultas;
		VentanaConsultas = new Stage();
		
		VentanaConsultas.setTitle("Control de ventas");
		VentanaConsultas.setScene(escenaConsulta);
		VentanaConsultas.setResizable(false);
		VentanaConsultas.show();
		

	}

	public void panelConsulta() {
		Text scenetitle = new Text("Consulta de productos");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Label lblDescripcion = new Label("Ingrese el producto a buscar: ");
		TextField txtDescripcion = new TextField();
		GridPane panelConsulta = new GridPane();
		panelConsulta.setAlignment(Pos.TOP_CENTER);
		panelConsulta.setVgap(10);
		panelConsulta.setHgap(10);
		panelConsulta.setPadding(new Insets(25, 25, 25, 25));
		panelConsulta.add(scenetitle, 0, 0, 2, 1);
		panelConsulta.add(lblDescripcion, 0, 1);
		panelConsulta.add(txtDescripcion, 1, 1);
		/**/
		BorderPane pruebaBordes = new BorderPane();
		pruebaBordes.setStyle("-fx-background-color: #336699;");
		pruebaBordes.setTop(panelConsulta);
		/**/
		tableId.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
		tableNomp.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		tableDesc.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		tableStock.setCellValueFactory(new PropertyValueFactory<>("ValorUnitario"));
		tableId.setMinWidth(20);
		tableNomp.setMinWidth(150);
		tableDesc.setMinWidth(100);
		tableStock.setMinWidth(30);
		tableT.getItems().removeAll();
		tableT.getItems().clear();
		/**/
		if (!segunda)
			tableT.getColumns().addAll(tableId, tableNomp, tableDesc, tableStock);
		txtDescripcion.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					tableT.getItems().removeAll();
					tableT.getItems().clear();
					System.out.println(
							"================================================================================");
					System.out.println(" Búsqueda en la base de datos");
					System.out.println(
							"================================================================================");

					String parametro = txtDescripcion.getText().toString().toUpperCase();
					if (parametro.length() == 0) {
						String srtError = "Debe poner algo para buscar...";
						alertaGeneral(srtError);
					} else
						consultarProducto(parametro); // OJO OBTENER EL ID
														// INGRESADO
					segunda = true;

				}
			}
		});
		pruebaBordes.setCenter(tableT);
		SplitPane splitConsulta = new SplitPane();
		splitConsulta.setOrientation(Orientation.VERTICAL);
		splitConsulta.setPrefSize(200, 200);
		splitConsulta.getItems().add(panelConsulta);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(pruebaBordes, 400, 400);
		escenaConsulta.setFill(null);
		Stage VentanaConsultas;
		VentanaConsultas = new Stage();
		VentanaConsultas.setTitle("Consultas");
		VentanaConsultas.setScene(escenaConsulta);
		VentanaConsultas.show();

	}

	@SuppressWarnings("unchecked")
	public void panelCompras() {
		Label lblFecha = new Label("Fecha: ");
		TextField txtFecha = new TextField();
		Label lblProducto = new Label("Producto: ");
		TextField txtProd = new TextField();
		txtProd.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					System.out.println(" * Presion de enter en el textfield");
				}
			}
		});
		Label lblDescripción = new Label("Descripción: ");
		TextField txtDesc = new TextField();
		Label lblCantidad = new Label("Cantidad: ");
		TextField txtcant = new TextField();
		Label lblPrecio = new Label("Precio: ");
		TextField txtPrecio = new TextField();
		Label lblSubtotal = new Label("Subtotal: ");
		TextField txtSubtotal = new TextField();
		Label lblImpuesto = new Label("IVA: ");
		TextField txtImp = new TextField();
		txtImp.setText("12%");
		txtImp.setEditable(false);
		Label lblTotal = new Label("Total: ");
		TextField txtTotal = new TextField();
		btnAgregar = new Button("Agregar");
		// System.out.println(" - "+txtProd.getText());
		int idProd = 0;
		try {
			txtProd.getText();
			// idProd = txtProd.getText().valueOf(idProd);
			String.valueOf(idProd);
			System.out.println(idProd);
		} catch (Exception e) {
			System.err.println("Error");
		}
		btnAgregar.setOnMouseClicked(
				event -> agregarProducto(idProd, txtcant.getText(), txtPrecio.getText(), txtTotal.getText()));
		Button btnExit = new Button("Salir");
		/*
		 * btnExit.setOnMouseClicked(event -> VentanaCompras.close());
		 */

		SplitPane splitPane1 = new SplitPane();
		splitPane1.setOrientation(Orientation.VERTICAL);
		splitPane1.setPrefSize(200, 200);
		GridPane panelCompras = new GridPane();
		panelCompras.setVgap(5);
		panelCompras.setHgap(5);
		panelCompras.setAlignment(Pos.TOP_CENTER);
		Text scenetitle = new Text("Ingreso de producto");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		panelCompras.add(scenetitle, 0, 0, 2, 1);
		panelCompras.add(lblFecha, 0, 1);
		panelCompras.add(txtFecha, 1, 1);
		panelCompras.add(lblProducto, 0, 2);
		panelCompras.add(txtProd, 1, 2);
		panelCompras.add(lblDescripción, 0, 3);
		panelCompras.add(txtDesc, 1, 3);
		panelCompras.add(lblCantidad, 0, 4);
		panelCompras.add(txtcant, 1, 4);
		panelCompras.add(lblPrecio, 0, 5);
		panelCompras.add(txtPrecio, 1, 5);
		panelCompras.add(lblSubtotal, 0, 6);
		panelCompras.add(txtSubtotal, 1, 6);
		panelCompras.add(lblImpuesto, 0, 7);
		panelCompras.add(txtImp, 1, 7);
		panelCompras.add(lblTotal, 0, 8);
		panelCompras.add(txtTotal, 1, 8);
		panelCompras.add(btnAgregar, 0, 9);
		panelCompras.add(btnExit, 1, 9);
		splitPane1.getItems().add(panelCompras);
		tableId.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
		tableNomp.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
		tableDesc.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		tableStock.setCellValueFactory(new PropertyValueFactory<>("Precio"));
		// table.setMinWidth(400);
		// tableId= new TableColumn<>("Id producto");
		tableId.setMinWidth(20);
		// tableNomp = new TableColumn<>("Nombre Producto");
		tableNomp.setMinWidth(150);
		// tableDesc = new TableColumn<>("Descripción");
		tableDesc.setMinWidth(100);
		// tableStock = new TableColumn<>("Stock");
		tableStock.setMinWidth(30);
		if (!primera)
			table.getColumns().addAll(tableId, tableNomp, tableDesc, tableStock);
		SplitPane splitProductos = null;
		splitProductos = new SplitPane();
		splitProductos.setOrientation(Orientation.VERTICAL);
		splitProductos.setPrefSize(200, 200);
		splitProductos.getItems().addAll(splitPane1, table);
		splitProductos.setStyle("-fx-background-color: #336699;");
		Scene escena = null;
		escena = new Scene(splitProductos, 400, 400);
		escena.setFill(null);
		Stage VentanaCompras;
		VentanaCompras = new Stage();
		VentanaCompras.setTitle("Compras");
		VentanaCompras.setScene(escena);
		VentanaCompras.show();
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Borrando todos los registros de la tabla...");
				System.out.println("=======================================================" + table.getItems().size());
				table.getItems().removeAll();
				table.getItems().clear();
				table.isDisable();
				primera = true;
				System.out.println("=======================================================" + table.getItems().size());
				VentanaCompras.toBack();
				VentanaCompras.close();
			}
		});
	}

	public void agregarProducto(int idProducto, String cantidad, String precio, String total) {
		System.out.println("==================================================");
		System.out.println("Agregando a la lista...");
		System.out.println("==================================================");
		// productosDTO producto= new
		// productosDTO(idProducto,cantidad,precio,total);
		// table.getItems().add(producto);
	}

	public void consultarCliente(String strIdentificacion) {
		try {
			System.out.println("==================================================");
			System.out.println("Consultando a la lista...");
			System.out.println("==================================================");

			List<ClientesDTO> lista = new ClientesBO().consultaCliente(strIdentificacion);
			System.out.println("que trae" + lista.size() + strIdentificacion);
			if (lista != null && !lista.isEmpty()) {
				for (ClientesDTO obj : lista) {
					if (obj != null) {
						System.out.println(obj.getDireccion());
					}
				}
			} else {
				// MOSTAR MENSAJE POR PANTALLA
				System.out.println("NO HAY DATOS");
			}
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

	}

	public void consultarProducto(String strCodigo) {
		try {
			System.out.println("==================================================");
			System.out.println("Consultando a la lista...");
			System.out.println("==================================================");

			List<ProductosDTO> lista = new ProductosBO().listarProductosXCodigo(strCodigo);
			if (lista != null && !lista.isEmpty()) {
				for (ProductosDTO obj : lista) {
					if (obj != null) {
						tableT.getItems().add(obj);
					}
				}
			} else {
				// MOSTAR MENSAJE POR PANTALLA
				System.out.println("NO HAY DATOS");
			}
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnLogin) {
			System.out.println("==================================================");
			System.out.println("Logueandose...");
			System.out.println("==================================================");
			panelPrincipal();

		} else if (event.getSource() == btnExit) {
			System.out.println("==================================================");
			System.out.println("Saliendo...");
			System.out.println("==================================================");
			System.exit(0);

		} else if (event.getSource() == facturacionMenuItem) {
			System.out.println("==================================================");
			System.out.println("Facturacion...");
			System.out.println("==================================================");
			facturacion fact = new facturacion();
			facturaGenera a = new facturaGenera();
			a.formularioFactura(VentanaT);
			// panelFactura();
			// fact.formularioFactura();
			boolFact = true;

		} else if (event.getSource() == bodegaMenuItem) {
			System.out.println("==================================================");
			System.out.println("Compras...");
			System.out.println("==================================================");
			panelCompras();

		} else if (event.getSource() == exMenuItem) {
			System.out.println("==================================================");
			System.out.println("Saliendo...");
			System.out.println("==================================================");
			System.exit(0);

		} else if (event.getSource() == ingresoProductoMenuItem) {
			System.out.println("==================================================");
			System.out.println("Ingreso de producto.");
			System.out.println("==================================================");
			productosPrincipal ingreso = new productosPrincipal();
			ingreso.ingresoProductos(VentanaT);

		}  else if (event.getSource() == reporteProductoMenuItem) {
			System.out.println("==================================================");
			System.out.println("Reporte de producto.");
			System.out.println("==================================================");
			// panelConsulta();
			ProductoReporte consulta = new ProductoReporte();
			consulta.vistaProductoReporte(VentanaT);

		} else if (event.getSource() == ingresoClientesMenuItem) {
			System.out.println("==================================================");
			System.out.println("Ingreso de Clientes.");
			System.out.println("==================================================");
			clientesPrincipal clientes = new clientesPrincipal();
			clientes.ingresoClientes(VentanaT);

		}  else if (event.getSource() == facturasMenuItem) {
			System.out.println("==================================================");
			System.out.println("Creacion de facturas.");
			System.out.println("==================================================");
			facturacion factura = new facturacion();
			factura.formularioFactura(VentanaT);

		} else if (event.getSource() == factConsulta) {
			System.out.println("==================================================");
			System.out.println("Consulta facturas.");
			System.out.println("==================================================");
			consultaFacturas objConsultaFactura = new consultaFacturas();
			objConsultaFactura.consultaFacturas(VentanaT);
			
		} else if (event.getSource() == factReporteGeneral) {
			System.out.println("==================================================");
			System.out.println("Reporte de Facturas General.");
			System.out.println("==================================================");
			factReporteGeneral objfactReporteGeneral = new factReporteGeneral();
			objfactReporteGeneral.ventanaReporte(VentanaT);
			
		} else if (event.getSource() == cargaUserMenuItem) {
			System.out.println("==================================================");
			System.out.println("carga Usuarios.");
			System.out.println("==================================================");
			UsuariosPrincipal usuario = new UsuariosPrincipal();
			usuario.ingresoUsuarios(VentanaT);

		} else if (event.getSource() == pruebasCombos) {
			System.out.println("==================================================");
			System.out.println("COmbos");
			System.out.println("==================================================");
			// panelConsulta();
			// modificarProducto addProductos = new modificarProducto();
			// addProductos.panelProductos();
			productosPrincipal add = new productosPrincipal();
			add.consultaProductoCliente();

		}
		else if (event.getSource() == provMenuItem) {
			System.out.println("==================================================");
			System.out.println("Menu de proveedores.");
			System.out.println("==================================================");
			proveedoresPrincipal proveedores = new proveedoresPrincipal();
			proveedores.ingresoProveedores(VentanaT);

		} 
		else if (event.getSource() == CompraProductoMenuItem)
		{
			System.out.println("==================================================");
			System.out.println("Menu de compras de productos.");
			System.out.println("==================================================");
			comprasPrincipal compras = new comprasPrincipal();
			compras.comprasPrin(VentanaT);
			

		} 
		

	}

	public static void main(String[] args) {
		launch(args);
	}

}

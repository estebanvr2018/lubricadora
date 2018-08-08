package application.vistas.productos;

import java.sql.SQLException;
import java.util.List;

import application.Principal;
import application.BO.ClientesBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.extras.botones;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class clientesPrincipal implements EventHandler<ActionEvent> {

	public TextField txtIdentificacion;
	public Button btnBuscar, btnExit, btnAdd, btnUpdate, btnClear,btnClearT;
	public Stage ventanaActual;

	ClientesDTO quitarRegistro = new ClientesDTO();

	public TableView<ClientesDTO> tableClientes = new TableView();
	public TableColumn<ClientesDTO, String> Ced = new TableColumn<>("Número");
	public TableColumn<ClientesDTO, String> tipoDoc = new TableColumn<>("Tipo Doc.");
	public TableColumn<ClientesDTO, String> Nombre = new TableColumn<>("Nombre");
	public TableColumn<ClientesDTO, String> Apellido = new TableColumn<>("Apellido");
	public TableColumn<ClientesDTO, String> Direccion = new TableColumn<>("Dirección");
	public TableColumn<ClientesDTO, String> Telefono = new TableColumn<>("Teléfono");
	public TableColumn<ClientesDTO, String> Correo = new TableColumn<>("Correo");
	public List<ClientesDTO> clientes = null;

	public void ingresoClientes(Stage ventanaIngreso) {
		// cargaComboTipo();
		ventanaActual = ventanaIngreso;
		
		Text scenetitle = new Text("Clientes");
		scenetitle.setFill(Color.WHITE);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(320);
		scenetitle.setY(40);
		
		
		Label lblProd = new Label("Cédula o RUC");
		lblProd.setLayoutX(130);
		lblProd.setLayoutY(60);
		txtIdentificacion = new TextField();
		txtIdentificacion.setLayoutX(250);
		txtIdentificacion.setLayoutY(55);
		txtIdentificacion.setPrefSize(150, 25);
		txtIdentificacion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,10}?") && !newValue.matches("\\d{0,13}?")) {
					txtIdentificacion.setText(oldValue);
				}

			}
		});
		/*
		 * Image imgCarga = new Image("application/1.jpg"); ImageView imgView =
		 * new ImageView(imgCarga);
		 */
		botones b = new botones();
		btnBuscar = new Button("Buscar");
		btnBuscar.setGraphic(b.botonBuscar());
		btnBuscar.setLayoutX(420);
		btnBuscar.setLayoutY(55);
		btnBuscar.setPrefSize(150, 20);
		btnBuscar.setOnAction(this);

		btnClearT= new Button(); 
		btnClearT.setContentDisplay(ContentDisplay.BOTTOM);
		btnClearT.setLayoutX(650);
		btnClearT.setLayoutY(50);
		btnClearT.setPrefSize(150, 40);
		btnClearT.setVisible(false);
		
		AnchorPane datosBusqueda = new AnchorPane();
		datosBusqueda.getChildren().addAll(scenetitle,lblProd,txtIdentificacion,btnBuscar, btnClearT// tableProductos,								
		);
		datosBusqueda.setBorder(new Border(
				new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		datosBusqueda.setPadding(new Insets(5));
		datosBusqueda.setTranslateX(35);
		datosBusqueda.setTranslateY(40);
		datosBusqueda.setTranslateZ(20);
		datosBusqueda.setMaxSize(650, 200);
		
		btnAdd = new Button("Agregar");
		btnAdd.setGraphic(b.botonAgregar());
		btnAdd.setLayoutX(200);
		btnAdd.setLayoutY(520);
		btnAdd.setPrefSize(150, 35);
		btnAdd.setOnAction(this);
		btnAdd.setDisable(false);
		btnAdd.setContentDisplay(ContentDisplay.BOTTOM
				);
		

		btnUpdate = new Button("Editar");
		btnUpdate.setGraphic(b.botonActualizar());
		btnUpdate.setLayoutX(400);
		btnUpdate.setLayoutY(520);
		btnUpdate.setPrefSize(150, 35);
		btnUpdate.setOnAction(this);
		btnUpdate.setDisable(true);
		btnUpdate.setContentDisplay(ContentDisplay.BOTTOM);

		btnClear = new Button("Limpiar");
		btnClear.setGraphic(b.botonLimpiarJPG());
		btnClear.setLayoutX(600);
		btnClear.setLayoutY(520);
		btnClear.setPrefSize(150, 35);
		btnClear.setOnAction(this);
		btnClear.setDisable(false);
		btnClear.setContentDisplay(ContentDisplay.BOTTOM);

		btnExit = new Button("Regresar");
		btnExit.setGraphic(b.botonRegresar());
		btnExit.setLayoutX(900);
		btnExit.setLayoutY(570);
		btnExit.setPrefSize(160, 35);
		btnExit.setOnAction(this);

		/**/
		Ced.setCellValueFactory(new PropertyValueFactory<>("IdIdentificacion"));
		tipoDoc.setCellValueFactory(new PropertyValueFactory<>("TipoIdentificacion"));
		Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombres"));
		Apellido.setCellValueFactory(new PropertyValueFactory<>("PrimerApellido"));
		Direccion.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
		Telefono.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
		Correo.setCellValueFactory(new PropertyValueFactory<>("correo"));

		Ced.setMinWidth(50);
		tipoDoc.setMinWidth(30);
		Nombre.setMinWidth(130);
		Apellido.setMinWidth(130);
		Direccion.setMinWidth(170);
		Telefono.setMinWidth(30);
		Correo.setMinWidth(50);

		tableClientes.getColumns().addAll(tipoDoc,Ced, Nombre, Apellido, Direccion, Telefono, Correo);

		tableClientes.setLayoutX(20);
		tableClientes.setLayoutY(50);
		tableClientes.setPrefSize(980, 260);
		/**/
		cargarClientesTabla();
		
		Text sceneTable = new Text("Clientes registrados");
		sceneTable.setFill(Color.WHITE);
		sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		sceneTable.setX(290);
		sceneTable.setY(30);
		
		AnchorPane datosResultantes = new AnchorPane();
		datosResultantes.getChildren().addAll(sceneTable, tableClientes// tableProductos,								
		);
		datosResultantes.setBorder(new Border(
				new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		datosResultantes.setPadding(new Insets(5));
		datosResultantes.setTranslateX(35);
		datosResultantes.setTranslateY(170);
		datosResultantes.setTranslateZ(20);
		datosResultantes.setMaxSize(1000, 300);
		
		
		tableClientes.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				try {
					System.out.println("Doble click");
					quitarRegistro = tableClientes.getSelectionModel().getSelectedItem();
					if (quitarRegistro != null && !quitarRegistro.equals("")) {
						btnUpdate.setDisable(false);
					}
				} catch (Exception exs) {
					System.out.println("Error");
				}
			}
			if (event.getClickCount() == 1) {
				System.out.println("Un solo click");
				quitarRegistro = tableClientes.getSelectionModel().getSelectedItem();
				if (quitarRegistro != null && !quitarRegistro.equals("")) {
					btnUpdate.setDisable(false);
				}
			}
		});
		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();
		bp.setCenter(b.fondoPantalla());
		rootIngreso.getChildren().addAll(bp, datosBusqueda, datosResultantes, btnExit,
				btnAdd, btnUpdate, btnClear);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 1100, 630);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual.setTitle("LmLaren");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.getIcons().add(b.iconoLaren());
		ventanaActual.show();

	}

	public void cargarClientesTabla() {
		try {
			tableClientes.getItems().removeAll();
			tableClientes.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando clientes...");
			System.out.println("================================================================================");
			List<ClientesDTO> lista = null;
			lista = new ClientesBO().cargaTClientes();

			if (lista != null && !lista.isEmpty()) {
				for (ClientesDTO obje : lista) {

					if (obje != null) {
						tableClientes.getItems().add(obje);
						/*
						 * System.out.println(obje.getIdProducto() + " "
						 * +obje.getDescripcion() +obje.getPrecio()); int
						 * cantidad=0; cantidad =
						 * Integer.parseInt(txtCantidad.getText().toString());
						 * ProductosBO llenaCampo = new ProductosBO();
						 * tablaFacturaDet mapeoTabla = new tablaFacturaDet();
						 * mapeoTabla=llenaCampo.valoresTabla(cantidad,
						 * obje.getDescripcion(),obje.getValorUnitario());
						 * tableFacturacion.getItems().add(mapeoTabla);
						 */

					}
				}
				/*
				 * btnUpdate.setDisable(false); } else {
				 * btnAdd.setDisable(true);
				 * 
				 * String srtError=
				 * "Cliente no existe, se prodecerá a registrarlo...";
				 * alertasMensajes alerta = new alertasMensajes();
				 * alerta.alertaGeneral(srtError);
				 */
			}

		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

	public void consultarCliente(String strIdentificacion) {
		try {
			tableClientes.getItems().removeAll();
			tableClientes.getItems().clear();
			System.out.println("==================================================");
			System.out.println("Consultando a la lista...");
			System.out.println("==================================================");
			List<ClientesDTO> lista = null;
			lista = new ClientesBO().consultaCliente(strIdentificacion);

			if (lista != null && !lista.isEmpty()) {
				for (ClientesDTO obje : lista) {
					if (obje != null) {
						tableClientes.getItems().add(obje);
					}
				}
			} else {
				String srtError = "El cliente con la identificación:  " + strIdentificacion + "  no existe";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				btnAdd.setDisable(false);
				btnUpdate.setDisable(true);
			}
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

	public void limpiaPantalla() {
		cargarClientesTabla();
		quitarRegistro = new ClientesDTO();
		btnUpdate.setDisable(true);
		txtIdentificacion.setText("");
		txtIdentificacion.requestFocus();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnBuscar) {
			System.out.println("==================================================");
			System.out.println("Buscando en la base...");
			System.out.println("==================================================");
			String strParametro = null;
			strParametro = txtIdentificacion.getText().toString().trim();
			if (!strParametro.isEmpty()) {
				try {
					consultarCliente(strParametro);
				} catch (Exception e) {
					String srtError = "No se pudo conectar a la base de datos...";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				}
			} else {
				String srtError = "No ha ingresado CED/RUC, no puede continuar";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				cargarClientesTabla();
				btnAdd.setDisable(true);
			}
		} else if (event.getSource() == btnExit) {
			System.out.println("==================================================");
			System.out.println("Redirigiendose al menú principal...");
			System.out.println("==================================================");
			ventanaActual.toBack();
			ventanaActual.close();
			Principal menuInicio = new Principal();
			menuInicio.panelPrincipal();

		} else if (event.getSource() == btnAdd) {
			System.out.println("==================================================");
			System.out.println("Agregar nuevo cliente...");
			System.out.println("==================================================");
			// ventanaActual.close();
			clientesIU insertaCliente = new clientesIU();
			insertaCliente.insertaCliente(txtIdentificacion.getText().toString().trim());

		} else if (event.getSource() == btnUpdate) {
			System.out.println("==================================================");
			System.out.println("Modificar cliente...");
			System.out.println("==================================================");
			clientesIU actualizaCliente = new clientesIU();
			if (quitarRegistro.equals("")) {
				System.out.println("Error");
			} else {
				actualizaCliente.modificaCliente(quitarRegistro);
			}
		} else if (event.getSource() == btnClear) {
			System.out.println("==================================================");
			System.out.println("Limpiar Pantalla...");
			System.out.println("==================================================");
			limpiaPantalla();
		}
	}

}

package application.vistas.productos;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import application.Principal;
import application.BO.ClientesBO;
import application.BO.ProveedorBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.ProveedorDTO;
import application.com.DTOS.productosDescripcionDTO;
import application.extras.botones;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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


public class proveedoresPrincipal   implements EventHandler<ActionEvent>
{
	public TextField txtIdentificacion;
	public Button btnBuscar, btnExit, btnAdd, btnUpdate, btnClear,btnClearT;

	ProveedorDTO quitarRegistro = new ProveedorDTO();

	public TableView<ProveedorDTO> tableProveedor = new TableView();
	public TableColumn<ProveedorDTO, String> Ced = new TableColumn<>("Cod");
	public TableColumn<ProveedorDTO, String> Doc = new TableColumn<>("Doc");
	public TableColumn<ProveedorDTO, String> Nombre = new TableColumn<>("Nombre");
	public TableColumn<ProveedorDTO, String> Descripcion = new TableColumn<>("Descripción");
	public TableColumn<ProveedorDTO, String> Telefono = new TableColumn<>("Teléfono");
	
	public List<ClientesDTO> clientes = null;
	public Stage ventanaActual;
	
	public void ingresoProveedores(Stage ventanaIngreso) {
		ventanaActual = new Stage();

		// Bandera para controlar que ingreso por primera vez
				Label scenetitle = new Label("Proveedor");
				scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
				scenetitle.setLayoutX(190);
				scenetitle.setLayoutY(5);
				scenetitle.setId("texto");
				
				Label lblProd = new Label("Cédula o RUC");
				lblProd.setLayoutX(50);
				lblProd.setLayoutY(60);
				txtIdentificacion = new TextField();
				txtIdentificacion.setLayoutX(150);
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
				btnBuscar.setLayoutX(350);
				btnBuscar.setLayoutY(55);
				btnBuscar.setPrefSize(150, 20);
				btnBuscar.setOnAction(this);

				btnClearT = new Button("");
				btnClearT.setLayoutX(400);
				btnClearT.setLayoutY(55);
				btnClearT.setPrefSize(100, 20);
				btnClearT.setVisible(false);
				
				btnAdd = new Button("Agregar");
				btnAdd.setGraphic(b.botonAgregar());
				btnAdd.setLayoutX(80);
				btnAdd.setLayoutY(280);
				btnAdd.setPrefSize(120, 35);
				btnAdd.setOnAction(this);
				btnAdd.setDisable(false);
				btnAdd.setContentDisplay(ContentDisplay.BOTTOM);
				

				btnUpdate = new Button("Editar");
				btnUpdate.setGraphic(b.botonActualizar());
				btnUpdate.setLayoutX(220);
				btnUpdate.setLayoutY(280);
				btnUpdate.setPrefSize(120, 35);
				btnUpdate.setOnAction(this);
				btnUpdate.setDisable(true);
				btnUpdate.setContentDisplay(ContentDisplay.BOTTOM);

				btnClear = new Button("Limpiar");
				btnClear.setGraphic(b.botonLimpiarJPG());
				btnClear.setLayoutX(360);
				btnClear.setLayoutY(280);
				btnClear.setPrefSize(120, 35);
				btnClear.setOnAction(this);
				btnClear.setDisable(false);
				btnClear.setContentDisplay(ContentDisplay.BOTTOM);

				btnExit = new Button("Regresar");
				btnExit.setGraphic(b.botonRegresar());
				btnExit.setLayoutX(440);
				btnExit.setLayoutY(530);
				btnExit.setPrefSize(150, 35);
				btnExit.setOnAction(this);
				
				AnchorPane datosBusqueda = new AnchorPane();
				datosBusqueda.getChildren().addAll(scenetitle,lblProd,txtIdentificacion,btnBuscar,btnClearT // tableProductos,								
				);
				
				datosBusqueda.setPadding(new Insets(5));
				datosBusqueda.setTranslateX(20);
				datosBusqueda.setTranslateY(20);
				datosBusqueda.setTranslateZ(20);
				datosBusqueda.setMaxSize(650, 300);
				datosBusqueda.setId("colorMarco");
				
				/**/
				Ced.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
				Doc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
				Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
				Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
				Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
				
				Ced.setMinWidth(50);
				Doc.setMinWidth(60);
				Nombre.setMinWidth(100);
				Descripcion.setMinWidth(175);
				Telefono.setMinWidth(50);
				
				tableProveedor.getColumns().addAll(Ced,Doc, Nombre, Descripcion, Telefono);

				tableProveedor.setLayoutX(20);
				tableProveedor.setLayoutY(50);
				tableProveedor.setPrefSize(530, 220);
				/**/
				
				//MEtodo que carga la tabla de proveedores registrados
				//HAce un select a la tabla y me devuelve informacion la cual guardo dentro de
				//CargarClientesTabla();
				cargarClientesTabla();
				
				Label sceneTable = new Label("Proveedores registrados");
				
				sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
				sceneTable.setLayoutX(150);
				sceneTable.setLayoutY(5);
				sceneTable.setId("texto");
				
				AnchorPane datosResultantes = new AnchorPane();
				datosResultantes.getChildren().addAll(sceneTable, tableProveedor, btnAdd,btnUpdate,btnClear// tableProductos,								
				);
				datosResultantes.setPadding(new Insets(5));
				datosResultantes.setTranslateX(20);
				datosResultantes.setTranslateY(150);
				datosResultantes.setTranslateZ(20);
				datosResultantes.setMaxSize(700, 200);
				datosResultantes.setId("colorMarco");
				
				tableProveedor.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2) {
						try {
							System.out.println("Doble click");
							quitarRegistro = tableProveedor.getSelectionModel().getSelectedItem();
							if (quitarRegistro != null && !quitarRegistro.equals("")) {
								btnUpdate.setDisable(false);
							}
						} catch (Exception exs) {
							System.out.println("Error");
						}
					}
					if (event.getClickCount() == 1) {
						System.out.println("Un solo click");
						quitarRegistro = tableProveedor.getSelectionModel().getSelectedItem();
						if (quitarRegistro != null && !quitarRegistro.equals("")) {
							btnUpdate.setDisable(false);
						}
					}
				});
				Group rootIngreso = new Group();

				BorderPane bp = new BorderPane();
				bp.setCenter(b.fondoPantalla());
				rootIngreso.getChildren().addAll(bp, datosBusqueda, datosResultantes, btnExit
						);
				Scene escenaProductos = null;
				escenaProductos = new Scene(rootIngreso, 600, 600);
				escenaProductos.getStylesheets().add("DarkTheme.css");
				ventanaActual.setTitle("LmLaren");
				ventanaActual.setScene(escenaProductos);
				ventanaActual.setResizable(false);
				ventanaActual.getIcons().add(b.iconoLaren());
				ventanaActual.initModality(Modality.APPLICATION_MODAL);
				ventanaActual.show();
				
				
			}

			public void cargarClientesTabla() {
				try {
					tableProveedor.getItems().removeAll();
					tableProveedor.getItems().clear();
					System.out.println("================================================================================");
					System.out.println(" Cargando Proveedores...");
					System.out.println("================================================================================");
					List<ProveedorDTO> lista = null;
					System.out.println("  1 CP");
					//Trae la informacion de base de datos
					lista = new ProveedorBO().cargaTProveedor();
					System.out.println(" Fuera de carga proveedores");
					if (lista != null && !lista.isEmpty()) {
						for (ProveedorDTO obje : lista) {

							if (obje != null) {
								tableProveedor.getItems().add(obje);
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
					tableProveedor.getItems().removeAll();
					tableProveedor.getItems().clear();
					System.out.println("==================================================");
					System.out.println("Consultando a la lista...");
					System.out.println("==================================================");
					List<ProveedorDTO> lista = null;
					lista = new ProveedorBO().consultaProveedor(strIdentificacion); 
							//ClientesBO().consultaCliente(strIdentificacion);

					if (lista != null && !lista.isEmpty()) {
						for (ProveedorDTO obje : lista) {
							if (obje != null) {
								tableProveedor.getItems().add(obje);
							}
						}
					} else {
						String srtError = "El proveedor con la identificación:  " + strIdentificacion + "  no existe";
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
				quitarRegistro = new ProveedorDTO();
				btnUpdate.setDisable(true);
				txtIdentificacion.setText("");
				txtIdentificacion.requestFocus();
			}

			public void handle(ActionEvent event) {
				alertasMensajes alerta = new alertasMensajes();
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
							alerta.alertaGeneral(srtError);
						}
					} else {
						String srtError = "No ha ingresado CED/RUC, no puede continuar";
						
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
					//Principal menuInicio = new Principal();
					//menuInicio.panelPrincipal();

				} else if (event.getSource() == btnAdd) {
					System.out.println("==================================================");
					System.out.println("Agregar nuevo proveedor...");
					System.out.println("==================================================");
					// ventanaActual.close();
					proveedorUI insertaProveedor = new proveedorUI();
					Optional<ButtonType> resInsercion = null;
					try{
					resInsercion = insertaProveedor.insertaProveedor(txtIdentificacion.getText().toString().trim());
					System.out.println(" Que trajo: "+resInsercion.get());
					if (resInsercion.get() == ButtonType.OK){
						limpiaPantalla();
				} else {
				   System.out.println("NOK");
				}}
					catch(Exception e)
					{
						System.out.println("NOK");
					}
				} else if (event.getSource() == btnUpdate) {
					System.out.println("==================================================");
					System.out.println("Modificar PRoveedor...");
					System.out.println("==================================================");
					if (quitarRegistro.equals("")) {
						System.out.println("Error");
					} else {
						proveedorUI modificaProveedor = new proveedorUI();
						Optional<ButtonType> resUpdate = null;
						try{
							resUpdate = modificaProveedor.modificaProveedor(quitarRegistro);;
							
							if (resUpdate.get() == ButtonType.OK){
								limpiaPantalla();
						} else {
						   System.out.println("NOK");
						}}
							catch(Exception e)
							{
								System.out.println("Err");
							}
					}
					
				} else if (event.getSource() == btnClear) {
					System.out.println("==================================================");
					System.out.println("Limpiar Pantalla...");
					System.out.println("==================================================");
					limpiaPantalla();
				}
			}
}

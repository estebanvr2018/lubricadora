package application.vistas.productos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import application.Principal;
import application.BO.ProductosBO;
import application.BO.UsuariosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.UsuariosDTO;
import application.extras.botones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsuariosPrincipal implements EventHandler<ActionEvent> {

	public TextField txtIdentificacion;
	public Button btnBuscar, btnExit, btnAdd, btnUpdate, btnEliminar, btnClear,btnAddT;
	public Stage ventanaActual;

	UsuariosDTO quitarRegistro = new UsuariosDTO();

	public TableView<UsuariosDTO> tableUsuarios = new TableView();
	public TableColumn<UsuariosDTO, String> idUsuario = new TableColumn<>("Id usuario");
	public TableColumn<UsuariosDTO, String> Nombres = new TableColumn<>("Nombres");
	public TableColumn<UsuariosDTO, String> Usuario = new TableColumn<>("Usuario");
	public List<UsuariosDTO> usuarios = null;

	public void ingresoUsuarios(Stage ventanaIngreso) {
		// cargaComboTipo();
		ventanaActual = new Stage();
		Label scenetitle = new Label("Usuarios");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(150);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");
		
		Label lblProd = new Label("Usuario");
		lblProd.setLayoutX(50);
		lblProd.setLayoutY(60);
		txtIdentificacion = new TextField();
		txtIdentificacion.setLayoutX(120);
		txtIdentificacion.setLayoutY(55);
		txtIdentificacion.setPrefSize(150, 25);
		/*
		 * Image imgCarga = new Image("application/1.jpg"); ImageView imgView =
		 * new ImageView(imgCarga);
		 */
		botones b = new botones();
		btnBuscar = new Button("Buscar");
		btnBuscar.setGraphic(b.botonBuscar());
		btnBuscar.setLayoutX(285);
		btnBuscar.setLayoutY(55);
		btnBuscar.setOnAction(this);
		
		AnchorPane datosResultantes = new AnchorPane();
		datosResultantes.getChildren().addAll(scenetitle, lblProd, txtIdentificacion, btnBuscar// tableProductos,								
		);
		datosResultantes.setPadding(new Insets(15));
		datosResultantes.setTranslateX(20);
		datosResultantes.setTranslateY(10);
		datosResultantes.setTranslateZ(20);
		datosResultantes.setMaxSize(350, 150);
		datosResultantes.setId("colorMarco");
		
		btnAdd = new Button("Agregar");
		btnAdd.setGraphic(b.botonAgregar());
		btnAdd.setLayoutX(310);
		btnAdd.setLayoutY(40);
		btnAdd.setPrefSize(150, 35);
		btnAdd.setOnAction(this);
		btnAdd.setDisable(false);
		
		btnAddT = new Button("Agregar");
		btnAddT.setGraphic(b.botonAgregar());
		btnAddT.setLayoutX(310);
		btnAddT.setLayoutY(20);
		btnAddT.setPrefSize(80, 35);
		btnAddT.setOnAction(this);
		btnAddT.setVisible(false);

		btnUpdate = new Button("Editar");
		btnUpdate.setGraphic(b.botonActualizar());
		btnUpdate.setLayoutX(310);
		btnUpdate.setLayoutY(80);
		btnUpdate.setPrefSize(150, 35);
		btnUpdate.setOnAction(this);
		btnUpdate.setDisable(true);

		btnEliminar = new Button("Eliminar");
		btnEliminar.setGraphic(b.botonEliminar());
		btnEliminar.setLayoutX(310);
		btnEliminar.setLayoutY(120);
		btnEliminar.setPrefSize(150, 35);
		btnEliminar.setOnAction(this);
		btnEliminar.setDisable(true);

		btnClear = new Button("Limpiar");
		btnClear.setGraphic(b.botonLimpiarJPG());
		btnClear.setLayoutX(310);
		btnClear.setLayoutY(160);
		btnClear.setPrefSize(150, 35);
		btnClear.setOnAction(this);
		btnClear.setDisable(false);

		btnExit = new Button("Regresar");
		btnExit.setGraphic(b.botonRegresar());
		btnExit.setLayoutX(360);
		btnExit.setLayoutY(400);
		btnExit.setPrefSize(150, 35);
		btnExit.setOnAction(this);

		/**/
		idUsuario.setCellValueFactory(new PropertyValueFactory<>("IdUsuario"));
		Nombres.setCellValueFactory(new PropertyValueFactory<>("Nombres"));
		Usuario.setCellValueFactory(new PropertyValueFactory<>("Usuario"));

		idUsuario.setMinWidth(30);
		Nombres.setMinWidth(80);
		Usuario.setMinWidth(80);

		tableUsuarios.getColumns().addAll(idUsuario, Nombres, Usuario);

		tableUsuarios.setLayoutX(30);
		tableUsuarios.setLayoutY(40);
		tableUsuarios.setPrefSize(265, 150);
		/**/
		cargarUsuariosTabla();
		tableUsuarios.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				try {
					quitarRegistro = tableUsuarios.getSelectionModel().getSelectedItem();
					if (quitarRegistro != null && !quitarRegistro.equals("")) {
						btnUpdate.setDisable(false);
						btnEliminar.setDisable(false);
					}
				} catch (Exception exs) {
					System.out.println("Error");
				}
			}
			if (event.getClickCount() == 1) {
				System.out.println("Un solo click");
				quitarRegistro = tableUsuarios.getSelectionModel().getSelectedItem();
				if (quitarRegistro != null && !quitarRegistro.equals("")) {
					btnUpdate.setDisable(false);
					btnEliminar.setDisable(false);
				}
			}
		});
		
		
		Label sceneTable = new Label("Usuarios registrados");
		sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		sceneTable.setLayoutX(90);
		sceneTable.setLayoutY(5);
		sceneTable.setId("texto");
		
		AnchorPane datosTabla = new AnchorPane();
		datosTabla.getChildren().addAll(sceneTable, tableUsuarios, btnAddT,btnAdd,btnUpdate, btnEliminar, btnClear	// tableProductos,								
		);
		
		datosTabla.setPadding(new Insets(15));
		datosTabla.setTranslateX(20);
		datosTabla.setTranslateY(160);
		datosTabla.setTranslateZ(20);
		datosTabla.setMaxSize(350, 150);
		datosTabla.setId("colorMarco");
		
		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		rootIngreso.getChildren().addAll(bp, datosResultantes, datosTabla, btnExit
				);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 550, 450);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual.setTitle("LmLaren");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.getIcons().add(bot.iconoLaren());
		ventanaActual.initModality(Modality.APPLICATION_MODAL);
		/*dialog.initOwner(parentStage);
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();*/
		ventanaActual.show();
		
	}

	public void recibeParametro(int texto)
	{
		System.out.println(texto);
		System.out.println("=======================================================");
		System.out.println("Resultado..."+texto);
		System.out.println("=======================================================");
		//limpiaPantalla(); 
		cargarUsuariosTabla();
			
	}

	
	public void cargarUsuariosTabla() {
		try {
			tableUsuarios.getItems().removeAll();
			tableUsuarios.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando Usuarios...");
			System.out.println("================================================================================");
			List<UsuariosDTO> lista = null;
			
			
			lista = new UsuariosBO().cargaUsuarios();

			if (lista != null && !lista.isEmpty()) {
				for (UsuariosDTO obje : lista) {

					if (obje != null) {
						tableUsuarios.getItems().add(obje);
					}
				}
				
			}
			refreshTableView(tableUsuarios, Arrays.asList(idUsuario, Nombres, Usuario), lista);
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

	public static <T,U> void refreshTableView(final TableView<T> tableView, final List<TableColumn<T,U>> columns, final List<T> rows) {

	    tableView.getColumns().clear();
	    tableView.getColumns().addAll(columns);

	    ObservableList<T> list = FXCollections.observableArrayList(rows);
	    tableView.setItems(list);
	}
	
	public void consultarUsuario(String strIdentificacion) {
		try {
			tableUsuarios.getItems().removeAll();
			tableUsuarios.getItems().clear();
			System.out.println("==================================================");
			System.out.println("Consultando a la lista...");
			System.out.println("==================================================");
			List<UsuariosDTO> lista = null;
			lista = new UsuariosBO().consultaUsuarioNombre(strIdentificacion);

			if (lista != null && !lista.isEmpty()) {
				for (UsuariosDTO obje : lista) {

					if (obje != null) {
						tableUsuarios.getItems().add(obje);

					}
				}
			} else {
				String srtError = "El cliente con la identificación:  " + strIdentificacion + "  no existe";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				btnAdd.setDisable(false);
				btnUpdate.setDisable(true);
				btnEliminar.setDisable(true);
			}

		} catch (SQLException ex) {
			System.out.println(ex.toString());

		}

	}

	public void eliminaUsuario(int idUsuario) {
		UsuariosBO objEliminar = new UsuariosBO();
		int resInsert = 0;
		try {
			System.out.println(" 1");
			resInsert = objEliminar.eliminaUsuario(idUsuario);
			System.out.println(" 2: " + resInsert);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha borrado el usuario";
				alertas.alertaOK(strMensaje);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void limpiaPantalla() {
		cargarUsuariosTabla();
		quitarRegistro = new UsuariosDTO();
		btnUpdate.setDisable(true);
		btnEliminar.setDisable(true);
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
					consultarUsuario(strParametro);
				} catch (Exception e) {
					String srtError = "No se pudo conectar a la base de datos...";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				}
			} else {
				String srtError = "No ha ingresado usuario, no puede continuar";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				cargarUsuariosTabla();
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
			System.out.println("Agregar nuevo cliente...");
			System.out.println("==================================================");
			// ventanaActual.close();
			usuariosUI insertaUsuario = new usuariosUI();
			Optional<ButtonType> result = insertaUsuario.insertaUsuario(txtIdentificacion.getText().toString().trim());
			System.out.println(" Que trajo: "+result.get());
			if (result.get() == ButtonType.OK){
					limpiaPantalla();
			} else {
			   System.out.println("OK");
			}
			
			limpiaPantalla();

		} else if (event.getSource() == btnUpdate) {
			System.out.println("==================================================");
			System.out.println("Modificar cliente...");
			System.out.println("==================================================");
			usuariosUI actualizaUsuario = new usuariosUI();
			if (quitarRegistro.equals("")) {
				System.out.println("Error");
			} else {
				System.out.println("pass wd: " + quitarRegistro.getPaasword());
				
				usuariosUI insertaUsuario = new usuariosUI();
				Optional<ButtonType> result = insertaUsuario.modificaUsuario(quitarRegistro);
				System.out.println(" Que trajo: "+result.get());
				if (result.get() == ButtonType.OK){
						limpiaPantalla();
				} else {
				   System.out.println("NOK");
				}

			}

		} else if (event.getSource() == btnEliminar) {
			System.out.println("==================================================");
			System.out.println("Eliminar cliente...");
			System.out.println("==================================================");
			if (quitarRegistro.equals("")) {
				System.out.println("Error");
			} else {
				eliminaUsuario(quitarRegistro.getIdUsuario());
				limpiaPantalla();
			}

		} else if (event.getSource() == btnClear) 
		{
			System.out.println("==================================================");
			System.out.println("Limpiar Panatalla...");
			System.out.println("==================================================");
			limpiaPantalla();
			usuariosUI pruebas = new usuariosUI();
			Optional<ButtonType> result = pruebas.pruebasRetorno();
			if (result.get() == ButtonType.OK){
				System.out.println("entro");
				if (quitarRegistro.equals("")) {
					System.out.println("Error");
				} else {
					eliminaUsuario(quitarRegistro.getIdUsuario());
					limpiaPantalla();
				}
			    // ... user chose OK// ... user chose OK
			} else {
			    // ... user chose CANCEL or closed the dialog// ... user chose CANCEL or closed the dialog
			}
		}
	}

}
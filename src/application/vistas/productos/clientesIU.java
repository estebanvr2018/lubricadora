package application.vistas.productos;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import application.BO.ClientesBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.extras.botones;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class clientesIU implements EventHandler<ActionEvent> {
	public TextField txtRuc, txtCliente, txtApellidos, txtDireccion, txtTelefono, txtCorreo, txtFechaI;
	public Button btnAdd, btnCancelar, btnUpdate;

	Stage VentanaConsultas;

	public float totalFacturar = 0.2f;
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Seleccione un producto");;
	public ComboBox<String> comboProductos = new ComboBox<String>(Contenido);

	public void insertaCliente(String identificacion) {
		Text scenetitle = new Text("Datos del cliente");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		// scenetitle.setFont(new Font("Arial",20));

		Label lblRuc = new Label("Cédula/Ruc ");
		lblRuc.setLayoutX(20);
		lblRuc.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(55);
		txtRuc.setPrefSize(160, 35);
		txtRuc.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,10}?") && !newValue.matches("\\d{0,13}?")) {
					txtRuc.setText(oldValue);
				}

			}
		});
		if (identificacion != null && !identificacion.equals("")) {
			txtRuc.setText(identificacion);
			txtRuc.setEditable(false);
		} else {
			txtRuc.setText("");
			txtRuc.setEditable(true);
			txtRuc.requestFocus();
		}

		Label lblFecha = new Label("Fecha");
		lblFecha.setLayoutX(270);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(350);
		txtFecha.setLayoutY(55);
		txtFecha.setPrefSize(160, 35);
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: " + dateFormat.format(date));
		txtFecha.setText(dateFormat.format(date).toString());
		txtFecha.setEditable(false);

		Label lblCliente = new Label("Nombres ");
		lblCliente.setLayoutX(20);
		lblCliente.setLayoutY(100);
		txtCliente = new TextField();
		txtCliente.setLayoutX(100);
		txtCliente.setLayoutY(95);
		txtCliente.setPrefSize(160, 35);
		
		Label lblApellidos = new Label("Apellidos ");
		lblApellidos.setLayoutX(270);
		lblApellidos.setLayoutY(100);
		txtApellidos = new TextField();
		txtApellidos.setLayoutX(350);
		txtApellidos.setLayoutY(95);
		txtApellidos.setPrefSize(160, 35);

		Label lblDireccion = new Label("Dirección ");
		lblDireccion.setLayoutX(20);
		lblDireccion.setLayoutY(140);
		txtDireccion = new TextField();
		txtDireccion.setLayoutX(100);
		txtDireccion.setLayoutY(135);
		txtDireccion.setPrefSize(160, 35);
		
		Label lblTelefono = new Label("Teléfono");
		lblTelefono.setLayoutX(270);
		lblTelefono.setLayoutY(140);
		txtTelefono = new TextField();
		txtTelefono.setLayoutX(350);
		txtTelefono.setLayoutY(135);
		txtTelefono.setPrefSize(160, 35);
		
		Label lblCorreo = new Label("Correo");
		lblCorreo.setLayoutX(20);
		lblCorreo.setLayoutY(180);
		txtCorreo = new TextField();
		txtCorreo.setLayoutX(100);
		txtCorreo.setLayoutY(175);
		txtCorreo.setPrefSize(415, 25);

		Label lblFechaIngreso = new Label("Fecha Ing.");
		lblFechaIngreso.setLayoutX(20);
		lblFechaIngreso.setLayoutY(220);
		txtFechaI = new TextField();
		txtFechaI.setLayoutX(100);
		txtFechaI.setLayoutY(215);
		txtFechaI.setPrefSize(160, 35);

		/*
		 * Date dateI = new Date(); DateFormat dateFormatI = new
		 * SimpleDateFormat("dd/MM/yyyy"); System.out.println("Fecha actual: "
		 * +dateFormatI.format(dateI));
		 * txtFechaI.setText(dateFormatI.format(dateI).toString());
		 * txtFechaI.setEditable(false);
		 */

		/*
		 * Label lblFechaActua = new Label("Fecha");
		 * lblFechaActua.setLayoutX(300); lblFechaActua.setLayoutY(180);
		 * TextField txtFechaAct = new TextField(); txtFechaAct.setLayoutX(380);
		 * txtFechaAct.setLayoutY(175);
		 * 
		 * Date dateU = new Date(); DateFormat dateFormatU = new
		 * SimpleDateFormat("dd/MM/yyyy"); System.out.println("Fecha actual: "
		 * +dateFormatU.format(dateU));
		 * txtFechaAct.setText(dateFormatU.format(dateU).toString());
		 * txtFechaAct.setEditable(false);
		 */
		botones b = new botones();

		btnAdd = new Button("Guardar");
		btnAdd.setLayoutX(110);
		btnAdd.setLayoutY(270);
		btnAdd.setGraphic(b.botonGuardar());
		// btnAdd.setFont(new Font("Arial",15));
		btnAdd.setPrefSize(150, 30);
		btnAdd.setOnAction(this);

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(270);
		btnCancelar.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(150, 30);
		btnCancelar.setOnAction(this);

		Group root = new Group();

		BorderPane bp = new BorderPane();
		bp.setCenter(b.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblDireccion, lblTelefono, lblCorreo, lblFechaIngreso);
		root.getChildren().addAll(scenetitle, lblRuc, txtRuc, lblCliente, txtCliente, lblApellidos, txtApellidos,
				txtDireccion, txtTelefono, txtCorreo, lblFecha, txtFecha, txtFechaI, btnAdd, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 320);
		escenaConsulta.getStylesheets().add("DarkTheme.css");
		// escenaConsulta.setFill(null);
		VentanaConsultas = new Stage();
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Saliendo al panel principal...");
				System.out.println("=======================================================");
				VentanaConsultas.close();
			}

		});
		VentanaConsultas.setTitle("Ficha de datos");
		VentanaConsultas.setScene(escenaConsulta);
		VentanaConsultas.setResizable(false);
		VentanaConsultas.initModality(Modality.APPLICATION_MODAL);
		VentanaConsultas.showAndWait();

	}

	/*** INI modifica cliente ***/
	public void modificaCliente(ClientesDTO objCliente) {
		
		Text scenetitle = new Text("Datos del cliente");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		scenetitle.setFill(Color.WHITE);
		// scenetitle.setFont(new Font("Arial",20));

		Label lblRuc = new Label("CEDULA/RUC: ");
		lblRuc.setLayoutX(20);
		lblRuc.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(55);
		txtRuc.setEditable(false);

		Label lblFecha = new Label("FECHA: ");
		lblFecha.setLayoutX(270);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(350);
		txtFecha.setLayoutY(55);
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: " + dateFormat.format(date));
		txtFecha.setText(dateFormat.format(date).toString());
		txtFecha.setEditable(false);

		Label lblCliente = new Label("NOMBRES: ");
		lblCliente.setLayoutX(20);
		lblCliente.setLayoutY(90);
		txtCliente = new TextField();
		txtCliente.setLayoutX(100);
		txtCliente.setLayoutY(85);
		Label lblApellidos = new Label("APELLIDOS: ");
		lblApellidos.setLayoutX(270);
		lblApellidos.setLayoutY(90);
		txtApellidos = new TextField();
		txtApellidos.setLayoutX(350);
		txtApellidos.setLayoutY(85);

		Label lblDireccion = new Label("DIRECCIÓN: ");
		lblDireccion.setLayoutX(20);
		lblDireccion.setLayoutY(120);
		txtDireccion = new TextField();
		txtDireccion.setLayoutX(100);
		txtDireccion.setLayoutY(115);

		Label lblTelefono = new Label("TELÉFONO: ");
		lblTelefono.setLayoutX(270);
		lblTelefono.setLayoutY(120);
		txtTelefono = new TextField();
		txtTelefono.setLayoutX(350);
		txtTelefono.setLayoutY(115);

		Label lblCorreo = new Label("CORREO: ");
		lblCorreo.setLayoutX(20);
		lblCorreo.setLayoutY(150);
		txtCorreo = new TextField();
		txtCorreo.setLayoutX(100);
		txtCorreo.setLayoutY(145);
		txtCorreo.setPrefSize(400, 25);

		Label lblFechaIngreso = new Label("Fecha Ing.");
		lblFechaIngreso.setLayoutX(20);
		lblFechaIngreso.setLayoutY(180);
		txtFechaI = new TextField();
		txtFechaI.setLayoutX(100);
		txtFechaI.setLayoutY(175);
		txtFechaI.setEditable(false);

		/*** carga campos al formulario ***/
		cargaCamposU(objCliente);
		/*** carga campos al formulario ***/

		botones b = new botones();

		btnUpdate = new Button("Actualizar");
		btnUpdate.setLayoutX(170);
		btnUpdate.setLayoutY(230);
		btnUpdate.setGraphic(b.botonGuardar());
		btnUpdate.setPrefSize(100, 30);
		btnUpdate.setOnAction(this);

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(230);
		btnCancelar.setGraphic(b.botonError());
		btnCancelar.setPrefSize(100, 30);
		btnCancelar.setOnAction(this);

		Group root = new Group();

		Image imgCarga = new Image("application/1.jpg");
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp = new BorderPane();
		bp.setCenter(imgView);
		/**/
		root.getChildren().addAll(bp, lblDireccion, lblTelefono, lblCorreo, lblFechaIngreso);
		root.getChildren().addAll(scenetitle, lblRuc, txtRuc, lblCliente, txtCliente, lblApellidos, txtApellidos,
				txtDireccion, txtTelefono, txtCorreo, lblFecha, txtFecha, txtFechaI, btnUpdate, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
		escenaConsulta.getStylesheets().add("DarkTheme.css");
		VentanaConsultas = new Stage();
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Saliendo...");
				System.out.println("=======================================================");
				VentanaConsultas.close();
			}

		});
		VentanaConsultas.setTitle("Ficha de datos");
		VentanaConsultas.setScene(escenaConsulta);
		VentanaConsultas.setResizable(false);
		VentanaConsultas.initModality(Modality.APPLICATION_MODAL);
		VentanaConsultas.showAndWait();

	}

	public void cargaCampos(ClientesDTO clientes) {
		// txtRuc,txtCliente,txtDireccion,txtApellidos,txtTelefono,txtCorreo;
		// txtRuc,txtCliente,txtApellidos,txtDireccion,txtTelefono,txtCorreo;
		System.out.println("cargaCampos 1");
		if (clientes != null) {
			System.out.println("cargaCampos 2");
			txtRuc.setText(clientes.getIdIdentificacion());
			// txtRuc.setEditable(false);
			txtCliente.setText(clientes.getNombres());
			txtApellidos.setText(clientes.getPrimerApellido() + " " + clientes.getSegundoApellido());
			txtDireccion.setText(clientes.getDireccion());
			txtTelefono.setText(clientes.getTelefono());
			txtCorreo.setText(clientes.getCorreo());

		}
		System.out.println("cargaCampos 3");
	}

	public void cargaCamposU(ClientesDTO clientes) {
		// txtRuc,txtCliente,txtDireccion,txtApellidos,txtTelefono,txtCorreo;
		// txtRuc,txtCliente,txtApellidos,txtDireccion,txtTelefono,txtCorreo;
		if (clientes != null) {

			txtRuc.setText(clientes.getIdIdentificacion());
			/*** ***/
			String fecha = null;
			DateFormat dateFormatU = new SimpleDateFormat("dd/MM/yyyy");
			fecha = dateFormatU.format(clientes.getFechaIngreso());
			/*** ***/
			txtFechaI.setText(fecha);
			txtCliente.setText(clientes.getNombres());
			txtApellidos.setText(clientes.getPrimerApellido() + " " + clientes.getSegundoApellido());
			txtDireccion.setText(clientes.getDireccion());
			txtTelefono.setText(clientes.getTelefono());
			txtCorreo.setText(clientes.getCorreo());

		}
		System.out.println("cargaCampos 3");
	}

	/*** FIN modifica cliente ***/

	public boolean verificaCampos(String strNombres, String strApellidos, String srtDireccion, String strTelefono,
			String strCorreo) {
		boolean blData = false;
		String srtError = "Faltan datos del Cliente, por favor revise: ";
		String srtConcatenaERR = "";
		alertasMensajes alerta = new alertasMensajes();

		if (strNombres.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + " Nombres  ";
		}

		if (strApellidos.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Apellidos  ";
		}
		if (srtDireccion.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Dirección ";
		}
		if (strTelefono.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Teléfono  ";
		}

		if (strCorreo.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Correo  ";
		}

		if (!srtConcatenaERR.isEmpty()) {
			String strEnvio = null;
			strEnvio = srtError + " " + srtConcatenaERR;
			alerta.alertaGeneral(strEnvio);
			blData = true;
		}
		return blData;
	}

	public void insertaClienteBD(String strId, String strNombre, String strApellidos, String strDireccion,
			String strTelefono, String strCorreo) {
		System.out.println("================================================================================");
		System.out.println(" Ingreso de cliente...");
		System.out.println("================================================================================");
		ClientesBO objInsertar = new ClientesBO();
		int resInsert = 0;
		try {
			System.out.println(" 1");
			resInsert = objInsertar.insertaCliente(strId, strNombre, strApellidos, strDireccion, strTelefono,
					strCorreo);
			System.out.println(" 2: " + resInsert);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha insertado el cliente:" + strId;
				alertas.alertaOK(strMensaje);
				VentanaConsultas.toBack();
				VentanaConsultas.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actualizaClienteBD(String strId, String strNombre, String strApellidos, String strDireccion,
			String strTelefono, String strCorreo) {
		System.out.println("================================================================================");
		System.out.println(" update de cliente...");
		System.out.println("================================================================================");
		ClientesBO objInsertar = new ClientesBO();
		int resInsert = 0;
		try {
			System.out.println(" 1");
			resInsert = objInsertar.actualizaCliente(strId, strNombre, strApellidos, strDireccion, strTelefono,
					strCorreo);
			System.out.println(" 2: " + resInsert);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha actualizado el cliente:";
				alertas.alertaOK(strMensaje);
				VentanaConsultas.toBack();
				VentanaConsultas.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*** ***/
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnAdd) {
			System.out.println("==================================================");
			System.out.println(" Crear nuevo cliente...");
			System.out.println("==================================================");
			boolean verifica = false;
			verifica = verificaCampos(txtCliente.getText().toString().trim(), txtApellidos.getText().toString().trim(),
					txtDireccion.getText().toString().trim(), txtTelefono.getText().toString().trim(),
					txtCorreo.getText().toString().trim());
			System.out.println("Verifica: " + verifica);
			if (!verifica) {
				System.out.println("Entro");
				insertaClienteBD(txtRuc.getText().toString().trim(), txtCliente.getText().toString().trim(),
						txtApellidos.getText().toString().trim(), txtDireccion.getText().toString().trim(),
						txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim());
			}
		} else if (event.getSource() == btnCancelar) {
			System.out.println("==================================================");
			System.out.println(" Cancelar Salir...");
			System.out.println("==================================================");
			// ventanaActual.close();

		} else if (event.getSource() == btnUpdate) {
			System.out.println("==================================================");
			System.out.println(" Actualizar registro...");
			System.out.println("==================================================");
			actualizaClienteBD(txtRuc.getText().toString().trim(), txtCliente.getText().toString().trim(),
					txtApellidos.getText().toString().trim(), txtDireccion.getText().toString().trim(),
					txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim());
			// ventanaActual.close();

		}
	}

}

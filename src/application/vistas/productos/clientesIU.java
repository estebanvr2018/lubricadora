package application.vistas.productos;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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
import javafx.scene.control.ButtonType;
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
	public String usuarioGlobal = "";
	
	public float totalFacturar = 0.2f;
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Seleccione un producto");;
	public ComboBox<String> comboProductos = new ComboBox<String>(Contenido);
	
	/**/
	Optional<ButtonType> optionRetorno=null;
	/**/

	public Optional<ButtonType> insertaCliente(String identificacion, String usuario) 
	{
		usuarioGlobal = usuario;
		Label scenetitle = new Label(" - Datos del nuevo cliente -");
		//scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(100);
		scenetitle.setLayoutY(10);
		scenetitle.setId("texto");
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

		botones b = new botones();

		Button btnAdd = new Button("Guardar");
		btnAdd.setLayoutX(110);
		btnAdd.setLayoutY(270);
		btnAdd.setGraphic(b.botonGuardar());
		// btnAdd.setFont(new Font("Arial",15));
		btnAdd.setPrefSize(150, 30);
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				System.out.println("==================================================");
				System.out.println(" Crear nuevo cliente...");
				System.out.println("==================================================");
				boolean verifica = false;
				verifica = verificaCampos(txtCliente.getText().toString().trim(), txtApellidos.getText().toString().trim(),
						txtDireccion.getText().toString().trim(), txtTelefono.getText().toString().trim(),
						txtCorreo.getText().toString().trim());
				System.out.println("Verifica: " + verifica);
				alertasMensajes alertas = new alertasMensajes();
				if (!verifica) 
				{
					ClientesBO objInsertar = new ClientesBO();
					int existeCliente = 0;
					try {
						existeCliente = objInsertar.verificaCliente(txtRuc.getText().toString().trim());
						if ( existeCliente == 0 )
						{	
								int resultadoInsert = insertaClienteBD(txtRuc.getText().toString().trim(), txtCliente.getText().toString().trim(),
										txtApellidos.getText().toString().trim(), txtDireccion.getText().toString().trim(),
										txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim(), usuarioGlobal);
								
								
								if ( resultadoInsert == 1 )
								{
									
									optionRetorno = alertas.opcionConfirmacion("Confirmación", "Se ha guardado el cliente con la identificación " +txtRuc.getText().toString());
									VentanaConsultas.close();
								}	
								else 
								{
									
									String  strMensaje="No se ha guardado el cliente, por favor vuelva a intentarlo";
									alertas.alertaError(strMensaje);
									VentanaConsultas.close();
								}			   
						}
						else 
						{
							String mesj = "Ya existe un cliente registrado con la identificación "+ txtRuc.getText().toString().trim() +" , por favor revise ...";
							 alertas.alertaOK(mesj);
							 VentanaConsultas.close();
						}	
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
							
				}
		
	
		
			}});
		/**/
		

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
		//Scene escenaConsulta = null;
		Scene escenaConsulta = new Scene(root, 510, 320);
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
		VentanaConsultas = new Stage();
		VentanaConsultas.setTitle("Ficha de datos");
		VentanaConsultas.setScene(escenaConsulta);
		VentanaConsultas.setResizable(false);
		VentanaConsultas.getIcons().add(b.iconoLaren());
		VentanaConsultas.initModality(Modality.APPLICATION_MODAL);
		VentanaConsultas.showAndWait();
		return optionRetorno;
	}

	/*** INI modifica cliente 
	 * @return ***/
	public Optional<ButtonType> modificaCliente(ClientesDTO objCliente, String usuario) {
		usuarioGlobal = usuario;
		Label scenetitle = new Label(" - Datos del cliente - ");
		//scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(150);
		scenetitle.setLayoutY(10);
		scenetitle.setId("texto");
		//scenetitle.setFill(Color.WHITE);
		// scenetitle.setFont(new Font("Arial",20));

		Label lblRuc = new Label("Cédula/Ruc");
		lblRuc.setLayoutX(20);
		lblRuc.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(55);
		txtRuc.setPrefSize(150, 25);
		txtRuc.setEditable(false);

		Label lblFecha = new Label("Fecha");
		lblFecha.setLayoutX(270);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(350);
		txtFecha.setLayoutY(55);
		txtFecha.setPrefSize(150, 25);
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: " + dateFormat.format(date));
		txtFecha.setText(dateFormat.format(date).toString());
		txtFecha.setEditable(false);

		Label lblCliente = new Label("Nombres");
		lblCliente.setLayoutX(20);
		lblCliente.setLayoutY(100);
		txtCliente = new TextField();
		txtCliente.setLayoutX(100);
		txtCliente.setLayoutY(95);
		txtCliente.setPrefSize(150, 25);
		Label lblApellidos = new Label("Apellidos");
		lblApellidos.setLayoutX(270);
		lblApellidos.setLayoutY(100);
		txtApellidos = new TextField();
		txtApellidos.setLayoutX(350);
		txtApellidos.setLayoutY(95);
		txtApellidos.setPrefSize(150, 25);

		Label lblDireccion = new Label("Dirección");
		lblDireccion.setLayoutX(20);
		lblDireccion.setLayoutY(140);
		txtDireccion = new TextField();
		txtDireccion.setLayoutX(100);
		txtDireccion.setLayoutY(135);
		txtDireccion.setPrefSize(150, 25);

		Label lblTelefono = new Label("Teléfono");
		lblTelefono.setLayoutX(270);
		lblTelefono.setLayoutY(140);
		txtTelefono = new TextField();
		txtTelefono.setLayoutX(350);
		txtTelefono.setLayoutY(135);
		txtTelefono.setPrefSize(150, 25);

		Label lblCorreo = new Label("Correo");
		lblCorreo.setLayoutX(20);
		lblCorreo.setLayoutY(180);
		txtCorreo = new TextField();
		txtCorreo.setLayoutX(100);
		txtCorreo.setLayoutY(175);
		txtCorreo.setPrefSize(400, 25);

		Label lblFechaIngreso = new Label("Fecha Ing.");
		lblFechaIngreso.setLayoutX(20);
		lblFechaIngreso.setLayoutY(220);
		txtFechaI = new TextField();
		txtFechaI.setLayoutX(100);
		txtFechaI.setLayoutY(215);
		txtFechaI.setEditable(false);
		txtFechaI.setPrefSize(150, 25);

		/*** carga campos al formulario ***/
		cargaCamposU(objCliente);
		/*** carga campos al formulario ***/

		botones b = new botones();

		Button btnUpdate = new Button("Actualizar");
		btnUpdate.setLayoutX(120);
		btnUpdate.setLayoutY(300);
		btnUpdate.setGraphic(b.botonGuardar());
		btnUpdate.setPrefSize(140, 30);
		btnUpdate.setOnAction(this);
		
		/**/
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				System.out.println("==================================================");
				System.out.println(" Actualizar registro...");
				System.out.println("==================================================");
				int resultadoInsert = actualizaClienteBD(txtRuc.getText().toString().trim(), txtCliente.getText().toString().trim(),
						txtApellidos.getText().toString().trim(), txtDireccion.getText().toString().trim(),
						txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim(),usuarioGlobal);
				alertasMensajes alertas = new alertasMensajes();
				if ( resultadoInsert == 1 )
				{
					
					optionRetorno = alertas.opcionConfirmacion("Confirmación", "Se ha actualizado la información el cliente");
					VentanaConsultas.close();
				}	
				else 
				{
					
					String  strMensaje="No se ha actualizado la información del cliente, por favor vuelva a intentarlo";
					alertas.alertaError(strMensaje);
					VentanaConsultas.close();
				   
				}	
			}});
		/**/

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(300);
		btnCancelar.setGraphic(b.botonError());
		btnCancelar.setPrefSize(140, 30);
		btnCancelar.setOnAction(this);

		Group root = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblDireccion, lblTelefono, lblCorreo, lblFechaIngreso);
		root.getChildren().addAll(scenetitle, lblRuc, txtRuc, lblCliente, txtCliente, lblApellidos, txtApellidos,
				txtDireccion, txtTelefono, txtCorreo, lblFecha, txtFecha, txtFechaI, btnUpdate, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 380);
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
		VentanaConsultas.getIcons().add(b.iconoLaren());
		VentanaConsultas.setResizable(false);
		VentanaConsultas.initModality(Modality.APPLICATION_MODAL);
		VentanaConsultas.showAndWait();
		return optionRetorno;
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

	public int insertaClienteBD(String strId, String strNombre, String strApellidos, String strDireccion,
			String strTelefono, String strCorreo, String usuarioGlobal) {
		System.out.println("================================================================================");
		System.out.println(" Ingreso de cliente...");
		System.out.println("================================================================================");
		ClientesBO objInsertar = new ClientesBO();
		int resInsert = 0;
		try 
		{
			resInsert = objInsertar.insertaCliente(strId, strNombre, strApellidos, strDireccion, strTelefono,
					strCorreo, usuarioGlobal);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				/*alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha insertado el cliente:" + strId;
				alertas.alertaOK(strMensaje);
				VentanaConsultas.toBack();
				VentanaConsultas.close();*/
				return resInsert;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resInsert;
	}

	public int actualizaClienteBD(String strId, String strNombre, String strApellidos, String strDireccion,
			String strTelefono, String strCorreo, String usuarioGlobal) {
		System.out.println("================================================================================");
		System.out.println(" update de cliente...");
		System.out.println("================================================================================");
		ClientesBO objInsertar = new ClientesBO();
		int resInsert = 0;
		try 
		{
			resInsert = objInsertar.actualizaCliente(strId, strNombre, strApellidos, strDireccion, strTelefono,
					strCorreo, usuarioGlobal);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				/*alertasMensajes alertas = new alertasMensajes();
				String strMensaje = "Se ha actualizado el cliente:";
				alertas.alertaOK(strMensaje);
				VentanaConsultas.toBack();
				VentanaConsultas.close();*/
				return resInsert;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resInsert;
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
						txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim(),usuarioGlobal);
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
					txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim(),usuarioGlobal);
			// ventanaActual.close();

		}
	}

}

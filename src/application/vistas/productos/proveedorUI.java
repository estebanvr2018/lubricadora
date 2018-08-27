package application.vistas.productos;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import application.BO.ClientesBO;
import application.BO.ProveedorBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.ProveedorDTO;
import application.extras.botones;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class proveedorUI implements EventHandler<ActionEvent>
{
	
	public TextField txtRuc,txtNombres, txtUsuario, txtTelefono, txtPasswordT;
	public Button btnAdd, btnCancelar, btnUpdate;
	public int idUser = 0;
	Stage VentanaConsultas;
	Optional<ButtonType> optionPrincipal=null;
	int retInsercionProveedor=0;
	public String usuarioGlobal = "";
	public Optional<ButtonType> insertaProveedor(String identificacion, String usuario) 
	{
		usuarioGlobal = usuario;
		Label scenetitle = new Label("Datos del nuevo proveedor");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(100);
		scenetitle.setLayoutY(15);
		scenetitle.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));

		Label lblCedula = new Label("Identificación ");
		lblCedula.setLayoutX(20);
		lblCedula.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(105);
		txtRuc.setLayoutY(55);
		txtRuc.setText(identificacion);
		txtRuc.setPrefSize(150, 25);
		
		Label lblNombres = new Label("Nombre ");
		lblNombres.setLayoutX(20);
		lblNombres.setLayoutY(100);
		txtNombres = new TextField();
		txtNombres.setLayoutX(105);
		txtNombres.setLayoutY(95);
		txtNombres.setPrefSize(150, 25);
		//txtNombres.setText(identificacion);

		Label lblFecha = new Label("Fecha ");
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

		Label lblTelefono = new Label("Teléfono ");
		lblTelefono.setLayoutX(270);
		lblTelefono.setLayoutY(100);
		txtTelefono = new TextField();
		txtTelefono.setLayoutX(350);
		txtTelefono.setLayoutY(95);
		txtTelefono.setPrefSize(150, 25);

		Label lblContraseniaT = new Label("Descripción  ");
		lblContraseniaT.setLayoutX(20);
		lblContraseniaT.setLayoutY(140);
		txtPasswordT = new TextField();
		txtPasswordT.setLayoutX(105);
		txtPasswordT.setLayoutY(135);
		txtPasswordT.setPrefSize(400, 30);
		
		botones b = new botones();

		Button btnAdd = new Button("Agregar");
		btnAdd.setLayoutX(90);
		btnAdd.setLayoutY(200);
		btnAdd.setGraphic(b.botonAgregar());
		// btnAdd.setFont(new Font("Arial",15));
		btnAdd.setPrefSize(140, 30);
		 
		//Variable de retorno 
		
		
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Agregando el nuevo proveedor...");
				System.out.println("=======================================================");
				System.out.println("==================================================");
				System.out.println(" Crear nuevo Proveedor...");
				System.out.println("==================================================");
				boolean verifica = false;
				alertasMensajes alerta = new alertasMensajes();
				verifica = verificaCampos(txtRuc.getText().toString(),txtNombres.getText().toString(),
						txtTelefono.getText().toString(), txtPasswordT.getText().toString());
				if (!verifica) 
				{
					ProveedorBO objInsertar = new ProveedorBO();
					int verificaExistencia = 0;
					try {
						verificaExistencia = objInsertar.existeProveedor(txtRuc.getText().toString());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (verificaExistencia == 0 )
					{	
						System.out.println("Entro");
						retInsercionProveedor = insertaProveedorBD(txtRuc.getText().toString().trim(),txtNombres.getText().toString().trim(),
								txtTelefono.getText().toString().trim(), txtPasswordT.getText().toString().trim(), usuarioGlobal);
						if ( retInsercionProveedor == 1 )
						{
							
							optionPrincipal = alerta.opcionConfirmacion("Confirmación", "Se ha registrado el proveedor "+txtNombres.getText().toString());
							VentanaConsultas.close();	
						}
						else
						{
							String mesj = "No se pudo registrar el proveedor, por favor vuelva a intentarlo";
							 alerta.alertaError(mesj);
							 VentanaConsultas.close();	
						}
					}
					else 
					{
						String mesj = "El proveedor con la identificación "+ txtRuc.getText().toString().trim() +" ya está registrado, por favor revise ...";
						 alerta.alertaOK(mesj);
						 VentanaConsultas.close();	
					}	
				}
			}

		});

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(200);
		btnCancelar.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(140, 30);
		btnCancelar.setOnAction(this);

		Group root = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblContraseniaT);
		root.getChildren().addAll(scenetitle, lblCedula,txtRuc, lblNombres, txtNombres, lblTelefono, txtTelefono,
				txtPasswordT, lblFecha, txtFecha, btnAdd, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
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
		VentanaConsultas.getIcons().add(bot.iconoLaren());
		VentanaConsultas.showAndWait();
		return optionPrincipal;
	}
	
	/*** Modifica proveedor 
	 * @return ***/
	public Optional<ButtonType> modificaProveedor(ProveedorDTO objProv, String usuario) 
	{
		usuarioGlobal = usuario;
		Label scenetitle = new Label("Datos del proveedor");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(140);
		scenetitle.setLayoutY(15);
		scenetitle.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));

		Label lblCedula = new Label("Identif");
		lblCedula.setLayoutX(20);
		lblCedula.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(55);
		txtRuc.setPrefSize(150, 25);
		//txtRuc.setText(identificacion);

		
		Label lblNombres = new Label("Nombre ");
		lblNombres.setLayoutX(20);
		lblNombres.setLayoutY(100);
		txtNombres = new TextField();
		txtNombres.setLayoutX(100);
		txtNombres.setLayoutY(95);
		txtNombres.setPrefSize(150, 25);
		//txtNombres.setText(identificacion);

		Label lblFecha = new Label("Fecha ");
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

		Label lblTelefono = new Label("Teléfono ");
		lblTelefono.setLayoutX(270);
		lblTelefono.setLayoutY(100);
		txtTelefono = new TextField();
		txtTelefono.setLayoutX(350);
		txtTelefono.setLayoutY(95);
		txtTelefono.setPrefSize(150, 25);

		Label lblContraseniaT = new Label("Descripción  ");
		lblContraseniaT.setLayoutX(20);
		lblContraseniaT.setLayoutY(140);
		txtPasswordT = new TextField();
		txtPasswordT.setLayoutX(100);
		txtPasswordT.setLayoutY(135);
		txtPasswordT.setPrefSize(400, 30);
		
		botones b = new botones();

		Button btnUpdate = new Button("Actualizar");
		btnUpdate.setLayoutX(110);
		btnUpdate.setLayoutY(200);
		btnUpdate.setGraphic(b.botonGuardar());
		btnUpdate.setPrefSize(150, 30);
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				alertasMensajes alerta = new alertasMensajes();
				System.out.println("=======================================================");
				System.out.println("Saliendo al panel principal...");
				System.out.println("=======================================================");
				System.out.println("==================================================");
				System.out.println(" Actualizar registro...");
				System.out.println("==================================================");
				retInsercionProveedor = actualizaProveedorBD(txtRuc.getText().toString().trim(),txtNombres.getText().toString().trim(),
						txtTelefono.getText().toString().trim(), txtPasswordT.getText().toString().trim(),usuarioGlobal );
				if ( retInsercionProveedor == 1 )
				{
					
					optionPrincipal = alerta.opcionConfirmacion("Confirmación", "Se ha actualizado el proveedor "+txtNombres.getText().toString());
					VentanaConsultas.close();	
				}
				else
				{
					String mesj = "No se pudo actualizar el proveedor, por favor vuelva a intentarlo";
					 alerta.alertaError(mesj);
					 optionPrincipal=null;
					 VentanaConsultas.close();	
				}
				
			}

		});

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(300);
		btnCancelar.setLayoutY(200);
		btnCancelar.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(140, 30);
		btnCancelar.setOnAction(this);
		cargaCamposU(objProv);
		Group root = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblContraseniaT);
		root.getChildren().addAll(scenetitle, lblCedula,txtRuc, lblNombres, txtNombres, lblTelefono, txtTelefono,
				txtPasswordT, lblFecha, txtFecha, btnUpdate, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
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
		VentanaConsultas.getIcons().add(bot.iconoLaren());
		VentanaConsultas.showAndWait();
		return optionPrincipal;
	}
	/*** FIn modifica proveedor***/
	
	/*** INI Carga campos del proveedor ***/
	public void cargaCamposU(ProveedorDTO proveedor) {
		// txtRuc,txtCliente,txtDireccion,txtApellidos,txtTelefono,txtCorreo;
		// txtRuc,txtCliente,txtApellidos,txtDireccion,txtTelefono,txtCorreo;
		if (proveedor != null) {

			txtRuc.setText(proveedor.getRuc());
			/*** ***/
			String fecha = null;
			DateFormat dateFormatU = new SimpleDateFormat("dd/MM/yyyy");
			//fecha = dateFormatU.format(proveedor.getFechaIngreso());
			/*** ***/
			
			txtNombres.setText(proveedor.getNombre());
			txtTelefono.setText(proveedor.getTelefono());
			txtPasswordT.setText(proveedor.getDescripcion());

		}
		System.out.println("cargaCampos 3");
	}
	/*** FIN carga campos del proveedor***/
	
	/*** Ingreso y actualizacion de proveedores ***/
	public int insertaProveedorBD(String strIdentificacion,String strNombres, String strTelefono,String srtDescripcion, String usuario) {
		System.out.println("================================================================================");
		System.out.println(" Ingreso de cliente...");
		System.out.println("================================================================================");
		ProveedorBO objInsertar = new ProveedorBO();
		int resInsert = 0;
		try {
			resInsert = objInsertar.insertaProveedor(strIdentificacion, strNombres, srtDescripcion, strTelefono, usuario);
			if (resInsert == 1) {
				//alertasMensajes alertas = new alertasMensajes();
				//String strMensaje = "Se ha insertado el proveedor:" + strIdentificacion;
				//alertas.alertaOK(strMensaje);
				//VentanaConsultas.toBack();
				//VentanaConsultas.close();
				return resInsert;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resInsert;
	}

	public int actualizaProveedorBD(String strIdentificacion,String strNombres, String strTelefono,String srtDescripcion, String usuarioGlobal )
	{
		System.out.println("================================================================================");
		System.out.println(" update de Proveedor...");
		System.out.println("================================================================================");
		ProveedorBO objInsertar = new ProveedorBO();
		int resInsert = 0;
		try {
			resInsert = objInsertar.actualizaProveedor(strIdentificacion, strNombres, strTelefono, srtDescripcion, usuarioGlobal) ;
			System.out.println(" 2: " + resInsert);
			if (resInsert == 1) {
				//alertasMensajes alertas = new alertasMensajes();
				//String strMensaje = "Se ha actualizado el proveedor:";
				//alertas.alertaOK(strMensaje);
				//VentanaConsultas.toBack();
				//VentanaConsultas.close();
				return resInsert;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resInsert;
	}

	/*** ***/
	/*** Fin ingreso y actualizacion de proveedores***/
	
	
	public boolean verificaCampos(String strIdentificacion,String strNombres, String strTelefono,String srtDescripcion
			) {
		boolean blData = false;
		String srtError = "Faltan datos del Cliente, por favor revise: ";
		String srtConcatenaERR = "";
		alertasMensajes alerta = new alertasMensajes();

		if (strIdentificacion.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + " Identificacion  ";
		}

		if (strNombres.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Nombre  ";
		}
		if (strTelefono.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Teléfono ";
		}
		if (srtDescripcion.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Descripción  ";
		}

		
		if (!srtConcatenaERR.isEmpty()) {
			String strEnvio = null;
			strEnvio = srtError + " " + srtConcatenaERR;
			alerta.alertaGeneral(strEnvio);
			blData = true;
		}
		return blData;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnAdd) {
			System.out.println("==================================================");
			System.out.println(" Crear nuevo Proveedor...");
			System.out.println("==================================================");
			boolean verifica = false;
			verifica = verificaCampos(txtRuc.getText().toString(),txtNombres.getText().toString(),
					txtTelefono.getText().toString(), txtPasswordT.getText().toString());
			if (!verifica) 
			{
				System.out.println("Entro");
				insertaProveedorBD(txtRuc.getText().toString().trim(),txtNombres.getText().toString().trim(),
						txtTelefono.getText().toString().trim(), txtPasswordT.getText().toString().trim(),usuarioGlobal);
				/*insertaClienteBD(txtRuc.getText().toString().trim(), txtCliente.getText().toString().trim(),
						txtApellidos.getText().toString().trim(), txtDireccion.getText().toString().trim(),
						txtTelefono.getText().toString().trim(), txtCorreo.getText().toString().trim());*/
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
			actualizaProveedorBD(txtRuc.getText().toString().trim(),txtNombres.getText().toString().trim(),
					txtTelefono.getText().toString().trim(), txtPasswordT.getText().toString().trim(),usuarioGlobal);
		}
	}

}

package application.vistas.productos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import application.BO.UsuariosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.UsuariosDTO;
import application.extras.botones;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class usuariosUI implements EventHandler<ActionEvent> {
	public TextField txtNombres, txtUsuario;
	public PasswordField txtPassword, txtPasswordT;
	public Button btnAdd, btnCancelar, btnUpdate;
	public int idUser = 0;
	Stage VentanaConsultas;

	/*Modificacion*/
	Optional<ButtonType> option=null;
	Optional<ButtonType> optionPrincipal=null;
	Stage dialogStage;
	/*Modificacion*/
	
	public float totalFacturar = 0.2f;
	public ObservableList<String> Contenido = FXCollections.observableArrayList("Seleccione un producto");;
	public ComboBox<String> comboProductos = new ComboBox<String>(Contenido);

	public Optional<ButtonType> insertaUsuario(String identificacion) {
		Label scenetitle = new Label("Datos del nuevo usuario");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(130);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));
		
		Label lblNombres = new Label("Nombres ");
		lblNombres.setLayoutX(20);
		lblNombres.setLayoutY(60);
		txtNombres = new TextField();
		txtNombres.setLayoutX(100);
		txtNombres.setLayoutY(55);
		txtNombres.setText(identificacion);
		txtNombres.setPrefSize(150, 25);
		
		Label lblFecha = new Label("FECHA ");
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

		Label lblUser = new Label("Usuario ");
		lblUser.setLayoutX(20);
		lblUser.setLayoutY(100);
		txtUsuario = new TextField();
		txtUsuario.setLayoutX(100);
		txtUsuario.setLayoutY(95);
		txtUsuario.setPrefSize(150, 25);
		Label lblContrasenia = new Label("Contraseña ");
		lblContrasenia.setLayoutX(270);
		lblContrasenia.setLayoutY(100);
		txtPassword = new PasswordField();
		txtPassword.setLayoutX(350);
		txtPassword.setLayoutY(95);
		txtPassword.setPrefSize(150, 25);

		Label lblContraseniaT = new Label("Repetir  ");
		lblContraseniaT.setLayoutX(270);
		lblContraseniaT.setLayoutY(140);
		txtPasswordT = new PasswordField();
		txtPasswordT.setLayoutX(350);
		txtPasswordT.setLayoutY(135);
		txtPasswordT.setPrefSize(150, 25);

		botones b = new botones();

		Button btnAdd = new Button("Agregar");
		btnAdd.setLayoutX(120);
		btnAdd.setLayoutY(185);
		btnAdd.setGraphic(b.botonAgregar());
		// btnAdd.setFont(new Font("Arial",15));
		btnAdd.setPrefSize(150, 30);
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

		System.out.println("==================================================");
		System.out.println(" Crear nuevo Usuario...");
		System.out.println("==================================================");
		if (!txtPassword.getText().toString().trim().equals(txtPasswordT.getText().toString().trim()))
		{
			String srtError = "Las contraseñas ingresadas no coinciden, por favor vuelva a introducirlas...";
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(srtError);
			txtPassword.setText("");
			txtPasswordT.setText("");

		} 
		else
		{	
			System.out.println("Paso");
		boolean verifica = false;
		verifica = verificaCampos(txtNombres.getText().toString().trim(), txtUsuario.getText().toString().trim(),
				txtPassword.getText().toString().trim(), txtPasswordT.getText().toString().trim());
		System.out.println("Verifica: " + verifica);
		alertasMensajes alertas = new alertasMensajes();
		if (!verifica) 
		{
			int existeUser = 0;
			UsuariosBO objInsertar = new UsuariosBO();
			try 
			{
				existeUser = objInsertar.existeUsuarioRegistrado(txtUsuario.getText().toString().trim());
				if ( existeUser == 0 )
				{	
				System.out.println("Entro");
				int resultadoInsert = insertaUsuarioBD(txtNombres.getText().toString().trim(), txtUsuario.getText().toString().trim(),
						txtPassword.getText().toString().trim());
				
				System.out.println("resultado"+resultadoInsert);
				if ( resultadoInsert == 1 )
				{
					
					optionPrincipal = alertas.opcionConfirmacion("Confirmación", "Se ha insertado el usuario");
					dialogStage.close();
				}	
				else 
				{
					System.out.println("No se ha insertado el usuario");
				   
				}
				}
				else 
				{
					String mesj = "Ya existe un usuario registrado con el alias de "+ txtUsuario.getText().toString().trim() +" , por favor ingrese otro ...";
					 alertas.alertaOK(mesj);
					dialogStage.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		}}});
		
		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(185);
		btnCancelar.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(150, 30);
		btnCancelar.setOnAction(this);

		Group root = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblContraseniaT);
		root.getChildren().addAll(scenetitle, lblNombres, txtNombres, lblUser, txtUsuario, lblContrasenia, txtPassword,
				txtPasswordT, lblFecha, txtFecha, btnAdd, btnCancelar);
		
		Scene escenaConsulta = new Scene(root, 510, 300);
		escenaConsulta.getStylesheets().add("DarkTheme.css");
		// escenaConsulta.setFill(null);
		//VentanaConsultas = new Stage();
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Saliendo al panel principal...");
				System.out.println("=======================================================");
				dialogStage.close();
			}

		});
		dialogStage = new Stage();
		dialogStage.setTitle("Ficha de datos");
		dialogStage.setScene(escenaConsulta);
		dialogStage.setResizable(false);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.getIcons().add(bot.iconoLaren());
		dialogStage.showAndWait();
		return optionPrincipal;
	}

	
	  
	/*** INI modifica cliente 
	 * @return ***/
	public Optional<ButtonType> modificaUsuario(UsuariosDTO objUsuario) {
		Label scenetitle = new Label("Datos del usuario");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(180);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");
		// scenetitle.setFont(new Font("Arial",20));

		Label lblNombres = new Label("Nombres ");
		lblNombres.setLayoutX(20);
		lblNombres.setLayoutY(60);
		txtNombres = new TextField();
		txtNombres.setLayoutX(100);
		txtNombres.setLayoutY(55);
		txtNombres.setPrefSize(150, 30);

		Label lblFecha = new Label("Fecha");
		lblFecha.setLayoutX(270);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(350);
		txtFecha.setLayoutY(55);
		txtFecha.setPrefSize(150, 30);
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: " + dateFormat.format(date));
		txtFecha.setText(dateFormat.format(date).toString());
		txtFecha.setEditable(false);

		Label lblUser = new Label("Usuario ");
		lblUser.setLayoutX(20);
		lblUser.setLayoutY(100);
		txtUsuario = new TextField();
		txtUsuario.setLayoutX(100);
		txtUsuario.setLayoutY(95);
		txtUsuario.setPrefSize(150, 30);
		
		Label lblContrasenia = new Label("Contraseña ");
		lblContrasenia.setLayoutX(270);
		lblContrasenia.setLayoutY(100);
		txtPassword = new PasswordField();
		txtPassword.setLayoutX(350);
		txtPassword.setLayoutY(95);
		txtPassword.setPrefSize(150, 30);
		

		Label lblContraseniaT = new Label("Repetir  ");
		lblContraseniaT.setLayoutX(270);
		lblContraseniaT.setLayoutY(140);
		txtPasswordT = new PasswordField();
		txtPasswordT.setLayoutX(350);
		txtPasswordT.setLayoutY(135);
		txtPasswordT.setPrefSize(150, 30);
		
		botones b = new botones();

		Button btnUpdate = new Button("Actualizar");
		btnUpdate.setLayoutX(115);
		btnUpdate.setLayoutY(185);
		btnUpdate.setGraphic(b.botonGuardar());
		// btnAdd.setFont(new Font("Arial",15));
		btnUpdate.setPrefSize(150, 30);
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		System.out.println("==================================================");
		System.out.println(" Actualizar registro...");
		System.out.println("==================================================");
		if (!txtPassword.getText().toString().trim().equals(txtPasswordT.getText().toString().trim())) {
			String srtError = "Las contraseñas ingresadas no coinciden, por favor vuelva a introducirlas...";
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(srtError);
			txtPassword.setText("");
			txtPasswordT.setText("");

		} else {
			boolean verifica = false;
			verifica = verificaCampos(txtNombres.getText().toString().trim(),
					txtUsuario.getText().toString().trim(), txtPassword.getText().toString().trim(),
					txtPasswordT.getText().toString().trim());
			System.out.println("Verifica: " + verifica);
			if (!verifica) 
			{
				System.out.println("Entro");
				int resUpdate = actualizaUsuarioBD(idUser, txtNombres.getText().toString().trim(),
						txtUsuario.getText().toString().trim(), txtPassword.getText().toString().trim());
				System.out.println("resultado"+resUpdate);
				if ( resUpdate == 1 )
				{
					alertasMensajes alertas = new alertasMensajes();
					optionPrincipal = alertas.opcionConfirmacion("Confirmación", "Se ha actualizado el usuario");
					dialogStage.close();
				}	
				else 
				{
					System.out.println("No se ha insertado el producto");
				   
				}
			}

		}
			}});

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(185);
		btnCancelar.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(150, 30);
		btnCancelar.setOnAction(this);
		/*** carga campos al formulario ***/
		cargaCamposU(objUsuario);
		/*** carga campos al formulario ***/

		Group root = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblContraseniaT);
		root.getChildren().addAll(scenetitle, lblNombres, txtNombres, lblUser, txtUsuario, lblContrasenia, txtPassword,
				txtPasswordT, lblFecha, txtFecha, btnUpdate, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
		escenaConsulta.getStylesheets().add("DarkTheme.css");
		// escenaConsulta.setFill(null);
		dialogStage = new Stage();
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Saliendo al panel principal...");
				System.out.println("=======================================================");
				dialogStage.close();
			}

		});
		dialogStage.setTitle("Ficha de datos");
		dialogStage.setScene(escenaConsulta);
		dialogStage.setResizable(false);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.getIcons().add(bot.iconoLaren());
		dialogStage.showAndWait();
		return optionPrincipal;
	}

	/*** INI modifica cliente ***/
	public void pruebas(UsuariosDTO objUsuario) {
		Text scenetitle = new Text("Datos del usuario");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		// scenetitle.setFont(new Font("Arial",20));

		Label lblNombres = new Label("Nombres ");
		lblNombres.setLayoutX(20);
		lblNombres.setLayoutY(60);
		txtNombres = new TextField();
		txtNombres.setLayoutX(100);
		txtNombres.setLayoutY(55);

		Label lblFecha = new Label("FECHA ");
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

		Label lblUser = new Label("Usuario ");
		lblUser.setLayoutX(20);
		lblUser.setLayoutY(90);
		txtUsuario = new TextField();
		txtUsuario.setLayoutX(100);
		txtUsuario.setLayoutY(85);
		Label lblContrasenia = new Label("Contraseña ");
		lblContrasenia.setLayoutX(270);
		lblContrasenia.setLayoutY(90);
		txtPassword = new PasswordField();
		txtPassword.setLayoutX(350);
		txtPassword.setLayoutY(85);

		Label lblContraseniaT = new Label("Repetir  ");
		lblContraseniaT.setLayoutX(270);
		lblContraseniaT.setLayoutY(120);
		txtPasswordT = new PasswordField();
		txtPasswordT.setLayoutX(350);
		txtPasswordT.setLayoutY(115);

		botones b = new botones();

		btnUpdate = new Button("Guardar");
		btnUpdate.setLayoutX(170);
		btnUpdate.setLayoutY(185);
		btnUpdate.setGraphic(b.botonGuardar());
		// btnAdd.setFont(new Font("Arial",15));
		btnUpdate.setPrefSize(100, 30);
		btnUpdate.setOnAction(this);

		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(185);
		btnCancelar.setGraphic(b.botonError());
		// btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(100, 30);
		btnCancelar.setOnAction(this);
		/*** carga campos al formulario ***/
		cargaCamposU(objUsuario);
		/*** carga campos al formulario ***/

		Group root = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		/**/
		root.getChildren().addAll(bp, lblContraseniaT);
		root.getChildren().addAll(scenetitle, lblNombres, txtNombres, lblUser, txtUsuario, lblContrasenia, txtPassword,
				txtPasswordT, lblFecha, txtFecha, btnUpdate, btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
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
		VentanaConsultas.show();

	}
	
	public void cargaCamposU(UsuariosDTO usuarios) {
		// txtNombres,txtUsuario,txtPasswordT,txtPassword,txtTelefono,txtCorreo;
		// txtNombres,txtUsuario,txtPassword,txtPasswordT,txtTelefono,txtCorreo;
		if (usuarios != null) {
			System.out.println(usuarios.getPaasword());
			
			//idUsuario = Integer.parseInt(usuarios.getIdUsuario());
			idUser = usuarios.getIdUsuario();
			txtNombres.setText(usuarios.getNombres());
			txtUsuario.setText(usuarios.getUsuario());
			txtPassword.setText("");
			txtPasswordT.setText("");

		}
	}

	/*** FIN modifica cliente 
	 * @return ***/

	/* */
	
	public Optional<ButtonType> pruebasRetorno()
	{
		Button btnOk = new Button("Probado");
		btnOk.setLayoutX(20);
		btnOk.setLayoutY(20);
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Refrescando...");
				System.out.println("=======================================================");
				alertasMensajes alertas = new alertasMensajes();
				option = alertas.opcionConfirmacion("Borrar","Borrar");
				dialogStage.close();
			}

		});
		Group page = new Group();
		dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        page.getChildren().add(btnOk);
        Scene scene = new Scene(page, 300,300);
        dialogStage.setScene(scene);
        // Set the person into the controller.
       
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
		
		return option;
	}
	/* */
	
	
	public boolean verificaCampos(String nomApellido, String strUsuario, String strApellidos, String srtDireccion) {
		boolean blData = false;
		String srtError = "Faltan datos del USUARIO, por favor revise: ";
		String srtConcatenaERR = "";
		alertasMensajes alerta = new alertasMensajes();

		if (nomApellido.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + " Nombre y apellido ";
		}
		if (strUsuario.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + " User ";
		}

		if (strApellidos.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Contraseña  ";
		}
		if (srtDireccion.isEmpty()) {
			srtConcatenaERR = srtConcatenaERR + "- Contraseña de confirmación ";
		}

		if (!srtConcatenaERR.isEmpty()) {
			String strEnvio = null;
			strEnvio = srtError + " " + srtConcatenaERR;
			alerta.alertaGeneral(strEnvio);
			blData = true;
		}
		return blData;
	}

	public int  insertaUsuarioBD(String NomApellidos, String strUser, String strPassword) {
		System.out.println("================================================================================");
		System.out.println(" Ingreso de cliente...");
		System.out.println("================================================================================");
		UsuariosBO objInsertar = new UsuariosBO();
		int resInsert = 0;
		try {
			System.out.println(" 1");
			resInsert = objInsertar.insertaUsuario(NomApellidos, strUser, strPassword);
			System.out.println(" 2: " + resInsert);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				
				return resInsert;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resInsert;
	}

	public int actualizaUsuarioBD(int idUsuario, String NomApellidos, String strUser, String strPassword) {
		System.out.println("================================================================================");
		System.out.println(" update de Usuario...");
		System.out.println("================================================================================");
		UsuariosBO objInsertar = new UsuariosBO();
		int resInsert = 0;
		try {
			System.out.println(" 1");
			resInsert = objInsertar.actualizaUsuario(idUsuario, NomApellidos, strUser, strPassword);
			System.out.println(" 2: " + resInsert);
			if (resInsert == 1) {
				System.out.println("Resultado del query: " + resInsert);
				//alertasMensajes alertas = new alertasMensajes();
				//String strMensaje = "Se ha actualizado el usuario:";
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
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnAdd) {
			System.out.println("==================================================");
			System.out.println(" Crear nuevo Usuario...");
			System.out.println("==================================================");
			if (!txtPassword.getText().toString().trim().equals(txtPasswordT.getText().toString().trim())) {
				String srtError = "Las contraseñas ingresadas no coinciden, por favor vuelva a introducirlas...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				txtPassword.setText("");
				txtPasswordT.setText("");

			} else

				System.out.println("Paso");
			boolean verifica = false;
			verifica = verificaCampos(txtNombres.getText().toString().trim(), txtUsuario.getText().toString().trim(),
					txtPassword.getText().toString().trim(), txtPasswordT.getText().toString().trim());
			System.out.println("Verifica: " + verifica);
			if (!verifica) {
				System.out.println("Entro");
				 insertaUsuarioBD(txtNombres.getText().toString().trim(), txtUsuario.getText().toString().trim(),
						txtPassword.getText().toString().trim());
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
			if (!txtPassword.getText().toString().trim().equals(txtPasswordT.getText().toString().trim())) {
				String srtError = "Las contraseñas ingresadas no coinciden, por favor vuelva a introducirlas...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				txtPassword.setText("");
				txtPasswordT.setText("");

			} else {
				boolean verifica = false;
				verifica = verificaCampos(txtNombres.getText().toString().trim(),
						txtUsuario.getText().toString().trim(), txtPassword.getText().toString().trim(),
						txtPasswordT.getText().toString().trim());
				System.out.println("Verifica: " + verifica);
				if (!verifica) {
					System.out.println("Entro");
					actualizaUsuarioBD(idUser, txtNombres.getText().toString().trim(),
							txtUsuario.getText().toString().trim(), txtPassword.getText().toString().trim());
				}

			}
		}
	}

}
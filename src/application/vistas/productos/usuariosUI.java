package application.vistas.productos;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.BO.ClientesBO;
import application.BO.UsuariosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.UsuariosDTO;
import application.extras.botones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class usuariosUI implements EventHandler<ActionEvent> 
{
	public TextField txtNombres,txtUsuario;
	public PasswordField txtPassword,txtPasswordT;
	public Button btnAdd, btnCancelar,btnUpdate;
	public int idUser=0;
	Stage VentanaConsultas;
	
	public float totalFacturar = 0.2f;
	public ObservableList<String> Contenido= 
		    FXCollections.observableArrayList (
		            "Seleccione un producto"
		        );;
	public ComboBox<String> comboProductos= new ComboBox<String>(Contenido);
	
	public void insertaUsuario(String identificacion) 
	{
		Text scenetitle = new Text("Datos del nuevo usuario");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		//scenetitle.setFont(new Font("Arial",20));
        
		Label lblNombres = new Label("Nombres ");
		lblNombres.setLayoutX(20);
		lblNombres.setLayoutY(60);
		txtNombres = new TextField();
		txtNombres.setLayoutX(100);
		txtNombres.setLayoutY(55);
		txtNombres.setText(identificacion);
		
		Label lblFecha = new Label("FECHA ");
		lblFecha.setLayoutX(270);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(350);
		txtFecha.setLayoutY(55);
		Date date = new Date();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: "+dateFormat.format(date));
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
		
		btnAdd = new Button("Guardar");
		btnAdd.setLayoutX(170);
		btnAdd.setLayoutY(185);
		btnAdd.setGraphic(b.botonGuardar());
		//btnAdd.setFont(new Font("Arial",15));
		btnAdd.setPrefSize(100, 30);
		btnAdd.setOnAction(this);
		
		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(185);
		btnCancelar.setGraphic(b.botonError());
		//btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(100, 30);
		btnCancelar.setOnAction(this);
		
		
        Group root = new Group();
        
        Image imgCarga = new Image("application/1.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp  = new BorderPane();
		bp.setCenter(imgView);
        /**/
        root.getChildren().addAll(bp,lblContraseniaT);
        root.getChildren().addAll(scenetitle, lblNombres,txtNombres,lblUser,txtUsuario,lblContrasenia,txtPassword,txtPasswordT,lblFecha,txtFecha,btnAdd,btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
		//escenaConsulta.setFill(null);
		VentanaConsultas = new Stage();
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
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
		
		VentanaConsultas.show();

	}
	
/*** INI modifica cliente ***/
	public void modificaUsuario(UsuariosDTO objUsuario) 
	{
		Text scenetitle = new Text("Datos del usuario");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		//scenetitle.setFont(new Font("Arial",20));
        
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
		System.out.println("Fecha actual: "+dateFormat.format(date));
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
		//btnAdd.setFont(new Font("Arial",15));
		btnUpdate.setPrefSize(100, 30);
		btnUpdate.setOnAction(this);
		
		btnCancelar = new Button("Cancelar");
		btnCancelar.setLayoutX(280);
		btnCancelar.setLayoutY(185);
		btnCancelar.setGraphic(b.botonError());
		//btnAdd.setFont(new Font("Arial",15));
		btnCancelar.setPrefSize(100, 30);
		btnCancelar.setOnAction(this);
		/*** carga campos al formulario***/
		cargaCamposU(objUsuario);
		/*** carga campos al formulario***/
		
        Group root = new Group();
        
        Image imgCarga = new Image("application/1.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp  = new BorderPane();
		bp.setCenter(imgView);
        /**/
        root.getChildren().addAll(bp,lblContraseniaT);
        root.getChildren().addAll(scenetitle, lblNombres,txtNombres,lblUser,txtUsuario,lblContrasenia,txtPassword,txtPasswordT,lblFecha,txtFecha,btnUpdate,btnCancelar);
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 510, 300);
		//escenaConsulta.setFill(null);
		VentanaConsultas = new Stage();
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
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
		
		VentanaConsultas.show();
		
	}
	

	public void cargaCamposU(UsuariosDTO usuarios)
	{
		//txtNombres,txtUsuario,txtPasswordT,txtPassword,txtTelefono,txtCorreo; txtNombres,txtUsuario,txtPassword,txtPasswordT,txtTelefono,txtCorreo;
		if (usuarios != null) 
		{
			System.out.println(usuarios.getPaasword());
			idUser=usuarios.getIdUsuario();
			txtNombres.setText(usuarios.getNombres());	
			txtUsuario.setText(usuarios.getUsuario());
			txtPassword.setText("");
			txtPasswordT.setText("");
		
		}
	}
	
/*** FIN modifica cliente ***/

public boolean verificaCampos(String nomApellido, String strUsuario, String strApellidos, String srtDireccion)
{
	boolean blData=false;
	String srtError="Faltan datos del USUARIO, por favor revise: ";
	String srtConcatenaERR="";
	alertasMensajes alerta = new alertasMensajes();
	
	if (nomApellido.isEmpty())
	{
		srtConcatenaERR = srtConcatenaERR+ " Nombre y apellido ";
	}
	if (strUsuario.isEmpty())
	{
		srtConcatenaERR = srtConcatenaERR+ " User ";
	}
		
	if (strApellidos.isEmpty())
	{
		srtConcatenaERR = srtConcatenaERR+ "- Contraseña  ";
	}
	if (srtDireccion.isEmpty())
	{
		srtConcatenaERR = srtConcatenaERR+ "- Contraseña de confirmación ";
	}
		
	
	if (!srtConcatenaERR.isEmpty())
	{	
		String strEnvio = null; 
		strEnvio = srtError+ " " +srtConcatenaERR;
		alerta.alertaGeneral(strEnvio);
		blData=true;
	}
	return blData;
}

	public void insertaUsuarioBD(String NomApellidos, String strUser, String strPassword)
	{
		System.out.println("================================================================================");
		System.out.println(" Ingreso de cliente...");
		System.out.println("================================================================================");
		UsuariosBO objInsertar = new UsuariosBO();
		int resInsert=0;
		try 
		{	System.out.println(" 1");
			resInsert = objInsertar.insertaUsuario(NomApellidos, strUser, strPassword);
			System.out.println(" 2: "+resInsert);
			if ( resInsert == 1)
			{	
				System.out.println("Resultado del query: "+resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje="Se ha insertado el usuario:"+ strUser;
				alertas.alertaOK(strMensaje);
				VentanaConsultas.toBack();
				VentanaConsultas.close();
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actualizaUsuarioBD(int idUsuario,String NomApellidos, String strUser, String strPassword)
	{
		System.out.println("================================================================================");
		System.out.println(" update de Usuario...");
		System.out.println("================================================================================");
		UsuariosBO objInsertar = new UsuariosBO();
		int resInsert=0;
		try 
		{	System.out.println(" 1");
			resInsert = objInsertar.actualizaUsuario(idUsuario,NomApellidos,strUser, strPassword);
			System.out.println(" 2: "+resInsert);
			if ( resInsert == 1)
			{	
				System.out.println("Resultado del query: "+resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje="Se ha actualizado el usuario:";
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
	public void handle(ActionEvent event) 
	{
	if (event.getSource() == btnAdd) 
	{
		System.out.println("==================================================");
		System.out.println(" Crear nuevo Usuario...");
		System.out.println("==================================================");
		if(!txtPassword.getText().toString().trim().equals(txtPasswordT.getText().toString().trim()))
		{
			String srtError="Las contraseñas ingresadas no coinciden, por favor vuelva a introducirlas...";
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(srtError);
			txtPassword.setText("");
			txtPasswordT.setText("");
			
		}
		else
			
			System.out.println("Paso");
		boolean verifica = false;
		verifica = verificaCampos(txtNombres.getText().toString().trim(),txtUsuario.getText().toString().trim(),txtPassword.getText().toString().trim(),txtPasswordT.getText().toString().trim());
		System.out.println("Verifica: "+verifica);
		if (!verifica)
		{	
			System.out.println("Entro");
			insertaUsuarioBD(txtNombres.getText().toString().trim(),txtUsuario.getText().toString().trim(),txtPassword.getText().toString().trim());
		}
	}
	else if ( event.getSource() == btnCancelar ) 
	   {
			System.out.println("==================================================");
	    	System.out.println(" Cancelar Salir...");
	    	System.out.println("==================================================");
	    	//ventanaActual.close();
	    	
	   }
	else if ( event.getSource() == btnUpdate ) 
	   {
			System.out.println("==================================================");
	    	System.out.println(" Actualizar registro...");
	    	System.out.println("==================================================");
	    	if(!txtPassword.getText().toString().trim().equals(txtPasswordT.getText().toString().trim()))
			{
				String srtError="Las contraseñas ingresadas no coinciden, por favor vuelva a introducirlas...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				txtPassword.setText("");
				txtPasswordT.setText("");
				
			}
			else
			{	
	    	boolean verifica = false;
			verifica = verificaCampos(txtNombres.getText().toString().trim(),txtUsuario.getText().toString().trim(),txtPassword.getText().toString().trim(),txtPasswordT.getText().toString().trim());
			System.out.println("Verifica: "+verifica);
			if (!verifica)
			{	
				System.out.println("Entro");
				actualizaUsuarioBD(idUser,txtNombres.getText().toString().trim(),txtUsuario.getText().toString().trim(),txtPassword.getText().toString().trim());
			}
			
	    	//actualizaClienteBD(txtNombres.getText().toString().trim(),txtUsuario.getText().toString().trim(),txtPassword.getText().toString().trim(),txtPasswordT.getText().toString().trim(),txtTelefono.getText().toString().trim(),txtCorreo.getText().toString().trim());
	    	//ventanaActual.close();
			}
	   }
	}
	
}
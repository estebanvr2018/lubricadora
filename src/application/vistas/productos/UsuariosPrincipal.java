package application.vistas.productos;

import java.sql.SQLException;
import java.util.List;

import application.Principal;
import application.BO.UsuariosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.UsuariosDTO;
import application.extras.botones;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UsuariosPrincipal  implements EventHandler<ActionEvent> 
{

	public TextField  txtIdentificacion;
	public Button btnBuscar,btnExit, btnAdd, btnUpdate, btnEliminar;
	public Stage ventanaActual;
	
	UsuariosDTO quitarRegistro = new UsuariosDTO();
	
	public TableView<UsuariosDTO> tableUsuarios = new TableView();
	public TableColumn<UsuariosDTO, String> idUsuario = new TableColumn<>("Id usuario");
	public TableColumn<UsuariosDTO, String> Nombres = new TableColumn<>("Nombres");
	public TableColumn<UsuariosDTO, String> Usuario = new TableColumn<>("Usuario");
	public List<UsuariosDTO> usuarios=null;
		 
	
	public void ingresoUsuarios(Stage ventanaIngreso) 
	{
		//cargaComboTipo();
		ventanaActual = ventanaIngreso;
		Text scenetitle = new Text("Usuarios.");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(150);
		scenetitle.setY(40);
		 
		Label lblProd = new Label("Usuario");
		lblProd.setLayoutX(50);
		lblProd.setLayoutY(60);
		txtIdentificacion = new TextField();
		txtIdentificacion.setLayoutX(120);
		txtIdentificacion.setLayoutY(55);
		txtIdentificacion.setPrefSize(150, 25);
		/*
		Image imgCarga = new Image("application/1.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		*/
		botones b = new botones();
		btnBuscar = new Button();
		btnBuscar.setGraphic(b.botonBuscar());
		btnBuscar.setLayoutX(285);
		btnBuscar.setLayoutY(55);
		btnBuscar.setPrefSize(30,20);
		btnBuscar.setOnAction(this);
		
		
		btnAdd = new Button("Add");
		btnAdd.setGraphic(b.botonAgregar());
		btnAdd.setLayoutX(30);
		btnAdd.setLayoutY(340);
		btnAdd.setPrefSize(80,35);
		btnAdd.setOnAction(this);
		btnAdd.setDisable(true);
		
		btnUpdate= new Button("Editar");
		btnUpdate.setGraphic(b.botonActualizar());
		btnUpdate.setLayoutX(120);
		btnUpdate.setLayoutY(340);
		btnUpdate.setPrefSize(80,35);
		btnUpdate.setOnAction(this);
		btnUpdate.setDisable(true);
		
		btnEliminar= new Button("Eliminar");
		btnEliminar.setGraphic(b.botonEliminar());
		btnEliminar.setLayoutX(210);
		btnEliminar.setLayoutY(340);
		btnEliminar.setPrefSize(80,35);
		btnEliminar.setOnAction(this);
		btnEliminar.setDisable(true);
		
		btnExit = new Button("Regresar");
		btnExit.setGraphic(b.botonRegresar());
		btnExit.setLayoutX(270);
		btnExit.setLayoutY(390);
		btnExit.setPrefSize(100, 35);
		btnExit.setOnAction(this);
		
	    /**/
	    idUsuario.setCellValueFactory(new PropertyValueFactory<>("IdUsuario"));
		Nombres.setCellValueFactory(new PropertyValueFactory<>("Nombres"));
		Usuario.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
		
		idUsuario.setMinWidth(30);
		Nombres.setMinWidth(80);
		Usuario.setMinWidth(80);
		
		tableUsuarios.getColumns().addAll(idUsuario, Nombres, Usuario);
		
		tableUsuarios.setLayoutX(60);
		tableUsuarios.setLayoutY(100);
		tableUsuarios.setPrefSize(265, 220);
	    /**/
		cargarUsuariosTabla();
		tableUsuarios.setOnMouseClicked( event -> {
			if( event.getClickCount() == 2 ) 
		    {
				try
				{
					quitarRegistro = tableUsuarios.getSelectionModel().getSelectedItem();
				}
				catch(Exception exs)
				{
					//btnAdd.setDisable(true);
					System.out.println("Error");
				}
			}
			if( event.getClickCount() == 1 )
			{
				System.out.println("Un solo click");
				quitarRegistro = tableUsuarios.getSelectionModel().getSelectedItem();
			}	
			}
			);
        Group rootIngreso = new Group();
        
        Image imgCarga = new Image("application/madmenmag-fondo-verano-agua.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp  = new BorderPane();
		bp.setCenter(imgView);
		rootIngreso.getChildren().addAll(bp,scenetitle,lblProd,txtIdentificacion, btnBuscar,tableUsuarios,btnExit,btnAdd,btnUpdate,btnEliminar);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 400, 430);
		
		//VentanaConsultasProductos = new Stage();
		ventanaActual.setTitle("Formulario de usuarios");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.show();

	}
	
	
	
	public void cargarUsuariosTabla() 
	{
		try 
		{
			tableUsuarios.getItems().removeAll();
			tableUsuarios.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando Usuarios...");
			System.out.println("================================================================================");
			List<UsuariosDTO> lista=null; 
			lista = new UsuariosBO().cargaUsuarios();
			
			if (lista != null && !lista.isEmpty())
			{
				for (UsuariosDTO obje : lista) 
				{
					
					if (obje != null) 
					{
						tableUsuarios.getItems().add(obje);
					}	
				}	
				btnUpdate.setDisable(false);
				btnEliminar.setDisable(false);
			}	
			else 
			{	
				btnAdd.setDisable(true);
				/*String srtError="Cliente no existe, se prodecerá a registrarlo...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);*/
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			
		}
		
		
	}
	
	public void consultarUsuario(String strIdentificacion) 
	{
		try 
		{	tableUsuarios.getItems().removeAll();
			tableUsuarios.getItems().clear();
			System.out.println("==================================================");
			System.out.println("Consultando a la lista...");
			System.out.println("==================================================");
			List<UsuariosDTO> lista=null; 
			lista = new UsuariosBO().consultaUsuarioNombre(strIdentificacion);
			
			if (lista != null && !lista.isEmpty())
			{	
				for (UsuariosDTO obje : lista) 
				{
					
					if (obje != null) 
					{
						tableUsuarios.getItems().add(obje);
						
					}	
				}	
			}
			else 
			{
				String srtError="El cliente con la identificación:  "+strIdentificacion+ "  no existe";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				btnAdd.setDisable(false);
				btnUpdate.setDisable(true);
			}	
			
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			
		}
		
	}		
	
	
	public void eliminaUsuario(int idUsuario)
	{
		UsuariosBO objEliminar = new UsuariosBO();
		int resInsert=0;
		try 
		{	System.out.println(" 1");
			resInsert = objEliminar.eliminaUsuario(idUsuario);
			System.out.println(" 2: "+resInsert);
			if ( resInsert == 1)
			{	
				System.out.println("Resultado del query: "+resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje="Se ha borrado el usuario";
				alertas.alertaOK(strMensaje);
				
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void handle(ActionEvent event) 
	{
		if (event.getSource() == btnBuscar) 
		{
			System.out.println("==================================================");
	    	System.out.println("Buscando en la base...");
	    	System.out.println("==================================================");
	    	String strParametro=null;
	    	strParametro=txtIdentificacion.getText().toString().trim();
	    	if(!strParametro.isEmpty())
	    	{	
		    	try 
		    	{
		    		consultarUsuario(strParametro);
				}
		    	catch (Exception e) 
		    	{
					String srtError="No se pudo conectar a la base de datos...";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				}
	    	}
	    	else 
	    	{
	    		String srtError="No ha ingresado usuario, no puede continuar";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				cargarUsuariosTabla();
				btnAdd.setDisable(true);
	    	}	
		}
		else if ( event.getSource() == btnExit ) 
		   {
				System.out.println("==================================================");
		    	System.out.println("Redirigiendose al menú principal...");
		    	System.out.println("==================================================");
		    	ventanaActual.toBack();
		    	ventanaActual.close();
		    	Principal menuInicio = new Principal();
		    	menuInicio.panelPrincipal();
		    	
		   }
		
		else if ( event.getSource() == btnAdd ) 
		{
				System.out.println("==================================================");
		    	System.out.println("Agregar nuevo cliente...");
		    	System.out.println("==================================================");
		    	//ventanaActual.close();
		    	usuariosUI insertaUsuario = new usuariosUI();
		    	insertaUsuario.insertaUsuario(txtIdentificacion.getText().toString().trim());
		    	cargarUsuariosTabla();
		    	
		}
		
		else if ( event.getSource() == btnUpdate ) 
		   {
				System.out.println("==================================================");
		    	System.out.println("Modificar cliente...");
		    	System.out.println("==================================================");
		    	usuariosUI actualizaUsuario = new usuariosUI();
		    	if (quitarRegistro.equals(""))
		    	{
		    		System.out.println("Error");
		    	}
		    	else
		    	{	System.out.println("pass wd: "+quitarRegistro.getPaasword());
		    		actualizaUsuario.modificaUsuario(quitarRegistro);
		    		cargarUsuariosTabla();
		    	}
		    	
		   }
		else if ( event.getSource() == btnEliminar ) 
		   {
				System.out.println("==================================================");
		    	System.out.println("Eliminar cliente...");
		    	System.out.println("==================================================");
		    	if (quitarRegistro.equals(""))
		    	{
		    		System.out.println("Error");
		    	}
		    	else
		    	{	eliminaUsuario(quitarRegistro.getIdUsuario());
		    		cargarUsuariosTabla();
		    	}
		    	
		   }
		}	
	

}
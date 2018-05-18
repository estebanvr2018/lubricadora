package application.vistas.productos;

import java.sql.SQLException;
import java.util.List;

import application.Principal;
import application.BO.ClientesBO;
import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.extras.botones;
import application.tablas.tablaFacturaDet;
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

public class clientesPrincipal  implements EventHandler<ActionEvent> 
{

		public TextField  txtIdentificacion;
		public Button btnBuscar,btnExit, btnAdd, btnUpdate;
		public Stage ventanaActual;
		
		ClientesDTO quitarRegistro = new ClientesDTO();
		
		public TableView<ClientesDTO> tableClientes = new TableView();
		public TableColumn<ClientesDTO, String> Ced = new TableColumn<>("Ced/RUC");
		public TableColumn<ClientesDTO, String> Nombre = new TableColumn<>("Nombre");
		public TableColumn<ClientesDTO, String> Apellido = new TableColumn<>("Apellido");
		public TableColumn<ClientesDTO, String> Direccion = new TableColumn<>("Dirección");
		public TableColumn<ClientesDTO, String> Telefono = new TableColumn<>("Teléfono");
		public TableColumn<ClientesDTO, String> Correo = new TableColumn<>("Correo");
		public List<ClientesDTO> clientes=null;
			 
		
		public void ingresoClientes(Stage ventanaIngreso) 
		{
			//cargaComboTipo();
			ventanaActual = ventanaIngreso;
			Text scenetitle = new Text("Clientes.");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			scenetitle.setX(250);
			scenetitle.setY(40);
			 
			Label lblProd = new Label("Cédula o RUC");
			lblProd.setLayoutX(30);
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
			btnBuscar = new Button("Buscar");
			btnBuscar.setGraphic(b.botonBuscar());
			btnBuscar.setLayoutX(300);
			btnBuscar.setLayoutY(55);
			btnBuscar.setPrefSize(80,20);
			btnBuscar.setOnAction(this);
			
			
			btnAdd = new Button("Add");
			btnAdd.setGraphic(b.botonAgregar());
			btnAdd.setLayoutX(30);
			btnAdd.setLayoutY(390);
			btnAdd.setPrefSize(80,35);
			btnAdd.setOnAction(this);
			btnAdd.setDisable(true);
			
			btnUpdate= new Button("Editar");
			btnUpdate.setGraphic(b.botonActualizar());
			btnUpdate.setLayoutX(120);
			btnUpdate.setLayoutY(390);
			btnUpdate.setPrefSize(80,35);
			btnUpdate.setOnAction(this);
			btnUpdate.setDisable(true);
			
			
			btnExit = new Button("Regresar");
			btnExit.setGraphic(b.botonRegresar());
			btnExit.setLayoutX(560);
			btnExit.setLayoutY(390);
			btnExit.setPrefSize(100, 35);
			btnExit.setOnAction(this);
			
		    /**/
		    Ced.setCellValueFactory(new PropertyValueFactory<>("IdIdentificacion"));
			Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombres"));
			Apellido.setCellValueFactory(new PropertyValueFactory<>("PrimerApellido"));
			Direccion.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
			Telefono.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
			Correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
			
			Ced.setMinWidth(50);
			Nombre.setMinWidth(100);
			Apellido.setMinWidth(100);
			Direccion.setMinWidth(50);
			Telefono.setMinWidth(30);
			Correo.setMinWidth(50);
			
			tableClientes.getColumns().addAll(Ced, Nombre, Apellido, Direccion,Telefono,Correo);
			
			tableClientes.setLayoutX(30);
			tableClientes.setLayoutY(100);
			tableClientes.setPrefSize(620, 220);
		    /**/
			cargarClientesTabla();
			tableClientes.setOnMouseClicked( event -> {
				if( event.getClickCount() == 2 ) 
			    {
					try
					{
						quitarRegistro = tableClientes.getSelectionModel().getSelectedItem();
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
					quitarRegistro = tableClientes.getSelectionModel().getSelectedItem();
				}	
				}
				);
	        Group rootIngreso = new Group();
	        
	        Image imgCarga = new Image("application/madmenmag-fondo-verano-agua.jpg"); 
			ImageView imgView = new ImageView(imgCarga);
			BorderPane bp  = new BorderPane();
			bp.setCenter(imgView);
			rootIngreso.getChildren().addAll(bp,scenetitle,lblProd,txtIdentificacion, btnBuscar,tableClientes,btnExit,btnAdd,btnUpdate);
			Scene escenaProductos = null;
			escenaProductos = new Scene(rootIngreso, 670, 430);
			
			//VentanaConsultasProductos = new Stage();
			ventanaActual.setTitle("Clientes");
			ventanaActual.setScene(escenaProductos);
			ventanaActual.setResizable(false);
			ventanaActual.show();

		}
		
		
		
		public void cargarClientesTabla() 
		{
			try 
			{
				tableClientes.getItems().removeAll();
				tableClientes.getItems().clear();
				System.out.println("================================================================================");
				System.out.println(" Cargando clientes...");
				System.out.println("================================================================================");
				List<ClientesDTO> lista=null; 
				lista = new ClientesBO().cargaTClientes();
				
				if (lista != null && !lista.isEmpty())
				{
					for (ClientesDTO obje : lista) 
					{
						
						if (obje != null) 
						{
							tableClientes.getItems().add(obje);
								/*System.out.println(obje.getIdProducto() + " " +obje.getDescripcion() +obje.getPrecio());
								int cantidad=0;
								cantidad = Integer.parseInt(txtCantidad.getText().toString());
								ProductosBO llenaCampo = new ProductosBO();
								tablaFacturaDet mapeoTabla = new tablaFacturaDet();
								mapeoTabla=llenaCampo.valoresTabla(cantidad, obje.getDescripcion(),obje.getValorUnitario());
								tableFacturacion.getItems().add(mapeoTabla);*/
								
							
						}	
					}	
					btnUpdate.setDisable(false);
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
		
		public void consultarCliente(String strIdentificacion) 
		{
			try 
			{	tableClientes.getItems().removeAll();
				tableClientes.getItems().clear();
				System.out.println("==================================================");
				System.out.println("Consultando a la lista...");
				System.out.println("==================================================");
				List<ClientesDTO> lista=null; 
				lista = new ClientesBO().consultaCliente(strIdentificacion);
				
				if (lista != null && !lista.isEmpty())
				{	
					for (ClientesDTO obje : lista) 
					{
						
						if (obje != null) 
						{
							tableClientes.getItems().add(obje);
							
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
			    		consultarCliente(strParametro);
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
		    		String srtError="No ha ingresado CED/RUC, no puede continuar";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
					cargarClientesTabla();
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
			    	clientesIU insertaCliente = new clientesIU();
			    	insertaCliente.insertaCliente(txtIdentificacion.getText().toString().trim());
			    	
			}
			
			else if ( event.getSource() == btnUpdate ) 
			   {
					System.out.println("==================================================");
			    	System.out.println("Modificar cliente...");
			    	System.out.println("==================================================");
			    	clientesIU actualizaCliente = new clientesIU();
			    	if (quitarRegistro.equals(""))
			    	{
			    		System.out.println("Error");
			    	}
			    	else
			    	{	
			    		actualizaCliente.modificaCliente(quitarRegistro);
			    		cargarClientesTabla();	
			    		//ventanaActual.close();
			    	}
			   }
			}	
		

}

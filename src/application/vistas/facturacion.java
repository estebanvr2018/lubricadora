package application.vistas;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.Principal;
import application.BO.ClientesBO;
import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.FacturaDtlDTO;
import application.com.DTOS.ProductosDTO;
import application.com.DTOS.productoDTO;
import application.extras.Numeros_a_Letras;
import application.extras.botones;
import application.tablas.tablaFacturaDet;
import application.vistas.productos.productosPrincipal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.stage.Stage;

public class facturacion implements EventHandler<ActionEvent> 
{
	/*** tabla que cargara los productos ***/
	public TableView<FacturaDtlDTO> productosFacturar = new TableView();
	
	public TableColumn<FacturaDtlDTO, String> cantidadProducto = new TableColumn<>("Cantidad");
	public TableColumn<FacturaDtlDTO, String> descripcionProducto = new TableColumn<>("Articulo");
	public TableColumn<FacturaDtlDTO, String> valorUnitario = new TableColumn<>("Valor unitario");
	public TableColumn<FacturaDtlDTO, String> valorTotal = new TableColumn<>("Valor total");
	/*** Fin tabla quye cargara los productos***/
	
	
	public Stage ventanaActual;
	
	public TextField txtRuc,txtCliente,txtDireccion,txtApellidos,txtTelefono,txtCorreo,txtStock;
	public TextField txtCantidad,txtSubtotal,txtIva,txtIvaDoce,txtTotal,txtCantidadString, txtDescripcion,numProductos;
	public Button btnAdd,btnExit,btnAddProducto;
	public float totalFacturar = 0.2f;
	public ObservableList<String> Contenido= 
		    FXCollections.observableArrayList (
		            "Seleccione un producto"
		        );;
	public ComboBox<String> comboProductos= new ComboBox<String>(Contenido);
	public List<ProductosDTO> productos=null;
	
	public List<productoDTO> productosAfactura=null;
	
	/*** ***/
	public TableView<productoDTO> productosIngresados = new TableView();
	public TableColumn<productoDTO, String> idProducto = new TableColumn<>("Cod");
	public TableColumn<productoDTO, String> nomProducto = new TableColumn<>("Nombre");
	public TableColumn<productoDTO, String> descProducto = new TableColumn<>("Descripción");
	public TableColumn<productoDTO, String> valorCompra = new TableColumn<>("V. Compra");
	public TableColumn<productoDTO, String> disponibles = new TableColumn<>("Stock");
	public TableColumn<productoDTO, String> valorVenta = new TableColumn<>("V. Venta");
	public TableColumn<productoDTO, String> categoria = new TableColumn<>("Categoría");
	public TableColumn<productoDTO, String> subcategoria = new TableColumn<>("Sub");
	public List<productoDTO> productosCargados=null;
	
	/*** ***/
	
	public void formularioFactura(Stage stgFactura) 
	{
		ventanaActual=stgFactura;
		Text scenetitle = new Text("Facturación");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(200);
		scenetitle.setY(40);
		//scenetitle.setFont(new Font("Arial",20));
        
		Label lblRuc = new Label("Cédula/RUC: ");
		lblRuc.setLayoutX(20);
		lblRuc.setLayoutY(60);
		txtRuc = new TextField();
		txtRuc.setLayoutX(100);
		txtRuc.setLayoutY(55);
		txtRuc.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) 
				{
					
					System.out.println("================================================================================");
					System.out.println(" Búsqueda en la base de datos");
					System.out.println("================================================================================");
					inilicializaCampos();
					String parametro=null; 
					parametro=txtRuc.getText().toString();
					if ( parametro.length() == 0 )
					{	
						String srtError="Debe ingresar Cédula o RUC...";
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaGeneral(srtError);
					}
					else
					{	
						List<ClientesDTO> cliente=null;
						cliente = consultarCliente(parametro); //OJO OBTENER EL ID INGRESADO
						
						if (cliente != null && !cliente.isEmpty()) 
						{
							seteaCampos(cliente); 
							deshabilitaCampos();
						}
						else
						{	
							seteaCamposNCL();
							habilitaCampos();
						}
					}
							
				}
			}
		});
		
		Label lblFecha = new Label("Fecha ");
		lblFecha.setLayoutX(300);
		lblFecha.setLayoutY(60);
		TextField txtFecha = new TextField();
		txtFecha.setLayoutX(380);
		txtFecha.setLayoutY(55);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Fecha actual: "+dateFormat.format(date));
		txtFecha.setText(dateFormat.format(date).toString());
		
		Label lblCliente = new Label("Nombres ");
		lblCliente.setLayoutX(20);
		lblCliente.setLayoutY(90);
		lblCliente.setPrefSize(100, 10);
		txtCliente = new TextField();
		txtCliente.setEditable(false);
		txtCliente.setLayoutX(100);
		txtCliente.setLayoutY(85);
		txtCliente.setPrefSize(150, 10);
		Label lblApellidos = new Label("Apellidos ");
		lblApellidos.setLayoutX(300);
		lblApellidos.setLayoutY(90);
		
		txtApellidos = new TextField();
		txtApellidos.setLayoutX(380);
		txtApellidos.setLayoutY(85);
		txtApellidos.setPrefSize(150, 10);
		txtApellidos.setEditable(false);
		
		
		Label lblDireccion = new Label("Dirección: ");
		lblDireccion.setLayoutX(20);
		lblDireccion.setLayoutY(120);
		txtDireccion = new TextField();
		txtDireccion.setEditable(false);
		txtDireccion.setLayoutX(100);
		txtDireccion.setLayoutY(115);
		txtDireccion.setPrefSize(150, 10);
		Label lblTelefono = new Label("Teléfono: ");
		lblTelefono.setLayoutX(300);
		lblTelefono.setLayoutY(120);
		txtTelefono = new TextField();
		txtTelefono.setEditable(false);
		txtTelefono.setLayoutX(380);
		txtTelefono.setLayoutY(115);
		txtTelefono.setPrefSize(150, 20);
		
		Label lblCorreo = new Label("Correo: ");
		lblCorreo.setLayoutX(20);
		lblCorreo.setLayoutY(150);
		txtCorreo = new TextField();	
		txtCorreo.setLayoutX(100);
		txtCorreo.setLayoutY(145);
		//txtCorreo.setPrefSize(200, 20);
		txtCorreo.setEditable(false);
		txtCorreo.setPrefSize(150, 10);
		
		/**/
		Label lblCantidadString = new Label("Son");
		lblCantidadString.setLayoutX(40);
		lblCantidadString.setLayoutY(240);
		txtCantidadString = new TextField();	
		txtCantidadString.setLayoutX(70);
		txtCantidadString.setLayoutY(235);
		txtCantidadString.setPrefSize(300, 25);
		txtCantidadString.setEditable(false);
		
		Label lblSubtotal = new Label("Subtotal");
		lblSubtotal.setLayoutX(400);
		lblSubtotal.setLayoutY(230);
		txtSubtotal = new TextField();	
		txtSubtotal.setLayoutX(460);
		txtSubtotal.setLayoutY(225);
		txtSubtotal.setPrefSize(70, 25);
		txtSubtotal.setEditable(false);
		
		Label lblIvaCero = new Label("IVA 0%");
		lblIvaCero.setLayoutX(400);
		lblIvaCero.setLayoutY(260);
		txtIva = new TextField();	
		txtIva.setLayoutX(460);
		txtIva.setLayoutY(255);
		txtIva.setPrefSize(70, 25);
		txtIva.setEditable(false);
		
		Label lblIvaDoce = new Label("IVA 12%");
		lblIvaDoce.setLayoutX(400);
		lblIvaDoce.setLayoutY(290);
		txtIvaDoce = new TextField();	
		txtIvaDoce.setLayoutX(460);
		txtIvaDoce.setLayoutY(285);
		txtIvaDoce.setPrefSize(70, 25);
		txtIvaDoce.setEditable(false);
		
		Label lblTotal = new Label("TOTAL");
		lblTotal.setLayoutX(400);
		lblTotal.setLayoutY(320);
		txtTotal = new TextField();	
		txtTotal.setLayoutX(460);
		txtTotal.setLayoutY(315);
		txtTotal.setPrefSize(70, 25);
		txtTotal.setEditable(false);
		//
		
		
		btnAdd = new Button("FACTURAR");
		btnAdd.setLayoutX(170);
		btnAdd.setLayoutY(280);
		btnAdd.setDisable(true);
		btnAdd.setFont(new Font("Arial",15));
		btnAdd.setPrefSize(150, 30);
		btnAdd.setOnAction(this);
		
		/*** Nueva tabla***/
		cantidadProducto.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
		descripcionProducto.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		valorUnitario.setCellValueFactory(new PropertyValueFactory<>("Valor"));
		valorTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		cantidadProducto.setStyle("-fx-alignment: CENTER;");
		descripcionProducto.setStyle("-fx-alignment: CENTER;");
		valorUnitario.setStyle("-fx-alignment: CENTER;");
		cantidadProducto.setMinWidth(50);
		descripcionProducto.setMinWidth(200);
		valorUnitario.setMinWidth(150);
		valorTotal.setMinWidth(50);
		
		productosFacturar.getColumns().addAll(cantidadProducto, descripcionProducto, valorUnitario, valorTotal);
		productosFacturar.setLayoutX(10);
		productosFacturar.setLayoutY(20);
		productosFacturar.setPrefSize(520, 200);
		productosFacturar.setOnMouseClicked( event -> {
			if( event.getClickCount() == 2 ) 
		    {
				try{
				FacturaDtlDTO quitarRegistro = new FacturaDtlDTO();
				quitarRegistro = productosFacturar.getSelectionModel().getSelectedItem();
				exitProductoTabla(quitarRegistro);
				sumaTotal();
				}catch(Exception exs)
				{
					//btnAdd.setDisable(true);
					System.out.println("Error");
				}
			}
			if( event.getClickCount() == 1 )
			{
				System.out.println("Un solo click");
			}	
			}
			);
		/*** Nueva tabla***/
		
		botones bot = new botones();
		btnExit = new Button("Regresar");
		btnExit.setGraphic(bot.botonRegresar());
		btnExit.setLayoutX(10);
		btnExit.setLayoutY(10);
		btnExit.setPrefSize(100, 25);
		btnExit.setOnAction(this);
		
		/*** Cabecera de la factura datos del cliente***/
		AnchorPane datosClientes = new AnchorPane();
		datosClientes.getChildren().addAll(scenetitle,
								   lblRuc,
								   txtRuc,
								   lblFecha,
	        					   txtFecha,
	        					   lblCliente,
	        					   txtCliente,
	        					   lblApellidos,
	        					   txtApellidos,
	        					   lblDireccion,
	        					   txtDireccion,
	        					   lblTelefono,
	        					   txtTelefono,
	        					   lblCorreo,
	        					   txtCorreo,
	        					   btnExit
	        					   );
		datosClientes.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		datosClientes.setPadding(new Insets(5));
		datosClientes.setTranslateX(20);
		datosClientes.setTranslateY(20);
		datosClientes.setTranslateZ(20);
		datosClientes.setMaxSize(650, 250);
		
		/*** ***/
		/*** Cabecera de la factura datos del cliente***/
		AnchorPane datosListaFactura = new AnchorPane();
		datosListaFactura.getChildren().addAll(
											   //tableFacturacion,
											   productosFacturar,
				 							   lblCantidadString,
				 							   txtCantidadString,
											   lblSubtotal,
											   lblIvaCero,
											   lblIvaDoce,
											   lblTotal,
											   txtSubtotal,
											   txtIva,
											   txtIvaDoce,	
											   txtTotal,
											   btnAdd
	        					   			);
		datosListaFactura.setBorder(new Border(new BorderStroke(Color.DARKGREEN, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		datosListaFactura.setPadding(new Insets(5));
		datosListaFactura.setTranslateX(20);
		datosListaFactura.setTranslateY(220);
		datosListaFactura.setTranslateZ(20);
		datosListaFactura.setMaxSize(650, 250);
		
		/*** ***/
		
		
        Group root = new Group();
        BorderPane bp  = new BorderPane();
		bp.setCenter(bot.fondoPantalla());
        
		/*** ***/
		idProducto.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
		idProducto.setMinWidth(10);
		nomProducto.setCellValueFactory(new PropertyValueFactory<>("NombreProducto"));
		nomProducto.setMinWidth(80);
		descProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		descProducto.setMinWidth(100);
		valorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
		valorCompra.setMinWidth(60);
		disponibles.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		disponibles.setMinWidth(40);
		valorVenta.setCellValueFactory(new PropertyValueFactory<>("valorVenta"));
		valorVenta.setMinWidth(40);
		categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		categoria.setMinWidth(40);
		subcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
		subcategoria.setMinWidth(40);
		productosIngresados.getColumns().addAll(
												//idProducto,
												//categoria,
												subcategoria,
												nomProducto,
												descProducto,
												valorVenta,
												disponibles);
		productosIngresados.setLayoutX(10);
		productosIngresados.setLayoutY(50);
		productosIngresados.setPrefSize(400, 150);
		productosIngresados.setVisible(true);
		/*** ***/
		
		Label lblCantidad = new Label("Cantidad");
		lblCantidad.setLayoutX(290);
		lblCantidad.setLayoutY(20);
		txtCantidad = new TextField();	
		txtCantidad.setLayoutX(340);
		txtCantidad.setLayoutY(15);
		txtCantidad.setPrefSize(35, 25);
		txtCantidad.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
                	txtCantidad.setText(oldValue);
                }
            }
        });
		
		productosIngresados.setOnMouseClicked( event -> {
			if( event.getClickCount() == 2  ) 
		    {
				 if (!txtCantidad.getText().toString().isEmpty())
				 {	 int numProd = 0;
					numProd = Integer.parseInt(txtCantidad.getText().toString());
					int Stock = 0;
					Stock = productosIngresados.getSelectionModel().getSelectedItem().getStock();
					if ( numProd > 0 && numProd <= Stock)
					{	
						try
						{	System.out.println("================================================================================");
							System.out.println("Agregando productos a facturacion");
							System.out.println("================================================================================");
							int idProducto = 0;
							idProducto = productosIngresados.getSelectionModel().getSelectedItem().getIdProducto();
							String Descr = null;
							Descr = productosIngresados.getSelectionModel().getSelectedItem().getNombreProducto();
							String Descr2 = null;
							Descr2 = productosIngresados.getSelectionModel().getSelectedItem().getDescripcion();
							String Descripcion = Descr +" "+ Descr2;
							float valorVenta = 0.2f;
							valorVenta = productosIngresados.getSelectionModel().getSelectedItem().getValorVenta();
							ProductosBO llenaCampo = new ProductosBO();
							FacturaDtlDTO mapeoTabla = new FacturaDtlDTO();
							mapeoTabla=llenaCampo.valoresTabla(numProd, Descripcion,valorVenta);
							   productosFacturar.getItems().add(mapeoTabla);
							   //productosIngresados.getItems().removeAll();
							   //productosIngresados.getItems().clear();
							   sumaTotal();		
							  
						}
						catch(Exception exs)
						{
							System.out.println("Error");
						}
					}
					else 
					 {
						String srtError="El número de productos a agregar a la factura no es válido, por favor revise...";
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaGeneral(srtError);
					 }	
				 }
				 else 
				 {
						String srtError="No ha ingresado el número de productos...";
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaGeneral(srtError);
				 }	 
			}
			}
			);
		/*** ***/
		Label lblDescripcionPro = new Label("Producto");
		lblDescripcionPro.setLayoutX(25);
		lblDescripcionPro.setLayoutY(20);
		
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(95);
		txtDescripcion.setLayoutY(15);
		
		btnAddProducto = new Button();
		btnAddProducto.setLayoutX(250);
		btnAddProducto.setLayoutY(15);
		btnAddProducto.setGraphic(bot.botonBuscar());
		btnAddProducto.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Cargando tabla de productos...");
				System.out.println("=======================================================");
				try {
					productosIngresados.getItems().removeAll();
					productosIngresados.getItems().clear();
					txtCantidad.setText("");
					productosCargados = new ProductosBO().extraeProductos(txtDescripcion.getText().toString());
					
					if (productosCargados != null && !productosCargados.isEmpty())
					{
						
						for (productoDTO obje : productosCargados) 
						{
							
							if (obje != null) 
							{
								productoDTO objeto = new productoDTO();
								objeto.setIdProducto(obje.getIdProducto());
								objeto.setNombreProducto(obje.getNombreProducto());
								objeto.setDescripcion(obje.getDescripcion());
								objeto.setValorVenta(obje.getValorVenta());
								objeto.setStock(obje.getStock());
								objeto.setCategoria(obje.getCategoria());
								objeto.setSubcategoria(obje.getSubcategoria());
								productosIngresados.getItems().add(objeto);
							}
						}
					}else
					{	
						String srtError="Producto no existe en stock ";
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaGeneral(srtError);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		});
		
		//btnAddProducto.setFont(new Font("Arial",15));
		//btnAddProducto.setPrefSize(150, 30);
		
		AnchorPane datosProductos = new AnchorPane();
		datosProductos.getChildren().addAll(productosIngresados,
											lblDescripcionPro,
											txtDescripcion,
											btnAddProducto,
											lblCantidad,
											txtCantidad
											
											);
		datosProductos.setBorder(new Border(new BorderStroke(Color.DARKGREEN, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		datosProductos.setPadding(new Insets(5));
		datosProductos.setTranslateX(580);
		datosProductos.setTranslateY(20);
		datosProductos.setTranslateZ(20);
		datosClientes.setMaxSize(450, 250);
		/*** ***/
		
        root.getChildren().addAll(bp,
        						  datosClientes,
        						  datosListaFactura,
        						  datosProductos
        						  );
        root.getChildren().addAll(
        						  //tableFacturacion,
        						  
        						  );
		Scene escenaConsulta = null;
		escenaConsulta = new Scene(root, 1025, 600);
		escenaConsulta.setFill(Color.TRANSPARENT);
		ventanaActual.setTitle("Control de ventas");
		ventanaActual.setScene(escenaConsulta);
		//ventanaActual.setResizable(false);
		ventanaActual.show();

	}
	
	
	public boolean verificaCampos(String strNombres, String srtDireccion, String strApellidos, String strTelefono)
	{
		boolean blData=false;
		String srtError="Faltan datos del Cliente, por favor revise: ";
		String srtConcatenaERR="";
		alertasMensajes alerta = new alertasMensajes();
		
		if (strNombres.isEmpty())
		{
			srtConcatenaERR = srtConcatenaERR+ " Nombres  ";
		}
		
		if (srtDireccion.isEmpty())
		{
			srtConcatenaERR = srtConcatenaERR+ "- Dirección  ";
		}
		if (strApellidos.isEmpty())
		{
			srtConcatenaERR = srtConcatenaERR+ "- Apellidos  ";
		}
		if (strTelefono.isEmpty())
		{
			srtConcatenaERR = srtConcatenaERR+ "- Teléfono  ";
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
	
	
	
	
	/*** ***/

	/*** ***/
	
	
	public void cargaProductoTabla(String strProducto)
	{
		String strCondicion=null;
		strCondicion = strProducto;
		String [] separaObjetos = null; 
		separaObjetos = strCondicion.split(" - ");
		String strParametro = separaObjetos[0];
		System.out.println("Que tiene:"+strParametro);
		for (ProductosDTO obje : productos) 
		{
			
			if (obje != null) 
			{
				if (obje.getDescripcion().equals(strParametro))
				{	
					System.out.println(obje.getIdProducto() + " " +obje.getDescripcion() +obje.getPrecio());
					int cantidad=0;
					cantidad = Integer.parseInt(txtCantidad.getText().toString());
					ProductosBO llenaCampo = new ProductosBO();
					tablaFacturaDet mapeoTabla = new tablaFacturaDet();
					//mapeoTabla=llenaCampo.valoresTabla(cantidad, obje.getDescripcion(),obje.getValorUnitario());
					//tableFacturacion.getItems().add(mapeoTabla);
					
				}
			}	
		}	
		btnAdd.setDisable(false);
		
	}
	
	public void   exitProductoTabla(FacturaDtlDTO objProducto)
	{
		System.out.println("==================================================");
		System.out.println(" Eliminando producto ...");
		System.out.println("==================================================");
		productosFacturar.getItems().remove(objProducto);
		
	}
	
	public void traeProductos(String strProducto)
	{
		System.out.println("==================================================");
		System.out.println(" Agregando productos al combo..."+strProducto);
		System.out.println("==================================================");
		try {
			/*productos = new ProductosBO().extraeProductos(strProducto);
			if (productos != null && !productos.isEmpty())
			{
				
				for (ProductosDTO obje : productos) 
				{
					
					if (obje != null) 
					{
					System.out.println(obje.getIdProducto() + " " +obje.getDescripcion()+ " " +obje.getPrecio());
					comboProductos.getItems().add(obje.getDescripcion()+" - "+obje.getStock());
					}
				}	
			}else 
			{
				//MOSTAR MENSAJE POR PANTALLA
				String srtError="Producto no existe en stock, por favor ingrese otra descripción : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}*/
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void seteaCampos(List<ClientesDTO> clientes)
	{
		//txtRuc,txtCliente,txtDireccion,txtApellidos,txtTelefono,txtCorreo;
		for (ClientesDTO obj : clientes) 
		{
			
			if (obj != null) 
			{
			txtCliente.setText(obj.getNombres());	
			txtDireccion.setText(obj.getDireccion());
			txtApellidos.setText(obj.getPrimerApellido()+" "+obj.getSegundoApellido());
			txtTelefono.setText(obj.getTelefono());
			txtCliente.setText(obj.getNombres());
			txtCorreo.setText(obj.getCorreo());
			comboProductos.setValue(null);
			}
		}	
	}
	
	public void seteaCamposNCL()
	{
		//txtRuc,txtCliente,txtDireccion,txtApellidos,txtTelefono,txtCorreo;
			txtCliente.setText("");	
			txtDireccion.setText("");
			txtApellidos.setText("");
			txtTelefono.setText("");
			txtCorreo.setText("");
	
	}
	
	public String obtieneDosDecimales(float valor){
	    DecimalFormat format = new DecimalFormat();
	    format.setMaximumFractionDigits(2); //Define 2 decimales.
	    return format.format(valor);
	}
	
	public void sumaTotal()
	{
		totalFacturar=0;
		productosFacturar.getItems().forEach(productos -> totalFacturar = totalFacturar +productos.getTotal());
		String txtFacturaSubTotal=Float.toString(totalFacturar);
		Numeros_a_Letras conversionSTR= new Numeros_a_Letras();
		//txtSubtotal,txtIva,txtIvaDoce,txtTotal;
		txtSubtotal.setText(txtFacturaSubTotal);
		txtIva.setText("0");
		//Calculo del IVA 12% 
		float flIva=0;
		flIva=(totalFacturar*12)/100;
		String txtFacturaIvaDoce=Float.toString(flIva);
		txtIvaDoce.setText(txtFacturaIvaDoce);
		//total
		float factTotal=0;
		factTotal=totalFacturar+flIva;
		//String resultado=
		String totalPago=obtieneDosDecimales(factTotal);
		txtTotal.setText(totalPago);
		//Total en letras
		String retornoLetra=conversionSTR.Convertir(totalPago,true);
		txtCantidadString.setText(retornoLetra);
		
	}
	
	
	
	public void habilitaCampos() 
	{
		txtCliente.setEditable(true);	
		txtDireccion.setEditable(true);
		txtApellidos.setEditable(true);
		txtTelefono.setEditable(true);
		txtCliente.setEditable(true);
		
		
		txtCantidad.setEditable(true);
		btnAdd.setDisable(false);
	}
	public void deshabilitaCampos() 
	{
		txtCliente.setEditable(false);	
		txtDireccion.setEditable(false);
		txtApellidos.setEditable(false);
		txtTelefono.setEditable(false);
		txtCliente.setEditable(false);
	
		txtCantidad.setEditable(true);
		btnAdd.setDisable(true);
	}
	public void inilicializaCampos()
	{
		txtCliente.setText(null);
		txtDireccion.setText(null);
		txtApellidos.setText(null);
		txtTelefono.setText(null);
		txtCorreo.setText(null);
		
	}
	public List<ClientesDTO> consultarCliente(String strIdentificacion) 
	{
		try 
		{
			System.out.println("--------------------------------------------------");
			System.out.println("Consultando a la lista...");
			System.out.println("--------------------------------------------------");
			List<ClientesDTO> lista=null; 
			lista = new ClientesBO().consultaCliente(strIdentificacion);
			/*if (lista != null && !lista.isEmpty())
			{
				for (ClientesDTO obj : lista) {
					
					if (obj != null) {
					System.out.println(obj.getDireccion());	
					}
				}
			}else 
			{
				//MOSTAR MENSAJE POR PANTALLA
				System.out.println("NO HAY DATOS");
			}*/
			if (lista != null && !lista.isEmpty())
				return lista;
			else 
			{	
				String srtError="Cliente no existe, se prodecerá a registrarlo...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return null;
		}
		return null;
		
	}
	
	
	
	public void handle(ActionEvent event) 
	{
		if (event.getSource() == btnAdd) 
		{
			System.out.println("==================================================");
	    	System.out.println("Verificando que todos los campos esten llenos...");
	    	System.out.println("==================================================");
	    	boolean boolVerifica=false;
	    	System.out.println("Ver: "+boolVerifica+"  "+txtCliente.getText());
	    	String strNombres=txtCliente.getText().toString();
	    	System.out.println("==================================================");
	    	boolVerifica=verificaCampos(txtCliente.getText().toString(),txtDireccion.getText().toString(),txtApellidos.getText().toString(),txtTelefono.getText().toString());
	    	
	    	if (!boolVerifica )
	    	{	
				
				try
				{
					System.out.println("==================================================");
					System.out.println(" Confirmacion de facturación...");
					System.out.println("==================================================");
					String Alerta="Mensaje de confirmación";
					String Contenido="Esta seguro que desea generar la factura?...";
					alertasMensajes msjError = new alertasMensajes(); 
					Optional<ButtonType> option = msjError.opcionConfirmacion(Alerta, Contenido);
					
					if (option.get() == null) 
					{
				         System.out.println("No selecciono nada!");
				     } 
					else 
					{	if (option.get() == ButtonType.OK) 
						{
						 System.out.println("==================================================");
				    	 System.out.println("Creando factura...");
				    	 System.out.println("==================================================");
				        } 
						else if (option.get() == ButtonType.CANCEL) 
						{
						  System.out.println("==================================================");
				    	  System.out.println("Cancelando factura");
				    	  System.out.println("==================================================");
						}
			         }
			    }
				catch(Exception exc)
				{
				    System.out.println("==================================================");
					System.out.println("Error msj confirmacion...");
					System.out.println("==================================================");
				}
	    	}
	    	else 
	    	{
	    		System.out.println("==================================================");
				System.out.println("Faltan datos...");
				System.out.println("==================================================");
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
		else if ( event.getSource() == btnAddProducto ) 
		   {
				System.out.println("==================================================");
		    	System.out.println("	Agregar producto...");
		    	System.out.println("==================================================");
		    	productosPrincipal productos = new productosPrincipal();
		    	productoDTO productoAdd = new productoDTO();
		    	productoAdd = productos.consultaProductoCliente();
		    	System.out.println("Que trae desde el prodcuto"+ productoAdd.getDescripcion());
		   }
		
		
	}	
}

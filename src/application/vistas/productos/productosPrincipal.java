package application.vistas.productos;

import java.sql.SQLException;
import java.util.List;
import application.Principal;
import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.ProductosDTO;
import application.com.DTOS.productosCategoriaDTO;
import application.com.DTOS.productosDescripcionDTO;
import application.extras.botones;
import application.tablas.tablaFacturaDet;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class productosPrincipal implements EventHandler<ActionEvent> 
{
	public TextField txtNProducto, txtDescripcion, txtPrecioCompra, txtValorPorcentual, txtPrecioVta, txtStock, txtValorU;
	
	public TextField txtProNew, txtCatNew;
	
	public Button btnGuardar, btnBuscar,btnExit, btnPrecio, btnGuardarCategoria;
	public Stage ventanaActual;
	
	public TableView<tablaFacturaDet> tableProductos = new TableView();
	
	public TableView<productosDescripcionDTO> tableDescProductos = new TableView();
	public TableColumn<productosDescripcionDTO, String> descripcion = new TableColumn<>("Descripción");
		
	public TableColumn<tablaFacturaDet, String> idTable = new TableColumn<>("Cod. Artículo");
	public TableColumn<tablaFacturaDet, String> Nombre = new TableColumn<>("Descripción");
	public TableColumn<tablaFacturaDet, String> Desc = new TableColumn<>("Valor unitario");
	public TableColumn<tablaFacturaDet, String> Stock = new TableColumn<>("Stock");
	public List<ProductosDTO> productos=null;
	public List<productosCategoriaDTO> categoriaProductos=null;
	public List<productosDescripcionDTO> subCategoriaProductos=null;
	
	
	public String productoGeneral = null, productoEspecifico=null;
	/*** ingreso de productos ***/
	public ObservableList<String> Contenido= 
			    FXCollections.observableArrayList (
			            "Nuevo producto"
			        );;
	public ObservableList<String> ContenidoDesc= 
				FXCollections.observableArrayList (
						);;			        
			        
						
	public ComboBox<String> comboProductosCat= new ComboBox<String>(Contenido);
	
	public ComboBox<String> comboProCatDescripcion= new ComboBox<String>(ContenidoDesc);
   /*** FIn ingreso de productois ***/		 
	

	public void ingresoProductos(Stage ventanaIngreso) 
	{
		
		ventanaActual = ventanaIngreso;
		Text scenetitle = new Text("Seccion Productos.");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		//scenetitle.setFont(new Font("Arial",20));
		/*** Nuevos Cambios para el ingreso del producto***/
		botones b = new botones();
		btnGuardarCategoria = new Button("Guardar");
		btnGuardarCategoria.setLayoutX(120);
		btnGuardarCategoria.setLayoutY(140);
		btnGuardarCategoria.setGraphic(b.botonGuardar());
		btnGuardarCategoria.setOnAction(this);
		btnGuardarCategoria.setVisible(false);
		
		Label lblNProducto = new Label("Nombre producto");
		lblNProducto.setLayoutX(30);
		lblNProducto.setLayoutY(210);
		lblNProducto.setVisible(false);
	    txtNProducto = new TextField();
		txtNProducto.setLayoutX(130);
		txtNProducto.setLayoutY(205);
		txtNProducto.setPrefSize(100, 25);
		txtNProducto.setVisible(false);
		
		Label lblDescripcion = new Label("Descripción");
		lblDescripcion.setLayoutX(240);
		lblDescripcion.setLayoutY(210);
		lblDescripcion.setVisible(false);
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(310);
		txtDescripcion.setLayoutY(205);
		txtDescripcion.setPrefSize(220, 25);
		txtDescripcion.setVisible(false);
		
		Label lblPCompra = new Label("Precio compra");
		lblPCompra.setLayoutX(30);
		lblPCompra.setLayoutY(240);
		lblPCompra.setVisible(false);
		txtPrecioCompra = new TextField();
		txtPrecioCompra.setLayoutX(130);
		txtPrecioCompra.setLayoutY(235);
		txtPrecioCompra.setPrefSize(60, 25);
		txtPrecioCompra.setVisible(false);
		txtPrecioCompra.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                	txtPrecioCompra.setText(oldValue);
                }
            }
        });
		
		Label lblStock = new Label("# Productos");
		lblStock.setLayoutX(220);
		lblStock.setLayoutY(240);
		lblStock.setVisible(false);
		txtStock = new TextField();
		txtStock.setLayoutX(300);
		txtStock.setLayoutY(235);
		txtStock.setPrefSize(60, 25);
		txtStock.setVisible(false);
		txtStock.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
                	txtStock.setText(oldValue);
                }
            }
        });
		
		
		Label lblPorcentaje = new Label("Porcentaje[%]");
		lblPorcentaje.setLayoutX(380);
		lblPorcentaje.setLayoutY(240);
		lblPorcentaje.setVisible(false);
		txtValorPorcentual = new TextField();
		txtValorPorcentual.setLayoutX(462);
		txtValorPorcentual.setLayoutY(235);
		txtValorPorcentual.setPrefSize(35, 25);
		txtValorPorcentual.setVisible(false);
		txtValorPorcentual.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,0})?")) {
                	txtValorPorcentual.setText(oldValue);
                }
            }
        });
		
		btnPrecio = new Button();
		btnPrecio.setGraphic(b.botonCalcular());
		btnPrecio.setLayoutX(500);
		btnPrecio.setLayoutY(235);
		btnPrecio.setPrefSize(15, 20);
		btnPrecio.setVisible(false);
		btnPrecio.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Calculando...");
				System.out.println("=======================================================");
				if ( txtPrecioCompra.getText().toString().trim().isEmpty() &&  txtValorPorcentual.getText().toString().trim().isEmpty())
				{
					alertasMensajes alerta = new alertasMensajes();
					String srtError="Faltan datos por ingresar.. ";
					alerta.alertaGeneral(srtError);
				}
				else 
				{ 
					float precioCompra=0;
					precioCompra = Float.parseFloat(txtPrecioCompra.getText().toString().trim());
					float calculoPorcentaje = 0, porcentaje = 0;
					porcentaje = Float.parseFloat(txtValorPorcentual.getText().toString().trim());
					calculoPorcentaje = ((precioCompra * porcentaje )/100)+precioCompra;
					String resultado=null;
					resultado = resultado.valueOf(calculoPorcentaje);
					txtPrecioVta.setText(resultado);
				}
			}
				
		});
		
		Label lblPVta = new Label("Precio Vta");
		lblPVta.setLayoutX(380);
		lblPVta.setLayoutY(270);
		lblPVta.setVisible(false);
		txtPrecioVta = new TextField();
		txtPrecioVta.setLayoutX(465);
		txtPrecioVta.setLayoutY(265);
		txtPrecioVta.setPrefSize(65, 25);
		txtPrecioVta.setVisible(false);
		txtPrecioVta.setEditable(false);
		
		/*** ***/
		Label lblCat = new Label("Categoría");
		lblCat.setLayoutX(250);
		lblCat.setLayoutY(60);
		lblCat.setVisible(false);
		txtProNew = new TextField();
		txtProNew.setLayoutX(320);
		txtProNew.setLayoutY(55);
		txtProNew.setVisible(false);
		
		Label lblSubProd = new Label("SubCategoria");
		lblSubProd.setLayoutX(250);
		lblSubProd.setLayoutY(60);
		lblSubProd.setVisible(false);
		comboProCatDescripcion.setLayoutX(330);
		comboProCatDescripcion.setLayoutY(55);
		comboProCatDescripcion.setVisible(false);
		
		
		Label lblCatMin = new Label("Medida");
		lblCatMin.setLayoutX(20);
		lblCatMin.setLayoutY(90);
		lblCatMin.setVisible(false);
		txtCatNew = new TextField();
		txtCatNew.setLayoutX(90);
		txtCatNew.setLayoutY(85);
		txtCatNew.setVisible(false);
	
		botones btna = new botones();
		Button btnAddMedida = new Button();
		btnAddMedida.setLayoutX(260);
		btnAddMedida.setLayoutY(90);
		btnAddMedida.setVisible(false);
		btnAddMedida.setGraphic(btna.botonAgregarLista());
		btnAddMedida.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("=======================================================");
				System.out.println("Guardando en la tabla...");
				System.out.println("=======================================================");
				productosDescripcionDTO producto = new  productosDescripcionDTO();
				
				if (! txtCatNew.getText().isEmpty() )
				{	btnGuardarCategoria.setVisible(true);
					producto.setDescripcion(txtCatNew.getText().toString().trim());
					tableDescProductos.getItems().add(producto);
					txtCatNew.setText("");
				}
				else 
				{
					String srtError="Debe ingresar la unidade de medida";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				}
			}
				
		});
					
		btnGuardar = new Button("Guardar");
		btnGuardar.setLayoutX(200);
		btnGuardar.setLayoutY(300);
		btnGuardar.setGraphic(btna.botonGuardar());
		btnGuardar.setVisible(false);
		btnGuardar.setOnAction(this);
		
		Label lblDe = new Label("Producto");
		lblDe.setLayoutX(20);
		lblDe.setLayoutY(60);
		comboProductosCat.setLayoutX(90);
		comboProductosCat.setLayoutY(55);
		
		comboProductosCat.valueProperty().addListener((ov, p1, p2) -> 
		{	
				
			System.out.println("Producto --> " + p2);
			productoGeneral=null;
			productoGeneral=p2;
			System.out.println("================================================================================");
			System.out.println(" Agregando producto a la tabla...");
			System.out.println("================================================================================");
			if (p2 == "Nuevo producto")
			{
				System.out.println(" Validacion...");
				lblCat.setVisible(true);
				txtProNew.setText("");
				txtProNew.setVisible(true);
				tableDescProductos.setVisible(true);
				lblCatMin.setVisible(true);
				txtCatNew.setVisible(true);
				btnAddMedida.setVisible(true);
				tableDescProductos.getItems().removeAll();
				tableDescProductos.getItems().clear();
				btnGuardarCategoria.setVisible(true);
				
				lblSubProd.setVisible(false);
				comboProCatDescripcion.setVisible(false);
				
				lblNProducto.setVisible(false);
				lblDescripcion.setVisible(false);
				lblPCompra.setVisible(false);
				lblStock.setVisible(false);
				lblPorcentaje.setVisible(false);
				lblPVta.setVisible(false);
				txtNProducto.setVisible(false);
				txtDescripcion.setVisible(false);
				txtPrecioCompra.setVisible(false);
				txtStock.setVisible(false);
				txtValorPorcentual.setVisible(false);
				btnPrecio.setVisible(false);
				txtPrecioVta.setVisible(false);
				
			}
			else 
			{
				lblCat.setVisible(false);
				txtProNew.setVisible(false);
				tableDescProductos.setVisible(false);
				lblCatMin.setVisible(false);
				txtCatNew.setVisible(false);
				btnAddMedida.setVisible(false);
				btnGuardarCategoria.setVisible(false);
				lblSubProd.setVisible(true);
				comboProCatDescripcion.setVisible(true);
				
				
				traeProdSubCat(p2);
				
				ContenidoDesc.add("Nuevo subproducto");
				System.out.println("Cantidad de combos; "+comboProCatDescripcion.getItems().size());
				if ( comboProCatDescripcion.getItems().size() >0 )
				{
					lblNProducto.setVisible(true);
					lblDescripcion.setVisible(true);
					lblPCompra.setVisible(true);
					lblStock.setVisible(true);
					lblPorcentaje.setVisible(true);
					lblPVta.setVisible(true);
					txtNProducto.setVisible(true);
					txtDescripcion.setVisible(true);
					txtPrecioCompra.setVisible(true);
					txtStock.setVisible(true);
					txtValorPorcentual.setVisible(true);
					btnPrecio.setVisible(true);
					txtPrecioVta.setVisible(true);
				}	
			}
		});
		
		comboProCatDescripcion.valueProperty().addListener((ov, p1, p2) -> 
		{	
			productoEspecifico = null ;
			productoEspecifico = p2;
			if (p2 == "Nuevo subproducto")
			{
				System.out.println("Nuevo subproducto");
				lblCatMin.setVisible(true);
				txtCatNew.setText("");
				txtCatNew.setVisible(true);
				btnAddMedida.setVisible(true);
				tableDescProductos.setVisible(true);
				tableDescProductos.getItems().removeAll();
				tableDescProductos.getItems().clear();
				btnGuardarCategoria.setVisible(true);
			}
			else
			{	lblCatMin.setVisible(false);
				txtCatNew.setVisible(false);
				btnAddMedida.setVisible(false);
				tableDescProductos.setVisible(false);
				btnGuardarCategoria.setVisible(false);
				
				System.out.println("SubProducto --> " + p2);
				lblNProducto.setVisible(true);
				lblDescripcion.setVisible(true);
				lblPCompra.setVisible(true);
				lblStock.setVisible(true);
				lblPorcentaje.setVisible(true);
				lblPVta.setVisible(true);
				txtNProducto.setVisible(true);
				txtDescripcion.setVisible(true);
				txtPrecioCompra.setVisible(true);
				txtStock.setVisible(true);
				txtValorPorcentual.setVisible(true);
				btnPrecio.setVisible(true);
				txtPrecioVta.setVisible(true);
			
			}
			
		});
		
		traeProductosCategoria();
		descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		descripcion.setMinWidth(130);
		tableDescProductos.getColumns().add(descripcion);
		tableDescProductos.setLayoutX(320);
		tableDescProductos.setLayoutY(90);
		tableDescProductos.setPrefSize(130, 90);
		tableDescProductos.setVisible(false);
		
		
	     /*** ***/
		
		 /*** Fin nuevos cambios apra el ingreso del producto ***/ 
		btnBuscar = new Button("");
		btnBuscar.setLayoutX(472);
		btnBuscar.setLayoutY(53);
		btnBuscar.setPrefSize(25, 25);
		btnBuscar.setOnAction(this);
		
		btnExit = new Button("Ir a Inicio");
		btnExit.setLayoutX(450);
		btnExit.setLayoutY(370);
		btnExit.setGraphic(btna.botonRegresar());
		btnExit.setOnAction(this);

        Group rootIngreso = new Group();
        
        Image imgCarga = new Image("application/madmenmag-fondo-verano-agua.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp  = new BorderPane();
		
		bp.setCenter(imgView);
		rootIngreso.getChildren().addAll(bp,
										 scenetitle,
										 lblDe,
										 lblCat,
										 txtProNew,
										 btnExit,
										 tableDescProductos,
										 lblCatMin,
										 txtCatNew,
										 btnAddMedida,
										 btnGuardar,
										 btnGuardarCategoria,
										 comboProductosCat,
										 lblSubProd,
										 comboProCatDescripcion
										 );
		
		rootIngreso.getChildren().addAll(	lblNProducto,
											lblDescripcion,
											lblPCompra,
											lblStock,
											lblPorcentaje,
											lblPVta,
											txtNProducto, 
											txtDescripcion,
											txtPrecioCompra,
											txtStock,
											txtValorPorcentual,
											btnPrecio,
											txtPrecioVta
										);
		
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 560, 600);
		
		ventanaActual.setTitle("Ingreso de productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.show();

	}
	/*** ***/
	public void creacionCategoria()
	{
		
		txtProNew.setVisible(true);
	}
	public void traeProductosCategoria()
	{
		System.out.println("==================================================");
		System.out.println(" Agregando productos al combo...");
		System.out.println("==================================================");
		try {
			categoriaProductos = new ProductosBO().extraeProductosCAT();
			if (categoriaProductos != null && !categoriaProductos.isEmpty())
			{
				
				for (productosCategoriaDTO obje : categoriaProductos) 
				{
					
					if (obje != null) 
					{
					
						comboProductosCat.getItems().add(obje.getDescripcion());
						System.out.println("Id de la categoria: "+obje.getId_producto_cat());
					}
				}	
			}else 
			{
				//MOSTAR MENSAJE POR PANTALLA
				String srtError="Producto no existe en stock, por favor ingrese otra descripción : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*** INICIO CONSULTA DE PRODUCTOS SUB ***/
	
	public void traeProdSubCat(String idCategoria)
	{
		System.out.println("==================================================");
		System.out.println(" Agregando productos al comboSubCategoria...");
		System.out.println("==================================================");
		
		try {
			subCategoriaProductos = new ProductosBO().extraeProdCatSub(idCategoria);
			//comboProCatDescripcion.setItems(null);
			if (subCategoriaProductos != null && !subCategoriaProductos.isEmpty())
			{
				ContenidoDesc.clear();
				for (productosDescripcionDTO obje : subCategoriaProductos) 
				{
					
					if (obje != null) 
					{
					
						comboProCatDescripcion.getItems().add(obje.getDescripcion());
						System.out.println(obje.getDescripcion() + " - "+obje.getId_producto_categoria() +" - "+ obje.getId_des_producto());
						
					}
				}
				
			}else 
			{
				//MOSTAR MENSAJE POR PANTALLA
				String srtError="Producto no existe en stock, por favor ingrese otra descripción : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*** FIN CONSULTA DE PRODUCTOS SUB ***/
	
	/*** INICIO guardando categoria del producto ***/
	public void insertaProductosCategoria(String Descripcion)
	{
		System.out.println("==================================================");
		System.out.println(" Guardando categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		try {
			resInsert = new ProductosBO().insertaCategoriaProd(Descripcion);
			if ( resInsert != 0 || resInsert != -1)
			{	
				insertaProductosDetalle(resInsert);
			}	
			else 
			{
				//MOSTAR MENSAJE POR PANTALLA
				String srtError="Categoría no se pudo insertar : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*** INICIO guardando categoria del producto ***/
	public void insertaProductosDetalle(int idCat)
	{
		System.out.println("==================================================");
		System.out.println(" Guardando Sub-categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		try
		{
		for (int i = 0; i < tableDescProductos.getItems().size(); i++)
		{	String descripcion = null;
			descripcion =tableDescProductos.getItems().get(i).getDescripcion();
			resInsert = new ProductosBO().insertaSubCatProd(idCat, descripcion);
		    System.out.println(tableDescProductos.getItems().get(i).getDescripcion());
		    if ( resInsert != 1)
			{	
				String srtError="La subcategoría " + descripcion  +  "no se pudo ingresar : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	/*** FIN guardando categoria del producto ***/
	
	/*** INICIO guardando solo subcategoria del producto ***/
	public void insertaProductosDet(String nomCat)
	{
		System.out.println("==================================================");
		System.out.println(" Guardando solo Sub-categoria...");
		System.out.println("==================================================");
		int resInsert = 0;
		try
		{
		for (int i = 0; i < tableDescProductos.getItems().size(); i++)
		{	String descripcion = null;
			descripcion =tableDescProductos.getItems().get(i).getDescripcion();
			resInsert = new ProductosBO().insertaSubProducto( descripcion, nomCat);
		    System.out.println(tableDescProductos.getItems().get(i).getDescripcion());
		    if ( resInsert != 1)
			{	
				String srtError="La subcategoría " + descripcion  +  "no se pudo ingresar : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				System.out.println("NO HAY DATOS");
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	/*** FIN guardando solo subcategoria del producto ***/
	

	public void consultaProductos(Stage ventanaParam) 
	{
		ventanaActual = ventanaParam; 
		Text scenetitle = new Text("CONSULTA DE PRODUCTOS");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		scenetitle.setX(180);
		scenetitle.setY(40);
		//scenetitle.setFont(new Font("Arial",20));
        
		Label lblDescripcion = new Label("Ingrese el producto y presione enter");
		lblDescripcion.setLayoutX(160);
		lblDescripcion.setLayoutY(60);
		lblDescripcion.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		txtDescripcion = new TextField();
		txtDescripcion.setLayoutX(160);
		txtDescripcion.setLayoutY(90);
		txtDescripcion.setPrefSize(330, 25);
		
		/*INICIO
		 CARGA DE PRODUCTOS A LA TABLA*/
		txtDescripcion.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent ke) 
			{
				if (ke.getCode().equals(KeyCode.ENTER)) 
				{
					try{
						tableProductos.getItems().removeAll();
						tableProductos.getItems().clear();
						System.out.println("================================================================================");
						System.out.println(" CARGA DE PRODUCTOS A LA TABLA");
						System.out.println("================================================================================");
						//cargaProductoTabla(txtConsulta.getText().toString());
						/*Metodo que traera de base todos los productos existentes [INICIO]*/
						String strCondicion = "";
						strCondicion = txtDescripcion.getText().toString().trim();
						cargaProductoTabla(strCondicion);
					}catch(Exception exs)
					{
						System.out.println("====================================");
					}
						/*Metodo que traera de base todos los productos existentes [FIN]*/
					//}
							
				}
			}
		});
		/*FIN
		 CARGA DE PRODUCTOS A LA TABLA*/
		
        Group rootConsulta = new Group();
        Image imgCarga = new Image("application/1.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp  = new BorderPane();
		bp.setCenter(imgView);
		
		idTable.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
		Nombre.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		Desc.setCellValueFactory(new PropertyValueFactory<>("ValorUnitario"));
		Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		
		idTable.setMinWidth(50);
		Nombre.setMinWidth(200);
		Desc.setMinWidth(150);
		Stock.setMinWidth(50);
		
		tableProductos.getColumns().addAll(idTable, Nombre, Desc, Stock);
		
		tableProductos.setLayoutX(40);
		tableProductos.setLayoutY(130);
		tableProductos.setPrefSize(510, 200);
		
		btnExit = new Button("Ir al menú ");
		btnExit.setLayoutX(520);
		btnExit.setLayoutY(360);
		btnExit.setOnAction(this);
		
		rootConsulta.getChildren().addAll(bp, scenetitle, lblDescripcion, txtDescripcion,tableProductos,btnExit);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootConsulta, 590, 400);
		//escenaConsulta.setFill(null);
		//Stage VentanaConsultasProductos;
		//VentanaConsultasProductos = new Stage();
		ventanaActual.setTitle("Productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		//ventanaActual.initModality(Modality.APPLICATION_MODAL);
		//VentanaConsultasProductos.initStyle(StageStyle.UNDECORATED);
		//VentanaConsultasProductos.initStyle(StageStyle.UNIFIED);
		/*VentanaConsultasProductos.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override public void handle(WindowEvent event) {
                           //Consumar el evento
            }  
        });*/
		//VentanaConsultasProductos.resizableProperty().setValue(Boolean.FALSE);
		ventanaActual.show();
		
	}
	/*** FIN CONSULTA DE PRODUCTOS ***/
	
	/*** MODIFICACION DE PRODUCTOS***/
	public void updateProductos(Stage ventanaParam) 
	{
		ventanaActual = ventanaParam; 
		Label lblDescripcion = new Label("Ingrese el producto y presione enter");
		lblDescripcion.setLayoutX(160);
		lblDescripcion.setLayoutY(60);
		lblDescripcion.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		btnExit = new Button("Ir a Inicio");
		btnExit.setLayoutX(520);
		btnExit.setLayoutY(370);
		//btnExit.setPrefSize(25, 25);
		btnExit.setOnAction(this);
	    Group rootModifica = new Group();
	    Image imgCarga = new Image("application/1.jpg"); 
		ImageView imgView = new ImageView(imgCarga);
		BorderPane bp  = new BorderPane();
		bp.setCenter(imgView);
		
		rootModifica.getChildren().addAll(bp, lblDescripcion,btnExit);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootModifica, 590, 400);
		//escenaConsulta.setFill(null);
		//Stage VentanaConsultasProductos;
		//VentanaConsultasProductos = new Stage();
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setTitle("Productos");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		//VentanaConsultasProductos.initModality(Modality.APPLICATION_MODAL);
		////VentanaConsultasProductos.initStyle(StageStyle.UNDECORATED);
	    //VentanaConsultasProductos.initStyle(StageStyle.UNIFIED);
		/*VentanaConsultasProductos.setOnCloseRequest(new EventHandler<WindowEvent>(){
	        @Override public void handle(WindowEvent event) {
	                       //Consumar el evento
	        }  
	    });*/
		ventanaActual.resizableProperty().setValue(Boolean.FALSE);
		ventanaActual.show();
		
	}
	
	
	/*** FIN MODIFICACION DE PRODUCTOS***/
	

	public void cargaProductoTabla(String strProducto) throws SQLException
	{
		try {
			tableProductos.getItems().removeAll();
			tableProductos.getItems().clear();
			productos = new ProductosBO().extraeProductos(strProducto);
			if (productos != null && !productos.isEmpty())
			{
				
				for (ProductosDTO obje : productos) 
				{
					
					if (obje != null) 
					{
						tablaFacturaDet mapeoTabla = new tablaFacturaDet();
						mapeoTabla.setCantidad(obje.getIdProducto());
						mapeoTabla.setDescripcion(obje.getDescripcion());
						mapeoTabla.setValorUnitario(obje.getValorUnitario());
						mapeoTabla.setStock(obje.getStock());
						tableProductos.getItems().add(mapeoTabla);
					}
				}
			}else
			{	
				String srtError="Producto no existe en stock, Se procederá a ingresarlo : ";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void insertaProducto(String nomProd, String Descirpcion, float fltValorUni, int intStock, float fltPrecioVta)
	{
		System.out.println("================================================================================");
		System.out.println(" Ingreso de productos...");
		System.out.println("================================================================================");
		ProductosBO objInsertar = new ProductosBO();
		int resInsert=0;
		try {
			resInsert = objInsertar.insertaProductos(nomProd, Descirpcion,  fltValorUni, intStock, fltPrecioVta);
			if ( resInsert == 1)
			{	
				System.out.println("Resultado del query: "+resInsert);
				alertasMensajes alertas = new alertasMensajes();
				String strMensaje="Se ha insertado el producto:"+nomProd;
				alertas.alertaOK(strMensaje);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No entro");
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
	    	
	    	try 
	    	{
				cargaProductoTabla(strParametro);
			}
	    	catch (SQLException e) 
	    	{
				String srtError="No se pudo conectar a la base de datos...";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
			}
		}
		else
			if ( event.getSource() == btnExit ) 
		   {
				System.out.println("==================================================");
		    	System.out.println("Redirigiendose al menú principal...");
		    	System.out.println("==================================================");
		    	ventanaActual.toBack();
		    	ventanaActual.close();
		    	Principal prin = new Principal();
				prin.panelPrincipal();
		   }
		
		else
		    if ( event.getSource() == btnGuardar ) 
		   {
				System.out.println("==================================================");
		    	System.out.println(" creando categorías...");
		    	System.out.println("==================================================");
		    	
		    	if (!txtNProducto.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty() && !txtPrecioCompra.getText().toString().isEmpty()
		    			&& 	!txtStock.getText().toString().isEmpty() && !txtPrecioVta.getText().toString().isEmpty())
		    	{	
		    	
			    	boolean campos=false;
			    	float fltPrecioCompra=0, fltPrecioVta=0;
			    	int Stock=0;
			    	fltPrecioCompra = Float.parseFloat(txtPrecioCompra.getText().toString().trim());
			    	fltPrecioVta = Float.parseFloat(txtPrecioVta.getText().toString().trim());
			    	Stock=Integer.parseInt(txtStock.getText().toString().trim());	
			    	System.out.println("Antes");
			    	insertaProducto(txtNProducto.getText().toString().trim(),txtDescripcion.getText().toString().trim(), fltPrecioCompra, Stock,fltPrecioVta );
			    }
		    	else 
		    	{
		    		String srtError="Faltan datos de ingresar, por favor revise...";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
		    	}	
		   }
		    else
			    if ( event.getSource() == btnGuardarCategoria )
			    {
			    	System.out.println("k tiene "+txtProNew.getText().length() );
			    	int validaCategoria = 0;
			    	validaCategoria =txtProNew.getText().length();
			    	System.out.println("valida: "+validaCategoria);
			    	if( validaCategoria == 0 ) 
			    	{ 
			    		if(	tableDescProductos.getItems().size() <= 0 )
			    	    {
				    		String srtError="No ha ingresado la nueva categoría o subcategoría...";
							alertasMensajes alerta = new alertasMensajes();
							alerta.alertaGeneral(srtError);
			    	    }
			    		else
			    		{	
			    			System.out.println("==================================================");
			    			System.out.println(" Creando subcategoría...");
			    			System.out.println("==================================================");
			    			System.out.println(" Correcion primera..." +productoGeneral+ " - "+productoEspecifico);
			    			insertaProductosDet(productoGeneral);
			    		}
			    	}	
			    	else
			    	{	
				    	System.out.println("==================================================");
				    	System.out.println(" creando categoría y subcategoría...");
				    	System.out.println("==================================================");
				    	insertaProductosCategoria(txtProNew.getText().toString().trim());
				    	
				    	
			    	}
			    	
			    }
			
		
	}
}	

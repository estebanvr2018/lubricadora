package reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Principal;
import application.BO.ProductosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.productoDTO;
import application.extras.botones;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ProductoReporte implements EventHandler<ActionEvent> {

	public Stage ventanaActual;
	public TextField txtProducto;

	public Button btnBuscar, btnExit, btnClear, btnClearT, btnReporte;

	public GeneraReportes objGeneraReportes = new GeneraReportes();
	Map<String, Object> mapParametros = new HashMap<String, Object>();

	public TableView<productoDTO> tableProductos = new TableView();
	public TableColumn<productoDTO, String> idProducto = new TableColumn<>("Cod");
	public TableColumn<productoDTO, String> nomProducto = new TableColumn<>("Nombre");
	public TableColumn<productoDTO, String> descProducto = new TableColumn<>("Descripción");
	public TableColumn<productoDTO, String> nombreDescripcion = new TableColumn<>("Nombre Producto");
	public TableColumn<productoDTO, String> valorCompra = new TableColumn<>("V.Compra");
	public TableColumn<productoDTO, String> disponibles = new TableColumn<>("Stock");
	public TableColumn<productoDTO, String> valorVenta = new TableColumn<>("V. Venta");
	public TableColumn<productoDTO, String> categoria = new TableColumn<>("Categoría");
	public TableColumn<productoDTO, String> subcategoria = new TableColumn<>("SubCategoría");

	public void vistaProductoReporte(Stage ventanaIngreso) {

		ventanaActual = new Stage();
		Label scenetitle = new Label("Consulta de productos.");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(150);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");

		llenaTablaProductos();
		
		Label lblProd = new Label("Producto");
		lblProd.setLayoutX(100);
		lblProd.setLayoutY(55);
		lblProd.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		txtProducto = new TextField();
		txtProducto.setLayoutX(170);
		txtProducto.setLayoutY(50);
		txtProducto.setPrefSize(200, 25);

		botones b = new botones();
		btnBuscar = new Button("Buscar", b.botonBuscar());
		btnBuscar.setLayoutX(390);
		btnBuscar.setLayoutY(50);
		btnBuscar.setOnAction(this);

		btnReporte = new Button("Exportar a Excel",b.botonReporte());
		btnReporte.setLayoutX(140);
		btnReporte.setLayoutY(480);
		btnReporte.setPrefSize(140, 40);
		btnReporte.setTextFill(Color.DARKBLUE);
		btnReporte.setContentDisplay(ContentDisplay.BOTTOM);
		btnReporte.setOnAction(this);

		btnClear = new Button("Limpiar", b.botonLimpiarJPG() );
		btnClear.setLayoutX(300);
		btnClear.setLayoutY(480);
		btnClear.setTextFill(Color.DARKBLUE);
		btnClear.setPrefSize(140, 50);
		btnClear.setContentDisplay(ContentDisplay.BOTTOM);
		btnClear.setOnAction(this);
		
		
		btnClearT= new Button(); 
		btnClearT.setContentDisplay(ContentDisplay.BOTTOM);
		btnClearT.setLayoutX(470);
		btnClearT.setLayoutY(50);
		btnClearT.setPrefSize(80, 40);
		btnClearT.setVisible(false);
		btnClearT.setOnAction(this);
		
		btnExit = new Button("Regresar");
		btnExit.setGraphic(b.botonRegresar());
		btnExit.setLayoutX(470);
		btnExit.setLayoutY(500);
		btnExit.setPrefSize(140, 35);
		btnExit.setOnAction(this);

		idProducto.setCellValueFactory(new PropertyValueFactory<>("IdProducto"));
		idProducto.setMinWidth(40);
		nomProducto.setCellValueFactory(new PropertyValueFactory<>("NombreProducto"));

		nombreDescripcion.setCellValueFactory(new PropertyValueFactory<>("nombreDescripcion"));
		nombreDescripcion.setMinWidth(180);
		valorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
		valorCompra.setMinWidth(90);
		disponibles.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		disponibles.setMinWidth(40);
		valorVenta.setCellValueFactory(new PropertyValueFactory<>("valorVenta"));
		valorVenta.setMinWidth(80);
		categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		categoria.setMinWidth(80);
		subcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));
		subcategoria.setMinWidth(120);
		
		tableProductos.getColumns().addAll(idProducto, categoria, subcategoria, nombreDescripcion, valorCompra, disponibles, valorVenta);
		Label sceneTable = new Label("Productos existentes.");
		sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		sceneTable.setLayoutX(170);
		sceneTable.setLayoutY(5);
		sceneTable.setId("texto");
		
		tableProductos.setLayoutX(15);
		tableProductos.setLayoutY(45);
		tableProductos.setPrefSize(660, 250);

		
		AnchorPane datosTabla = new AnchorPane();
		datosTabla.getChildren().addAll(scenetitle,lblProd,txtProducto,btnBuscar, btnClearT// tableProductos,								
		);
		
		datosTabla.setPadding(new Insets(5));
		datosTabla.setTranslateX(20);
		datosTabla.setTranslateY(20);
		datosTabla.setTranslateZ(20);
		datosTabla.setMaxSize(650, 200);
		datosTabla.setId("colorMarco");
		
		
		AnchorPane datosCuerpo = new AnchorPane();
		datosCuerpo.getChildren().addAll(sceneTable, tableProductos// tableProductos,								
		);
		datosCuerpo.setPadding(new Insets(5));
		datosCuerpo.setTranslateX(20);
		datosCuerpo.setTranslateY(155);
		datosCuerpo.setTranslateZ(20);
		datosCuerpo.setMaxSize(680, 200);
		datosCuerpo.setId("colorMarco");
		
		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();

		bp.setCenter(b.fondoPantalla());
		
		rootIngreso.getChildren().addAll(bp, datosTabla, datosCuerpo, btnReporte,btnClear, btnExit
				 );
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 720, 550);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual.setTitle("LmLaren");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.setResizable(false);
		ventanaActual.getIcons().add(b.iconoLaren());
		ventanaActual.initModality(Modality.APPLICATION_MODAL);
		ventanaActual.show();

	}

	public void llenaTablaProductos() {
		try {
			tableProductos.getItems().removeAll();
			tableProductos.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando Productos...");
			System.out.println("================================================================================");
			List<productoDTO> lista = null;
			lista = new ProductosBO().consultaProductos();
			System.out.println("=====" +lista.size());
			if (lista != null && !lista.isEmpty()) {
				for (productoDTO obje : lista) 
				{
					if (obje != null) 
					{  productoDTO llena = new productoDTO();
						//
						llena.setIdProducto(obje.getIdProducto());
						llena.setNombreDescripcion(obje.getNombreProducto() + " - " + obje.getDescripcion());
						llena.setCategoria(obje.getCategoria());
						llena.setSubcategoria(obje.getSubcategoria());
						llena.setValorCompra(obje.getValorCompra());
						llena.setStock(obje.getStock());
						llena.setValorVenta(obje.getValorVenta());
						//productosIngresados.getItems().add(llena);
					  //	
						
						tableProductos.getItems().add(llena);
					}
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

	public void limpiarPantalla() {
		txtProducto.setText("");
		llenaTablaProductos();
	}

	public void genereReporteProducto(String strFiltro, String strTipo, Date dtFecha) {
		DateFormat formatoFecha = new SimpleDateFormat("ddMMyyyyHHmmss");
		String strFechaActual = formatoFecha.format(dtFecha);
		try {
			if (strFiltro != null) {
				mapParametros.put("filtro", strFiltro);
				String strRuta = objGeneraReportes.generaRutaReportesGeneral();
				System.out.println("Ruta del reporte: "+strRuta);
				if (strRuta != null) {
					strRuta = strRuta + "\\" + strTipo + "_" + strFechaActual + ".xls";
					JasperReport jasper = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteProductoFiltro.jasper"));
					String strError = objGeneraReportes.creaReporte(jasper, mapParametros,
							strRuta, "XLS");
					if (strError != null) {
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaGeneral(strError);
					} else {
						File ruta = new File(strRuta);
						System.out.println("ruta: " + ruta);
						strError = "El reporte se ha generado exitosamente, espere un momento mientras se abre Microsoft Excel";
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaOK(strError);
						Desktop.getDesktop().open(ruta);
					}
				}
			}
		} catch (Exception e) {
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(e.toString());
		}
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnBuscar) {
			tableProductos.getItems().removeAll();
			tableProductos.getItems().clear();
			System.out.println("==================================================");
			System.out.println("Buscando facturas...");
			System.out.println("==================================================");
			String strParametro = null;
			strParametro = txtProducto.getText().toString().trim();
			if (!strParametro.isEmpty()) {
				try {
					List<productoDTO> lista = null;
					lista = new ProductosBO().extraeProductos(strParametro);
					if (lista != null && !lista.isEmpty()) {
						for (productoDTO obje : lista) {
							if (obje != null) {
								productoDTO llena = new productoDTO();
								//
								llena.setIdProducto(obje.getIdProducto());
								llena.setNombreDescripcion(obje.getNombreProducto() + " - " + obje.getDescripcion());
								llena.setCategoria(obje.getCategoria());
								llena.setSubcategoria(obje.getSubcategoria());
								llena.setValorCompra(obje.getValorCompra());
								llena.setStock(obje.getStock());
								llena.setValorVenta(obje.getValorVenta());
								//productosIngresados.getItems().add(llena);
							  //	
								
								tableProductos.getItems().add(llena);
							}
						}
					}

				} catch (Exception e) {
					String srtError = "No se pudo conectar a la base de datos...";
					alertasMensajes alerta = new alertasMensajes();
					alerta.alertaGeneral(srtError);
				}
			} else {
				String srtError = "No ha ingresado los datos correctos";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				llenaTablaProductos();
			}

		} else if (event.getSource() == btnExit) {
			System.out.println("==================================================");
			System.out.println("Redirigiendose al menú principal...");
			System.out.println("==================================================");
			ventanaActual.toBack();
			ventanaActual.close();
			//Principal menuInicio = new Principal();
			//menuInicio.panelPrincipal();

		} else if (event.getSource() == btnClear) {
			System.out.println("==================================================");
			System.out.println("Limpiar pantalla ...");
			System.out.println("==================================================");
			limpiarPantalla();

		} else if (event.getSource() == btnReporte) {
			System.out.println("==================================================");
			System.out.println("Genera Reporte ...");
			System.out.println("==================================================");
			genereReporteProducto(txtProducto.getText().toString().trim(), "REPORTE_PRODUCTO", new Date());
			limpiarPantalla();
		}

	}

}

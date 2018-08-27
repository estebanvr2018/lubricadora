package reportes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import application.BO.ComprasBO;
import application.BO.ParametrosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.comprasDTO;
import application.extras.botones;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentasReporteGeneral implements EventHandler<ActionEvent>{

	public Stage ventanaActual;
	public DatePicker dtFechaInicio, dtFechaFin;

	public Button btnBuscar, btnExit, btnClear, btnClearT, btnReporte;

	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public comprasDTO objCompras = new comprasDTO();
	public ParametrosBO objParametros = new ParametrosBO();
	public GeneraReportes objGeneraReportes = new GeneraReportes();

	public TableView<comprasDTO> tableCompras = new TableView();
	public TableColumn<comprasDTO, String> Mes = new TableColumn<>("Mes");
	public TableColumn<comprasDTO, String> Fecha = new TableColumn<>("Fecha");
	public TableColumn<comprasDTO, String> Ruc = new TableColumn<>("Ruc");
	public TableColumn<comprasDTO, String> Proveedor = new TableColumn<>("Proveedor");
	public TableColumn<comprasDTO, String> Factura = new TableColumn<>("Factura");
	public TableColumn<comprasDTO, String> Base_0 = new TableColumn<>("Base 0%");
	public TableColumn<comprasDTO, String> Base_12 = new TableColumn<>("Base 12%");
	public TableColumn<comprasDTO, String> Iva = new TableColumn<>("Iva");
	public TableColumn<comprasDTO, String> Total = new TableColumn<>("Total");

	public List<comprasDTO> lstCompras = null;

	public void ventanaReporte(Stage ventanaIngreso) {

		ventanaActual = new Stage();
		Label scenetitle = new Label(" - Consultar Reporte General de Ventas -");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(130);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");

		Label lblInicio = new Label("Fecha Inicio");
		lblInicio.setLayoutX(40);
		lblInicio.setLayoutY(55);

		dtFechaInicio = new DatePicker();
		dtFechaInicio.setLayoutX(130);
		dtFechaInicio.setLayoutY(50);
		dtFechaInicio.setPrefSize(100, 25);

		Label lblFin = new Label("Fecha Fin");
		lblFin.setLayoutX(240);
		lblFin.setLayoutY(55);

		dtFechaFin = new DatePicker();
		dtFechaFin.setLayoutX(320);
		dtFechaFin.setLayoutY(50);
		dtFechaFin.setPrefSize(100, 25);

		botones b = new botones();
		btnBuscar = new Button("Buscar");
		btnBuscar.setGraphic(b.botonBuscar());
		btnBuscar.setLayoutX(450);
		btnBuscar.setLayoutY(50);
		btnBuscar.setOnAction(this);

		btnClear = new Button("Limpiar");
		btnClear.setGraphic(b.botonLimpiarIcono());
		btnClear.setLayoutX(590);
		btnClear.setLayoutY(50);
		btnClear.setOnAction(this);

		btnReporte = new Button("A Excel");
		btnReporte.setGraphic(b.botonReporte());
		btnReporte.setLayoutX(290);
		btnReporte.setLayoutY(540);
		btnReporte.setTextFill(Color.DARKBLUE);
		btnReporte.setContentDisplay(ContentDisplay.BOTTOM);
		btnReporte.setOnAction(this);
		
		btnClearT = new Button();
		btnClearT.setContentDisplay(ContentDisplay.BOTTOM);
		btnClearT.setLayoutX(490);
		btnClearT.setLayoutY(50);
		btnClearT.setPrefSize(80, 40);
		btnClearT.setVisible(false);

		AnchorPane datosBusqueda = new AnchorPane();
		datosBusqueda.getChildren().addAll(scenetitle, lblInicio, lblFin, dtFechaFin, dtFechaInicio, btnBuscar,
				btnClear, btnClearT// tableProductos,
		);
		datosBusqueda.setPadding(new Insets(5));
		datosBusqueda.setTranslateX(20);
		datosBusqueda.setTranslateY(20);
		datosBusqueda.setMaxSize(650, 200);
		datosBusqueda.setId("colorMarco");

		btnExit = new Button("Regresar");
		btnExit.setGraphic(b.botonRegresar());
		btnExit.setLayoutX(670);
		btnExit.setLayoutY(550);
		btnExit.setPrefSize(140, 35);
		btnExit.setOnAction(this);

		Mes.setCellValueFactory(new PropertyValueFactory<>("mesCompra"));
		Fecha.setCellValueFactory(new PropertyValueFactory<>("fechaComprasTexto"));
		Ruc.setCellValueFactory(new PropertyValueFactory<>("rucProveedor"));
		Proveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
		Factura.setCellValueFactory(new PropertyValueFactory<>("factura"));
		Base_0.setCellValueFactory(new PropertyValueFactory<>("base_0"));
		Base_12.setCellValueFactory(new PropertyValueFactory<>("base_12"));
		Iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
		Total.setCellValueFactory(new PropertyValueFactory<>("total"));

		Mes.setMinWidth(20);
		Fecha.setMinWidth(20);
		Ruc.setMinWidth(20);
		Proveedor.setMinWidth(100);
		Factura.setMinWidth(80);
		Base_0.setMinWidth(80);
		Base_12.setMinWidth(80);
		Iva.setMinWidth(80);
		Total.setMinWidth(80);

		tableCompras.getColumns().addAll(Mes, Fecha, Ruc, Proveedor, Factura, Base_0, Base_12, Iva, Total);

		tableCompras.setLayoutX(20);
		tableCompras.setLayoutY(50);
		tableCompras.setPrefSize(765, 300);

		llenaTablaVentas();

		Label sceneTable = new Label("- Ventas Realizadas -");
		sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		sceneTable.setLayoutX(250);
		sceneTable.setLayoutY(5);
		sceneTable.setId("texto");

		AnchorPane datosResultantes = new AnchorPane();
		datosResultantes.getChildren().addAll(sceneTable, tableCompras// tableProductos,
		);
		datosResultantes.setPadding(new Insets(5));
		datosResultantes.setTranslateX(20);
		datosResultantes.setTranslateY(150);
		datosResultantes.setTranslateZ(20);
		datosResultantes.setMaxSize(670, 200);
		datosResultantes.setId("colorMarco");

		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		rootIngreso.getChildren().addAll(bp, datosBusqueda, datosResultantes,btnReporte, btnExit);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 825, 620);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual.setTitle("LmLaren");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.getIcons().add(bot.iconoLaren());
		ventanaActual.initModality(Modality.APPLICATION_MODAL);
		ventanaActual.setResizable(false);
		ventanaActual.show();

	}

	public void llenaTablaVentas() {
		try {
			tableCompras.getItems().removeAll();
			tableCompras.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando Ventas...");
			System.out.println("================================================================================");
			List<comprasDTO> lista = null;
			lista = new ComprasBO().buscaComprasReporteGeneral(null, null);
			
			if (lista != null && !lista.isEmpty()) {
				
				for (comprasDTO obje : lista) {
					if (obje != null) {
						comprasDTO objcomprasDTO = new comprasDTO();
						objcomprasDTO.setMesCompra(obje.getMesCompra());
						objcomprasDTO.setFechaComprasTexto(obje.getFechaComprasTexto());
						objcomprasDTO.setRucProveedor(obje.getRucProveedor());
						objcomprasDTO.setNombreProveedor(obje.getNombreProveedor());
						objcomprasDTO.setFactura(obje.getFactura());
						objcomprasDTO.setBase_0(obje.getBase_0());
						objcomprasDTO.setBase_12(obje.getBase_12());
						objcomprasDTO.setIva(obje.getIva());
						objcomprasDTO.setTotal(obje.getTotal());
						tableCompras.getItems().add(objcomprasDTO);
					}
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

	
	public void limpiarPantalla() {
		dtFechaInicio.setValue(null);
		dtFechaFin.setValue(null);
		llenaTablaVentas();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnBuscar) {
			tableCompras.getItems().removeAll();
			tableCompras.getItems().clear();
			System.out.println("==================================================");
			System.out.println("Buscando Ventas...");
			System.out.println("==================================================");
			String strFechaInicio = "";
			String strFechaFin = "";
			LocalDate dateInicio = dtFechaInicio.getValue();
			if (dateInicio != null)
				strFechaInicio = formatter.format(dateInicio);
			LocalDate dateFin = dtFechaFin.getValue();
			if (dateFin != null)
				strFechaFin = formatter.format(dateFin);
			if (!strFechaInicio.isEmpty() && !strFechaFin.isEmpty()) {
				try {
					List<comprasDTO> lista = null;
					lista = new ComprasBO().buscaComprasReporteGeneral(strFechaInicio, strFechaFin);
					if (lista != null && !lista.isEmpty()) {
						for (comprasDTO obje : lista) {
							if (obje != null) {
								tableCompras.getItems().add(obje);
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
				llenaTablaVentas();
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
			System.out.println("Genera Reporte Ventas...");
			System.out.println("==================================================");
			ReporteGeneralVentas obj = new ReporteGeneralVentas();
			String strFechaInicio = "";
			String strFechaFin = "";
			LocalDate dateInicio = dtFechaInicio.getValue();
			if (dateInicio != null)
				strFechaInicio = formatter.format(dateInicio);
			LocalDate dateFin = dtFechaFin.getValue();
			if (dateFin != null)
				strFechaFin = formatter.format(dateFin);
			if (!strFechaInicio.isEmpty() && !strFechaFin.isEmpty()) {
				obj.genereReporteGeneralVentas(strFechaInicio, strFechaFin, "REPORTE_VENTAS", new Date());
				limpiarPantalla();
			} else {
				String srtError = "No ha ingresado los datos correctos";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				llenaTablaVentas();
			}
		}
	}
}

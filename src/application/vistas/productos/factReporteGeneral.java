package application.vistas.productos;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import application.Principal;
import application.BO.FacturaBO;
import application.BO.ParametrosBO;
import application.Dialog.alertasMensajes;
import application.com.DTOS.FacturaCabDTO;
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
import reportes.GeneraReportes;
import reportes.ReporteGeneralFactura;

public class factReporteGeneral implements EventHandler<ActionEvent> {

	public Stage ventanaActual;
	public DatePicker dtFechaInicio, dtFechaFin;

	public Button btnBuscar, btnExit, btnClear, btnClearT, btnReporte;

	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public FacturaCabDTO objFactura = new FacturaCabDTO();
	public ParametrosBO objParametros = new ParametrosBO();
	public GeneraReportes objGeneraReportes = new GeneraReportes();

	public TableView<FacturaCabDTO> tableFacturas = new TableView();
	public TableColumn<FacturaCabDTO, String> IdFactura = new TableColumn<>("Núm Factura");
	public TableColumn<FacturaCabDTO, String> tipoDoc = new TableColumn<>("Tipo Doc.");
	public TableColumn<FacturaCabDTO, String> NombreCliente = new TableColumn<>("Cliente");
	public TableColumn<FacturaCabDTO, String> IdIdentificacion = new TableColumn<>("Identificación");
	public TableColumn<FacturaCabDTO, String> FechaFactura = new TableColumn<>("Fecha Factura");
	public TableColumn<FacturaCabDTO, String> ValorTotal = new TableColumn<>("Valor Total");

	public List<FacturaCabDTO> lstFacturas = null;

	public void ventanaReporte(Stage ventanaIngreso) {

		//ventanaActual = ventanaIngreso;
		ventanaActual = new Stage();
		Label scenetitle = new Label(" - Consultar Reporte General de Facturas -");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(80);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");

		Label lblInicio = new Label("Fecha Inicio");
		lblInicio.setLayoutX(20);
		lblInicio.setLayoutY(55);

		dtFechaInicio = new DatePicker();
		dtFechaInicio.setLayoutX(110);
		dtFechaInicio.setLayoutY(50);
		dtFechaInicio.setPrefSize(100, 25);

		Label lblFin = new Label("Fecha Fin");
		lblFin.setLayoutX(220);
		lblFin.setLayoutY(55);

		dtFechaFin = new DatePicker();
		dtFechaFin.setLayoutX(300);
		dtFechaFin.setLayoutY(50);
		dtFechaFin.setPrefSize(100, 25);

		botones b = new botones();
		btnBuscar = new Button("Buscar");
		btnBuscar.setGraphic(b.botonBuscar());
		btnBuscar.setLayoutX(410);
		btnBuscar.setLayoutY(50);
		btnBuscar.setOnAction(this);

		btnClear = new Button("Limpiar");
		btnClear.setGraphic(b.botonLimpiarIcono());
		btnClear.setLayoutX(525);
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

		IdFactura.setCellValueFactory(new PropertyValueFactory<>("IdFactura"));
		tipoDoc.setCellValueFactory(new PropertyValueFactory<>("tipoDoc"));
		NombreCliente.setCellValueFactory(new PropertyValueFactory<>("NombreCliente"));
		IdIdentificacion.setCellValueFactory(new PropertyValueFactory<>("IdIdentificacion"));
		FechaFactura.setCellValueFactory(new PropertyValueFactory<>("FechaFactura"));
		ValorTotal.setCellValueFactory(new PropertyValueFactory<>("ValorTotal"));

		IdFactura.setMinWidth(120);
		tipoDoc.setMinWidth(90);
		NombreCliente.setMinWidth(200);
		IdIdentificacion.setMinWidth(120);
		FechaFactura.setMinWidth(120);
		ValorTotal.setMinWidth(100);

		tableFacturas.getColumns().addAll(IdFactura, tipoDoc, IdIdentificacion, NombreCliente, FechaFactura,
				ValorTotal);

		tableFacturas.setLayoutX(20);
		tableFacturas.setLayoutY(50);
		tableFacturas.setPrefSize(765, 300);

		llenaTablaFacturas();

		Label sceneTable = new Label("- Facturaciones realizadas -");
		sceneTable.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		sceneTable.setLayoutX(220);
		sceneTable.setLayoutY(5);
		sceneTable.setId("texto");

		AnchorPane datosResultantes = new AnchorPane();
		datosResultantes.getChildren().addAll(sceneTable, tableFacturas// tableProductos,
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

	public void llenaTablaFacturas() {
		try {
			tableFacturas.getItems().removeAll();
			tableFacturas.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando Facturas...");
			System.out.println("================================================================================");
			List<FacturaCabDTO> lista = null;
			lista = new FacturaBO().buscaFacturasReporteGeneral(null, null);
			
			if (lista != null && !lista.isEmpty()) {
				
				for (FacturaCabDTO obje : lista) {
					if (obje != null) {
						FacturaCabDTO objFacturaCabDTO = new FacturaCabDTO();
						objFacturaCabDTO.setIdFactura(obje.getIdFactura());
						objFacturaCabDTO.setTipoDoc(obje.getTipoDoc());
						objFacturaCabDTO.setNombreCliente(obje.getNombreCliente());
						objFacturaCabDTO.setIdIdentificacion(obje.getIdIdentificacion());
						objFacturaCabDTO.setFechaFactura(obje.getFechaFactura());
						objFacturaCabDTO.setValorTotal(obje.getValorTotal());
						tableFacturas.getItems().add(objFacturaCabDTO);
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
		llenaTablaFacturas();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == btnBuscar) {
			tableFacturas.getItems().removeAll();
			tableFacturas.getItems().clear();
			System.out.println("==================================================");
			System.out.println("Buscando facturas...");
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
					List<FacturaCabDTO> lista = null;
					lista = new FacturaBO().buscaFacturasReporteGeneral(strFechaInicio, strFechaFin);
					if (lista != null && !lista.isEmpty()) {
						for (FacturaCabDTO obje : lista) {
							if (obje != null) {
								tableFacturas.getItems().add(obje);
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
				llenaTablaFacturas();
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
			ReporteGeneralFactura obj = new ReporteGeneralFactura();
			String strFechaInicio = "";
			String strFechaFin = "";
			LocalDate dateInicio = dtFechaInicio.getValue();
			if (dateInicio != null)
				strFechaInicio = formatter.format(dateInicio);
			LocalDate dateFin = dtFechaFin.getValue();
			if (dateFin != null)
				strFechaFin = formatter.format(dateFin);
			if (!strFechaInicio.isEmpty() && !strFechaFin.isEmpty()) {
				obj.genereReporteGeneralFactura(strFechaInicio, strFechaFin, "REPORTE_FACTURA", new Date());
				limpiarPantalla();
			} else {
				String srtError = "No ha ingresado los datos correctos";
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(srtError);
				llenaTablaFacturas();
			}
		}
	}

}

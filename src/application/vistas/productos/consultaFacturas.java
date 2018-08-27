package application.vistas.productos;

import java.awt.Desktop;
import java.io.File;
import java.sql.SQLException;
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
import reportes.GeneraReportes;

public class consultaFacturas implements EventHandler<ActionEvent> {

	public Stage ventanaActual;
	public TextField txtFactura;

	public Button btnBuscar, btnExit, btnClear, btnClearT;

	public FacturaCabDTO objFactura = new FacturaCabDTO();
	public ParametrosBO objParametros = new ParametrosBO();
	public GeneraReportes objGeneraReportes = new GeneraReportes();

	public TableView<FacturaCabDTO> tableFacturas = new TableView();
	public TableColumn<FacturaCabDTO, String> IdFactura = new TableColumn<>("Núm factura");
	public TableColumn<FacturaCabDTO, String> tipoDoc = new TableColumn<>("Tipo Doc.");
	public TableColumn<FacturaCabDTO, String> NombreCliente = new TableColumn<>("Cliente");
	public TableColumn<FacturaCabDTO, String> IdIdentificacion = new TableColumn<>("Identificacion");
	public TableColumn<FacturaCabDTO, String> FechaFactura = new TableColumn<>("Fecha Factura");
	public TableColumn<FacturaCabDTO, String> ValorTotal = new TableColumn<>("V Factura");

	public List<FacturaCabDTO> lstFacturas = null;

	public void consultaFacturas(Stage ventanaIngreso) {

		ventanaActual = new Stage();
//= ventanaIngreso;
		
		Label scenetitle = new Label("Consultar Facturas");
		
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setLayoutX(160);
		scenetitle.setLayoutY(5);
		scenetitle.setId("texto");

		Label lblProd = new Label("Factura");
		lblProd.setLayoutX(100);
		lblProd.setLayoutY(55);

		txtFactura = new TextField();
		txtFactura.setLayoutX(160);
		txtFactura.setLayoutY(50);
		txtFactura.setPrefSize(190, 25);

		botones b = new botones();
		btnBuscar = new Button();
		btnBuscar.setGraphic(b.botonBuscar());
		btnBuscar.setLayoutX(370);
		btnBuscar.setLayoutY(50);
		btnBuscar.setPrefSize(60, 20);
		btnBuscar.setOnAction(this);

		btnClear = new Button();
		btnClear.setGraphic(b.botonLimpiarIcono());
		btnClear.setLayoutX(460);
		btnClear.setLayoutY(50);
		btnClear.setPrefSize(50, 20);
		btnClear.setOnAction(this);

		btnClearT= new Button(); 
		btnClearT.setContentDisplay(ContentDisplay.BOTTOM);
		btnClearT.setLayoutX(470);
		btnClearT.setLayoutY(50);
		btnClearT.setPrefSize(80, 40);
		btnClearT.setVisible(false);
		
		AnchorPane datosBusqueda = new AnchorPane();
		datosBusqueda.getChildren().addAll(scenetitle,lblProd,txtFactura,btnBuscar, btnClear, btnClearT// tableProductos,								
		);
		datosBusqueda.setPadding(new Insets(5));
		datosBusqueda.setTranslateX(60);
		datosBusqueda.setTranslateY(20);
		datosBusqueda.setTranslateZ(20);
		datosBusqueda.setMaxSize(650, 200);
		datosBusqueda.setId("colorMarco");
		
		btnExit = new Button("Regresar");
		btnExit.setGraphic(b.botonRegresar());
		btnExit.setLayoutX(660);
		btnExit.setLayoutY(550);
		btnExit.setPrefSize(130, 35);
		btnExit.setOnAction(this);

		IdFactura.setCellValueFactory(new PropertyValueFactory<>("IdFactura"));
		tipoDoc.setCellValueFactory(new PropertyValueFactory<>("tipoDoc"));
		NombreCliente.setCellValueFactory(new PropertyValueFactory<>("NombreCliente"));
		IdIdentificacion.setCellValueFactory(new PropertyValueFactory<>("IdIdentificacion"));
		FechaFactura.setCellValueFactory(new PropertyValueFactory<>("FechaFactura"));
		ValorTotal.setCellValueFactory(new PropertyValueFactory<>("ValorTotal"));

		IdFactura.setMinWidth(110);
		tipoDoc.setMinWidth(80);
		NombreCliente.setMinWidth(240);
		IdIdentificacion.setMinWidth(110);
		FechaFactura.setMinWidth(60);
		ValorTotal.setMinWidth(100);

		tableFacturas.getColumns().addAll(IdFactura, tipoDoc,IdIdentificacion, NombreCliente , FechaFactura, ValorTotal);

		tableFacturas.setLayoutX(20);
		tableFacturas.setLayoutY(50);
		tableFacturas.setPrefSize(790, 300);

		llenaTablaFacturas();

		tableFacturas.setOnMouseClicked(event -> {
			try {
				if (event.getClickCount() == 2) {
					System.out.println("Doble click");
					objFactura = tableFacturas.getSelectionModel().getSelectedItem();
					llamaPDF();
				}
				if (event.getClickCount() == 1) {
					System.out.println("Un solo click");
				}

			} catch (Exception e) {
				System.out.println("Error" + e.toString());
				System.out.println("Error" + e.getMessage());
			}

		});
		
		Label sceneTable = new Label("Facturaciones realizadas");
		sceneTable.setLayoutX(260);
		sceneTable.setLayoutY(5);
		sceneTable.setId("texto");
		
		AnchorPane datosResultantes = new AnchorPane();
		datosResultantes.getChildren().addAll(sceneTable, tableFacturas// tableProductos,								
		);
		datosResultantes.setPadding(new Insets(5));
		datosResultantes.setTranslateX(20);
		datosResultantes.setTranslateY(150);
		datosResultantes.setMaxSize(650, 200);
		datosResultantes.setId("colorMarco");
		
		
		Group rootIngreso = new Group();

		BorderPane bp = new BorderPane();
		botones bot = new botones();
		bp.setCenter(bot.fondoPantalla());
		rootIngreso.getChildren().addAll(bp, datosBusqueda, datosResultantes, btnExit
				);
		Scene escenaProductos = null;
		escenaProductos = new Scene(rootIngreso, 860, 600);
		escenaProductos.getStylesheets().add("DarkTheme.css");
		ventanaActual.setTitle("LmLaren");
		ventanaActual.setScene(escenaProductos);
		ventanaActual.getIcons().add(bot.iconoLaren());
		ventanaActual.initModality(Modality.APPLICATION_MODAL);
		ventanaActual.setResizable(false);
		ventanaActual.show();

	}

	public void llamaPDF() throws SQLException {
		try {
			String srtError = "Factura no encontrada.";
			alertasMensajes alerta = new alertasMensajes();
			if (objFactura != null && objFactura.getFechaFactura() != null
					&& String.valueOf(objFactura.getIdFactura()) != null) {
				String strRuta = objGeneraReportes.generaRutaFacturasPDF(objFactura.getFechaFactura());
				
				System.out.println(strRuta);
				if (strRuta != null) {
					File ruta = new File(strRuta + "\\" + objFactura.getIdFactura() + ".pdf");
					
					System.out.println("ruta: " + ruta);
					Desktop.getDesktop().open(ruta);
					
				} else {
					alerta.alertaGeneral(srtError);
				}
			} else {
				alerta.alertaGeneral(srtError);
			}
		} catch (Exception ex) {
			System.out.println("Error iReport: " + ex.getMessage());
			String srtError = "Factura no encontrada.";
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(srtError);
		}
	}

	public void llenaTablaFacturas() {
		try {
			tableFacturas.getItems().removeAll();
			tableFacturas.getItems().clear();
			System.out.println("================================================================================");
			System.out.println(" Cargando Facturas...s");
			System.out.println("================================================================================");
			List<FacturaCabDTO> lista = null;
			lista = new FacturaBO().consultaFacturas();

			if (lista != null && !lista.isEmpty()) {
				for (FacturaCabDTO obje : lista) {
					if (obje != null) 
					{
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
		txtFactura.setText("");
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
			String strParametro = null;
			strParametro = txtFactura.getText().toString().trim();
			if (!strParametro.isEmpty()) {
				try {
					List<FacturaCabDTO> lista = null;
					lista = new FacturaBO().buscaFacturas(strParametro);
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
		}
	}

}

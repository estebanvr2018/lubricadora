package reportes;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import application.BO.ParametrosBO;
import application.Dialog.alertasMensajes;
import application.com.conxionMySql.ConexionMySQL;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class GeneraReportes {

	Connection objConnection = new ConexionMySQL().conexion();

	public String creaReporte(JasperReport strNombreJasper, Map<String, Object> mapParametros, String strRutaReporte,
			String strFormato) throws SQLException {

		String strPDF = ".pdf";
		String strXLS = ".xls";
		String strError = null;
		try {

			if (strFormato != null && strFormato.equals("PDF")) {
				JasperPrint PdfPrint = JasperFillManager.fillReport(strNombreJasper, mapParametros, objConnection);

				JRPdfExporter pdfExporter = new JRPdfExporter();

				pdfExporter.setExporterInput(new SimpleExporterInput(PdfPrint));
				if (strRutaReporte.indexOf(strPDF) <= 0)
					strRutaReporte = strRutaReporte + strPDF;

				pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(strRutaReporte));
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				configuration.setCreatingBatchModeBookmarks(true);
				pdfExporter.setConfiguration(configuration);
				pdfExporter.exportReport();

			} else if (strFormato != null && strFormato.equals("XLS")) {
				JasperPrint xlsPrint = JasperFillManager.fillReport(strNombreJasper, mapParametros, objConnection);

				JRXlsExporter xlsExporter = new JRXlsExporter();

				xlsExporter.setExporterInput(new SimpleExporterInput(xlsPrint));
				if (strRutaReporte.indexOf(strXLS) <= 0)
					strRutaReporte = strRutaReporte + strXLS;
				xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(strRutaReporte));
				SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
				SimpleXlsExporterConfiguration xlsExporterConfiguration = new SimpleXlsExporterConfiguration();
				xlsReportConfiguration.setOnePagePerSheet(false);
				xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
				xlsReportConfiguration.setDetectCellType(true);
				xlsReportConfiguration.setWhitePageBackground(false);
				xlsExporter.setConfiguration(xlsReportConfiguration);

				xlsExporter.exportReport();
			} else {
				strError = "Formato del Reporte Incorrecto.";
			}

		} catch (JRException e) {
			return e.toString();
		}
		return strError;
	}

	public String generaRutaFacturasPDF(Date dtFecha) 
	{
		System.out.println("Fecha que le llega: "+dtFecha);
		
		DateFormat formatoMes = new SimpleDateFormat("MM");
		DateFormat formatoAnio = new SimpleDateFormat("yyyy");
		String strMesActual = formatoMes.format(dtFecha);
		System.out.println("1");
		String strAnioActual = formatoAnio.format(dtFecha);
		System.out.println("2");
		String strRutaFinal = null;
		try {
			System.out.println("3");
			String strRutaRaiz = new ParametrosBO().consultaParametro("RUTA_RAIZ");
			if (strRutaRaiz != null) {
				/*
				 * File fileRutaRaiz = new File(strRutaPdf); if
				 * (!fileRutaRaiz.isDirectory()) { fileRutaRaiz.mkdirs();
				 * System.out.println("Se crea la ruta raiz" + strRutaPdf); }
				 */
				File fileRutaAnio = new File(strRutaRaiz + "\\" + strAnioActual + "\\" + strMesActual);
				if (!fileRutaAnio.isDirectory()) {
					fileRutaAnio.mkdirs();
				}
				strRutaFinal = fileRutaAnio.toString();
			} else {
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral("Datos no Parametrizados, verificar");
			}
		} catch (Exception e) {
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(e.toString());
			return null;
		}
		return strRutaFinal;
	}

	public String generaRutaReportesGeneral() {

		String strRutaFinal = null;
		try {
			String strRutaReporte = new ParametrosBO().consultaParametro("RUTA_REPORTE");
			String strRutaRaiz = new ParametrosBO().consultaParametro("RUTA_RAIZ");
			if (strRutaReporte != null && strRutaRaiz != null) {

				/*
				 * File fileRutaRaiz = new File(strRutaRaiz); if
				 * (!fileRutaRaiz.isDirectory()) { fileRutaRaiz.mkdirs();
				 * System.out.println("Se crea la ruta raiz" + strRutaRaiz); }
				 */

				File fileRutaAnio = new File(strRutaRaiz + strRutaReporte);
				if (!fileRutaAnio.isDirectory()) {
					fileRutaAnio.mkdirs();
				}
				strRutaFinal = fileRutaAnio.toString();
			} else {
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral("Datos PARAMETRIZADOS, verificar");
			}

		} catch (Exception e) {
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(e.toString());
			return null;
		}
		return strRutaFinal;
	}
}

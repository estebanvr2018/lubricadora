package reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import application.Dialog.alertasMensajes;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReporteGeneralFactura {
	
	public GeneraReportes objGeneraReportes = new GeneraReportes();
	Map<String, Object> mapParametros = new HashMap<String, Object>();
	
	public void genereReporteGeneralFactura(String strFechaInicio, String strFechaFin, String strTipo, Date dtFecha) {
		DateFormat formatoFecha = new SimpleDateFormat("ddMMyyyyHHmmss");
		String strFechaActual = formatoFecha.format(dtFecha);
		try {
			if (strFechaInicio != null && strFechaFin != null) {
				mapParametros.put("fecha_inicio", strFechaInicio);
				mapParametros.put("fecha_fin", strFechaFin);
				String strRuta = objGeneraReportes.generaRutaReportesGeneral();
				System.out.println("Ruta del reporte: "+strRuta);
				if (strRuta != null) {
					strRuta = strRuta + "\\" + strTipo + "_" + strFechaActual + ".xls";
					JasperReport jasper = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteGeneralFactura.jasper"));
					String strError = objGeneraReportes.creaReporte(jasper, mapParametros,strRuta, "XLS");
					if (strError != null) {
						alertasMensajes alerta = new alertasMensajes();
						alerta.alertaGeneral(strError);
					} else {
						File ruta = new File(strRuta);
						System.out.println("ruta: " + ruta);
						Desktop.getDesktop().open(ruta);
					}
				}
			}
		} catch (Exception e) {
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(e.toString());
		}
	}

}

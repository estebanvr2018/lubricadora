package reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import application.Dialog.alertasMensajes;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class FacturacionReporte {

	GeneraReportes objGeneraReportes = new GeneraReportes();
	Map<String, Object> mapParametros = new HashMap<String, Object>();

	public boolean genereFactura(int intIdFactura) 
	{
		boolean error=true;
		try {
			mapParametros.put("id_factura", intIdFactura);
			String strRuta = objGeneraReportes.generaRutaFacturasPDF(new Date());
			strRuta = strRuta + "\\" + intIdFactura + ".pdf";
			JasperReport jasper = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/facturaEjemplo.jasper"));
			String strError = objGeneraReportes.creaReporte(jasper, mapParametros, strRuta, "PDF");
			if (strError != null) {
				alertasMensajes alerta = new alertasMensajes();
				alerta.alertaGeneral(strError);
				error = false;
			} else {
				File ruta = new File(strRuta);
				Desktop.getDesktop().open(ruta);
				
			}
			return error;
		} catch (Exception e) {
			alertasMensajes alerta = new alertasMensajes();
			alerta.alertaGeneral(e.toString());
			return error ;
		}
	}
	
}

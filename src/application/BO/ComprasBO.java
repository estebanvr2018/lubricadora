package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.ComprasDAO;
import application.DAO.FacturaDAO;
import application.DAO.ProductosDAO;
import application.DAO.ProveedorDAO;
import application.com.DTOS.ProveedorDTO;
import application.com.DTOS.comprasDTO;
import application.com.DTOS.productosCategoriaDTO;
import application.com.conxionMySql.ConexionMySQL;

public class ComprasBO 
{
	/*** Traer productos de la tabla tb_productos_cat ***/
	public List<ProveedorDTO> extraeProveedores() throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<ProveedorDTO> lsProductosDTO = null;

		try {
			lsProductosDTO = new ProveedorDAO().extraeProveedores(objConnection);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}
	
		/*** FIN tb_productos CAT ***/

	/* INICIO inserta compra*/
	public int insertaCompra(int idProveedor, int nFactura, float base0, float baseD, float iva, float totalCOmpra, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ComprasDAO().insertaCompra(objConnection, idProveedor, nFactura, base0, baseD, iva, totalCOmpra, usuarioGlobal); 
			return insertQuery;
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return insertQuery;
	}
	/* FIN inserta compra*/
	public List<comprasDTO> buscaComprasReporteGeneral(String strFechaInicio, String strFechaFin) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<comprasDTO> lstFacturas = null;
		try {
			lstFacturas = new ComprasDAO().buscaComprasReporteGeneral(objConnection, strFechaInicio,strFechaFin);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lstFacturas;
	}
}

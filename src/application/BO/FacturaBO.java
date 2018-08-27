package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.FacturaDAO;
import application.com.DTOS.FacturaCabDTO;
import application.com.conxionMySql.ConexionMySQL;

public class FacturaBO {
	public int insertaCabeceraFactura(String intIdentificacion, float fltSutbtotal, float fltSutbtotalReq,
			float fltIvaC, float fltIvaCDoce, float valorTotal, String valorTotalLetras, String usuario) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			FacturaDAO facturar = new FacturaDAO();
			insertQuery = facturar.insertaFacturaCab(objConnection, intIdentificacion, fltSutbtotal, fltSutbtotalReq,
					fltIvaC, fltIvaCDoce, valorTotal, valorTotalLetras, usuario);

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

	public int insertaDetalleFactura(int idFactCab, int idProducto, int cantidad, float valor, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			FacturaDAO facturar = new FacturaDAO();
			insertQuery = facturar.insertaFacturaDet(objConnection, idFactCab, idProducto, cantidad, valor, usuarioGlobal);

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

	public int actualizaStockProducto(int idProducto, int cantidad, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			FacturaDAO facturar = new FacturaDAO();
			insertQuery = facturar.actualizaStockProductos(objConnection, idProducto, cantidad, usuarioGlobal);
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

	public List<FacturaCabDTO> consultaFacturas() throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<FacturaCabDTO> lstFacturas = null;
		try {
			lstFacturas = new FacturaDAO().consultaFacturas(objConnection);

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

	public List<FacturaCabDTO> buscaFacturas(String strValor) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<FacturaCabDTO> lstFacturas = null;
		try {
			lstFacturas = new FacturaDAO().buscaFacturas(objConnection, strValor);

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
	
	public List<FacturaCabDTO> buscaFacturasReporteGeneral(String strFechaInicio, String strFechaFin) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<FacturaCabDTO> lstFacturas = null;
		try {
			lstFacturas = new FacturaDAO().buscaFacturasReporteGeneral(objConnection, strFechaInicio,strFechaFin);

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

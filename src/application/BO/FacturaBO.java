package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.FacturaDAO;
import application.DAO.ProductosDAO;
import application.com.DTOS.FacturaCabDTO;
import application.com.DTOS.ProductosDTO;
import application.com.conxionMySql.ConexionMySQL;

public class FacturaBO 
{
	public int insertaCabeceraFactura( String intIdentificacion, float fltSutbtotal, float fltSutbtotalReq, 
										float fltIvaC, float fltIvaCDoce, float valorTotal, String valorTotalLetras) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		try 
		{
			FacturaDAO facturar = new FacturaDAO();
			insertQuery = facturar.insertaFacturaCab(objConnection, intIdentificacion, fltSutbtotal, fltSutbtotalReq, fltIvaC, fltIvaCDoce, valorTotal, valorTotalLetras) ;
			
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
	
	
	public int insertaDetalleFactura( int idFactCab, int idProducto, int cantidad, float valor) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		try 
		{
		FacturaDAO facturar = new FacturaDAO();
		insertQuery = facturar.insertaFacturaDet(objConnection, idFactCab, idProducto, cantidad, valor);
		
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

}

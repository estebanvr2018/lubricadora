package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaDAO 
{
	/*** Inserta cabecera de la factura***/
	public int insertaFacturaCab(Connection objConnection, String intIdentificacion, float fltSutbtotal, float fltSutbtotalReq, 
								float fltIvaC, float fltIvaCDoce, float valorTotal, String valorTotalLetras) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery=0;
		int idFacturaCab = idMayorFactura(objConnection);
		String user="Lubri";
		String estado="A";
		
		try {
			ps = objConnection.prepareStatement("insert into tb_factura_cab (id_factura,"
																		+ "  id_identificacion,"
																		+ "  sub_total_1,"
												                        + "  sub_total_2,"
												                        + "  iva_cero,"
												                        + "  iva_doce,"
												                        + "  valor_total,"
												                        + "  valor_total_letras,"
												                        + "  estado,"
												                        + "  usuario_ingreso)"
												                        + "  values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, idFacturaCab);
			ps.setString(2, intIdentificacion);
			ps.setFloat(3, fltSutbtotal);
			ps.setFloat(4, fltSutbtotalReq);
			ps.setFloat(5, fltIvaC);
			ps.setFloat(6, fltIvaCDoce);
			ps.setFloat(7, valorTotal);
			ps.setString(8, valorTotalLetras);
			ps.setString(9, estado);
			ps.setString(10, user);
			resQuery = ps.executeUpdate();
			if ( resQuery == 1)
			{
				resQuery = idFacturaCab;
			}	
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery=-1;
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		return resQuery;
		
	}
	/*** FIN inserta cabecera de la factura ***/
	public int idMayorFactura(Connection objConnection) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max((id_factura))+1,1) as idMax from tb_factura_cab;");
			rs = ps.executeQuery();
			
			if (rs.next() == true) 
			{
				return rs.getInt("idMax");
			}
			else 
				return rs.getInt("idMax");

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			return 0;
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
	/*** INI Inserta detalle de la factura ***/
	public int insertaFacturaDet(Connection objConnection, int idFactCab, int idProducto, int cantidad, 
								float valor) throws SQLException 
	{
			PreparedStatement ps = null;
			ResultSet rs = null;
			int resQuery=0;
			int idFacturaDet = idMayorFacturaDet(objConnection);
			String user="Lubri";
			String estado="A";
			
			try {
			ps = objConnection.prepareStatement("insert into tb_factura_dtl (id_factura_dtl,"
																		+ "  id_factura,"
																		+ "  id_producto,"
												                        + "  cantidad,"
												                        + "  valor,"
												                        + "  estado,"
												                        + "  usuario_ingreso)"
												                        + "  values(?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, idFacturaDet);
			ps.setInt(2, idFactCab);
			ps.setInt(3, idProducto);
			ps.setInt(4, cantidad);
			ps.setFloat(5, valor);
			ps.setString(6, estado);
			ps.setString(7, user);
			resQuery = ps.executeUpdate();
			System.out.println("Que tiene el update: "+resQuery);
			if ( resQuery == 1)
			{
			return resQuery ;
			}	
			} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery=-1;
			} finally {
			if (rs != null && rs.isClosed()) {
			rs.close();
			}
			if (ps != null && !ps.isClosed()) {
			ps.close();
			}
			}
			return resQuery;
	
	}
	/*** FIN inserta detalle de la factura ***/
	
	public int idMayorFacturaDet(Connection objConnection) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max((id_factura_dtl))+1,1) as idMax from tb_factura_dtl;");
			rs = ps.executeQuery();
			
			if (rs.next() == true) 
			{
				return rs.getInt("idMax");
			}
			else 
				return rs.getInt("idMax");

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			return 0;
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
}

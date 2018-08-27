package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import application.com.DTOS.FacturaCabDTO;
import sun.util.calendar.BaseCalendar.Date;

public class FacturaDAO {
	/*** Inserta cabecera de la factura ***/
	public int insertaFacturaCab(Connection objConnection, String intIdentificacion, float fltSutbtotal,
			float fltSutbtotalReq, float fltIvaC, float fltIvaCDoce, float valorTotal, String valorTotalLetras,  String usuario)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int idFacturaCab = idMayorFactura(objConnection);
		String estado = "A";

		try {
			ps = objConnection.prepareStatement("insert into tb_factura_cab (id_factura," + "  id_identificacion,"
					+ "  sub_total_1," + "  sub_total_2," + "  iva_cero," + "  iva_doce," + "  valor_total,"
					+ "  valor_total_letras," + "  estado," + "  usuario_ingreso)"
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
			ps.setString(10, usuario);
			resQuery = ps.executeUpdate();
			if (resQuery == 1) {
				resQuery = idFacturaCab;
			}
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery = -1;
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
	public int idMayorFactura(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max(id_factura),0)+1 as idMax from tb_factura_cab;");
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("idMax");
			} else
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
	public int insertaFacturaDet(Connection objConnection, int idFactCab, int idProducto, int cantidad, float valor, String usuarioGlobal)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int idFacturaDet = idMayorFacturaDet(objConnection);
		String estado = "A";

		try {
			ps = objConnection.prepareStatement(
					"insert into tb_factura_dtl (id_factura_dtl," + "  id_factura," + "  id_producto," + "  cantidad,"
							+ "  valor," + "  estado," + "  usuario_ingreso)" + "  values(?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, idFacturaDet);
			ps.setInt(2, idFactCab);
			ps.setInt(3, idProducto);
			ps.setInt(4, cantidad);
			ps.setFloat(5, valor);
			ps.setString(6, estado);
			ps.setString(7, usuarioGlobal);
			resQuery = ps.executeUpdate();
			if (resQuery == 1) {
				return resQuery;
			}
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery = -1;
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

	public int idMayorFacturaDet(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max(id_factura_dtl),0)+1 as idMax from tb_factura_dtl;");
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("idMax");
			} else
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

	/*** INI Actualiza Stock ***/
	public int actualizaStockProductos(Connection objConnection, int idProducto, int cantidad, String usuarioGlobal) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;

		try {
			ps = objConnection.prepareStatement(
					"update tb_producto " + "			set stock = stock - ?" + "			where  id_producto = ?");
			ps.setInt(1, cantidad);
			ps.setInt(2, idProducto);
			resQuery = ps.executeUpdate();
			if (resQuery == 1) {
				return resQuery;
			}
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery = -1;
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

	/*** FIN inserta Actualiza Stock ***/

	public List<FacturaCabDTO> consultaFacturas(Connection objConnection) throws SQLException {

		List<FacturaCabDTO> lstFacturas = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement(
					"SELECT f.id_factura, c.nombres,c.primer_apellido, c.tipo_identificacion,  f.id_identificacion,f.fecha_factura, f.valor_total\n"
							+ "FROM tb_factura_cab f , tb_clientes c\n"
							+ "where f.id_identificacion = c.id_identificacion\n" + "order by f.id_factura;");
			rs = ps.executeQuery();
			while (rs.next()) {
				FacturaCabDTO objFacturaCabDTO = new FacturaCabDTO();
				objFacturaCabDTO.setIdFactura(rs.getInt(1));
				
				if (! rs.getString(2).equals("C. final"))
				{	
				
					objFacturaCabDTO.setNombreCliente(rs.getString(2) + " " + rs.getString(3));
				}
				else 
					objFacturaCabDTO.setNombreCliente(rs.getString(2));
					
				objFacturaCabDTO.setTipoDoc(rs.getString(4));
				objFacturaCabDTO.setIdIdentificacion(rs.getString(5));
				objFacturaCabDTO.setFechaFactura(rs.getTimestamp(6) != null ? rs.getTimestamp(6) : null);
				objFacturaCabDTO.setValorTotal(rs.getFloat(7));
				lstFacturas.add(objFacturaCabDTO);
			}

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		return lstFacturas;
	}

	public List<FacturaCabDTO> buscaFacturas(Connection objConnection, String strValor) throws SQLException {

		List<FacturaCabDTO> lstFacturas = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement("SELECT F.ID_FACTURA,\n" + "       C.NOMBRES,\n"
					+ "       c.tipo_identificacion, F.ID_IDENTIFICACION,\n" + "       F.FECHA_FACTURA,\n" + "       F.VALOR_TOTAL\n"
					+ "  FROM TB_FACTURA_CAB F, TB_CLIENTES C\n" + " WHERE F.ID_IDENTIFICACION = C.ID_IDENTIFICACION\n"
					+ "   AND F.ID_FACTURA IN\n" + "       (SELECT DISTINCT A.ID_FACTURA\n"
					+ "          FROM (SELECT B.ID_FACTURA\n" + "                  FROM TB_FACTURA_CAB B\n"
					+ "                 WHERE B.ID_FACTURA = ?\n"
					+ "                    OR B.ID_IDENTIFICACION LIKE ?\n"
					+ "                    OR B.FECHA_FACTURA LIKE ?\n" + "                    OR B.VALOR_TOTAL = ?\n"
					+ "                UNION ALL\n" + "                SELECT C.ID_FACTURA\n"
					+ "                  FROM TB_FACTURA_CAB C, TB_CLIENTES D\n"
					+ "                 WHERE C.ID_IDENTIFICACION = D.ID_IDENTIFICACION\n"
					+ "                   AND D.NOMBRES LIKE ?) A);");
			ps.setString(1, strValor);
			ps.setString(2, "%" + strValor + "%");
			ps.setString(3, "%" + strValor + "%");
			ps.setString(4, strValor);
			ps.setString(5, "%" + strValor + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				FacturaCabDTO objFacturaCabDTO = new FacturaCabDTO();
				objFacturaCabDTO.setIdFactura(rs.getInt(1));
				objFacturaCabDTO.setNombreCliente(rs.getString(2));
				objFacturaCabDTO.setTipoDoc(rs.getString(3));
				objFacturaCabDTO.setIdIdentificacion(rs.getString(4));
				objFacturaCabDTO.setFechaFactura(rs.getTimestamp(5) != null ? rs.getTimestamp(5) : null);
				objFacturaCabDTO.setValorTotal(rs.getFloat(6));
				lstFacturas.add(objFacturaCabDTO);
			}

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		return lstFacturas;
	}
	
	public List<FacturaCabDTO> buscaFacturasReporteGeneral(Connection objConnection, String strFechaInicio, String strFechaFin) throws SQLException {

		List<FacturaCabDTO> lstFacturas = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if(strFechaInicio != null && strFechaFin != null){
				ps = objConnection.prepareStatement("SELECT F.ID_FACTURA AS NUMERO_FACTURA,\n" +
													"		C.tipo_identificacion,"+
													"       F.ID_IDENTIFICACION AS CEDULA,\n" + 
													"       CONCAT(C.NOMBRES, ' ', C.PRIMER_APELLIDO) AS NOMBRE,\n" +
													"       CONCAT(C.NOMBRES, ' ', C.PRIMER_APELLIDO) AS NOMBRE_COMPLETO,\n" + 
													"       F.SUB_TOTAL_1 AS SUB_TOTAL,\n" + 
													"       F.IVA_DOCE AS IVA_TOTAL,\n" + 
													"       F.VALOR_TOTAL AS VALOR_TOTAL,\n" + 
													"       F.FECHA_FACTURA AS FECHA_FACTURA\n" + 
													"  FROM TB_FACTURA_CAB F, TB_CLIENTES C\n" + 
													" WHERE F.ID_IDENTIFICACION = C.ID_IDENTIFICACION\n" + 
													"   AND CAST(F.FECHA_FACTURA AS DATE) >= ?\n" + 
													"   AND CAST(F.FECHA_FACTURA AS DATE) <= ?"+
													" order by F.fecha_factura;");
				ps.setString(1, strFechaInicio);
				ps.setString(2, strFechaFin);
			}else{
				ps = objConnection.prepareStatement("SELECT F.ID_FACTURA AS NUMERO_FACTURA,\n" +
													"		C.tipo_identificacion,"+
													"       F.ID_IDENTIFICACION AS CEDULA,\n" + 
													"       CONCAT(C.NOMBRES, ' ', C.PRIMER_APELLIDO) AS NOMBRE,\n" +
													"       CONCAT(C.NOMBRES, ' ', C.PRIMER_APELLIDO) AS NOMBRE_COMPLETO,\n" + 
													"       F.SUB_TOTAL_1 AS SUB_TOTAL,\n" + 
													"       F.IVA_DOCE AS IVA_TOTAL,\n" + 
													"       F.VALOR_TOTAL AS VALOR_TOTAL,\n" + 
													"       F.FECHA_FACTURA AS FECHA_FACTURA\n" + 
													"  FROM TB_FACTURA_CAB F, TB_CLIENTES C\n" + 
													" WHERE F.ID_IDENTIFICACION = C.ID_IDENTIFICACION" +
													" order by F.fecha_factura;");
			}
			rs = ps.executeQuery();
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			while (rs.next()) {
				FacturaCabDTO objFacturaCabDTO = new FacturaCabDTO();
				objFacturaCabDTO.setIdFactura(rs.getInt(1));
				objFacturaCabDTO.setTipoDoc(rs.getString(2));
				objFacturaCabDTO.setIdIdentificacion(rs.getString(3));
				objFacturaCabDTO.setNombreCliente(rs.getString(4));
				objFacturaCabDTO.setNombreCompleto(rs.getString(5));
				objFacturaCabDTO.setSubTotal1(rs.getFloat(6));
				objFacturaCabDTO.setIvaDoce(rs.getFloat(7));
				objFacturaCabDTO.setValorTotal(rs.getFloat(8));
				objFacturaCabDTO.setFechaFactura(rs.getDate(9) != null ? rs.getDate(9) : null);
				lstFacturas.add(objFacturaCabDTO);
			}

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			System.out.println(e.toString());
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		return lstFacturas;
	}

}

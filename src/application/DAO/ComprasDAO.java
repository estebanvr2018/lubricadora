package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.com.DTOS.ProveedorDTO;
import application.com.DTOS.comprasDTO;
import application.com.DTOS.productosCategoriaDTO;

public class ComprasDAO 
{

	public int insertaCompra(Connection objConnection, int idProveedor, int nFactura, float base0, float baseD, float iva, float totalCOmpra, String usuarioGlobal)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int idCompra = idMayorCompra(objConnection);
		String estado = "A";
		try {
			ps = objConnection.prepareStatement(
					"insert into tb_compras (id_compra, id_proveedor, factura, base_0, base_12, iva, total, estado, usuario_ingreso)values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, idCompra);
			ps.setInt(2, idProveedor);
			ps.setInt(3, nFactura);
			ps.setFloat(4, base0);
			ps.setFloat(5, baseD);
			ps.setFloat(6, iva);
			ps.setFloat(7, totalCOmpra);
			ps.setString(8, estado);
			ps.setString(9, usuarioGlobal);
			resQuery = ps.executeUpdate();

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
	
	public int idMayorCompra(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection
					.prepareStatement("select ifnull(max(id_compra),0)+1 as idMax from tb_compras;");
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
	public List<comprasDTO> buscaComprasReporteGeneral(Connection objConnection, String strFechaInicio, String strFechaFin) throws SQLException {

		List<comprasDTO> lstFacturas = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if(strFechaInicio != null && strFechaFin != null){
				ps = objConnection.prepareStatement("SELECT CASE MONTH(C.FECHA_COMPRA)\n" +
													"        WHEN 1 THEN 'ENERO'\n" + 
													"        WHEN 2 THEN 'FEBRERO'\n" + 
													"        WHEN 3 THEN 'MARZO'\n" + 
													"        WHEN 4 THEN 'ABRIL'\n" + 
													"        WHEN 5 THEN 'MAYO'\n" + 
													"        WHEN 6 THEN 'JUNIO'\n" + 
													"        WHEN 7 THEN 'JULIO'\n" + 
													"        WHEN 8 THEN 'AGOSTO'\n" + 
													"        WHEN 9 THEN 'SEPTIEMBRE'\n" + 
													"        WHEN 10 THEN 'OCTUBRE'\n" + 
													"        WHEN 11 THEN 'NOVIEMBRE'\n" + 
													"        WHEN 12 THEN 'DICIEMBRE'\n" + 
													"       END MES,\n" + 
													"       DATE_FORMAT(C.FECHA_COMPRA, '%d/%m/%Y') AS FECHA,\n" + 
													"       P.RUC AS RUC,\n" + 
													"       P.NOMBRE AS NOMBRE,\n" + 
													"       C.FACTURA AS FACTURA,\n" + 
													"       ROUND(C.BASE_0,2) AS 'BASE 0%',\n" + 
													"       ROUND(C.BASE_12,2) AS 'BASE 12%',\n" + 
													"       ROUND(C.IVA,2) AS IVA,\n" + 
													"       ROUND(C.TOTAL,2) AS TOTAL\n" + 
													"  FROM TB_COMPRAS C, TB_PROVEEDORES P\n" + 
													" WHERE C.ID_PROVEEDOR = P.ID_PROVEEDOR\n" + 
													"   AND CAST(C.FECHA_COMPRA AS DATE) >= ?\n" + 
													"   AND CAST(C.FECHA_COMPRA AS DATE) <= ?;");

				ps.setString(1, strFechaInicio);
				ps.setString(2, strFechaFin);
			}else{
				ps = objConnection.prepareStatement("SELECT CASE MONTH(C.FECHA_COMPRA)\n" +
													"        WHEN 1 THEN 'ENERO'\n" + 
													"        WHEN 2 THEN 'FEBRERO'\n" + 
													"        WHEN 3 THEN 'MARZO'\n" + 
													"        WHEN 4 THEN 'ABRIL'\n" + 
													"        WHEN 5 THEN 'MAYO'\n" + 
													"        WHEN 6 THEN 'JUNIO'\n" + 
													"        WHEN 7 THEN 'JULIO'\n" + 
													"        WHEN 8 THEN 'AGOSTO'\n" + 
													"        WHEN 9 THEN 'SEPTIEMBRE'\n" + 
													"        WHEN 10 THEN 'OCTUBRE'\n" + 
													"        WHEN 11 THEN 'NOVIEMBRE'\n" + 
													"        WHEN 12 THEN 'DICIEMBRE'\n" + 
													"       END MES,\n" + 
													"       DATE_FORMAT(C.FECHA_COMPRA, '%d/%m/%Y') AS FECHA,\n" + 
													"       P.RUC AS RUC,\n" + 
													"       P.NOMBRE AS NOMBRE,\n" + 
													"       C.FACTURA AS FACTURA,\n" + 
													"       ROUND(C.BASE_0,2) AS 'BASE 0%',\n" + 
													"       ROUND(C.BASE_12,2) AS 'BASE 12%',\n" + 
													"       ROUND(C.IVA,2) AS IVA,\n" + 
													"       ROUND(C.TOTAL,2) AS TOTAL\n" + 
													"  FROM TB_COMPRAS C, TB_PROVEEDORES P\n" + 
													" WHERE C.ID_PROVEEDOR = P.ID_PROVEEDOR;");
			}
			rs = ps.executeQuery();
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			while (rs.next()) {
				comprasDTO objcomprasDTO = new comprasDTO();
				objcomprasDTO.setMesCompra(rs.getString(1));
				objcomprasDTO.setFechaComprasTexto(rs.getString(2));
				objcomprasDTO.setRucProveedor(rs.getString(3));
				objcomprasDTO.setNombreProveedor(rs.getString(4));
				objcomprasDTO.setFactura(rs.getString(5));
				objcomprasDTO.setBase_0(rs.getFloat(6));
				objcomprasDTO.setBase_12(rs.getFloat(7));
				objcomprasDTO.setIva(rs.getFloat(8));
				objcomprasDTO.setTotal(rs.getFloat(9));
				lstFacturas.add(objcomprasDTO);
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

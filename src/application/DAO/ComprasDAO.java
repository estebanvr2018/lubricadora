package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.com.DTOS.ProveedorDTO;
import application.com.DTOS.productosCategoriaDTO;

public class ComprasDAO 
{

	public int insertaCompra(Connection objConnection, int idProveedor, int nFactura, float base0, float baseD, float iva, float totalCOmpra)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int idCompra = idMayorCompra(objConnection);
		String user = "Lubri";
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
			ps.setString(9, user);
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

}

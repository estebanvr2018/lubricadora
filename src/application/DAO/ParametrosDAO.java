package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParametrosDAO {

	public String consultaParametro(Connection objConnection, String strIdParametro) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strValor = null;
		try {
			ps = objConnection.prepareStatement("SELECT valor FROM tb_parametros WHERE id_parametro = ?");
			ps.setString(1, strIdParametro);
			rs = ps.executeQuery();
			if (rs.next() == true)
				return rs.getString("valor");
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
		return strValor;
	}

	public float consultaParametroIva(Connection objConnection, String strIdParametro) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		float strValor = 0;
		try {
			ps = objConnection
					.prepareStatement("SELECT ifnull(valor,0)valor FROM tb_parametros WHERE id_parametro = ?");
			ps.setString(1, strIdParametro);
			rs = ps.executeQuery();
			if (rs.next() == true)
				strValor = rs.getFloat("valor");
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
		return strValor;
	}

}

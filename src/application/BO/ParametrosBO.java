package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import application.DAO.ParametrosDAO;
import application.com.conxionMySql.ConexionMySQL;

public class ParametrosBO {

	public String consultaParametro(String strIdParametro) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		String strValor = "";

		try {
			strValor = new ParametrosDAO().consultaParametro(objConnection, strIdParametro);

		} catch (Exception e) {
			System.out.println(e.toString());
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return strValor;
	}

	public float consultaParametroIva(String strIdParametro) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		float Valor = 0;

		try {
			Valor = new ParametrosDAO().consultaParametroIva(objConnection, strIdParametro);

		} catch (Exception e) {
			System.out.println(e.toString());
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return Valor;
	}

}

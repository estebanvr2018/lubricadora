package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.ClientesDAO;
import application.com.DTOS.ClientesDTO;
import application.com.conxionMySql.ConexionMySQL;

public class ClientesBO {

	public List<ClientesDTO> consultaCliente(String strIdentificacion) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<ClientesDTO> lsClientesDTO = null;

		try {
			lsClientesDTO = new ClientesDAO().consultaClienteX(objConnection, strIdentificacion);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lsClientesDTO;
	}

	public List<ClientesDTO> cargaTClientes() throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<ClientesDTO> lsClientesDTO = null;
		try {
			lsClientesDTO = new ClientesDAO().cargaTClientes(objConnection);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lsClientesDTO;
	}

	public int insertaCliente(String strId, String strNombre, String strApellidos, String strDireccion,
			String strTelefono, String strCorreo, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;

		try {

			insertQuery = new ClientesDAO().insertaClienteDAO(objConnection, strId, strNombre, strApellidos,
					strDireccion, strTelefono, strCorreo, usuarioGlobal);

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

	public int actualizaCliente(String strId, String strNombre, String strApellidos, String strDireccion,
			String strTelefono, String strCorreo, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		System.out.println(" 3 ");
		try {
			System.out.println(" 4 ");
			insertQuery = new ClientesDAO().actualizaClienteDAO(objConnection, strId, strNombre, strApellidos,
					strDireccion, strTelefono, strCorreo, usuarioGlobal);
			System.out.println(" 5");
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

	
	
	public int verificaCliente(String strId) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;

		try {

			insertQuery = new ClientesDAO().existeCLiente(objConnection, strId);

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

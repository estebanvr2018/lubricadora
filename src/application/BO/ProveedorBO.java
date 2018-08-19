package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.ClientesDAO;
import application.DAO.ProveedorDAO;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.ProveedorDTO;
import application.com.conxionMySql.ConexionMySQL;

public class ProveedorBO 
{
	public List<ProveedorDTO> cargaTProveedor() throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<ProveedorDTO> lsClientesDTO = null;
		try {
			lsClientesDTO = new ProveedorDAO().cargaTProveedores(objConnection);

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
	
	
	public List<ProveedorDTO> consultaProveedor(String strIdentificacion) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<ProveedorDTO> lsClientesDTO = null;

		try {
			lsClientesDTO = new  ProveedorDAO().consultaProveedorCed(objConnection, strIdentificacion);

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

	/*** INI inserta proveedor***/
	public int insertaProveedor(String strId, String strNombre, String strDescripcion,
			String strTelefono) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;

		try {

			insertQuery = new ProveedorDAO().insertaProveedorDAO(objConnection, strId, strNombre, strTelefono, strDescripcion);
					
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

	/*** FIN inserta proveedor***/
	
	/*** INI actualiza proveedor***/
	public int actualizaProveedor(String strIdentificacion,String strNombres, String strTelefono,String srtDescripcion) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		System.out.println(" 3 ");
		try {
			System.out.println(" 4 ");
			insertQuery = new ProveedorDAO().actualizaProveedorDAO(objConnection, strIdentificacion, strNombres, strTelefono, srtDescripcion) ;
					
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
	/*** FIN actualiza proveedor ***/
	
	/*** Consulta existencia del proveedor ***/
	
	public int existeProveedor(String strIdentificacion) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int lsClientesDTO = 0;

		try {
			lsClientesDTO = new  ProveedorDAO().existeProveedor(objConnection, strIdentificacion); //.existeProveedor(objConnection, strIdentificacion);

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
	/*** FIN consulta existencia del proveedor **/
	
}

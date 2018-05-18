package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.ClientesDAO;
import application.DAO.UsuariosDAO;
import application.com.DTOS.ClientesDTO;
import application.com.DTOS.UsuariosDTO;
import application.com.conxionMySql.ConexionMySQL;

public class UsuariosBO 
{
	public List<UsuariosDTO> cargaUsuarios() throws SQLException 
	{
		Connection objConnection = new ConexionMySQL().conexion();
		List<UsuariosDTO> lsUsuariosDTO = null;
		try {
			lsUsuariosDTO = new UsuariosDAO().cargaUsuarios(objConnection);
			
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lsUsuariosDTO;
	}
	
	public List<UsuariosDTO> consultaUsuarioNombre(String strNombre) throws SQLException 
	{
		Connection objConnection = new ConexionMySQL().conexion();
		List<UsuariosDTO> lsUsuariosDTO = null;
		try {
			lsUsuariosDTO = new UsuariosDAO().cargaUsuariosDescripcion(objConnection, strNombre);
			
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lsUsuariosDTO;
	}
	
	public int insertaUsuario(String NomApellidos, String strUser, String strPassword) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		
		try {
			System.out.println("Usuarios bo 0");
			insertQuery = new UsuariosDAO().insertaUsuarioDAO(objConnection, NomApellidos,strUser, strPassword);
			System.out.println("Usuarios bo 1: "+insertQuery);
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
	
	public int actualizaUsuario(int idUsuario,String NomApellidos,String strUser,String strPassword) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		System.out.println(" 3 ");
		try {
			System.out.println(" 4 ");
			insertQuery = new UsuariosDAO().actualizaUsuarioDAO(objConnection,idUsuario,NomApellidos, strUser, strPassword);
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
	
	public int eliminaUsuario(int idUsuario) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		System.out.println(" 3 ");
		try {
			System.out.println(" 4 ");
			insertQuery = new UsuariosDAO().eliminaUsuarioDAO(objConnection,idUsuario);
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
}

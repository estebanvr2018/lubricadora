package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.com.DTOS.UsuariosDTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsuariosDAO {
	public List<UsuariosDTO> cargaUsuarios(Connection objConnection) throws SQLException {

		List<UsuariosDTO> lsUsuariosDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement(" select * from tb_usuarios order by id_usuario");

			rs = ps.executeQuery();
			while (rs.next()) {
				UsuariosDTO objUsuariosDTO = new UsuariosDTO();
				objUsuariosDTO.setIdUsuario(rs.getInt(1));
				objUsuariosDTO.setNombres(rs.getString(2));
				objUsuariosDTO.setUsuario( rs.getString(3));
				lsUsuariosDTO.add(objUsuariosDTO);
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
		return lsUsuariosDTO;
	}

	public List<UsuariosDTO> cargaUsuariosDescripcion(Connection objConnection, String strUsuario) throws SQLException {

		List<UsuariosDTO> lsUsuariosDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement(
					" select * from tb_usuarios where id_usuario like ? or nombres like ? or usuario like ? ");
			ps.setString(1, strUsuario);
			ps.setString(2, "%" + strUsuario + "%");
			ps.setString(3, "%" + strUsuario + "%");
			System.out.println(ps.toString());
			rs = ps.executeQuery();
			// ClientesDTO objClientesDTO;
			System.out.println(ps.toString() + "::::::::::::::");
			while (rs.next()) {
				UsuariosDTO objUsuariosDTO = new UsuariosDTO();

				objUsuariosDTO.setIdUsuario(rs.getInt(1));

				objUsuariosDTO.setNombres(rs.getString(2));
				

				 objUsuariosDTO.setNombres(rs.getString(3));
				 
				lsUsuariosDTO.add(objUsuariosDTO);
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
		return lsUsuariosDTO;
	}

	public int insertaUsuarioDAO(Connection objConnection, String NomApellidos, String strUser, String strPassword)
			throws SQLException {
		/*** ***/
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int id = 0;
		id = idMayor(objConnection);
		System.out.println("Usuarios bo 01");
		try {
			System.out.println("Usuarios bo 02");
			ps = objConnection.prepareStatement(
					"insert into tb_usuarios (id_usuario,nombres,usuario, password)values(?, ?, ?, ?)");
			ps.setInt(1, id);
			ps.setString(2, NomApellidos);
			ps.setString(3, strUser);
			ps.setString(4, strPassword);
			System.out.println("Query: " + ps.toString());
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
		/*** ***/
	}

	public int idMayor(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max(id_usuario),0)+1 as idMax from tb_usuarios;");
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

	public int actualizaUsuarioDAO(Connection objConnection, int idUser, String NomApellidos, String strUser,
			String strPassword) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		try {

			ps = objConnection.prepareStatement(
					"update tb_usuarios x set x.nombres = ?, x.usuario = ?, x.password= ?" + "where x.id_usuario = ?");
			ps.setString(1, NomApellidos);
			ps.setString(2, strUser);
			ps.setString(3, strPassword);
			ps.setInt(4, idUser);
			System.out.println("Query: " + ps.toString());
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

	public int eliminaUsuarioDAO(Connection objConnection, int idUser) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		try {

			ps = objConnection.prepareStatement("delete  from tb_usuarios where id_usuario= ?");

			ps.setInt(1, idUser);
			System.out.println("Query: " + ps.toString());
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
	
	public int existeUsuario(Connection objConnection, String usuario) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = objConnection.prepareStatement("select count(f.usuario) as usuario from tb_usuarios f where f.usuario= ?;");
			ps.setString(1, usuario);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("usuario");
			} else
				return rs.getInt("usuario");

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
	
	
	public int loginUsuarioSistema(Connection objConnection, String usuario, String password) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = objConnection.prepareStatement("select count(f.usuario) as usuario from tb_usuarios f where f.usuario= ? and f.password = ? ;");
			ps.setString(1, usuario);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("usuario");
			} else
				return rs.getInt("usuario");

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

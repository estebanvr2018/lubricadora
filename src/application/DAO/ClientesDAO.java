package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.com.DTOS.ClientesDTO;

public class ClientesDAO {
	public List<ClientesDTO> consultaClienteX(Connection objConnection, String strIdentificacion) throws SQLException {

		List<ClientesDTO> lsClientesDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println("Entro al DAO: " + strIdentificacion);
		try {
			ps = objConnection.prepareStatement(" select * from tb_clientes p where p.id_identificacion= ? ");
			ps.setString(1, strIdentificacion);

			rs = ps.executeQuery();
			// ClientesDTO objClientesDTO;
			System.out.println(ps.toString() + "::::::::::::::");
			while (rs.next()) {
				ClientesDTO objClientesDTO = new ClientesDTO();
				objClientesDTO.setIdIdentificacion(rs.getString(1));
				objClientesDTO.setTipoIdentificacion(rs.getString(2));
				objClientesDTO.setNombres(rs.getString(3));
				objClientesDTO.setPrimerApellido(rs.getString(4));
				objClientesDTO.setSegundoApellido(rs.getString(5));
				objClientesDTO.setDireccion(rs.getString(6));
				objClientesDTO.setTelefono(rs.getString(7));
				objClientesDTO.setCorreo(rs.getString(8));
				objClientesDTO.setFechaIngreso(rs.getTimestamp(9) != null ? rs.getTimestamp(9) : null);
				objClientesDTO.setFechaActualizada(rs.getTimestamp(10) != null ? rs.getTimestamp(10) : null);
				objClientesDTO.setEstado(rs.getString(11) != null ? rs.getString(11) : null);
				objClientesDTO.setUsuarioIngreso(rs.getString(12) != null ? rs.getString(12) : null);
				lsClientesDTO.add(objClientesDTO);
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
		return lsClientesDTO;
	}

	public List<ClientesDTO> cargaTClientes(Connection objConnection) throws SQLException {

		List<ClientesDTO> lsClientesDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement(" select * from tb_clientes order by id_identificacion");

			rs = ps.executeQuery();

			// ClientesDTO objClientesDTO;
			System.out.println(ps.toString() + "::::::::::::::");
			while (rs.next()) {
				ClientesDTO objClientesDTO = new ClientesDTO();
				objClientesDTO.setIdIdentificacion(rs.getString(1));
				objClientesDTO.setTipoIdentificacion(rs.getString(2));
				objClientesDTO.setNombres(rs.getString(3));
				objClientesDTO.setPrimerApellido(rs.getString(4));
				objClientesDTO.setSegundoApellido(rs.getString(5));
				objClientesDTO.setDireccion(rs.getString(6));
				objClientesDTO.setTelefono(rs.getString(7));
				objClientesDTO.setCorreo(rs.getString(8));
				objClientesDTO.setFechaIngreso(rs.getTimestamp(9) != null ? rs.getTimestamp(9) : null);
				objClientesDTO.setFechaActualizada(rs.getTimestamp(10) != null ? rs.getTimestamp(10) : null);
				objClientesDTO.setEstado(rs.getString(11) != null ? rs.getString(11) : null);
				objClientesDTO.setUsuarioIngreso(rs.getString(12) != null ? rs.getString(12) : null);

				lsClientesDTO.add(objClientesDTO);
			}

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			System.out.println("error " + e.toString());
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		return lsClientesDTO;
	}

	public int insertaClienteDAO(Connection objConnection, String strId, String strNombre, String strApellidos,
			String strDireccion, String strTelefono, String strCorreo,String usuarioGlobal) throws SQLException {
		/*** ***/
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
				String estado = "A";
		String strApellidosT = " ";
		String tipo = null;
		if (strId.length() == 10)
			tipo = "CED";
		else
			tipo = "RUC";
		Date dateT = new Date();

		DateFormat dateFormatT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		String fechaU = dateFormatT.format(dateT).toString();
		try {

			ps = objConnection.prepareStatement(
					"insert into tb_clientes (id_identificacion,tipo_identificacion,nombres, primer_apellido,segundo_apellido,direccion,telefono,correo,fecha_ingreso,fecha_actualiza, estado,usuario_ingreso)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, strId);
			ps.setString(2, tipo);
			ps.setString(3, strNombre);
			ps.setString(4, strApellidos);
			ps.setString(5, strApellidosT);
			ps.setString(6, strDireccion);
			ps.setString(7, strTelefono);
			ps.setString(8, strCorreo);
			ps.setString(9, fechaU);
			ps.setString(10, fechaU);
			ps.setString(11, estado);
			ps.setString(12, usuarioGlobal);
			resQuery = ps.executeUpdate();
			System.out.println("Que retorna " + resQuery);
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

	public int actualizaClienteDAO(Connection objConnection, String strId, String strNombre, String strApellidos,
			String strDireccion, String strTelefono, String strCorreo, String usuarioGlobal) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
				String estado = "A";
		String strApellidosT = "v";
		Date dateT = new Date();

		DateFormat dateFormatT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		String fechaU = dateFormatT.format(dateT).toString();
		try {

			ps = objConnection.prepareStatement(
					"update tb_clientes x set x.nombres = ?, x.primer_apellido = ?, x.direccion = ?,  x.telefono =?, x.correo = ?, x.fecha_actualiza = ?, x.usuario_ingreso= ?"
							+ "where x.id_identificacion= ?");
			ps.setString(1, strNombre);
			ps.setString(2, strApellidos);
			ps.setString(3, strDireccion);
			ps.setString(4, strTelefono);
			ps.setString(5, strCorreo);
			ps.setString(6, fechaU);
			ps.setString(7, usuarioGlobal);
			ps.setString(8, strId);
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

	
	
	public int existeCLiente(Connection objConnection, String idCliente) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = objConnection.prepareStatement("select count(f.id_identificacion) as cliente from tb_clientes f where f.id_identificacion= ?;");
			ps.setString(1, idCliente);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("cliente");
			} else
				return rs.getInt("cliente");

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

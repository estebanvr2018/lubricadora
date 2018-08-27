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
import application.com.DTOS.ProveedorDTO;

public class ProveedorDAO 
{
	public List<ProveedorDTO> cargaTProveedores(Connection objConnection) throws SQLException {

		List<ProveedorDTO> lsClientesDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement(" select * from tb_proveedores");

			rs = ps.executeQuery();

			while (rs.next()) {
				ProveedorDTO objProductoDTO = new ProveedorDTO();
				objProductoDTO.setIdProveedor(rs.getInt(1));
				objProductoDTO.setRuc(rs.getString(2));
				objProductoDTO.setNombre(rs.getString(3));
				objProductoDTO.setDescripcion(rs.getString(4));
				objProductoDTO.setTelefono(rs.getString(5));
				
				lsClientesDTO.add(objProductoDTO);
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

	/*** Metodo que me va a a traer el cliente***/
	public List<ProveedorDTO> consultaProveedorCed(Connection objConnection, String strIdentificacion) throws SQLException {

		List<ProveedorDTO> lsClientesDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = objConnection.prepareStatement(" select * from tb_proveedores p where p.ruc= ? ");
			ps.setString(1, strIdentificacion);

			rs = ps.executeQuery();
			// ClientesDTO objClientesDTO;
			System.out.println(ps.toString() + "::::::::::::::");
			while (rs.next()) {
				ProveedorDTO objProductoDTO = new ProveedorDTO();
				objProductoDTO.setIdProveedor(rs.getInt(1));
				objProductoDTO.setRuc(rs.getString(2));
				objProductoDTO.setNombre(rs.getString(3));
				objProductoDTO.setDescripcion(rs.getString(4));
				objProductoDTO.setTelefono(rs.getString(5));
				
				lsClientesDTO.add(objProductoDTO);
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
	/*** Fin metodo que me trae a los clientes ***/
	
	/*** INI inserta proveedor***/
	public int insertaProveedorDAO(Connection objConnection, String strIdentificacion,String strNombres, String strTelefono,String srtDescripcion, String usuario)
										throws SQLException {
		/*** ***/
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int idProveedor= idMaxProveedor(objConnection);
		int resQuery = 0;
		String estado = "A";
		Date dateT = new Date();

		DateFormat dateFormatT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		System.out.println("Aqui va ");
		String fechaU = dateFormatT.format(dateT).toString();
		try {

			ps = objConnection.prepareStatement(
					"insert into tb_proveedores (id_proveedor,ruc,nombre, descripcion,telefono,estado,usuario_ingreso)values(?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, idProveedor);
			ps.setString(2, strIdentificacion);
			ps.setString(3, strNombres);
			ps.setString(4, srtDescripcion);
			ps.setString(5, strTelefono);
			ps.setString(6, estado);
			ps.setString(7, usuario);
			System.out.println("Query " + ps.toString());
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
	/*** FIN inserta proveedor***/
	public int idMaxProveedor(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max(id_proveedor),0)+1 as idMax from tb_proveedores;");
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
	
	/*** INI actualiza proveedor ***/
	public int actualizaProveedorDAO(Connection objConnection, String strIdentificacion,String strNombres, String strTelefono,String srtDescripcion, String usuarioGlobal) 
			throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		String estado = "A";
		Date dateT = new Date();

		DateFormat dateFormatT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		String fechaU = dateFormatT.format(dateT).toString();
		try {

			ps = objConnection.prepareStatement(
					"update tb_proveedores x set x.ruc = ?, x.nombre = ?, x.descripcion = ?,  x.telefono =?, x.fecha_ingreso = ?, x.estado = ?, x.usuario_ingreso= ?"
							+ "where x.ruc= ?");
			ps.setString(1, strIdentificacion);
			ps.setString(2, strNombres);
			ps.setString(3, srtDescripcion);
			ps.setString(4,  strTelefono);
			ps.setString(5, fechaU);
			ps.setString(6, estado);
			ps.setString(7, usuarioGlobal);
			ps.setString(8, strIdentificacion);
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
	/*** FIn actualiza proveedor***/
	
	/*** Metodo que trae categorias principales de los productos ***/
	public List<ProveedorDTO> extraeProveedores(Connection objConnection) throws SQLException {

		List<ProveedorDTO> lsProductosDTO = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(" select * from tb_proveedores ");

			rs = ps.executeQuery();

			while (rs.next()) {
				ProveedorDTO objProductosDTO = new ProveedorDTO();
				objProductosDTO.setIdProveedor(rs.getInt(1));
				objProductosDTO.setNombre(rs.getString(3));
				objProductosDTO.setDescripcion(rs.getString(4));
				lsProductosDTO.add(objProductosDTO);
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
		return lsProductosDTO;
	}
	
	
	public int existeProveedor(Connection objConnection, String cedula) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = objConnection.prepareStatement("select count(f.ruc) as cedula from tb_proveedores f where f.ruc= ?;");
			ps.setString(1, cedula);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("cedula");
			} else
				return rs.getInt("cedula");

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

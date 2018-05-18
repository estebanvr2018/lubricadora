package application.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.com.DTOS.ProductosDTO;
import application.com.DTOS.productosCategoriaDTO;
import application.com.DTOS.productosDescripcionDTO;



public class ProductosDAO {

	public List<ProductosDTO> listarProductosXCodigo(Connection objConnection, String strCodigo) throws SQLException 
	{

		List<ProductosDTO> lsProductosDTO = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(" select * from tb_productos p where p.id_producto like ? or descripcion like ? ");
			ps.setString(1, "%" + strCodigo + "%");
			ps.setString(2, "%" + strCodigo + "%");
			
			rs = ps.executeQuery();

			while (rs.next()) {
				ProductosDTO objProductosDTO = new ProductosDTO();

				objProductosDTO.setIdProducto(rs.getInt(1));
				objProductosDTO.setIdTipoProducto(rs.getInt(2));
				objProductosDTO.setIdCapacidad(rs.getInt(3));
				objProductosDTO.setIdUnidad(rs.getInt(4));
				objProductosDTO.setCantidad(rs.getInt(5));
				objProductosDTO.setDescripcion(rs.getString(6));
				objProductosDTO.setValorUnitario(rs.getFloat(7));
				objProductosDTO.setValorCompra(rs.getFloat(8));
				objProductosDTO.setStock(rs.getInt(9));
				objProductosDTO.setFechaIngreso(rs.getTimestamp(10));
				objProductosDTO.setFechaActualizada(rs.getTimestamp(11));
				objProductosDTO.setEstado(rs.getString(12));
				objProductosDTO.setUsuarioIngreso(rs.getString(13));

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
	
	
	public List<ProductosDTO> extraeProductosDAO(Connection objConnection, String strProducto) throws SQLException 
	{

		List<ProductosDTO> lsProductosDTO = new ArrayList<>();

		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(" select * from tb_productos p where p.id_producto like ? or descripcion like ?");
			ps.setString(1, "%" + strProducto + "%");
			ps.setString(2, "%" + strProducto + "%");	
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ProductosDTO objProductosDTO = new ProductosDTO();
				objProductosDTO.setIdProducto(rs.getInt(1));
				System.out.println(objProductosDTO.getIdProducto());
				objProductosDTO.setIdTipoProducto(rs.getInt(2));
				System.out.println(objProductosDTO.getIdTipoProducto());
				objProductosDTO.setIdCapacidad(rs.getInt(3));
				objProductosDTO.setIdUnidad(rs.getInt(4));
				objProductosDTO.setCantidad(rs.getInt(5));
				objProductosDTO.setDescripcion(rs.getString(6));
				objProductosDTO.setValorUnitario(rs.getFloat(7));
				objProductosDTO.setValorCompra(rs.getFloat(8));
				objProductosDTO.setStock(rs.getInt(9));
			    objProductosDTO.setFechaIngreso(rs.getTimestamp(10));
			    objProductosDTO.setFechaActualizada(rs.getTimestamp(11));
				objProductosDTO.setEstado(rs.getString(12));
				objProductosDTO.setUsuarioIngreso(rs.getString(13));
				
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
	
	/*** Metodo que trae categorias principales de los productos***/
	public List<productosCategoriaDTO> extraeProductosCAT(Connection objConnection) throws SQLException 
	{

		List<productosCategoriaDTO> lsProductosDTO = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(" select * from tb_producto_cat ");	
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				productosCategoriaDTO objProductosDTO = new productosCategoriaDTO();
				objProductosDTO.setId_producto_cat(rs.getInt(1));
				objProductosDTO.setDescripcion(rs.getString(2));
				
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
	
	
	/*** INICIO  trae categoria Sub ***/
	
	/*** ****/ 
	public List<productosDescripcionDTO> extraeProductoSubCat(Connection objConnection, String idCategoria) throws SQLException 
	{

		List<productosDescripcionDTO> lsProductosDTO = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(" select * from tb_producto_descrip f where f.id_producto_cat =(select g.id_producto_cat from tb_producto_cat g where g.descripcion like ?);");	
			ps.setString(1, "%" + idCategoria + "%");
			rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				productosDescripcionDTO objProductosDTO = new productosDescripcionDTO();
				objProductosDTO.setId_des_producto(rs.getInt(1));
				objProductosDTO.setDescripcion(rs.getString(3));
				
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
	
	
	/*** FIN trae categoria Sub ***/
	/*** ***/
	
	public int insertaProductoCategoria(Connection objConnection, String strProducto) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery=0;
		int id_productoCat = idMayorPCAT(objConnection);
		String user="Lubri";
		String estado="A";
		
		try {
			ps = objConnection.prepareStatement("insert into tb_producto_cat (id_producto_cat,descripcion,estado,usuario_ingreso)values(?, ?, ?, ?)");
			ps.setInt(1, id_productoCat);
			ps.setString(2, strProducto);
			ps.setString(3, estado);
			ps.setString(4, user);
			resQuery = ps.executeUpdate();
			if ( resQuery == 1)
			{
				resQuery = id_productoCat;
			}	
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery=-1;
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
	/*** FIN inserta categoría ***/
	
	public int insertaPrCategDes(Connection objConnection, int id, String strProducto) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery=0;
		int id_productoCat = idMayorProducto(objConnection);
		String user="Lubri";
		String estado="A";
		try {
			ps = objConnection.prepareStatement("insert into tb_producto_descrip (id_prod_des,id_producto_cat,descripcion,estado,usuario_ingreso)values(?, ?, ?, ?, ?)");
			ps.setInt(1, id_productoCat);
			ps.setInt(2, id);
			ps.setString(3, strProducto);
			ps.setString(4, estado);
			ps.setString(5, user);
			resQuery = ps.executeUpdate();
			
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery=-1;
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
	/*** FIN inserta categoría ***/

	public int insertaPrCSoloSubcategoria(Connection objConnection,  String subCate, String strProducto) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery=0;
		int id_productoCat = idMayorProducto(objConnection);
		int id_categ = idCategoria(objConnection, strProducto);
		String user="Lubri";
		String estado="A";
		try {
			ps = objConnection.prepareStatement("insert into tb_producto_descrip (id_prod_des,id_producto_cat,descripcion,estado,usuario_ingreso)values(?, ?, ?, ?, ?)");
			ps.setInt(1, id_productoCat);
			ps.setInt(2, id_categ);
			ps.setString(3, subCate);
			ps.setString(4, estado);
			ps.setString(5, user);
			resQuery = ps.executeUpdate();
			
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery=-1;
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
	/*** FIN inserta categoría ***/
	
	public int insertaProductosDAO(Connection objConnection, String strProducto, String descripcionProd, float valorUni, int Stock, float fltPrecioVta) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery=0;
		int id_producto = idMayorProducto(objConnection);
		String user="Lubri";
		String estado="A";
		try {
			ps = objConnection.prepareStatement("insert into tb_producto_descrip (id_prod_des,id_producto_cat,descripcion,precioCompra,stock,precioVenta,estado,usuario_ingreso)values(?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, id_producto);
			ps.setInt(2, id_producto);
			ps.setString(3, descripcionProd);
			ps.setFloat(4, valorUni);
			ps.setInt(5, Stock);
			ps.setFloat(6, fltPrecioVta);
			ps.setString(7, estado);
			ps.setString(8, user);
			resQuery = ps.executeUpdate();
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			resQuery=-1;
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
	
	
	public int idMayor(Connection objConnection) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max((id_producto))+1,-1) as idMax from tb_producto;");
			rs = ps.executeQuery();
			
			if (rs.next() == true) 
			{
				return rs.getInt("idMax");
			}
			else 
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
	
	public int idCategoria(Connection objConnection, String categoria) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select f.id_producto_cat as idCategoria from tb_producto_cat f where f.descripcion= ?");
			ps.setString(1, categoria);
			rs = ps.executeQuery();
			
			if (rs.next() == true) 
			{
				return rs.getInt("idCategoria");
			}
			else 
				return rs.getInt("idCategoria");

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
	
	public int idMayorProducto(Connection objConnection) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max((id_prod_des))+1,-1) as idMax from tb_producto_descrip;");
			rs = ps.executeQuery();
			
			if (rs.next() == true) 
			{
				return rs.getInt("idMax");
			}
			else 
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
	
	public int idMayorPCAT(Connection objConnection) throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max((id_producto_cat))+1,-1) as idMax from tb_producto_cat;");
			rs = ps.executeQuery();
			
			if (rs.next() == true) 
			{
				return rs.getInt("idMax");
			}
			else 
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

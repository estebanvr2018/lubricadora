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
import application.com.DTOS.productoDTO;
import application.com.DTOS.productosCategoriaDTO;
import application.com.DTOS.productosDescripcionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;

public class ProductosDAO {

	public List<ProductosDTO> listarProductosXCodigo(Connection objConnection, String strCodigo) throws SQLException {

		List<ProductosDTO> lsProductosDTO = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection
					.prepareStatement(" select * from tb_producto p where p.id_producto like ? or descripcion like ? ");
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

	public List<productoDTO> extraeProductosDAO(Connection objConnection, String strProducto) throws SQLException {

		List<productoDTO> lsProductosDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("SELECT    P.ID_PRODUCTO, " + "		   P.NOMBREPRODUCTO,"
					+ "	       P.DESCRIPCION," +"	       P.VALOR_COMPRA,"+"	       P.STOCK,"+"	       P.VALOR_VENTA,"  
					+ "	       C.DESCRIPCION    AS CATEGORIA," + "	       D.DESCRIPCION    AS DESCRIPCION"
					+ "	  FROM TB_PRODUCTO_CAT     C," + "	       TB_PRODUCTO_DESCRIP D,"
					+ "	       TB_PRODUCTO         P" + "	 WHERE C.ID_PRODUCTO_CAT = D.ID_PRODUCTO_CAT"
					+ "	   AND D.ID_PROD_DES = P.ID_PROD_DES" + "	   AND P.ID_PRODUCTO IN"
					+ "	       (SELECT DISTINCT (A.ID_PRODUCTO)" + "	          FROM (SELECT P.ID_PRODUCTO"
					+ "	                  FROM TB_PRODUCTO P" + "	                 WHERE P.NOMBREPRODUCTO LIKE ?"
					+ "	                    OR P.DESCRIPCION LIKE ?" + "	                UNION ALL"
					+ "	                SELECT P.ID_PRODUCTO" + "	                  FROM TB_PRODUCTO_DESCRIP D,"
					+ "	                       TB_PRODUCTO         P"
					+ "	                 WHERE D.ID_PROD_DES = P.ID_PROD_DES"
					+ "	                   AND D.DESCRIPCION LIKE ?" + "	                UNION ALL"
					+ "	                SELECT P.ID_PRODUCTO" + "	                  FROM TB_PRODUCTO_CAT     C,"
					+ "	                       TB_PRODUCTO_DESCRIP D,"
					+ "	                       TB_PRODUCTO         P"
					+ "	                 WHERE C.ID_PRODUCTO_CAT = D.ID_PRODUCTO_CAT"
					+ "	                   AND D.ID_PROD_DES = P.ID_PROD_DES"
					+ "	                   AND C.DESCRIPCION LIKE ?) A)" + "   	ORDER BY P.ID_PRODUCTO;");
			ps.setString(1, "%" + strProducto + "%");
			ps.setString(2, "%" + strProducto + "%");
			ps.setString(3, "%" + strProducto + "%");
			ps.setString(4, "%" + strProducto + "%");
			System.out.println(ps.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				productoDTO objProductosDTO = new productoDTO();
				objProductosDTO.setIdProducto(rs.getInt(1));
				// objProductosDTO.setIdProductoDescri(rs.getInt(2));
				objProductosDTO.setNombreProducto(rs.getString(2));
				objProductosDTO.setDescripcion(rs.getString(3));
				objProductosDTO.setNombreDescripcion(
						objProductosDTO.getNombreProducto() + " " + objProductosDTO.getDescripcion());
				objProductosDTO.setValorVenta(rs.getFloat(6)); // .setValorCompra(rs.getFloat(4));
				objProductosDTO.setStock(rs.getInt(5));
				objProductosDTO.setValorCompra(rs.getFloat(4));
				// objProductosDTO.setValorVenta(rs.getFloat(7));
				// objProductosDTO.setFechaIngreso(rs.getTimestamp(8));
				// objProductosDTO.setFechaActualiza(rs.getTimestamp(9));
				// objProductosDTO.setEstado(rs.getString(10));
				// objProductosDTO.setUsuarioIngreso(rs.getString(11));
				objProductosDTO.setCategoria(rs.getString(7));
				objProductosDTO.setSubcategoria(rs.getString(8));
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

	public List<productoDTO> traeProductosDAO(Connection objConnection, String strProducto) throws SQLException {

		List<productoDTO> lsProductosDTO = new ArrayList<>();
		int idProducto = idCategoria(objConnection, strProducto);

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(" select * from tb_producto p where p.id_prod_des = ?");
			ps.setInt(1, idProducto);

			rs = ps.executeQuery();

			while (rs.next()) {
				productoDTO objProductosDTO = new productoDTO();
				objProductosDTO.setIdProducto(rs.getInt(1));
				objProductosDTO.setIdProductoDescri(rs.getInt(2));
				
				objProductosDTO.setNombreProducto(rs.getString(3));
				objProductosDTO.setDescripcion(rs.getString(4));
				objProductosDTO.setValorCompra(rs.getFloat(5));
				objProductosDTO.setStock(rs.getInt(6));
				objProductosDTO.setValorVenta(rs.getFloat(7));
				objProductosDTO.setFechaIngreso(rs.getTimestamp(8));
				objProductosDTO.setFechaActualiza(rs.getTimestamp(9));
				objProductosDTO.setEstado(rs.getString(10));
				objProductosDTO.setUsuarioIngreso(rs.getString(11));

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
	
	/*** Trae productos por id proveedor ***/
	public List<productoDTO> traeProdProveedor(Connection objConnection, String strProducto) throws SQLException {

		List<productoDTO> lsProductosDTO = new ArrayList<>();
		
		int idProveedor = idProveedor(objConnection, strProducto);
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			//ps = objConnection.prepareStatement(" select * from tb_producto f where f.id_proveedor= ?");
			/**/
			System.out.println("11"); 
			ps = objConnection.prepareStatement("SELECT    P.ID_PRODUCTO,"	   
												+ "		   P.NOMBREPRODUCTO,"
												+ "		   P.DESCRIPCION, "
												+ "        P.VALOR_COMPRA, "
												+ "        P.STOCK, "
												+ "        P.VALOR_VENTA," 
												+ "        P.fecha_ingreso,"
												+ "		   P.fecha_actualiza,"
												+ "		   C.DESCRIPCION  AS CATEGORIA," 
												+ "        D.DESCRIPCION AS DESCRIPCION,"
												+ "		   P.estado,"
												+ "		   P.usuario_ingreso"
												+ "		   FROM TB_PRODUCTO_CAT C,"
												+ "		   TB_PRODUCTO_DESCRIP D,"
												+ "		   TB_PRODUCTO P "
												+ "		   WHERE C.ID_PRODUCTO_CAT = D.ID_PRODUCTO_CAT"
												+ "		   AND D.ID_PROD_DES = P.ID_PROD_DES"
												+ "        AND P.id_proveedor= ?");

			/**/
			
			ps.setInt(1, idProveedor);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				productoDTO objProductosDTO = new productoDTO();
				objProductosDTO.setIdProducto(rs.getInt(1));
				/*objProductosDTO.setIdProductoDescri(rs.getInt(2));
				objProductosDTO.setIdProveedor(rs.getInt(3)); */
				objProductosDTO.setNombreProducto(rs.getString(2));
				objProductosDTO.setDescripcion(rs.getString(3));
				objProductosDTO.setValorCompra(rs.getFloat(4));
				objProductosDTO.setStock(rs.getInt(5));
				objProductosDTO.setValorVenta(rs.getFloat(6));
				objProductosDTO.setFechaIngreso(rs.getTimestamp(7));
				objProductosDTO.setFechaActualiza(rs.getTimestamp(8));
				objProductosDTO.setCategoria(rs.getString(9));
				objProductosDTO.setSubcategoria(rs.getString(10));
				objProductosDTO.setEstado(rs.getString(11));
				objProductosDTO.setUsuarioIngreso(rs.getString(12));

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
	/*** FIN ***/

	/*** carga dinamica***/
	public String nomProveedor(Connection objConnection, int idProveedor) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select nombre from tb_proveedores f where f.id_proveedor=?;");
			ps.setInt(1, idProveedor);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getString("nombre");
			} else
				return rs.getString("nombre");

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
			return null;
		} finally {
			if (rs != null && rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
	
	
	/*** Metodo que trae categorias principales de los productos ***/
	public List<productosCategoriaDTO> extraeProductosCAT(Connection objConnection) throws SQLException {

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

	/*** INICIO trae categoria Sub ***/

	/*** ****/
	public List<productosDescripcionDTO> extraeProductoSubCat(Connection objConnection, String idCategoria)
			throws SQLException {

		List<productosDescripcionDTO> lsProductosDTO = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(
					" select * from tb_producto_descrip f where f.id_producto_cat =(select g.id_producto_cat from tb_producto_cat g where g.descripcion like ?);");
			ps.setString(1, "%" + idCategoria + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
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

	public int insertaProductoCategoria(Connection objConnection, String strProducto, String user) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int id_productoCat = idMayorPCAT(objConnection);
		String estado = "A";

		try {
			ps = objConnection.prepareStatement(
					"insert into tb_producto_cat (id_producto_cat,descripcion,estado,usuario_ingreso)values(?, ?, ?, ?)");
			ps.setInt(1, id_productoCat);
			ps.setString(2, strProducto);
			ps.setString(3, estado);
			ps.setString(4, user);
			resQuery = ps.executeUpdate();
			if (resQuery == 1) {
				resQuery = id_productoCat;
			}
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

	/*** FIN inserta categoría ***/

	public int insertaPrCategDes(Connection objConnection, int id, String strProducto, String user) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int id_productoCat = idMayorProducto(objConnection);
		String estado = "A";
		try {
			ps = objConnection.prepareStatement(
					"insert into tb_producto_descrip (id_prod_des,id_producto_cat,descripcion,estado,usuario_ingreso)values(?, ?, ?, ?, ?)");
			ps.setInt(1, id_productoCat);
			ps.setInt(2, id);
			ps.setString(3, strProducto);
			ps.setString(4, estado);
			ps.setString(5, user);
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

	/*** FIN inserta categoría ***/

	public int insertaPrCSoloSubcategoria(Connection objConnection, String subCate, String strProducto,String usuarioGlobal)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int id_productoCat = idMayorProducto(objConnection);
		int id_categ = idCategoriaSub(objConnection, strProducto);
		String estado = "A";
		try {
			ps = objConnection.prepareStatement(
					"insert into tb_producto_descrip (id_prod_des,id_producto_cat,descripcion,estado,usuario_ingreso)values(?, ?, ?, ?, ?)");
			ps.setInt(1, id_productoCat);
			ps.setInt(2, id_categ);
			ps.setString(3, subCate);
			ps.setString(4, estado);
			ps.setString(5, usuarioGlobal);
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

	/*** FIN inserta categoría ***/

	public int insertaProductosDAO(Connection objConnection, int idProveedor,String prodEsp, String strProducto, String descripcionProd,
			float valorUni, int Stock, float fltPrecioVta, String usuarioGlobal) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int id_producto = idProducto(objConnection);
		int id_proSub = idCategoria(objConnection, prodEsp);
		String estado = "A";
		try {
			ps = objConnection.prepareStatement(
					"insert into tb_producto (id_producto,id_prod_des,id_proveedor,nombreProducto,descripcion,valor_compra,stock,valor_venta,estado,usuario_ingreso)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, id_producto);
			ps.setInt(2, id_proSub);
			ps.setInt(3, idProveedor);
			ps.setString(4, strProducto);
			ps.setString(5, descripcionProd);
			ps.setFloat(6, valorUni);
			ps.setInt(7, Stock);
			ps.setFloat(8, fltPrecioVta);
			ps.setString(9, estado);
			ps.setString(10, usuarioGlobal);
			resQuery = ps.executeUpdate();
			System.out.println(ps.toString());
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
	
	/* Actualizar productos */
	public int actualizarProducto(Connection objConnection,  int idProducto, float valorUni, int Stock, float fltPrecioVta) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		String user = "Lubri";
		String estado = "A";
		try {
			ps = objConnection.prepareStatement(" UPDATE tb_producto  "
					+" SET  valor_compra = ? ,"
					+"             stock = stock + ?, "
					+"       valor_venta = ?, "
					+"            estado = ?, " 
					+"  usuario_ingreso = ? "
					+" WHERE id_producto = ? ;");
			
			ps.setFloat(1, valorUni);
			ps.setInt(2, Stock);
			ps.setFloat(3, fltPrecioVta);
			ps.setString(4, estado);
			ps.setString(5, user);
			ps.setInt(6, idProducto);
			System.out.println("Query :"+ps.toString());
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
	/* FIN actualiza productos */
	public int actualizarProductos(Connection objConnection, int intIdProd, String prodEsp, String strProducto,
			String descripcionProd, float valorUni, int Stock, float fltPrecioVta, String usuarioGlobal) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		int id_proSub = idCategoria(objConnection, prodEsp);
		String estado = "A";
		try {
			ps = objConnection.prepareStatement("UPDATE tb_producto\n" + "SET `id_prod_des` = ?,\n"
					+ "`nombreProducto` = ?,\n" + "`descripcion` = ?,\n" + "`valor_compra` = ?,\n" + "`stock` = ?,\n"
					+ "`valor_venta` = ?,\n" + "`estado` = ?,\n" + "`usuario_ingreso` = ?\n"
					+ "WHERE `id_producto` = ?;");
			ps.setInt(1, id_proSub);
			ps.setString(2, strProducto);
			ps.setString(3, descripcionProd);
			ps.setFloat(4, valorUni);
			ps.setInt(5, Stock);
			ps.setFloat(6, fltPrecioVta);
			ps.setString(7, estado);
			ps.setString(8, usuarioGlobal);
			ps.setInt(9, intIdProd);
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

	public int idMayor(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max(id_producto),0)+1 as idMax from tb_producto;");
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

	public int idCategoria(Connection objConnection, String categoria) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(
					"select f.id_prod_des as idCategoria from tb_producto_descrip f where f.descripcion= ?");
			ps.setString(1, categoria);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("idCategoria");
			} else
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
	
	/* Trae productos por nombre proveedor */
	public int idProveedor(Connection objConnection, String categoria) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(
					"select f.id_proveedor as proveedor from tb_proveedores f where f.nombre=?");
			ps.setString(1, categoria);
			
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("proveedor");
			} else
				return rs.getInt("proveedor");

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

	/* FIN trae productos por nombre proveedor*/

	public int idCategoriaSub(Connection objConnection, String categoria) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(
					"select f.id_producto_cat as idCategoria from tb_producto_cat f where f.descripcion= ?");
			ps.setString(1, categoria);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("idCategoria");
			} else
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

	public int idMayorProducto(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection
					.prepareStatement("select ifnull(max(id_prod_des),0)+1 as idMax from tb_producto_descrip;");
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

	public int idSubproducto(Connection objConnection, String descriSub) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection
					.prepareStatement("select id_prod_des as id from tb_producto_descrip f where f.descripcion= ?;");
			ps.setString(1, descriSub);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("id");
			} else
				return rs.getInt("id");

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

	public int idMayorPCAT(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection
					.prepareStatement("select ifnull(max(id_producto_cat),0)+1 as idMax from tb_producto_cat;");
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

	public int idProducto(Connection objConnection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("select ifnull(max(id_producto),0)+1 as idMax from tb_producto;");
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

	public List<productoDTO> consultaProductos(Connection objConnection) throws SQLException {

		List<productoDTO> lsProductosDTO = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement("SELECT    P.ID_PRODUCTO, " + "		   P.NOMBREPRODUCTO,"
					+ "	       P.DESCRIPCION, " + "	P.VALOR_COMPRA, " + " P.VALOR_VENTA," + "	       P.STOCK,"
					+ "	       C.DESCRIPCION    AS CATEGORIA," + "	       D.DESCRIPCION    AS DESCRIPCION"
					+ "	  FROM TB_PRODUCTO_CAT     C," + "	       TB_PRODUCTO_DESCRIP D,"
					+ "	       TB_PRODUCTO         P" + "	 WHERE C.ID_PRODUCTO_CAT = D.ID_PRODUCTO_CAT"
					+ "	   AND D.ID_PROD_DES = P.ID_PROD_DES" + "   ORDER BY P.ID_PRODUCTO");
			rs = ps.executeQuery();
			System.out.println(ps.toString());
			while (rs.next()) {
				productoDTO objProductosDTO = new productoDTO();
				objProductosDTO.setIdProducto(rs.getInt(1));
				// objProductosDTO.setIdProductoDescri(rs.getInt(2));
				
				//Cambiar
				
				/*String NomPRod="";
				NomPRod = rs.getString(2);
			
				SimpleStringProperty actual = new SimpleStringProperty (NomPRod);
				objProductosDTO.setNombreProducto(actual);*/
				objProductosDTO.setNombreProducto(rs.getString(2));
				objProductosDTO.setDescripcion(rs.getString(3));
				objProductosDTO.setNombreDescripcion(
						objProductosDTO.getNombreProducto() + " " + objProductosDTO.getDescripcion());
				objProductosDTO.setValorVenta(rs.getFloat(5)); // 
				objProductosDTO.setValorCompra(rs.getFloat(4));
				objProductosDTO.setStock(rs.getInt(6));
				// objProductosDTO.setValorVenta(rs.getFloat(7));
				// objProductosDTO.setFechaIngreso(rs.getTimestamp(8));
				// objProductosDTO.setFechaActualiza(rs.getTimestamp(9));
				// objProductosDTO.setEstado(rs.getString(10));
				// objProductosDTO.setUsuarioIngreso(rs.getString(11));
				objProductosDTO.setCategoria(rs.getString(7));
				objProductosDTO.setSubcategoria(rs.getString(8));
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

	
	
	public int existeCategoria(Connection objConnection, String categoria) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = objConnection.prepareStatement(
					"select count(f.id_producto_cat) as idCategoria from tb_producto_cat f where f.descripcion=? ;");
			ps.setString(1, categoria);
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("idCategoria");
			} else
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
	
	
	
	public int existeSubcategoria(Connection objConnection, String categoria, String descripcion) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id_categ = idCategoriaSub(objConnection, categoria);
		try {
			ps = objConnection.prepareStatement(
					 " select count(f.descripcion) as idCategoria "	
					+ " from tb_producto_descrip f " 
					+ " where f.id_producto_cat= ? "
			        + " and f.descripcion = ? ;");
			ps.setInt(1, id_categ);
			ps.setString(2, descripcion);
			System.out.println("Query : "+ps.toString());
			rs = ps.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("idCategoria");
			} else
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
	
	
	public int actualizarProductoStock(Connection objConnection,  int idProducto, int Stock) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resQuery = 0;
		String user = "Lubri";
		String estado = "A";
		try {
			ps = objConnection.prepareStatement(" UPDATE tb_producto " 
												+" SET stock = stock + ? "
												+" WHERE id_producto = ? ;");
			
			ps.setInt(1, Stock);
			ps.setInt(2, idProducto);
			System.out.println("Query :"+ps.toString());
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
}

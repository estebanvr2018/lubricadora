package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import application.DAO.ProductosDAO;
import application.com.DTOS.*;
import application.com.conxionMySql.ConexionMySQL;

public class ProductosBO {

	ProductosDAO objDAO = new ProductosDAO();

	public List<ProductosDTO> listarProductosXCodigo(String strCodigo) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<ProductosDTO> lsProductosDTO = null;

		try {
			lsProductosDTO = new ProductosDAO().listarProductosXCodigo(objConnection, strCodigo);
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lsProductosDTO;
	}

	public List<productoDTO> extraeProductos(String strProducto) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<productoDTO> lsProductosDTO = null;

		try {
			lsProductosDTO = new ProductosDAO().extraeProductosDAO(objConnection, strProducto);
			System.out.println("Que trae " + lsProductosDTO.size());
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}

	public List<productoDTO> traeProductos(String strProducto) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<productoDTO> lsProductosDTO = null;

		try {
			lsProductosDTO = new ProductosDAO().traeProductosDAO(objConnection, strProducto);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}
	
	/* Ini productos por proveedor*/
	public List<productoDTO> traeProductosProveedor(String strProducto) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<productoDTO> lsProductosDTO = null;
		
		try {
			
			lsProductosDTO = new ProductosDAO().traeProdProveedor(objConnection, strProducto);
			

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}
	/* Fin productos pro proveedor**/
	
	/* Ini productos por proveedor*/
	public String traeNombreProveedor(int strPro) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		String lsProductosDTO = null;

		try {
			lsProductosDTO = new ProductosDAO().nomProveedor(objConnection, strPro);//.nomProveedor(objConnection, strProducto);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}
	/* Fin productos pro proveedor**/

	public FacturaDtlDTO valoresTabla(int idProducto, int intCantidad, String strDescripcion, float Precio) {
		float productoTotal = 0, productoNIVA = 0, iva = 0;

		iva = ((12 * Precio) / 100);

		productoNIVA = Precio - iva;
		productoTotal = productoNIVA * intCantidad;
		FacturaDtlDTO retorno = new FacturaDtlDTO();
		retorno.setIdProducto(idProducto);
		retorno.setCantidad(intCantidad);
		retorno.setDescripcion(strDescripcion);

		DecimalFormat df = new DecimalFormat("#.00");
		String ivaEstandar = df.format(productoNIVA);
		String totalProducto = df.format(productoTotal);
		String auxIva = ivaEstandar.replaceAll(",", ".");
		String totalProd = totalProducto.replaceAll(",", ".");
		float ivaDoce = 0;
		float TotalVenta = 0;
		ivaDoce = Float.parseFloat(auxIva);
		TotalVenta = Float.parseFloat(totalProd);

		retorno.setValor(ivaDoce);
		retorno.setTotal(TotalVenta);
		return retorno;
	}

	/*** Traer productos de la tabla tb_productos_cat ***/
	public List<productosCategoriaDTO> extraeProductosCAT() throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<productosCategoriaDTO> lsProductosDTO = null;

		try {
			lsProductosDTO = new ProductosDAO().extraeProductosCAT(objConnection);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}

	/*** FIN tb_productos CAT ***/

	/*** Traer productos de la tabla tb_productos_cat_descripcion ***/
	public List<productosDescripcionDTO> extraeProdCatSub(String DescCategoria) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<productosDescripcionDTO> lsProductosDTO = null;
		try {
			lsProductosDTO = new ProductosDAO().extraeProductoSubCat(objConnection, DescCategoria);

		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}

		return lsProductosDTO;
	}

	/*** FIN tb_productos CAT tb_productos_cat_descripcion ***/

	/*** INICIO Inserta producto categoria ***/
	public int insertaCategoriaProd(String descripcionProd, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().insertaProductoCategoria(objConnection, descripcionProd, usuarioGlobal);
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

	/*** FIN Inserta producto categoria ***/

	/*** INICIO Inserta producto subcategoria ***/
	public int insertaSubCatProd(int idCat, String descripcionProd, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().insertaPrCategDes(objConnection, idCat, descripcionProd, usuarioGlobal);
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

	/*** FIN Inserta producto subcategoria ***/

	/*** INICIO Inserta solo subproducto subcategoria ***/
	public int insertaSubProducto(String SubCate, String descripcionProd, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().insertaPrCSoloSubcategoria(objConnection, SubCate, descripcionProd, usuarioGlobal);
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

	/*** FIN Inserta producto subcategoria ***/

	public int insertaProductos(int idProveedor,String prodEsp, String nomProd, String descripcionProd, float precioCompra, int stock,
			float fltPrecioVta, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().insertaProductosDAO(objConnection, idProveedor,prodEsp, nomProd, descripcionProd,
					precioCompra, stock, fltPrecioVta,usuarioGlobal);
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
	
	/* INI Modifica productos */
	public int modificaProductosNew( int idProducto, float precioCompra, int stock, float fltPrecioVta) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().actualizarProducto(objConnection, idProducto, precioCompra, stock, fltPrecioVta);
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
	/* FIN */

	public int actualizarProductos(int intIdProd, String prodEsp, String nomProd, String descripcionProd,
			float precioCompra, int stock, float fltPrecioVta, String usuarioGlobal) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().actualizarProductos(objConnection, intIdProd, prodEsp, nomProd,
					descripcionProd, precioCompra, stock, fltPrecioVta,usuarioGlobal);
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

	public List<productoDTO> consultaProductos() throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		List<productoDTO> lsProductosDTO = null;

		try {
			lsProductosDTO = new ProductosDAO().consultaProductos(objConnection);
			
		} catch (Exception e) {
			StringWriter errores = new StringWriter();
			e.printStackTrace(new PrintWriter(errores));
		} finally {
			if (objConnection != null) {
				objConnection.close();
			}
		}
		return lsProductosDTO;
	}

	
	
	public int existeCategoriaProd(String descripcionProd) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().existeCategoria(objConnection, descripcionProd); //.insertaProductoCategoria(objConnection, descripcionProd);
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
	
	
	
	
	public int existeSubCatProductos(String descripcionProd, String descripcion) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().existeSubcategoria(objConnection, descripcionProd, descripcion); //.existeCategoria(objConnection, descripcionProd); //.insertaPrCSoloSubcategoria(objConnection, SubCate, descripcionProd);
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
	
	
	public int modifStockProductos( int idProducto, int stock) throws SQLException {
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery = 0;
		try {
			insertQuery = new ProductosDAO().actualizarProductoStock(objConnection, idProducto, stock); // .actualizarProducto(objConnection, idProducto, stock);
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

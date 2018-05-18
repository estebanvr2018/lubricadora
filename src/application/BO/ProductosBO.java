package application.BO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import application.DAO.ProductosDAO;
import application.com.DTOS.*;
import application.com.conxionMySql.ConexionMySQL;
import application.tablas.tablaFacturaDet;

public class ProductosBO {

	ProductosDAO objDAO = new ProductosDAO();

	public List<ProductosDTO> listarProductosXCodigo(String strCodigo) throws SQLException 
	{
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
	
	public List<ProductosDTO> extraeProductos(String strProducto) throws SQLException 
	{
		Connection objConnection = new ConexionMySQL().conexion();
		List<ProductosDTO> lsProductosDTO = null;
		
		try {
			lsProductosDTO = new ProductosDAO().extraeProductosDAO(objConnection, strProducto);
		
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
	
	public tablaFacturaDet valoresTabla(int intCantidad, String strDescripcion, float Precio)
	{
		float total=0;
		total= intCantidad * Precio;
		tablaFacturaDet retorno = new tablaFacturaDet();
		retorno.setCantidad(intCantidad);
		retorno.setDescripcion(strDescripcion);
		retorno.setValorUnitario(Precio);
		retorno.setPrecio(total);
		return retorno;
	}
	
	/*** Traer productos de la tabla tb_productos_cat***/
	public List<productosCategoriaDTO> extraeProductosCAT() throws SQLException 
	{
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
	/*** FIN tb_productos CAT***/
	
	/*** Traer productos de la tabla tb_productos_cat_descripcion***/
	public List<productosDescripcionDTO> extraeProdCatSub(String DescCategoria) throws SQLException 
	{
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
	/*** FIN tb_productos CAT tb_productos_cat_descripcion***/
	
	/*** INICIO Inserta producto categoria ***/
	public int insertaCategoriaProd(String descripcionProd) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		try {
			insertQuery = new ProductosDAO().insertaProductoCategoria(objConnection,descripcionProd);
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
	public int insertaSubCatProd(int idCat, String descripcionProd) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		try {
			insertQuery = new ProductosDAO().insertaPrCategDes(objConnection,idCat,descripcionProd);
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
	public int insertaSubProducto(String SubCate ,String descripcionProd) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		try {
			insertQuery = new ProductosDAO().insertaPrCSoloSubcategoria(objConnection, SubCate, descripcionProd);
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
	
	
	public int insertaProductos(String nomProd, String descripcionProd, float precioCompra, int stock, float fltPrecioVta) throws SQLException
	{
		Connection objConnection = new ConexionMySQL().conexion();
		int insertQuery=0;
		try {
			insertQuery = new ProductosDAO().insertaProductosDAO(objConnection, nomProd,descripcionProd, precioCompra, stock, fltPrecioVta);
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

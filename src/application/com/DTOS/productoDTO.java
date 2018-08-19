package application.com.DTOS;

import java.util.Date;


import javafx.beans.property.StringProperty;



public class productoDTO {

	/*** PARAMETROS ***/
	private int IdProducto;
	private int IdProductoDescri;
	/* Agregando id_proveedor*/
	private int idProveedor;
	/*Agregando id_proveedor*/
	private String NombreProducto;
	private String descripcion;
	private float valorCompra;
	private int Stock;
	private float valorVenta;
	private Date FechaIngreso;
	private Date FechaActualiza;
	private String Estado;
	private String UsuarioIngreso;
	private String categoria;
	private String subcategoria;
	private String nombreDescripcion;

	/*** METODOS ***/
	public int getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(int idProducto) {
		IdProducto = idProducto;
	}

	public int getIdProductoDescri() {
		return IdProductoDescri;
	}

	public void setIdProductoDescri(int idProductoDescri) {
		IdProductoDescri = idProductoDescri;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(float valorCompra) {
		this.valorCompra = valorCompra;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public float getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(float valorVenta) {
		this.valorVenta = valorVenta;
	}

	public Date getFechaIngreso() {
		return FechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		FechaIngreso = fechaIngreso;
	}

	public Date getFechaActualiza() {
		return FechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		FechaActualiza = fechaActualiza;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public String getUsuarioIngreso() {
		return UsuarioIngreso;
	}

	public void setUsuarioIngreso(String usuarioIngreso) {
		UsuarioIngreso = usuarioIngreso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getNombreDescripcion() {
		return nombreDescripcion;
	}

	public void setNombreDescripcion(String nombreDescripcion) {
		this.nombreDescripcion = nombreDescripcion;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombreProducto() {
		return NombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		NombreProducto = nombreProducto;
	}


}

package application.com.DTOS;

import java.util.Date;

public class ProductosDTO {

	/*** CONSTRUCTOR NO APLICA ***/
	// public ProductosDTO(int idProducto, String nombre, String descripcion,
	// float
	// valorUnitario, int stock,
	// Date fechaIngreso, String estado, String usuarioIngreso) {
	// super();
	// IdProducto = idProducto;
	// Descripcion = descripcion;
	// Nombre = nombre;
	// Stock = stock;
	// ValorUnitario = valorUnitario;
	// FechaIngreso = fechaIngreso;
	// Estado = estado;
	// UsuarioIngreso = usuarioIngreso;
	// }

	/*** PARAMETROS NO APLICA ***/
	// public final int IdProducto;
	// public final String Descripcion;
	// public final int Stock;
	// public final String Nombre;
	// public final float ValorUnitario;
	// public final Date FechaIngreso;
	// public final String Estado;
	// public final String UsuarioIngreso;

	/*** PARAMETROS ***/
	private int IdProducto;
	private String Descripcion;
	private int Stock;
	private int Cantidad;
	private String Nombre;
	private float ValorUnitario;
	public float Precio;
	private Date FechaIngreso;
	private String Estado;
	private String UsuarioIngreso;

	/*** NUEVOS PARAMETROS ***/
	private int IdTipoProducto;
	private int IdCapacidad;
	private int IdUnidad;
	private float valorCompra;
	private Date FechaActualizada;

	/*** METODOS ***/
	public int getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(int idProducto) {
		IdProducto = idProducto;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public float getValorUnitario() {
		return ValorUnitario;
	}

	public void setValorUnitario(float valorUnitario) {
		ValorUnitario = valorUnitario;
	}

	public Date getFechaIngreso() {
		return FechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		FechaIngreso = fechaIngreso;
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

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	public float getPrecio() {
		return Precio;
	}

	public void setPrecio(float precio) {
		Precio = precio;
	}

	public int getIdTipoProducto() {
		return IdTipoProducto;
	}

	public void setIdTipoProducto(int idTipoProducto) {
		IdTipoProducto = idTipoProducto;
	}

	public int getIdCapacidad() {
		return IdCapacidad;
	}

	public void setIdCapacidad(int idCapacidad) {
		IdCapacidad = idCapacidad;
	}

	public int getIdUnidad() {
		return IdUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		IdUnidad = idUnidad;
	}

	public float getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(float valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Date getFechaActualizada() {
		return FechaActualizada;
	}

	public void setFechaActualizada(Date fechaActualizada) {
		FechaActualizada = fechaActualizada;
	}

}

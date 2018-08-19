package application.com.DTOS;

import java.util.Date;

public class ProveedorDTO 
{
	public int idProveedor;
	public String ruc;
	public String Nombre;
	public Date FechaIngreso;
	public String Estado;
	public String UsuarioIngreso;
	public String telefono;
	public String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
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
}

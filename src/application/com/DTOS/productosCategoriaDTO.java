package application.com.DTOS;

public class productosCategoriaDTO 
{
	public int id_producto_cat;
	public String descripcion;
	public String fecha_ingreso;
	public String estado;
	public String usuarioIngreso;
	public int getId_producto_cat() {
		return id_producto_cat;
	}
	public void setId_producto_cat(int id_producto_cat) {
		this.id_producto_cat = id_producto_cat;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFecha_ingreso() {
		return fecha_ingreso;
	}
	public void setFecha_ingreso(String fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getUsuarioIngreso() {
		return usuarioIngreso;
	}
	public void setUsuarioIngreso(String usuarioIngreso) {
		this.usuarioIngreso = usuarioIngreso;
	}

}

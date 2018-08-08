package application.com.DTOS;

public class productosDescripcionDTO {
	
	/*** PARAMETROS ***/
	public int id_des_producto;
	public int id_producto_categoria;
	public String descripcion;
	public String fecha_ingreso;
	public String estado;
	public String usuarioIngreso;

	/*** METODOS ***/
	public int getId_des_producto() {
		return id_des_producto;
	}

	public void setId_des_producto(int id_des_producto) {
		this.id_des_producto = id_des_producto;
	}

	public int getId_producto_categoria() {
		return id_producto_categoria;
	}

	public void setId_producto_categoria(int id_producto_categoria) {
		this.id_producto_categoria = id_producto_categoria;
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

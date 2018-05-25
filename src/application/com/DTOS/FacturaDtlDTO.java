package application.com.DTOS;
import java.util.Date;

public class FacturaDtlDTO {

	/*** PARAMETROS  ***/
	public int IdFacturaDtl;
	public int IdFactura;
	public int IdProducto;
	public int Cantidad;
	public float Valor;
	public String Descripcion;
	public float total;
	public void setIdFacturaDtl(int idFacturaDtl) {
		IdFacturaDtl = idFacturaDtl;
	}
	public void setIdFactura(int idFactura) {
		IdFactura = idFactura;
	}
	public void setIdProducto(int idProducto) {
		IdProducto = idProducto;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	public void setValor(float valor) {
		Valor = valor;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	public void setUsuarioIngreso(String usuarioIngreso) {
		UsuarioIngreso = usuarioIngreso;
	}
	public Date FechaCreacion;
	public String Estado;
	public String UsuarioIngreso;
	
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public int getIdFacturaDtl() {
		return IdFacturaDtl;
	}
	public int getIdFactura() {
		return IdFactura;
	}
	public int getIdProducto() {
		return IdProducto;
	}
	public int getCantidad() {
		return Cantidad;
	}
	public float getValor() {
		return Valor;
	}
	public Date getFechaCreacion() {
		return FechaCreacion;
	}
	public String getEstado() {
		return Estado;
	}
	public String getUsuarioIngreso() {
		return UsuarioIngreso;
	}

	/*** METODOS  ***/ 
	
}

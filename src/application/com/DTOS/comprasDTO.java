package application.com.DTOS;
import java.util.Date;
public class comprasDTO 
{
	public int idCompra;
	public Date fechaCompra;
	public int idProveedor;
	public String factura;
	public float base_0;
	public float base_12;
	public float iva;
	public float total;
	public String estado;
	public String usuarioIngreso;
	// Campos necesario para reportes
	public String mesCompra;
	public String rucProveedor;
	public String nombreProveedor;
	public String fechaComprasTexto;
	public int getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	public float getBase_0() {
		return base_0;
	}
	public void setBase_0(float base_0) {
		this.base_0 = base_0;
	}
	public float getBase_12() {
		return base_12;
	}
	public void setBase_12(float base_12) {
		this.base_12 = base_12;
	}
	public float getIva() {
		return iva;
	}
	public void setIva(float iva) {
		this.iva = iva;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
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

	public String getMesCompra() {
		return mesCompra;
	}

	public void setMesCompra(String mesCompra) {
		this.mesCompra = mesCompra;
	}

	public String getRucProveedor() {
		return rucProveedor;
	}

	public void setRucProveedor(String rucProveedor) {
		this.rucProveedor = rucProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getFechaComprasTexto() {
		return fechaComprasTexto;
	}

	public void setFechaComprasTexto(String fechaComprasTexto) {
		this.fechaComprasTexto = fechaComprasTexto;
	}
}

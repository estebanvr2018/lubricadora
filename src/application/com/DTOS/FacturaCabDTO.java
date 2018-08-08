package application.com.DTOS;

import java.util.Date;

public class FacturaCabDTO {

	/*** CONSTRUCTOR ***/
	public FacturaCabDTO(int idFactura, String idIdentificacion, Date fechaFactura, float subTotal1, float subTotal2,
			float ivaCero, float ivaDoce, float valorTotal, String ValorTotalLetras, String estado,
			String usuarioIngreso) {
		super();
		IdFactura = idFactura;
		IdIdentificacion = idIdentificacion;
		FechaFactura = fechaFactura;
		SubTotal1 = subTotal1;
		SubTotal2 = subTotal2;
		IvaCero = ivaCero;
		IvaDoce = ivaDoce;
		ValorTotal = valorTotal;
		valorTotalLetras = ValorTotalLetras;
		Estado = estado;
		UsuarioIngreso = usuarioIngreso;
	}

	public FacturaCabDTO() {
		super();
	}

	/*** PARAMETROS ***/
	public int IdFactura;
	public String IdIdentificacion;
	public String NombreCliente;
	public String tipoDoc;
	public Date FechaFactura;
	public float SubTotal1;
	public float SubTotal2;
	public float IvaCero;
	public float IvaDoce;
	public float ValorTotal;
	public String valorTotalLetras;
	public Date FechaIngreso;
	public String Estado;
	public String UsuarioIngreso;
	public String NombreCompleto;

	/*** METODOS ***/
	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public int getIdFactura() {
		return IdFactura;
	}

	public void setIdFactura(int idFactura) {
		IdFactura = idFactura;
	}

	public String getIdIdentificacion() {
		return IdIdentificacion;
	}

	public void setIdIdentificacion(String idIdentificacion) {
		IdIdentificacion = idIdentificacion;
	}

	public String getNombreCliente() {
		return NombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		NombreCliente = nombreCliente;
	}

	public Date getFechaFactura() {
		return FechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		FechaFactura = fechaFactura;
	}

	public float getSubTotal1() {
		return SubTotal1;
	}

	public void setSubTotal1(float subTotal1) {
		SubTotal1 = subTotal1;
	}

	public float getSubTotal2() {
		return SubTotal2;
	}

	public void setSubTotal2(float subTotal2) {
		SubTotal2 = subTotal2;
	}

	public float getIvaCero() {
		return IvaCero;
	}

	public void setIvaCero(float ivaCero) {
		IvaCero = ivaCero;
	}

	public float getIvaDoce() {
		return IvaDoce;
	}

	public void setIvaDoce(float ivaDoce) {
		IvaDoce = ivaDoce;
	}

	public float getValorTotal() {
		return ValorTotal;
	}

	public void setValorTotal(float valorTotal) {
		ValorTotal = valorTotal;
	}

	public String getValorTotalLetras() {
		return valorTotalLetras;
	}

	public void setValorTotalLetras(String valorTotalLetras) {
		this.valorTotalLetras = valorTotalLetras;
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

	public String getNombreCompleto() {
		return NombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}

}

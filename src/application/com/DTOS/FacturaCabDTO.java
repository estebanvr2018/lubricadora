package application.com.DTOS;



import java.util.Date;

public class FacturaCabDTO {

	/*** CONSTRUCTOR ***/
	public FacturaCabDTO(int idFactura, String idIdentificacion, Date fechaFactura, float subTotal1, float subTotal2,
			float ivaCero, float ivaDoce, float valorTotal, Date fechaIngreso, String estado, String usuarioIngreso) {
		super();
		IdFactura = idFactura;
		IdIdentificacion = idIdentificacion;
		FechaFactura = fechaFactura;
		SubTotal1 = subTotal1;
		SubTotal2 = subTotal2;
		IvaCero = ivaCero;
		IvaDoce = ivaDoce;
		ValorTotal = valorTotal;
		FechaIngreso = fechaIngreso;
		Estado = estado;
		UsuarioIngreso = usuarioIngreso;
	}

	/*** PARAMETROS  ***/
	public final int IdFactura;
	public final String IdIdentificacion;
	public final Date FechaFactura;
	public final float SubTotal1;
	public final float SubTotal2;
	public final float IvaCero;
	public final float IvaDoce;
	public final float ValorTotal;
	public final Date FechaIngreso;
	public final String Estado;
	public final String UsuarioIngreso;

	/*** METODOS  ***/ 
	public int getIdFactura() {
		return IdFactura;
	}

	public String getIdIdentificacion() {
		return IdIdentificacion;
	}

	public Date getFechaFactura() {
		return FechaFactura;
	}

	public float getSubTotal1() {
		return SubTotal1;
	}

	public float getSubTotal2() {
		return SubTotal2;
	}

	public float getIvaCero() {
		return IvaCero;
	}

	public float getIvaDoce() {
		return IvaDoce;
	}

	public float getValorTotal() {
		return ValorTotal;
	}

	public Date getFechaIngreso() {
		return FechaIngreso;
	}

	public String getEstado() {
		return Estado;
	}

	public String getUsuarioIngreso() {
		return UsuarioIngreso;
	}

}

package application.com.DTOS;



import java.util.Date;

public class FacturaCabDTO 
{

	/*** CONSTRUCTOR ***/
	public FacturaCabDTO(int idFactura, String idIdentificacion, Date fechaFactura, float subTotal1, float subTotal2,
			float ivaCero, float ivaDoce, float valorTotal, String ValorTotalLetras, String estado, String usuarioIngreso) 
	{
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

	/*** PARAMETROS  ***/
	public int IdFactura;
	public String IdIdentificacion;
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

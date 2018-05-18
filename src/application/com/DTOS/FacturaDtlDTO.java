package application.com.DTOS;
import java.util.Date;

public class FacturaDtlDTO {

	/*** CONSTRUCTOR ***/
	public FacturaDtlDTO(int idFacturaDtl, int idFactura, int idProducto, int cantidad, String valor,
			Date fechaCreacion, String estado, String usuarioIngreso) {
		super();
		IdFacturaDtl = idFacturaDtl;
		IdFactura = idFactura;
		IdProducto = idProducto;
		Cantidad = cantidad;
		Valor = valor;
		FechaCreacion = fechaCreacion;
		Estado = estado;
		UsuarioIngreso = usuarioIngreso;
	}

	/*** PARAMETROS  ***/
	public final int IdFacturaDtl;
	public final int IdFactura;
	public final int IdProducto;
	public final int Cantidad;
	public final String Valor;
	public final Date FechaCreacion;
	public final String Estado;
	public final String UsuarioIngreso;

	/*** METODOS  ***/ 
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

	public String getValor() {
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

}

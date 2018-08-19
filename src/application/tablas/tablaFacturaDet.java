package application.tablas;

public class tablaFacturaDet {
	
	/*** PARAMETROS ***/
	public int IdFacturaDtl;
	public int IdFactura;
	public int IdProducto;

	/*** METODOS ***/	
	public int getIdFacturaDtl() {
		return IdFacturaDtl;
	}

	public void setIdFacturaDtl(int idFacturaDtl) {
		IdFacturaDtl = idFacturaDtl;
	}

	public int getIdFactura() {
		return IdFactura;
	}

	public void setIdFactura(int idFactura) {
		IdFactura = idFactura;
	}

	public int getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(int idProducto) {
		IdProducto = idProducto;
	}

	private int Cantidad;
	private String Descripcion;
	private float ValorUnitario;
	private float Precio;
	private int Stock;

	public float getPrecio() {
		return Precio;
	}

	public void setPrecio(float precio) {
		Precio = precio;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public float getValorUnitario() {
		return ValorUnitario;
	}

	public void setValorUnitario(float valorUnitario) {
		ValorUnitario = valorUnitario;
	}

	public void calculaDetalle() {

	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}
}

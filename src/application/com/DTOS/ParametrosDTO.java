package application.com.DTOS;

public class ParametrosDTO {

	/*** PARAMETROS ***/
	public String IdParametro;
	public String Valor;
	public String Descripcion;

	/*** METODOS ***/
	public String getIdParametro() {
		return IdParametro;
	}

	public void setIdParametro(String idParametro) {
		IdParametro = idParametro;
	}

	public String getValor() {
		return Valor;
	}

	public void setValor(String valor) {
		Valor = valor;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

}

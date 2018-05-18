package application.com.DTOS;

import java.util.Date;

public class ParametrosDTO {

	/*** CONSTRUCTOR  ***/ 
	public ParametrosDTO(String idParametro, String valor, String descripcion, Date fechaIngreso) {
		super();
		IdParametro = idParametro;
		Valor = valor;
		Descripcion = descripcion;
		FechaIngreso = fechaIngreso;
	}

	/*** PARAMETROS  ***/ 
	public final String IdParametro;
	public final String Valor;
	public final String Descripcion;
	public final Date FechaIngreso;

	/*** METODOS  ***/ 
	public String getIdParametro() {
		return IdParametro;
	}

	public String getValor() {
		return Valor;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public Date getFechaIngreso() {
		return FechaIngreso;
	}

}

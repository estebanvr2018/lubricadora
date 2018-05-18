package application.com.DTOS;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

public class UsuariosDTO 
{
	

	/*** CONSTRUCTOR  ***/ 
	/*
	public UsuariosDTO(int idUsuario, String nombres, String usuario, String paasword, Date fechaIngreso,
			String estado) {
		super();
		IdUsuario = idUsuario;
		Nombres = nombres;
		Usuario = usuario;
		Paasword = paasword;
		FechaIngreso = fechaIngreso;
		Estado = estado;
	}
*/
	/*** PARAMETROS  ***/ 
	public int IdUsuario;
	public String Nombres;
	public String Usuario;
	public String Paasword;
	public Date FechaIngreso;
	public String Estado;

	/*** METODOS  ***/ 
	public int getIdUsuario() {
		return IdUsuario;
	}

	public String getNombres() {
		return Nombres;
	}

	public String getUsuario() {
		return Usuario;
	}

	public String getPaasword() {
		return Paasword;
	}

	public Date getFechaIngreso() {
		return FechaIngreso;
	}

	public String getEstado() {
		return Estado;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public void setNombres(String nombres) {
		Nombres = nombres;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public void setPaasword(String paasword) {
		Paasword = paasword;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		FechaIngreso = fechaIngreso;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

}

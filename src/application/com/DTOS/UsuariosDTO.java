package application.com.DTOS;

import java.util.Date;


public class UsuariosDTO {

	/*** CONSTRUCTOR ***/
	/*
	 * public UsuariosDTO(int idUsuario, String nombres, String usuario, String
	 * paasword, Date fechaIngreso, String estado) { super(); IdUsuario =
	 * idUsuario; Nombres = nombres; Usuario = usuario; Paasword = paasword;
	 * FechaIngreso = fechaIngreso; Estado = estado; }
	 */
	/*** PARAMETROS ***/
	public Integer IdUsuario;
	public String Nombres;public String usuario;
	public String Paasword;
	public Date FechaIngreso;
	public String Estado;
	public Integer getIdUsuario() {
		return IdUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		IdUsuario = idUsuario;
	}
	public String getNombres() {
		return Nombres;
	}
	public void setNombres(String nombres) {
		Nombres = nombres;
	}
	public String getPaasword() {
		return Paasword;
	}
	public void setPaasword(String paasword) {
		Paasword = paasword;
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}

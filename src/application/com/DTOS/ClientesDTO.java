package application.com.DTOS;

import java.util.Date;

public class ClientesDTO {

	/*** PARAMETROS ***/
	public String IdIdentificacion;
	public String TipoIdentificacion;
	public String Nombres;
	public String PrimerApellido;
	public String SegundoApellido;
	public String Direccion;
	public String Telefono;
	public Date FechaIngreso;

	public String Estado;
	public String UsuarioIngreso;

	/*** NUEVO PARAMETRO ***/
	public String correo;
	public Date FechaActualizada;

	/*** METODOS ***/

	public Date getFechaActualizada() {
		return FechaActualizada;
	}

	public void setFechaActualizada(Date fechaActualizada) {
		FechaActualizada = fechaActualizada;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getIdIdentificacion() {
		return IdIdentificacion;
	}

	public String getTipoIdentificacion() {
		return TipoIdentificacion;
	}

	public String getNombres() {
		return Nombres;
	}

	public String getPrimerApellido() {
		return PrimerApellido;
	}

	public String getSegundoApellido() {
		return SegundoApellido;
	}

	public String getDireccion() {
		return Direccion;
	}

	public String getTelefono() {
		return Telefono;
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

	public void setIdIdentificacion(String idIdentificacion) {
		IdIdentificacion = idIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		TipoIdentificacion = tipoIdentificacion;
	}

	public void setNombres(String nombres) {
		Nombres = nombres;
	}

	public void setPrimerApellido(String primerApellido) {
		PrimerApellido = primerApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		SegundoApellido = segundoApellido;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		FechaIngreso = fechaIngreso;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public void setUsuarioIngreso(String usuarioIngreso) {
		UsuarioIngreso = usuarioIngreso;
	}

}

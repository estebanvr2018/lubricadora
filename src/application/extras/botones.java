package application.extras;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class botones 
{
	public ImageView botonBuscar()
	{
		Image image = new Image("application/images/buscar.png",15,15,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonAgregarLista()
	{
		Image image = new Image("application/images/guardarGeneral.png",15,15,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonAgregar()
	{
		Image image = new Image("application/images/agregar.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonActualizar()
	{
		Image image = new Image("application/images/editar.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonEliminar()
	{
		Image image = new Image("application/images/eliminar.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonError()
	{
		Image image = new Image("application/images/error.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView inicioSesion()
	{
		Image image = new Image("application/images/iniciosesion.jpg",15,15,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView logueoSesion()
	{
		Image image = new Image("application/images/loginOk.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonSuccess()
	{
		Image image = new Image("application/images/okai.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonRegresar()
	{
		Image image = new Image("application/images/regresar.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonGuardar()
	{
		Image image = new Image("application/images/guardar.jpg",25,25,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}

	public ImageView botonCalcular()
	{
		Image image = new Image("application/images/calcula.jpg",15,15,true,true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
}

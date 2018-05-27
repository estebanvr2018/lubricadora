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
	
	public ImageView botonBuscarMax()
	{
		Image image = new Image("application/images/buscar.png",30,30,true,true);
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
	
	public ImageView fondoPantalla()
	{
	Image imgCarga = new Image("application/images/fondo.jpg"); 
	ImageView imgView = new ImageView(imgCarga);
	return imgView;
	}
	
	public ImageView ClientesP()
	{
	Image imgCarga = new Image("application/images/clientes.png",30,30,true,true); 
	ImageView imgView = new ImageView(imgCarga);
	return imgView;
	}
	
	public ImageView UsuariosP()
	{
	Image imgCarga = new Image("application/images/usuario.jpg",30,30,true,true); 
	ImageView imgView = new ImageView(imgCarga);
	return imgView;
	}
	
	public ImageView productosP()
	{
	Image imgCarga = new Image("application/images/producto.png",30,30,true,true); 
	ImageView imgView = new ImageView(imgCarga);
	return imgView;
	}
	public ImageView facturaP()
	{
	Image imgCarga = new Image("application/images/facturacion.png",30,40,true,true); 
	ImageView imgView = new ImageView(imgCarga);
	return imgView;
	}
	public ImageView botonFacturaGrande()
	{
		Image image = new Image("application/images/facturaGrande.png",30,30,true, true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	public ImageView botonLimpiar()
	{
		Image image = new Image("application/images/limpiar.png",30,30,true, true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonLaren()
	{
		Image image = new Image("application/images/lmlaren.jpg",30,30,true, true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public ImageView botonImprimir()
	{
		Image image = new Image("application/images/imprimir.png",30,30,true, true);
		ImageView imgVie = new ImageView(image);
		return imgVie;
	}
	
	public Image iconoLaren()
	{
		Image image = new Image("application/images/lmlaren.jpg",30,30,true, true);
		ImageView imgVie = new ImageView(image);
		return image;
	}
}

package application.Dialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

public class alertasMensajes 
{
	public void alertaGeneral(String strMensaje)
	{
		Alert diAlerta = new Alert(AlertType.WARNING);
		diAlerta.setTitle("Alerta");
		diAlerta.setHeaderText(null);
		diAlerta.setContentText(strMensaje);
		diAlerta.initStyle(StageStyle.UTILITY);
		diAlerta.showAndWait();
	}
	
	public Optional<ButtonType> opcionConfirmacion(String titleAlert,String contentAlert) {
		 
	      Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle(titleAlert);
	      alert.setHeaderText(contentAlert);
	     
	      // option != null.
	      Optional<ButtonType> option = alert.showAndWait();
	 
	      /*if (option.get() == null) {
	         this.label.setText("No selection!");
	      } else if (option.get() == ButtonType.OK) {
	         this.label.setText("File deleted!");
	      } else if (option.get() == ButtonType.CANCEL) {
	         this.label.setText("Cancelled!");
	      } else {
	         this.label.setText("-");
	      }*/
	      return option;
	   }
	public void alertaOK(String strMensaje)
	{
		Alert diAlerta = new Alert(AlertType.INFORMATION);
		diAlerta.setTitle("Alerta");
		diAlerta.setHeaderText(null);
		diAlerta.setContentText(strMensaje);
		diAlerta.initStyle(StageStyle.UTILITY);
		diAlerta.showAndWait();
	}

}

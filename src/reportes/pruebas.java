package reportes;

import java.sql.SQLException;
import java.util.List;

import application.DAO.ClientesDAO;
import application.com.DTOS.ClientesDTO;
import application.com.conxionMySql.*;

public class pruebas {

	public static void main(String[] args) {
		ClientesDAO a = new ClientesDAO();
		ConexionMySQL s = new ConexionMySQL();
		String ced = "0950664714";
		try {
			List<ClientesDTO> c = a.consultaClienteX(s.getConect(), ced);
			System.out.println(c.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

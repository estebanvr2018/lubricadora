package application.com.conxionMySql;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionMySQL {
    
    private Connection conect = null;
    
    private String cadenaConexion = "jdbc:mysql";
    private String cadenaUrlConex = "localhost:3306";//"localhost";
    private String baseDatos      = "db_facturacion";
    private String usuario        = "root";
    private String clave          = "1234";
    
    public Connection conexion()
    {
    	
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //--> jdbc:mysql://localhost/fxLubricadora
            conect = DriverManager.getConnection(cadenaConexion+"://"+cadenaUrlConex+"/"+baseDatos, usuario, clave);
        }catch(ClassNotFoundException | SQLException e){
        	System.out.println(e.toString());
            System.exit(0);
        }
        return conect;
    }
    
    public void desconectar() 
    throws SQLException {
        conect.close();
    }

    public Connection getConect() {
        return conect;
    }

    public void setConect(Connection conect) {
        this.conect = conect;
    }

    public String getCadenaConexion() {
        return cadenaConexion;
    }

    public void setCadenaConexion(String cadenaConexion) {
        this.cadenaConexion = cadenaConexion;
    }

    public String getCadenaUrlConex() {
        return cadenaUrlConex;
    }

    public void setCadenaUrlConex(String cadenaUrlConex) {
        this.cadenaUrlConex = cadenaUrlConex;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}


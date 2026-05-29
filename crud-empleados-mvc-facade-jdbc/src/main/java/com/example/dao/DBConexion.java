package com.example.dao;

import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConexion implements AutoCloseable {

	private static final Logger LOG = Logger.getLogger("DBConexion");

	private String user;
	private String password;
	private Connection connection;

	public DBConexion(String user, String password) {
		super();
		this.user = user;
		this.password = password;

	}

	// Metodo que establece la conexion a la base de datos
	public Connection getConexion() throws ClassNotFoundException {
		String urlConnection = "jdbc:mysql://localhost:3306/empresa-crud-empleados";
		
		Properties info = new Properties();
		
		info.put("user", this.user);
		info.put("password", this.password);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(urlConnection, info);
			LOG.info("Conexion establecida con exito a la base de datos");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	

		return this.connection;
	}

	@Override
	public void close() throws Exception {
		
		this.connection.close();
	}
	
	// Método que recupera todos los registros de la tabla empleados
	public ResultSet getEmpleados(Connection connection) {
		
		ResultSet rs= null;
		String query = "SELECT * FROM `empresa-crud-empleados`.empleados";
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	//Metodo de recuperacion de departamentos
	public ResultSet getDptos(Connection connection) {
		
		ResultSet rs = null;
		String query = "SELECT * FROM `empresa-crud-empleados`.departamentos";
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			LOG.severe("Error al crear el statement para recuperar los departamentos" + e.getMessage());
			e.printStackTrace();
		}
		
		
		return rs;
	    	
	}

}












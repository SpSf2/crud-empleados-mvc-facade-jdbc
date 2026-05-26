package com.example.services;

import java.sql.Connection;
import java.sql.SQLException;
import com.example.dao.DBConexion;

public class EmpleadoServiceImpl implements EmpleadoService {

	@Override
	public boolean isConnectionOK() throws SQLException {

		DBConexion dbConexion = new DBConexion("root", "Temp2026");
		
		boolean connectionOK = false;
		Connection connection = null;
		
		try {
			connection = dbConexion.getConexion();
			if (connection != null) {
				connectionOK = true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.close();
		}
		return connectionOK;
	}

}

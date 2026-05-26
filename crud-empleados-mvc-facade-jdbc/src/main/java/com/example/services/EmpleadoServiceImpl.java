package com.example.services;

import java.sql.Connection;

import com.example.dao.DBConexion;

public class EmpleadoServiceImpl implements EmpleadoService {

	@Override
	public boolean isConnectionOK() throws Exception {

		
		
		boolean connectionOK = false;
		
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");) {
			Connection connection = dbConexion.getConexion();
			if (connection != null) {
				connectionOK = true;
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
		return connectionOK;
	}

}

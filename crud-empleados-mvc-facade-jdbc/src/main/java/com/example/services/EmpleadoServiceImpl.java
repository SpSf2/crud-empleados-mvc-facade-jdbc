package com.example.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.DBConexion;
import com.example.models.Empleado;
import com.example.models.Genero;

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

	@Override
	public List<Empleado> getEmpleado() {

		List<Empleado> empleados = new ArrayList<Empleado>();
		
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026"); 
				Connection connection = dbConexion.getConexion()) {
			 
			ResultSet rs = dbConexion.getEmpleados(connection);	
			
			while (rs.next()) {
				empleados.add(Empleado.builder()
						.id(rs.getInt("id"))
						.nombre(rs.getString("nombre"))
						.primerApellido(rs.getString("primerApellido"))
						.segundoApellido(rs.getString("segundoApellido"))
						.fechaAlta(rs.getDate("fechaAlta").toLocalDate())
						.genero(Genero.valueOf(rs.getString("genero")))							
						.salario(new BigDecimal(rs.getDouble("salario")))
						.departamentos_id(rs.getInt("departamentos_id"))
						.build());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return empleados;
	}

	@Override
	public void altaEmpleado(Empleado empleado, 
			List<String> emails, List<String> nTelefonos) throws SQLException {
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");) {
			Connection connection = dbConexion.getConexion();
			dbConexion.altaEmpleado(
					empleado, 
					emails, 
					nTelefonos, 
					connection);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}

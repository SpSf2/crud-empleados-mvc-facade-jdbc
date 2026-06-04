package com.example.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Detalle;
import com.example.models.Empleado;
import com.example.models.Genero;

public class EmpleadoServiceImpl implements EmpleadoService {

	private static final Logger LOG = Logger.getLogger("EmpleadoServiceImpl");
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

	@Override
	public Detalle detalles(int idEmpleado) {

		Detalle detalles = null;
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");) {
			Connection connection = dbConexion.getConexion();
			
			ResultSet rs = dbConexion.detallesEmpleado(idEmpleado, connection);
			
			/*Para recuperar los datos del record Detalle hay que recorrerlo*/
			// Declaramos las variables
			String nombreDpto = null;
			
			if (rs.next()) {
				nombreDpto = rs.getString("nombreDpto");
			}
			
			Set<String> numerosTelefonos = new HashSet<String>();
			
			rs.beforeFirst(); // Volvemos al inicio del ResultSet para recorrerlo de nuevo y recuperar los números de teléfono
			while (rs.next()) {
				numerosTelefonos.add(rs.getString("numeroTelefono"));
			}
			rs.beforeFirst();
			Set<String> emails = new HashSet<String>();
			while (rs.next()) {
				emails.add(rs.getString("email"));
			}
			
			detalles = new Detalle(nombreDpto, emails, numerosTelefonos);
			
			// Mostrar el record detalles en la consola
			LOG.info("Detalle recuperado: " + detalles);
			
		} catch (Exception e) {
			
			LOG.severe("Error al recuperar los detalles del empleado en la capa de servicios " + idEmpleado + " " + e.getMessage());
			e.printStackTrace();
		}
		
		return detalles;
	}

}

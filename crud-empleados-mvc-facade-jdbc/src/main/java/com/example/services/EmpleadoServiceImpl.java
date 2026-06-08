package com.example.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Detalle;
import com.example.models.Empleado;
import com.example.models.EmpleadoUpdate;
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

	@Override
	public EmpleadoUpdate getEmpleadoById(int idEmpleado) {
		
		EmpleadoUpdate empleadoUpdate = null;
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion()) {
			
			ResultSet rs = dbConexion.getEmpleadoById(idEmpleado, connection);
			
						int idEmp = 0;
						String nombre = null;
						String primerApellido = null;
						String segundoApellido = null;
						LocalDate fechaAlta = null;
						Genero genero = null;
						BigDecimal salario = null;
						int idDpto = 0;
						String nombreDpto = null;
						Set<String> telefonos = new HashSet<String>();
						Set<String> emails = new HashSet<String>();					
						
						if (rs.next()) {
							idEmp = rs.getInt("idEmpleado");
							nombre = rs.getString("nombreEmpleado");
							primerApellido = rs.getString("primerApellido");
							segundoApellido = rs.getString("segundoApellido");
							fechaAlta = rs.getDate("fechaAlta").toLocalDate();
							genero = Genero.valueOf(rs.getString("genero"));
							salario = rs.getBigDecimal("salario");
							idDpto = rs.getInt("idDpto");
							nombreDpto = rs.getString("nombreDpto");
						}
						
						
			
						rs.beforeFirst();

				        while (rs.next()) {
				            String telefono = rs.getString("numero");
				            if (telefono != null && !telefono.trim().isEmpty()) {
				                telefonos.add(telefono.trim());
				            }

				            String email = rs.getString("email");
				            if (email != null && !email.trim().isEmpty()) {
				                emails.add(email.trim());
				            }
				        }
						
						empleadoUpdate = new EmpleadoUpdate(
								idEmp, 
								nombre, 
								primerApellido, 
								segundoApellido, 
								fechaAlta, 
								genero, 
								salario, 
								idDpto, 
								nombreDpto, 
								telefonos, 
								emails);
			
			// Mostrar el record empleadoUpdate en la consola
			LOG.info("Empleado recuperado para modificar: " + empleadoUpdate);
			
		} catch (Exception e) {
			
			LOG.severe("Error al recuperar el empleado con id " + idEmpleado + " en la capa de servicios"
					+ " y la causa mas probable es: " + e.getMessage());
			e.printStackTrace();
		}
 		
		return empleadoUpdate;
	}

	@Override
	public void updateEmpleado(Empleado empleado, List<String> emails, List<String> telefono) {
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion()) {
			
			dbConexion.updateEmpleado(empleado, emails, telefono, connection);
		} catch  (Exception e) {
		
			LOG.severe("Error actualizando empleado: " + e.getMessage());
		    e.printStackTrace();
		}	
	}

}

package com.example.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.example.models.Empleado;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	/* metodo que inserta empleado y sus correos y telefonos en la DB en el marco de una transacción*/
	
	public void altaEmpleado(Empleado empleado, List<String> dirCorreos, List<String> numerosTelefono,
			Connection connection)  throws SQLException {
		
		//Inserta empleados y devuelve el last insert id en la tabla empleados
		String query1 = "INSERT INTO `empleados` (`nombre`, `primerApellido`, `segundoApellido`, `fechaAlta`,"
				+ " `genero`, `salario`, `departamentos_id`) VALUES (?, ?, ?, ?,"
				+ " ?, ?, ?)";
		
		/*  Que son las sentencias preparadas?  (prepared Statement)
		 *  Son la primera linea de defensa contra los ataques de inyección de SQL, separan la parte
		 *  fija de la consulta de los parámetros que recibe la misma.
		 *  El rendimiento es muy similar al de los procedimientos almacenado porque una vez que se 
		 *  ejecuta la consulta, el componente analiador de consulta u "optimizador de consulta" no tiene 
		 *  que analizar nuevamente el plan de ejecución de la consulta y la misma es compilada y guardada
		 *  en el servidor.  De forma tal, que la próxima solamente hay que pasarle los parámetros 
		 *  variables a la consulta para ejecutarla y la ejecución será lo más rápido, eficiente y
		 *  seguro posible. 
		 *  NOTA: Los párametros que se le pasan a la consulta preparada comienzan en 1 no en cero. !!! */
		
		//Con el id del empleado, tenemos que insertar sus correos y sus telefonos correspondientes
		//Inserta correos
		String query2 = "INSERT INTO `correos` (`email`, `empleados_id`) VALUES (?, ?)";
		
		//Inserta telefonos
		String query3 = "INSERT INTO `telefonos` (`numero`, `empleados_id`) VALUES (?, ?)";
		
		/* Tanto insertar el empleado como sus correos y telefonos tiene que hacerse en el marco de una
		 * Transacción  */
		
	 try {
		 //iniciamos la transacción
		 connection.setAutoCommit(false);
		 
		 PreparedStatement stmt1 = connection.prepareStatement(query1,
				 Statement.RETURN_GENERATED_KEYS);
		 
		 stmt1.setString(1, empleado.nombre());
		 stmt1.setString(2, empleado.primerApellido());
//Considerar que el el segundoApellido no es requerido; pero ya en Altacontroller lo pusimos null, en caso tal.
		 stmt1.setString(3, empleado.segundoApellido());
		 stmt1.setDate(4, Date.valueOf(empleado.fechaAlta()));
		 stmt1.setString(5, empleado.genero().name());
		 stmt1.setDouble(6, empleado.salario().doubleValue());
		 stmt1.setInt(7, empleado.departamentos_id());
		 
	//Lanzar la consulta preparada
		 int totalFilas = stmt1.executeUpdate();
		 
		 
		 if(totalFilas != 0) {
			 
			 //Recuperamos el id del empleado que se acaba de insertar, para ir a las tablas de correos y telf.
			 
			 long lastInsertedId = 0L;
			 
			 ResultSet rs = stmt1.getGeneratedKeys();
			 
			 if(rs.next()) {
				 lastInsertedId = rs.getLong(1);
							 
				//Insertamos los correos del empleado si los han proporcionado
				 if(dirCorreos != null && dirCorreos.size() > 0) {
					 PreparedStatement stmt2 = connection.prepareStatement(query2);
					 
					 stmt2.setInt(2, Math.toIntExact(lastInsertedId));
					 
		/* El código siguiente funciona pero no es eficiente porque por cada correo va a realizar una
		 * conexión a la DB lo cual consume recursos, por lo cual lo mejor es tener el lote completo de 
		 * correos y enviarlo todo de golpe  */
					 
					 /*for(String email: dirCorreos) {
						 stmt2.setString(1, email);
						 stmt2.executeUpdate();  */
					 
					 // el bueno:
					 for(String email: dirCorreos) {
						 stmt2.setString(1, email);
						 stmt2.addBatch();
					 }
					 stmt2.executeBatch();
				 		 
						 
			 }
				// Insertar telefonos si es que me los han proporcionado
				 if (numerosTelefono != null && numerosTelefono.size() > 0) {

				 PreparedStatement stmt3 = connection.prepareStatement(query3);

				 stmt3.setInt(2, Math.toIntExact(lastInsertedId));

				 /* El codigo siguiente funciona pero no es nada eficiente,
				 * porque por cada correo va a realizar una conexion a la base
				 * de datos, lo cual consume recursos, por lo cual lo mejor es
				 * tener el lote completo de los correos y enviarlo todo 
				 * de golpe */
				 // for (String numero : numerosTelefono) {
				 // stmt3.setString(1, numero);
				 // 
				 // stmt3.executeUpdate();
				 // }

				 for (String numero : numerosTelefono) {
				 stmt3.setString(1, numero);
				 stmt3.addBatch();
				 }

				 stmt3.executeBatch();
				 }
			 
		 }
	}	 
		 
		 connection.commit();
	 } catch(SQLException e) {
		 LOG.severe("Error insertando empleado y la causa mas probable es = + e.getMessage()");
		 e.printStackTrace();
		 
		 connection.rollback();
		 LOG.info("Transaccion revertida, no se ha insertado el nuevo empleado");
		 
	 }finally {
		 
			 connection.setAutoCommit(true);
		
	 }
	 
	 }
	
	/*Metodo que recupera los detalles (nombre de depto, telefonos,correos) de un empleado cuyo id se 
	 * recibe como parámetro**/
	
	public ResultSet detallesEmpleado(int idEmpleado, Connection connection) {
		
		ResultSet rs = null;
		String query = "select dep.nombre nombreDpto, tel.numero numeroTelefono, cor.email email\r\n"
				+ "	from empleados emp left join departamentos dep on\r\n"
				+ "		emp.departamentos_id = dep.id left join telefonos tel on\r\n"
				+ "			emp.id = tel.empleados_id left join correos cor on \r\n"
				+ "				emp.id = cor.empleados_id \r\n"
				+ "where emp.id = ?";
		
		PreparedStatement stmt1 = null;
		
		 try {
			stmt1 = connection.prepareStatement(query);
			stmt1.setInt(1, idEmpleado);
			
			rs = stmt1.executeQuery();
		} catch (SQLException e) {
			LOG.severe("Error al recuperar los detalles del empleado con id " + idEmpleado + " y la causa mas probable es: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		return rs;
		
		
	}
}	 
	 
	















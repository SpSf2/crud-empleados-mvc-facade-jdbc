package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.example.models.Detalle;
import com.example.models.Empleado;
import com.example.services.EmpleadoService;
import com.example.services.EmpleadoServiceImpl;

/**
 * Servlet implementation class DetallesController
 */
@WebServlet("/DetallesController")
public class DetallesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger("DetallesController");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetallesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*Recibir  el idEmpleado, que es el parametro que me envían con la petición (request)**/
		
		int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
		
		/* Podriamos comprobar sis se esta recibiendo correctamente el idEmpleado**/
		
		// System.out.println("idEmpleado Rescibido: " + idEmpleado);
		LOG.info("Id Empleado Recibido: " + idEmpleado);
		
		// Conectar con la capa de servicio para recuperar los detalles del empleado:
		
		EmpleadoService empleadoService = new EmpleadoServiceImpl();
		
		// Recuperar los detalles del empleado utilizando el idEmpleado
		
		List<Empleado> empleados = empleadoService.getEmpleado();
		
		Empleado empleado = empleados.stream()
				.filter(e -> e.id() == idEmpleado)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
		
		request.setAttribute("empleado", empleado);
		
		Detalle detalles = empleadoService.detalles(idEmpleado);
		
		// Enviar los detalles a la vista (detalles.jsp)
		
		request.setAttribute("detalles", detalles);
		request.getRequestDispatcher("views/detallesEmpleado.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}

package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.example.models.Empleado;
import com.example.services.EmpleadoService;
import com.example.services.EmpleadoServiceImpl;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger("MainController");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se eliminó el código para comprobar la conexión y a continuación se creará una conexión con la capa de servicios
		EmpleadoService empleadoService = new EmpleadoServiceImpl();
		
		List<Empleado> empleados = empleadoService.getEmpleado();
		
		// El listado de empleados hay que enviarlo como atributo a la vista para que pueda ser renderizado
		request.setAttribute("empleados", empleados);
		request.getRequestDispatcher("views/listadoEmpleados.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}

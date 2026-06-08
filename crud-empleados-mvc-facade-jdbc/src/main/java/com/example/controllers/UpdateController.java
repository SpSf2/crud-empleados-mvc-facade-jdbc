package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.example.models.Departamento;
import com.example.models.EmpleadoUpdate;
import com.example.services.DepartamentoService;
import com.example.services.DepartamentoServiceImpl;
import com.example.services.EmpleadoServiceImpl;

/**
 * Servlet implementation class UpdateController
 */
@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
		
		//con el id del empleado conectamos con la capa de servicios y recuperamos todo
		//lo necesario para mostrarlo en el formulario de Modificación con los datos del empleado a modificar
		
		EmpleadoServiceImpl empleadoService = new EmpleadoServiceImpl();
		
		//Recuperamos toda la info del empleado a modificar
		
		EmpleadoUpdate empleadoUpdate = empleadoService.getEmpleadoById(idEmpleado);
		
		//Establecemos el empleado a actualizar como atributo de la request para que el formulario de modificación
		//pueda mostrar los datos del empleado a modificar
		
		request.setAttribute("empleadoUpdate", empleadoUpdate);
		
		// Necesitamos conectarnos con el servicio de Dpto para recuperar el listado de departamentos y mostrarlo 
		//en el formulario de modificación para que el usuario pueda seleccionar el departamento al que asignar 
		// al empleado a modificar
		
		DepartamentoService departamentoService = new DepartamentoServiceImpl();
		
		try {
			List<Departamento> departamentos = departamentoService.getDepartamentos();
			request.setAttribute("departamentos", departamentos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Redirigimos al formulario de modificación
		
		request.getRequestDispatcher("views/formularioDeAltaModificacion.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}

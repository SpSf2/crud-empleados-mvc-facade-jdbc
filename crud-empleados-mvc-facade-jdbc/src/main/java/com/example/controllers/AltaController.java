package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.example.models.Departamento;
import com.example.models.Empleado;
import com.example.models.Genero;
import com.example.services.DepartamentoService;
import com.example.services.DepartamentoServiceImpl;

/**
 * Servlet implementation class AltaController
 */
@WebServlet("/AltaController")
public class AltaController extends HttpServlet {
	
	private static final Logger LOG = Logger.getLogger("AltaController");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DepartamentoService departamentoService = new DepartamentoServiceImpl();
		
		List<Departamento> departamentos = null;
		
		try {
			departamentos = departamentoService.getDepartamentos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("departamentos", departamentos);
		
		request.getRequestDispatcher("views/formularioDeAltaModificacion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//aqui se reciben los datos procedentes de los controles del formulario
		//Tener en cuenta que toda la información llega en formato String
		
		String nombre = request.getParameter("nombre");
		String primerApellido = request.getParameter("primerApellido");
		String segundoApellido = request.getParameter("segundoApellido") == null ? 
				"" : request.getParameter("segundoApellido");
		LocalDate fechaAlta = LocalDate.parse(request.getParameter("fechaAlta"));
		Genero genero = Genero.valueOf(request.getParameter("genero"));
		BigDecimal salario = BigDecimal.valueOf(Double.valueOf(request.getParameter("salario"))); //creo que se puede con new BigDecimal
		
		int departamentoId = Integer.parseInt(request.getParameter("departamento"));
		
		//Primero que nada, tener en cuenta que los correos y los telefonos no son requeridos
		List<String> direccionesCorreos = null;
		
		List<String> numerosDeTelefonos = null;
		
		if (request.getParameter("correos") != null) {
			String direccionesCorreosRecibidas = request.getParameter("correos");
			String[] arrayDirCorreosRecibidos = direccionesCorreosRecibidas.split(",");
			
			direccionesCorreos = Arrays.asList(arrayDirCorreosRecibidos);
			
			//Comprobando que el split de los correos funciona correctamente
			System.out.println("Direcciones de correo recibidas");
			direccionesCorreos.forEach(System.out::println);
		}
		
		if (request.getParameter("telefonos") != null) {
			String numerosTelefonicosRecibidos = request.getParameter("telefonos");
			String[] arrayNumerosTelefonicosRecibidos = numerosTelefonicosRecibidos.split(",");
			
			numerosDeTelefonos = Arrays.asList(arrayNumerosTelefonicosRecibidos);
			
			//Comprobando que el split de los telefonos funciona correctamente
			System.out.println("Numeros de telefono recibidos");
			numerosDeTelefonos.forEach(System.out::println);
		}
		/*
		//El codigo siguiente no es necesario, se comentará porque solo sirve para comprobar que estamos 
		//recibiendo los datos del formulario correctamente, pero no es necesario para el funcionamiento de la app
		//comprobando el flujo a ver si estamos recibiendo en este metodo los datos del formulario
		LOG.info("El nombre del empleado recibido en el AltaController es: " + nombre);
		LOG.info("El segundo apellido del empleado recibido en el AltaController es: " + segundoApellido);*/
		
		//Crear el Objeto Empleado
		Empleado empleado = Empleado.builder()
				.nombre(nombre)
				.primerApellido(primerApellido)
				.segundoApellido(segundoApellido)
				.fechaAlta(fechaAlta)
				.genero(genero)
				.salario(salario)
				.departamentos_id(departamentoId)
				.build();
		
		
	}

}






<%@page import="java.util.stream.Collectors"%>
<%@page import="com.example.models.Genero"%>
<%@page import="com.example.models.EmpleadoUpdate"%>
<%@page import="com.example.models.Departamento"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario</title>
</head>
<body>
	<%
		EmpleadoUpdate empleadoUpdate = (EmpleadoUpdate) request.getAttribute("empleadoUpdate");
	
	%>
	<h1>Formulario de Alta/Modificacion de Empleado</h1>

	<fieldset>
		<legend>Formulario de Gestion de Empleado</legend>
		<form action="AltaController" method="post">
		<!-- El valor del campo oculto será: 0 si es alta nueva, si no, será el id del empleado a actualizar -->
			<input type="hidden" name="idEmpleado" value="<%=empleadoUpdate == null ? 0 : empleadoUpdate.id() %>">
			<div> 
				<label for="nombre">Nombre: </label> 
				<input type="text" id="nombre" required name="nombre" placeholder="Ingrese el nombre" 
						value="<%=empleadoUpdate != null ? empleadoUpdate.nombreEmpleado() : "" %>">
		    </div>
		    
		      <div>
		          <label for="primerApellido">Primer Apellido: </label>
				  <input type="text" id="primerApellido" required name="primerApellido" placeholder="Ingrese el Primer Apellido
				  		" value="<%=empleadoUpdate != null ? empleadoUpdate.primerApellido() : "" %>">
			  </div>
			  
			  <div>
                  <label for="segundoApellido">Segundo Apellido: </label>
                  <input type="text" id="segundoApellido" name="segundoApellido" placeholder="Ingrese el Segundo Apellido"
                          value="<%=empleadoUpdate != null ? empleadoUpdate.segundoApellido() : "" %>">
              </div>
              
              <div>
                  <label for="fechaAlta">Fecha de Alta: </label>
                  <input type="date" id="fechaAlta" name="fechaAlta" placeholder="Ingrese la Fecha de Alta" required
                          value="<%=empleadoUpdate != null ? empleadoUpdate.fechaAlta() : "" %>">
                </div>
                
              <div>
                <fieldset>
                    <legend>Genero</legend> 
                    <label for="hombre">Hombre: </label> 
                    <input type="radio" id="hombre" required name="genero" value="HOMBRE"
                           <%=empleadoUpdate != null && empleadoUpdate.genero().equals(Genero.HOMBRE) ? "checked" : "" %>>
                    <label for="mujer">Mujer: </label>
                    <input type="radio" id="mujer" required name="genero" value="MUJER"
                  		   <%=empleadoUpdate != null && empleadoUpdate.genero().equals(Genero.MUJER) ? "checked" : "" %>>
                    <label for="otro">Otro: </label>
                    <input type="radio" id="otro" required name="genero" value="OTRO"
                           <%=empleadoUpdate != null && empleadoUpdate.genero().equals(Genero.OTRO) ? "checked" : "" %>>
                </fieldset>
              </div>
              
             <div>
                 <label for="salario">Salario: </label>
                 <input type="text" id="salario" name="salario" required 
                 value="<%=empleadoUpdate != null ? empleadoUpdate.salario() : "" %>">
            </div>   
            	        
            
            <div>
            	<%
            		List<Departamento> departamentos = (List<Departamento>) request.getAttribute("departamentos");
            	%>
            	
                <label for="departamento">Departamento: </label>
               	<select id="departamento" name="departamento" required>
            		<option></option>	
            		<% 
            		   for(Departamento departamento : departamentos) {
            			   %>
            		       <option  value="<%=departamento.id() %>" <%=empleadoUpdate != null && 
            		       		empleadoUpdate.idDpto() == departamento.id()
            		       			? "selected" : ' ' %> ><%= departamento.nombre() %>
            		      </option>
            		   <%
            		   }
            		%>
            		
            	</select>
            </div>
                 <div>
                 	<label for="correos">Correos: </label>
                 	<input type="text" id="correos" name="correos" 
                 	       placeholder="uno o varios separados por punto y coma" 
                 	       value="<%=empleadoUpdate != null && !empleadoUpdate.emails().contains(null) ?
                 	    		     empleadoUpdate.emails().stream()
                 	    		        .collect(Collectors.joining(";")) : "" %>">
                 </div>
                 
                 <div>
                 	<label for="telefonos">Telefonos: </label>
                 	<input type="text" id="telefonos" name="telefonos" 
                 		   placeholder="uno o varios separados por punto y coma " 
                 		   value="<%=empleadoUpdate != null && !empleadoUpdate.telefonos().contains(null) ?
                 		     		empleadoUpdate.telefonos().stream()
                 		     		    .collect(Collectors.joining(";")) : "" %>">
                 </div>
              <br>
              <br>
              
              <input type="submit" value="Enviar">
		</form>
	</fieldset>
</body>
</html>
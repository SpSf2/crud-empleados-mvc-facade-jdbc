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
	<h1>Formulario de Alta/Modificacion de Empleado</h1>

	<fieldset>
		<legend>Formulario de Gestion de Empleado</legend>
		<form action="#" method="post">
			<div> 
				<label for="nombre">Nombre: </label> 
				<input type="text" id="nombre" required name="nombre" placeholder="Ingrese el nombre">
		    </div>
		    
		      <div>
		          <label for="primerApellido">Primer Apellido: </label>
				  <input type="text" id="primerApellido" required name="primerApellido" placeholder="Ingrese el Primer Apellido">
			  </div>
			  
			  <div>
                  <label for="segundoApellido">Segundo Apellido: </label>
                  <input type="text" id="segundoApellido" name="segundoApellido" placeholder="Ingrese el Segundo Apellido">
              </div>
              
              <div>
                  <label for="fechaAlta">Fecha de Alta: </label>
                  <input type="date" id="fechaAlta" name="fechaAlta" placeholder="Ingrese la Fecha de Alta" required>
                </div>
                
              <div>
                <fieldset>
                    <legend>Genero</legend> 
                    <label for="hombre">Hombre: </label> 
                    <input type="radio" id="hombre" requied name="genero" value="HOMBRE">
                    <label for="mujer">Mujer: </label>
                    <input type="radio" id="mujer" required name="genero" value="MUJER">
                    <label for="otro">Otro: </label>
                    <input type="radio" id="otro" required name="genero" value="OTRO">
                </fieldset>
              </div>
              
             <div>
                 <label for="salario">Salario: </label>
                 <input type="text" id="salario" name="salario" required>
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
            		       <option  value="<%=departamento.id() %>"><%= departamento.nombre() %></option>
            		   <%
            		   }
            		%>
            		
            	</select>
            </div>
                 <div>
                 	<label for="correos">Correos: </label>
                 	<input type="text" id="correos" name="correos" 
                 	       placeholder="uno o varios separados por punto y coma ">
                 </div>
                 
                 <div>
                 	<label for="telefonos">Telefonos: </label>
                 	<input type="text" id="telefonos" name="telefonos" 
                 		   placeholder="uno o varios separados por punto y coma ">
                 </div>
              <br>
              <br>
              
              <input type="submit" value="Enviar">
		</form>
	</fieldset>
</body>
</html>
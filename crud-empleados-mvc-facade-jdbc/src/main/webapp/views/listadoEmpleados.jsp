<%@page import="com.example.models.Empleado"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>empleados</title>
</head>
<body>
	<%
		List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
	%>
	<h1>Listado de Empleados</h1>
	
	<table>
		<thead>
            <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Segundo Apellido</th>
                <th>Fecha de Alta</th>
                <th>Genero</th>
                <th>Salario</th>
            </tr>
        </thead>
        <tbody>
        	<% for(Empleado empleado : empleados) { %>
               <tr>
                   <td><%=empleado.nombre()%></td>
                   <td><%=empleado.primerApellido()%></td>
                   <td><%=empleado.segundoApellido()%></td>
                   <td><%=empleado.fechaAlta()%></td>
                   <td><%=empleado.genero()%></td>
                   <td><%=empleado.salario()%></td>
               </tr>
               <%
               }
               %>
        </tbody>
	</table>

</body>
</html>
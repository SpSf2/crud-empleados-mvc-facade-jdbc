<%@page import="com.example.models.Detalle"%>
<%@page import="com.example.models.Empleado"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% Empleado empleado = (Empleado) request.getAttribute("empleado");
	   Detalle detalles = (Detalle) request.getAttribute("detalles");
	   
	%>
	
	
	
	<h1>Detalles del Empleado: <%=empleado.nombre() + " " + empleado.primerApellido() %></h1>
    
    <h3>Departamento: <%=detalles.nombreDpto() %></h3>
    
    <div>
    	<h3>Telefonos: </h3>
    	
    	<ul>
    		<%
    			for (String numero : detalles.numerosTelefonos()) {
    				%>
    				<li><%=numero %></li>
    				<%
    				
    			}
    		%>
    		
    		<%
    			for (String correo : detalles.emails()) {
    				%>
    				<li><%=correo %></li>
    				<%
    				
    			}
    		%>
    	
    	</ul>	
    </div>

    
</body>
</html>
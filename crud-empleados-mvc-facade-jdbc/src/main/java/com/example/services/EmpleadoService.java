package com.example.services;

import java.sql.SQLException;

public interface EmpleadoService {

	public abstract boolean isConnectionOK() throws SQLException;
}

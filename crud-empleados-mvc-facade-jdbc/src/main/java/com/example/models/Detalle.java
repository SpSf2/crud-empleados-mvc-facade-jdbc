package com.example.models;

import java.util.List;
import java.util.Set;

public record Detalle(String nombreDpto, Set<String> emails, Set<String> numerosTelefonos) {

}

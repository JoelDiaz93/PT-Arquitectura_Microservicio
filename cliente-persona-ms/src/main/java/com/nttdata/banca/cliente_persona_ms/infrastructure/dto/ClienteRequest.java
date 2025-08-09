package com.nttdata.banca.cliente_persona_ms.infrastructure.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ClienteRequest {

    @NotBlank
    public String nombre;
    @NotBlank
    public String contrasena;
    @NotBlank
    public String genero;
    @Min(0)
    public int edad;
    @NotBlank
    public String identificacion;
    public boolean estado = true;
    @NotBlank
    public String direccion;
    @NotBlank
    public String telefono;
}

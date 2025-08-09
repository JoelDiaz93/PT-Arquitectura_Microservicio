package com.nttdata.banca.cliente_persona_ms.infrastructure.dto;

import java.time.LocalDateTime;

public class ClienteResponse {

    public Long id;
    public String nombre;
    public String identificacion;
    public boolean estado;
    public LocalDateTime fechaCreacion;
    public LocalDateTime fechaActualizacion;
}

package com.nttdata.banca.cliente_persona_ms.exception;

public class IdentificacionDuplicadaException extends RuntimeException {

    public IdentificacionDuplicadaException(String identificacion) {
        super("La identificación ya existe: " + identificacion);
    }
}

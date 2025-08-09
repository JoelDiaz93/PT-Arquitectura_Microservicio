package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto;

import java.time.LocalDateTime;

public class MovimientoResponse {

    public Long id;
    public Long cuentaId;
    public LocalDateTime fecha;
    public String tipo;
    public Double valor;
    public Double saldoPosterior;
}

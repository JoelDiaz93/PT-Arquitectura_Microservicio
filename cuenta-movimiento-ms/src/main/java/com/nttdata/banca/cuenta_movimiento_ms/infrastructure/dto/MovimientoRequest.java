package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovimientoRequest {

    @NotNull
    public Long cuentaId;
    @NotNull
    public String tipo;
    @Positive
    public Double valor;
}

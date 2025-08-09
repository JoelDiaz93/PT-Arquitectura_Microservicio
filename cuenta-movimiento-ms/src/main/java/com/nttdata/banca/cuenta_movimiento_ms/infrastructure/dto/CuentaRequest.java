package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CuentaRequest {

    @NotBlank
    public String numeroCuenta;
    @NotBlank
    public String tipo;
    @NotNull
    public Double saldo;      
    public boolean estado = true;
    @NotNull
    public Long clienteId;
}

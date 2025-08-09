package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto;

import java.time.LocalDateTime;

public class CuentaResponse {

    public Long id;
    public String numeroCuenta;
    public String tipo;
    public Double saldo;
    public boolean estado;
    public Long clienteId;
    public LocalDateTime fechaCreacion;
    public LocalDateTime fechaActualizacion;
}

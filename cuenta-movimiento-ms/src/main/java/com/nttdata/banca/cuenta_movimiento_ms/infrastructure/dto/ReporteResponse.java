package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto;

import java.util.List;

public class ReporteResponse {

    public Long clienteId;
    public List<CuentaConMovimientos> cuentas;

    public static class CuentaConMovimientos {

        public Long cuentaId;
        public String numeroCuenta;
        public String tipo;
        public Double saldoActual;
        public List<MovimientoResponse> movimientos;
    }
}

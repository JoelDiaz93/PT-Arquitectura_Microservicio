package com.nttdata.banca.cuenta_movimiento_ms.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;

public interface MovimientoService {

    Movimiento registrarMovimiento(Long cuentaId, String tipo, Double valor);

    List<Movimiento> listar(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);
}

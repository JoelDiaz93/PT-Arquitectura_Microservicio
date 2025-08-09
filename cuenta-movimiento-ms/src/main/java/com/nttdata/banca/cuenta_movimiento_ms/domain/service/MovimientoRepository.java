package com.nttdata.banca.cuenta_movimiento_ms.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;

public interface MovimientoRepository {

    Movimiento guardar(Movimiento movimiento);

    List<Movimiento> listarPorCuentaYRango(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);

    void eliminarPorCuenta(Long cuentaId);
}

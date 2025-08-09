package com.nttdata.banca.cuenta_movimiento_ms.domain.service;

import java.util.List;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;

public interface CuentaService {

    Cuenta crear(Cuenta cuenta);

    Cuenta obtener(Long id);

    List<Cuenta> listarPorCliente(Long clienteId);

    Cuenta actualizar(Long id, Cuenta cuenta);

    void eliminar(Long id);
}

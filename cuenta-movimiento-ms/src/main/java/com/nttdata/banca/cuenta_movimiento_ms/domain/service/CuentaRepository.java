package com.nttdata.banca.cuenta_movimiento_ms.domain.service;

import java.util.List;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;

public interface CuentaRepository {

    Cuenta guardar(Cuenta cuenta);

    Cuenta buscarPorId(Long id);

    Cuenta buscarPorNumero(String numero);

    List<Cuenta> listarPorCliente(Long clienteId);

    Cuenta actualizar(Long id, Cuenta cuenta);

    void eliminar(Long id); // soft delete
}

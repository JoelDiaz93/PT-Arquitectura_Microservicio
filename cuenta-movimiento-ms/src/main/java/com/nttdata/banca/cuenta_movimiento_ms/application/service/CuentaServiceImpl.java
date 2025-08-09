package com.nttdata.banca.cuenta_movimiento_ms.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaRepository;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repo;

    public CuentaServiceImpl(CuentaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Cuenta crear(Cuenta cuenta) {
        return repo.guardar(cuenta);
    }

    @Override
    public Cuenta obtener(Long id) {
        return repo.buscarPorId(id);
    }

    @Override
    public List<Cuenta> listarPorCliente(Long clienteId) {
        return repo.listarPorCliente(clienteId);
    }

    @Override
    public Cuenta actualizar(Long id, Cuenta cuenta) {
        return repo.actualizar(id, cuenta);
    }

    @Override
    public void eliminar(Long id) {
        repo.eliminar(id);
    }
}

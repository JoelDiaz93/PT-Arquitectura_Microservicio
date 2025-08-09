package com.nttdata.banca.cuenta_movimiento_ms.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaRepository;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.MovimientoRepository;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.MovimientoService;
import com.nttdata.banca.cuenta_movimiento_ms.exception.CuentaNoEncontradaException;
import com.nttdata.banca.cuenta_movimiento_ms.exception.SaldoNoDisponibleException;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movRepo;
    private final CuentaRepository cuentaRepo;

    public MovimientoServiceImpl(MovimientoRepository movRepo, CuentaRepository cuentaRepo) {
        this.movRepo = movRepo;
        this.cuentaRepo = cuentaRepo;
    }

    @Override
    @Transactional
    public Movimiento registrarMovimiento(Long cuentaId, String tipo, Double valor) {
        Cuenta cuenta = cuentaRepo.buscarPorId(cuentaId);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada: " + cuentaId);
        }

        double delta = valor;
        if ("RETIRO".equalsIgnoreCase(tipo)) {
            if (valor <= 0) {
                delta = -Math.abs(valor);
            } else {
                delta = -valor;
            }
        } else if ("DEPOSITO".equalsIgnoreCase(tipo)) {
            if (valor <= 0) {
                throw new IllegalArgumentException("Valor de depósito debe ser positivo");
            }
            delta = Math.abs(valor);
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido (DEPOSITO/RETIRO)");
        }

        double nuevoSaldo = (cuenta.getSaldo() == null ? 0d : cuenta.getSaldo()) + delta;
        if (nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException();
        }

        cuenta.setSaldo(nuevoSaldo);
        cuentaRepo.actualizar(cuenta.getId(), cuenta); // persiste nuevo saldo

        Movimiento m = new Movimiento(null, cuenta.getId(), LocalDateTime.now(),
                delta >= 0 ? "DEPOSITO" : "RETIRO", delta, nuevoSaldo);
        return movRepo.guardar(m);
    }

    @Override
    public List<Movimiento> listar(Long cuentaId, LocalDateTime desde, LocalDateTime hasta) {
        return movRepo.listarPorCuentaYRango(cuentaId, desde, hasta);
    }
}

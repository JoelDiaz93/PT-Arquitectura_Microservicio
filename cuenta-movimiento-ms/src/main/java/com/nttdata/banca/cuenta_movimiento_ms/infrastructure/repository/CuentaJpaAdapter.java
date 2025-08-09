package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaRepository;
import com.nttdata.banca.cuenta_movimiento_ms.exception.CuentaNoEncontradaException;

@Component
public class CuentaJpaAdapter implements CuentaRepository {

    private final CuentaJpaRepository jpa;

    public CuentaJpaAdapter(CuentaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Transactional
    public Cuenta guardar(Cuenta cuenta) {
        if (jpa.existsByNumeroCuentaAndEliminadoFalse(cuenta.getNumeroCuenta())) {
            throw new DataIntegrityViolationException("Número de cuenta duplicado: " + cuenta.getNumeroCuenta());
        }
        CuentaEntity e = CuentaEntity.fromDomain(cuenta);
        return jpa.save(e).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta buscarPorId(Long id) {
        if (!jpa.existsByIdAndEliminadoFalse(id)) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada: " + id);
        }
        CuentaEntity ref = jpa.getReferenceById(id);
        return ref.isEliminado() ? null : ref.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta buscarPorNumero(String numero) {
        CuentaEntity e = jpa.findByNumeroCuentaAndEliminadoFalse(numero);
        if (e == null) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada: " + numero);
        }
        return e.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listarPorCliente(Long clienteId) {
        return jpa.findByClienteIdAndEliminadoFalse(clienteId).stream()
                .map(CuentaEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Cuenta actualizar(Long id, Cuenta cuenta) {
        if (!jpa.existsByIdAndEliminadoFalse(id)) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada: " + id);
        }
        CuentaEntity e = jpa.getReferenceById(id);
        e.setNumeroCuenta(cuenta.getNumeroCuenta());
        e.setTipo(cuenta.getTipo());
        e.setSaldo(cuenta.getSaldo() == null ? 0d : cuenta.getSaldo());
        e.setEstado(cuenta.isEstado());
        e.setClienteId(cuenta.getClienteId());
        return jpa.save(e).toDomain();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!jpa.existsByIdAndEliminadoFalse(id)) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada: " + id);
        }
        CuentaEntity e = jpa.getReferenceById(id);
        e.setEliminado(true);
        e.setFechaEliminacion(LocalDateTime.now());
        jpa.save(e);
    }
}

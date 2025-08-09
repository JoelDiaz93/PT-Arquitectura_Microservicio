package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.MovimientoRepository;

@Component
public class MovimientoJpaAdapter implements MovimientoRepository {

    private final MovimientoJpaRepository jpa;

    public MovimientoJpaAdapter(MovimientoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Transactional
    public Movimiento guardar(Movimiento movimiento) {
        MovimientoEntity e = MovimientoEntity.fromDomain(movimiento);
        return jpa.save(e).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> listarPorCuentaYRango(Long cuentaId, LocalDateTime desde, LocalDateTime hasta) {
        List<MovimientoEntity> entities;
        if (desde != null && hasta != null) {
            entities = jpa.findByCuentaIdAndFechaBetweenAndEliminadoFalseOrderByFechaAsc(cuentaId, desde, hasta);
        } else {
            entities = jpa.findByCuentaIdAndEliminadoFalseOrderByFechaAsc(cuentaId);
        }
        return entities.stream().map(MovimientoEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarPorCuenta(Long cuentaId) {
        // opcional: marcar eliminados los movimientos de esa cuenta
        List<MovimientoEntity> list = jpa.findByCuentaIdAndEliminadoFalseOrderByFechaAsc(cuentaId);
        for (MovimientoEntity e : list) {
            e.setEliminado(true);
            e.setFechaEliminacion(LocalDateTime.now());
            jpa.save(e);
        }
    }
}

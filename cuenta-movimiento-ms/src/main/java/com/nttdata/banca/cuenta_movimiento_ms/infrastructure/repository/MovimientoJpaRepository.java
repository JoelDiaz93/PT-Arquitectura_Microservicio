package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {

    List<MovimientoEntity> findByCuentaIdAndFechaBetweenAndEliminadoFalseOrderByFechaAsc(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);

    List<MovimientoEntity> findByCuentaIdAndEliminadoFalseOrderByFechaAsc(Long cuentaId);
}

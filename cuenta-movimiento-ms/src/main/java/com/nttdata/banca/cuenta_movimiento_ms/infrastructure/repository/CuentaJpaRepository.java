package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {

    boolean existsByNumeroCuentaAndEliminadoFalse(String numeroCuenta);

    boolean existsByIdAndEliminadoFalse(Long id);

    CuentaEntity findByNumeroCuentaAndEliminadoFalse(String numeroCuenta);

    List<CuentaEntity> findByClienteIdAndEliminadoFalse(Long clienteId);
}

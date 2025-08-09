package com.nttdata.banca.cliente_persona_ms.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {

    boolean existsByIdentificacionAndEliminadoFalse(String identificacion);

    boolean existsByIdAndEliminadoFalse(Long id);

    List<ClienteEntity> findByEliminadoFalse();
}

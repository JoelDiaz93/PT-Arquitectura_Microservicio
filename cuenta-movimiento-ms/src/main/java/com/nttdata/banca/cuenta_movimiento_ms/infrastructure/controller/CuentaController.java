package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaService;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.CuentaRequest;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.CuentaResponse;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository.CuentaEntity;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository.CuentaJpaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService service;
    private final CuentaJpaRepository jpa; // para fechas (pragmático)

    public CuentaController(CuentaService service, CuentaJpaRepository jpa) {
        this.service = service;
        this.jpa = jpa;
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crear(@Valid @RequestBody CuentaRequest req) {
        Cuenta c = new Cuenta(null, req.numeroCuenta, req.tipo, req.saldo, req.estado, req.clienteId);
        Cuenta creada = service.crear(c);
        CuentaEntity e = jpa.getReferenceById(creada.getId());
        return ResponseEntity.ok(toResponse(creada, e));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> obtener(@PathVariable Long id) {
        Cuenta c = service.obtener(id);
        CuentaEntity e = jpa.getReferenceById(id);
        return ResponseEntity.ok(toResponse(c, e));
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> listarPorCliente(@RequestParam Long clienteId) {
        List<Cuenta> list = service.listarPorCliente(clienteId);
        List<CuentaResponse> resp = list.stream().map(c -> {
            CuentaEntity e = jpa.getReferenceById(c.getId());
            return toResponse(c, e);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody CuentaRequest req) {
        Cuenta c = new Cuenta(id, req.numeroCuenta, req.tipo, req.saldo, req.estado, req.clienteId);
        Cuenta upd = service.actualizar(id, c);
        CuentaEntity e = jpa.getReferenceById(upd.getId());
        return ResponseEntity.ok(toResponse(upd, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private CuentaResponse toResponse(Cuenta c, CuentaEntity e) {
        CuentaResponse r = new CuentaResponse();
        r.id = c.getId();
        r.numeroCuenta = c.getNumeroCuenta();
        r.tipo = c.getTipo();
        r.saldo = c.getSaldo();
        r.estado = c.isEstado();
        r.clienteId = c.getClienteId();
        r.fechaCreacion = e.getFechaCreacion();
        r.fechaActualizacion = e.getFechaActualizacion();
        return r;
    }
}

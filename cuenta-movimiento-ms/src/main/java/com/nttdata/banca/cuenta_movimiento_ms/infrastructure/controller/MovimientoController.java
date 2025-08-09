package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.MovimientoService;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.MovimientoRequest;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.MovimientoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest req) {
        Movimiento m = service.registrarMovimiento(req.cuentaId, req.tipo, req.valor);
        return ResponseEntity.ok(toResponse(m));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> listar(
            @RequestParam Long cuentaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        List<Movimiento> list = service.listar(cuentaId, desde, hasta);
        return ResponseEntity.ok(list.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    private MovimientoResponse toResponse(Movimiento m) {
        MovimientoResponse r = new MovimientoResponse();
        r.id = m.getId();
        r.cuentaId = m.getCuentaId();
        r.fecha = m.getFecha();
        r.tipo = m.getTipo();
        r.valor = m.getValor();
        r.saldoPosterior = m.getSaldoPosterior();
        return r;
    }
}

package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaService;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.MovimientoService;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.MovimientoResponse;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.ReporteResponse;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;

    public ReporteController(CuentaService cuentaService, MovimientoService movimientoService) {
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public ResponseEntity<ReporteResponse> estadoDeCuenta(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        List<Cuenta> cuentas = cuentaService.listarPorCliente(clienteId);

        ReporteResponse res = new ReporteResponse();
        res.clienteId = clienteId;
        res.cuentas = cuentas.stream().map(c -> {
            ReporteResponse.CuentaConMovimientos cm = new ReporteResponse.CuentaConMovimientos();
            cm.cuentaId = c.getId();
            cm.numeroCuenta = c.getNumeroCuenta();
            cm.tipo = c.getTipo();
            cm.saldoActual = c.getSaldo();

            List<Movimiento> movs = movimientoService.listar(c.getId(), desde, hasta);
            cm.movimientos = movs.stream().map(m -> {
                MovimientoResponse r = new MovimientoResponse();
                r.id = m.getId();
                r.cuentaId = m.getCuentaId();
                r.fecha = m.getFecha();
                r.tipo = m.getTipo();
                r.valor = m.getValor();
                r.saldoPosterior = m.getSaldoPosterior();
                return r;
            }).collect(Collectors.toList());

            return cm;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(res);
    }
}

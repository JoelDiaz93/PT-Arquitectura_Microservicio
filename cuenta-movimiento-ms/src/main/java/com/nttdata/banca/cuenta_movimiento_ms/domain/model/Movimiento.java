package com.nttdata.banca.cuenta_movimiento_ms.domain.model;

import java.time.LocalDateTime;

public class Movimiento {

    private final Long id;
    private final Long cuentaId;
    private final LocalDateTime fecha;
    private final String tipo;         
    private final Double valor;        
    private final Double saldoPosterior;

    public Movimiento(Long id, Long cuentaId, LocalDateTime fecha, String tipo, Double valor, Double saldoPosterior) {
        this.id = id;
        this.cuentaId = cuentaId;
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;
        this.saldoPosterior = saldoPosterior;
    }

    public Long getId() {
        return id;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }

    public Double getSaldoPosterior() {
        return saldoPosterior;
    }
}

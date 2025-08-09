package com.nttdata.banca.cuenta_movimiento_ms.domain.model;

public class Cuenta {

    private final Long id;
    private final String numeroCuenta;
    private final String tipo;
    private Double saldo;
    private boolean estado;
    private final Long clienteId;

    public Cuenta(Long id, String numeroCuenta, String tipo, Double saldo, boolean estado, Long clienteId) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.estado = estado;
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public boolean isEstado() {
        return estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void activar() {
        this.estado = true;
    }

    public void desactivar() {
        this.estado = false;
    }
}

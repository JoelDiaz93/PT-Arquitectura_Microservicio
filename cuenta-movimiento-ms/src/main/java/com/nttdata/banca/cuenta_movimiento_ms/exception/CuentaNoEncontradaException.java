package com.nttdata.banca.cuenta_movimiento_ms.exception;

public class CuentaNoEncontradaException extends RuntimeException {

    public CuentaNoEncontradaException(String msg) {
        super(msg);
    }
}

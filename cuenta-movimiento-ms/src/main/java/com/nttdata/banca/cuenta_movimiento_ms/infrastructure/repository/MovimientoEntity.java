package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository;

import java.time.LocalDateTime;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimientos")
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_id", nullable = false)
    private Long cuentaId;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 20)
    private String tipo; // DEPOSITO / RETIRO

    @Column(nullable = false)
    private Double valor; // +depósito / -retiro

    @Column(name = "saldo_posterior", nullable = false)
    private Double saldoPosterior;

    // Auditoría & soft delete (si quieres borrar lógicamente movimientos)
    @Column(name = "eliminado", nullable = false)
    private boolean eliminado = false;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    public MovimientoEntity() {
    }

    public static MovimientoEntity fromDomain(Movimiento m) {
        MovimientoEntity e = new MovimientoEntity();
        e.setId(m.getId());
        e.setCuentaId(m.getCuentaId());
        e.setFecha(m.getFecha());
        e.setTipo(m.getTipo());
        e.setValor(m.getValor());
        e.setSaldoPosterior(m.getSaldoPosterior());
        return e;
    }

    public Movimiento toDomain() {
        return new Movimiento(id, cuentaId, fecha, tipo, valor, saldoPosterior);
    }

    // getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldoPosterior() {
        return saldoPosterior;
    }

    public void setSaldoPosterior(Double saldoPosterior) {
        this.saldoPosterior = saldoPosterior;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
}

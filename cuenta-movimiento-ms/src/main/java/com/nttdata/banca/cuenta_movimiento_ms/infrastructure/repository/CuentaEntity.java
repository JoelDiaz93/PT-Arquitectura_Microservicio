package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository;

import java.time.LocalDateTime;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "cuentas", uniqueConstraints = {
    @UniqueConstraint(name = "uk_numero_cuenta", columnNames = "numero_cuenta")
})
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false, length = 32, unique = true)
    private String numeroCuenta;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false)
    private boolean estado = true;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // Auditoría
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    // Soft delete
    @Column(nullable = false)
    private boolean eliminado = false;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    public CuentaEntity() {
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.fechaCreacion = now;
        this.fechaActualizacion = now;
        if (this.eliminado && this.fechaEliminacion == null) {
            this.fechaEliminacion = now;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
        if (this.eliminado && this.fechaEliminacion == null) {
            this.fechaEliminacion = LocalDateTime.now();
        }
        if (!this.eliminado) {
            this.fechaEliminacion = null;
        }
    }

    public static CuentaEntity fromDomain(Cuenta c) {
        CuentaEntity e = new CuentaEntity();
        e.setId(c.getId());
        e.setNumeroCuenta(c.getNumeroCuenta());
        e.setTipo(c.getTipo());
        e.setSaldo(c.getSaldo() == null ? 0d : c.getSaldo());
        e.setEstado(c.isEstado());
        e.setClienteId(c.getClienteId());
        return e;
    }

    public Cuenta toDomain() {
        return new Cuenta(id, numeroCuenta, tipo, saldo, estado, clienteId);
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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

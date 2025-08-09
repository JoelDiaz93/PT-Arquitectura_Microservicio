package com.nttdata.banca.cliente_persona_ms.infrastructure.repository;

import java.time.LocalDateTime;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;
import com.nttdata.banca.cliente_persona_ms.domain.model.Persona;

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
@Table(
        name = "clientes",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_identificacion", columnNames = "identificacion")
        }
)
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private boolean estado;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false, unique = true)
    private String identificacion;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "eliminado", nullable = false)
    private boolean eliminado = false;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    public ClienteEntity() {
    }

    public ClienteEntity(Long id, String contrasena, boolean estado, String nombre, String genero, int edad,
            String identificacion, String direccion, String telefono,
            LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, boolean eliminado) {
        this.id = id;
        this.contrasena = contrasena;
        this.estado = estado;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.eliminado = eliminado;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime ahora = LocalDateTime.now();
        this.fechaCreacion = ahora;
        this.fechaActualizacion = ahora;
        if (this.eliminado && this.fechaEliminacion == null) {
            this.fechaEliminacion = ahora;
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

    // ===== Getters y Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    // ===== Métodos de conversión =====
    public static ClienteEntity fromDomain(Cliente cliente) {
        Persona p = cliente.getPersona();
        ClienteEntity e = new ClienteEntity();
        e.setId(cliente.getId());
        e.setContrasena(cliente.getContrasena());
        e.setEstado(cliente.isEstado());
        e.setNombre(p.getNombre());
        e.setGenero(p.getGenero());
        e.setEdad(p.getEdad());
        e.setIdentificacion(p.getIdentificacion());
        e.setDireccion(p.getDireccion());
        e.setTelefono(p.getTelefono());
        e.setFechaCreacion(LocalDateTime.now());
        return e;
    }

    public Cliente toDomain() {
        Persona persona = new Persona(nombre, genero, edad, identificacion, direccion, telefono);
        return new Cliente(id, contrasena, estado, persona);
    }
}

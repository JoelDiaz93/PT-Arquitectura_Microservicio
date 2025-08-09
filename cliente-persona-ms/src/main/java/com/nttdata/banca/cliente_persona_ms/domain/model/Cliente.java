package com.nttdata.banca.cliente_persona_ms.domain.model;

public class Cliente {
    private final Long id; // autoincrement
    private final String contrasena;
    private boolean estado;
    private final Persona persona;

    public Cliente(Long id, String contrasena, boolean estado, Persona persona) {
        this.id = id;
        this.contrasena = contrasena;
        this.estado = estado;
        this.persona = persona;
    }

    public Long getId() { return id; }
    public String getContrasena() { return contrasena; }
    public boolean isEstado() { return estado; }
    public Persona getPersona() { return persona; }

    public void activar() { this.estado = true; }
    public void desactivar() { this.estado = false; }
}
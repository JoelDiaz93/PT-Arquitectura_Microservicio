package com.nttdata.banca.cliente_persona_ms.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;
import com.nttdata.banca.cliente_persona_ms.domain.model.Persona;
import com.nttdata.banca.cliente_persona_ms.domain.service.ClienteRepository;
import com.nttdata.banca.cliente_persona_ms.exception.ClienteNoEncontradoException;
import com.nttdata.banca.cliente_persona_ms.exception.IdentificacionDuplicadaException;

@Component
public class ClienteJpaAdapter implements ClienteRepository {

    private final ClienteJpaRepository jpa;
    private final PasswordEncoder passwordEncoder;

    public ClienteJpaAdapter(ClienteJpaRepository jpa, PasswordEncoder passwordEncoder) {
        this.jpa = jpa;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        // Unicidad de identificación (solo activos)
        if (jpa.existsByIdentificacionAndEliminadoFalse(cliente.getPersona().getIdentificacion())) {
            throw new IdentificacionDuplicadaException(cliente.getPersona().getIdentificacion());
        }
        // Hash de contraseña
        Cliente toSave = new Cliente(
                null,
                passwordEncoder.encode(cliente.getContrasena()),
                cliente.isEstado(),
                cliente.getPersona()
        );
        ClienteEntity entity = ClienteEntity.fromDomain(toSave);
        return jpa.save(entity).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        if (!jpa.existsByIdAndEliminadoFalse(id)) {
            throw new ClienteNoEncontradoException("Cliente no encontrado: " + id);
        }
        ClienteEntity ref = jpa.getReferenceById(id);
        if (ref.isEliminado()) {
            throw new ClienteNoEncontradoException("Cliente no encontrado: " + id);
        }
        ref.getId();
        return ref.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return jpa.findByEliminadoFalse().stream()
                .map(ClienteEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Cliente actualizar(Long id, Cliente cliente) {
        if (!jpa.existsByIdAndEliminadoFalse(id)) {
            throw new ClienteNoEncontradoException("Cliente no encontrado: " + id);
        }
        ClienteEntity existente = jpa.getReferenceById(id);
        if (existente.isEliminado()) {
            throw new ClienteNoEncontradoException("Cliente no encontrado: " + id);
        }

        // Validar identificación única si cambia
        Persona p = cliente.getPersona();
        if (!existente.getIdentificacion().equals(p.getIdentificacion())
                && jpa.existsByIdentificacionAndEliminadoFalse(p.getIdentificacion())) {
            throw new IdentificacionDuplicadaException(p.getIdentificacion());
        }

        // Si contrasena es null o vacía, mantener la actual; si no, re-hash
        if (cliente.getContrasena() != null && !cliente.getContrasena().isBlank()) {
            existente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));
        }

        existente.setEstado(cliente.isEstado());
        existente.setNombre(p.getNombre());
        existente.setGenero(p.getGenero());
        existente.setEdad(p.getEdad());
        existente.setIdentificacion(p.getIdentificacion());
        existente.setDireccion(p.getDireccion());
        existente.setTelefono(p.getTelefono());

        return jpa.save(existente).toDomain();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!jpa.existsByIdAndEliminadoFalse(id)) {
            throw new ClienteNoEncontradoException("Cliente no encontrado: " + id);
        }
        ClienteEntity existente = jpa.getReferenceById(id);
        existente.setEliminado(true);
        existente.setFechaEliminacion(LocalDateTime.now());
        jpa.save(existente);
    }
}

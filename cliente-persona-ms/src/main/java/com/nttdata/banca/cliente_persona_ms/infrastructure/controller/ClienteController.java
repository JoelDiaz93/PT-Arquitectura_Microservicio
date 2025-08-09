package com.nttdata.banca.cliente_persona_ms.infrastructure.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;
import com.nttdata.banca.cliente_persona_ms.domain.model.Persona;
import com.nttdata.banca.cliente_persona_ms.domain.service.ClienteService;
import com.nttdata.banca.cliente_persona_ms.infrastructure.dto.ClienteRequest;
import com.nttdata.banca.cliente_persona_ms.infrastructure.dto.ClienteResponse;
import com.nttdata.banca.cliente_persona_ms.infrastructure.repository.ClienteEntity;
import com.nttdata.banca.cliente_persona_ms.infrastructure.repository.ClienteJpaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteJpaRepository jpa; // solo para mapear fechas de auditoría (alternativa: usar assembler)

    public ClienteController(ClienteService clienteService, ClienteJpaRepository jpa) {
        this.clienteService = clienteService;
        this.jpa = jpa;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request) {
        Persona persona = new Persona(request.nombre, request.genero, request.edad,
                request.identificacion, request.direccion, request.telefono);
        Cliente nuevo = new Cliente(null, request.contrasena, request.estado, persona);
        Cliente creado = clienteService.crearCliente(nuevo);
        ClienteEntity e = jpa.getReferenceById(creado.getId());
        return ResponseEntity.ok(toResponse(creado, e));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtener(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        ClienteEntity e = jpa.getReferenceById(id);
        return ResponseEntity.ok(toResponse(cliente, e));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        List<Cliente> lista = clienteService.listarClientes();
        List<ClienteResponse> resp = lista.stream().map(c -> {
            ClienteEntity e = jpa.getReferenceById(c.getId());
            return toResponse(c, e);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        Persona persona = new Persona(request.nombre, request.genero, request.edad,
                request.identificacion, request.direccion, request.telefono);
        Cliente cliente = new Cliente(id, request.contrasena, request.estado, persona);
        Cliente actualizado = clienteService.actualizarCliente(id, cliente);
        ClienteEntity e = jpa.getReferenceById(actualizado.getId());
        return ResponseEntity.ok(toResponse(actualizado, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    private ClienteResponse toResponse(Cliente cliente, ClienteEntity entity) {
        ClienteResponse r = new ClienteResponse();
        r.id = cliente.getId();
        r.nombre = cliente.getPersona().getNombre();
        r.identificacion = cliente.getPersona().getIdentificacion();
        r.estado = cliente.isEstado();
        r.fechaCreacion = entity.getFechaCreacion();
        r.fechaActualizacion = entity.getFechaActualizacion();
        return r;
    }
}

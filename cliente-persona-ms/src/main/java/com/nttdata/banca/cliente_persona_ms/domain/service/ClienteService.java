package com.nttdata.banca.cliente_persona_ms.domain.service;

import java.util.List;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;

public interface ClienteService {

    Cliente crearCliente(Cliente cliente);

    Cliente obtenerClientePorId(Long id);

    List<Cliente> listarClientes();

    Cliente actualizarCliente(Long id, Cliente cliente);

    void eliminarCliente(Long id);
}

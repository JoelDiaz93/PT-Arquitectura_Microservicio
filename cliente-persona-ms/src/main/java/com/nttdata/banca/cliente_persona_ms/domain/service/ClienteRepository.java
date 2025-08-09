package com.nttdata.banca.cliente_persona_ms.domain.service;

import java.util.List;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;

public interface ClienteRepository {

    Cliente guardar(Cliente cliente);

    Cliente buscarPorId(Long id);

    List<Cliente> listarTodos();

    Cliente actualizar(Long id, Cliente cliente);

    void eliminar(Long id);
}

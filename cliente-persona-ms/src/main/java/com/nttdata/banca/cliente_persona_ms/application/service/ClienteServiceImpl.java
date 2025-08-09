package com.nttdata.banca.cliente_persona_ms.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;
import com.nttdata.banca.cliente_persona_ms.domain.service.ClienteRepository;
import com.nttdata.banca.cliente_persona_ms.domain.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return repository.guardar(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return repository.buscarPorId(id);
    }

    @Override
    public List<Cliente> listarClientes() {
        return repository.listarTodos();
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        return repository.actualizar(id, cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        repository.eliminar(id);
    }
}

package com.nttdata.banca.cliente_persona_ms.application.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;
import com.nttdata.banca.cliente_persona_ms.domain.model.Persona;
import com.nttdata.banca.cliente_persona_ms.domain.service.ClienteRepository;

public class ClienteServiceImplTest {

    private ClienteRepository repository;
    private ClienteServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(ClienteRepository.class);
        service = new ClienteServiceImpl(repository);
    }

    @Test
    void crearCliente_deberiaGuardar() {
        Cliente cli = buildCliente(null);
        when(repository.guardar(any())).thenReturn(new Cliente(1L, cli.getContrasena(), cli.isEstado(), cli.getPersona()));
        Cliente creado = service.crearCliente(cli);
        assertNotNull(creado.getId());
        verify(repository, times(1)).guardar(any());
    }

    @Test
    void listarClientes_deberiaRetornarLista() {
        when(repository.listarTodos()).thenReturn(Arrays.asList(buildCliente(1L), buildCliente(2L)));
        List<Cliente> lista = service.listarClientes();
        assertEquals(2, lista.size());
        verify(repository, times(1)).listarTodos();
    }

    private Cliente buildCliente(Long id) {
        Persona p = new Persona("Juan Perez", "Masculino", 30, "1234567890", "Av. Siempre Viva", "0999999999");
        return new Cliente(id, "1234", true, p);
    }
}

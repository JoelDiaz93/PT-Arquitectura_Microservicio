package com.nttdata.banca.cliente_persona_ms.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.banca.cliente_persona_ms.domain.model.Cliente;
import com.nttdata.banca.cliente_persona_ms.domain.model.Persona;
import com.nttdata.banca.cliente_persona_ms.domain.service.ClienteService;
import com.nttdata.banca.cliente_persona_ms.infrastructure.dto.ClienteRequest;
import com.nttdata.banca.cliente_persona_ms.infrastructure.repository.ClienteEntity;
import com.nttdata.banca.cliente_persona_ms.infrastructure.repository.ClienteJpaRepository;

@WebMvcTest(ClienteController.class)
@Import(ClienteControllerTest.MockConfig.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Estos beans se inyectan desde MockConfig
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @TestConfiguration
    static class MockConfig {

        @Bean
        public ClienteService clienteService() {
            return Mockito.mock(ClienteService.class);
        }

        /**
         * Solo es necesario si tu ClienteController tiene en el constructor el
         * ClienteJpaRepository (por ejemplo, para leer
         * fechaCreacion/fechaActualizacion). Si ya refactorizaste el controller
         * para depender solo del service, puedes borrar este bean.
         */
        @Bean
        public ClienteJpaRepository clienteJpaRepository() {
            return Mockito.mock(ClienteJpaRepository.class);
        }
    }

    @Test
    void crearCliente_ok() throws Exception {
        // arrange
        ClienteRequest req = req();
        Cliente creado = toCliente(1L, req);
        Mockito.when(clienteService.crearCliente(any())).thenReturn(creado);

        // Si el controller consulta el repo para fechas de auditoría, devolvemos una entidad con fechas
        ClienteEntity entidad = entidadDesdeRequest(1L, req);
        Mockito.when(clienteJpaRepository.getReferenceById(1L)).thenReturn(entidad);

        // act - assert
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value(req.nombre))
                .andExpect(jsonPath("$.identificacion").value(req.identificacion));
    }

    @Test
    void listar_ok() throws Exception {
        // arrange
        ClienteRequest req = req();
        Mockito.when(clienteService.listarClientes())
                .thenReturn(Collections.singletonList(toCliente(1L, req)));

        // si el controller mapea fechas consultando getReferenceById para cada cliente, preparamos la entidad
        ClienteEntity entidad = entidadDesdeRequest(1L, req);
        Mockito.when(clienteJpaRepository.getReferenceById(1L)).thenReturn(entidad);

        // act - assert
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value(req.nombre))
                .andExpect(jsonPath("$[0].identificacion").value(req.identificacion));
    }

    // ================= helpers =================
    private ClienteRequest req() {
        ClienteRequest r = new ClienteRequest();
        r.contrasena = "1234";
        r.estado = true;
        r.nombre = "Juan Perez";
        r.genero = "Masculino";
        r.edad = 30;
        r.identificacion = "1234567890";
        r.direccion = "Av. Siempre Viva";
        r.telefono = "0999999999";
        return r;
    }

    private Cliente toCliente(Long id, ClienteRequest r) {
        Persona p = new Persona(r.nombre, r.genero, r.edad, r.identificacion, r.direccion, r.telefono);
        return new Cliente(id, r.contrasena, r.estado, p);
    }

    /**
     * Construye una entidad de JPA con los mismos datos del request para que el
     * controller pueda mapear fechaCreacion / fechaActualizacion si las expone
     * en el response. Asegúrate de que ClienteEntity tenga setters para las
     * fechas.
     */
    private ClienteEntity entidadDesdeRequest(Long id, ClienteRequest r) {
        ClienteEntity e = new ClienteEntity();
        e.setId(id);
        e.setContrasena("$2a$10$hash-no-importa-en-test");
        e.setEstado(r.estado);
        e.setNombre(r.nombre);
        e.setGenero(r.genero);
        e.setEdad(r.edad);
        e.setIdentificacion(r.identificacion);
        e.setDireccion(r.direccion);
        e.setTelefono(r.telefono);
        LocalDateTime ahora = LocalDateTime.now();
        // requiere setters en la entidad para fechas de auditoría
        e.setFechaCreacion(ahora);
        e.setFechaActualizacion(ahora);
        return e;
    }
}

package com.nttdata.banca.cuenta_movimiento_ms.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaService;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.dto.CuentaRequest;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository.CuentaEntity;
import com.nttdata.banca.cuenta_movimiento_ms.infrastructure.repository.CuentaJpaRepository;

@WebMvcTest(CuentaController.class)
@Import(CuentaControllerTest.MockConfig.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private CuentaJpaRepository cuentaJpaRepository;

    @TestConfiguration
    static class MockConfig {

        @Bean
        public CuentaService cuentaService() {
            return Mockito.mock(CuentaService.class);
        }

        @Bean
        public CuentaJpaRepository cuentaJpaRepository() {
            return Mockito.mock(CuentaJpaRepository.class);
        }
    }

    @Test
    void crear_ok() throws Exception {
        CuentaRequest req = new CuentaRequest();
        req.numeroCuenta = "585545";
        req.tipo = "Corriente";
        req.saldo = 1000.0;
        req.estado = true;
        req.clienteId = 1L;

        Cuenta creada = new Cuenta(1L, req.numeroCuenta, req.tipo, req.saldo, req.estado, req.clienteId);
        Mockito.when(cuentaService.crear(any())).thenReturn(creada);

        CuentaEntity e = new CuentaEntity();
        e.setId(1L);
        e.setNumeroCuenta(req.numeroCuenta);
        e.setTipo(req.tipo);
        e.setSaldo(req.saldo);
        e.setEstado(req.estado);
        e.setClienteId(req.clienteId);
        e.setFechaCreacion(LocalDateTime.now());
        e.setFechaActualizacion(LocalDateTime.now());
        Mockito.when(cuentaJpaRepository.getReferenceById(1L)).thenReturn(e);

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numeroCuenta").value(req.numeroCuenta));
    }

    @Test
    void listarPorCliente_ok() throws Exception {
        Long clienteId = 1L;
        Cuenta c = new Cuenta(1L, "478758", "Ahorro", 2000.0, true, clienteId);
        Mockito.when(cuentaService.listarPorCliente(clienteId)).thenReturn(List.of(c));

        CuentaEntity e = new CuentaEntity();
        e.setId(1L);
        e.setNumeroCuenta("478758");
        e.setTipo("Ahorro");
        e.setSaldo(2000.0);
        e.setEstado(true);
        e.setClienteId(clienteId);
        e.setFechaCreacion(LocalDateTime.now());
        e.setFechaActualizacion(LocalDateTime.now());
        Mockito.when(cuentaJpaRepository.getReferenceById(1L)).thenReturn(e);

        mockMvc.perform(get("/cuentas").param("clienteId", String.valueOf(clienteId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCuenta").value("478758"));
    }
}

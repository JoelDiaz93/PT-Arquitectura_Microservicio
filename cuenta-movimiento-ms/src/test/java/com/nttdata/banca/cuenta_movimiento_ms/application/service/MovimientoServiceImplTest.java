package com.nttdata.banca.cuenta_movimiento_ms.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Cuenta;
import com.nttdata.banca.cuenta_movimiento_ms.domain.model.Movimiento;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.CuentaRepository;
import com.nttdata.banca.cuenta_movimiento_ms.domain.service.MovimientoRepository;
import com.nttdata.banca.cuenta_movimiento_ms.exception.CuentaNoEncontradaException;

class MovimientoServiceImplTest {

    private MovimientoRepository movRepo;
    private CuentaRepository cuentaRepo;
    private MovimientoServiceImpl service;

    @BeforeEach
    void setUp() {
        movRepo = Mockito.mock(MovimientoRepository.class);
        cuentaRepo = Mockito.mock(CuentaRepository.class);
        service = new MovimientoServiceImpl(movRepo, cuentaRepo);
    }

    @Test
    void registrarMovimiento_ok_deposito() {
        // ctor: (id, numeroCuenta, tipo, saldo, estado, clienteId)
        Cuenta cuenta = new Cuenta(1L, "123", "AHORROS", 100.0, true, 10L);

        when(cuentaRepo.buscarPorId(1L)).thenReturn(cuenta);
        when(cuentaRepo.actualizar(eq(1L), any(Cuenta.class))).thenAnswer(inv -> inv.getArgument(1));
        when(movRepo.guardar(any(Movimiento.class))).thenAnswer(inv -> inv.getArgument(0));

        Movimiento mov = service.registrarMovimiento(1L, "DEPOSITO", 50.0);

        assertEquals(150.0, mov.getSaldoPosterior());
        verify(cuentaRepo).actualizar(eq(1L), any(Cuenta.class));
        verify(movRepo).guardar(any(Movimiento.class));
    }

    @Test
    void registrarMovimiento_falla_cuentaNoEncontrada() {
        when(cuentaRepo.buscarPorId(99L)).thenReturn(null);

        CuentaNoEncontradaException ex = assertThrows(
                CuentaNoEncontradaException.class,
                () -> service.registrarMovimiento(99L, "DEPOSITO", 50.0)
        );

        assertTrue(ex.getMessage().contains("Cuenta no encontrada: 99"));

        verify(cuentaRepo, never()).actualizar(anyLong(), any(Cuenta.class));
        verify(movRepo, never()).guardar(any(Movimiento.class));
    }

}

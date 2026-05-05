package cl.duoc.evaluacion.ordenes_mascotas.controller;

import cl.duoc.evaluacion.ordenes_mascotas.model.ItemCompra;
import cl.duoc.evaluacion.ordenes_mascotas.model.OrdenCompra;
import cl.duoc.evaluacion.ordenes_mascotas.service.OrdenCompraService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenCompraControllerTest {

    @Mock
    private OrdenCompraService ordenCompraService;

    @InjectMocks
    private OrdenCompraController ordenCompraController;

    private OrdenCompra orden1;
    private OrdenCompra orden2;

    // Equivalente a @Before de JUnit 4
    @BeforeEach
    void setUp() {
        ItemCompra item = new ItemCompra(1L, "COD001", "Alimento perro", 15000.0, null);
        orden1 = new OrdenCompra(1, "Juan Pérez", "PENDIENTE", Arrays.asList(item));
        orden2 = new OrdenCompra(2, "María González", "COMPLETADA", null);
        item.setOrden(orden1);
    }

    // Equivalente a @After de JUnit 4
    @AfterEach
    void tearDown() {
        orden1 = null;
        orden2 = null;
    }

    @Test
    void testGetOrdenes_retornaListaCompleta() {
        when(ordenCompraService.getTodas()).thenReturn(Arrays.asList(orden1, orden2));

        List<OrdenCompra> resultado = ordenCompraController.getOrdenes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreCliente());
        verify(ordenCompraService, times(1)).getTodas();
    }

    @Test
    void testGetOrdenById_existente_retornaOk() {
        when(ordenCompraService.getPorId(1)).thenReturn(Optional.of(orden1));

        ResponseEntity<OrdenCompra> respuesta = ordenCompraController.getOrdenById(1);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Juan Pérez", respuesta.getBody().getNombreCliente());
        verify(ordenCompraService, times(1)).getPorId(1);
    }

    @Test
    void testGetOrdenById_noExistente_retornaNotFound() {
        when(ordenCompraService.getPorId(99)).thenReturn(Optional.empty());

        ResponseEntity<OrdenCompra> respuesta = ordenCompraController.getOrdenById(99);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testGetOrdenesByEstado_retornaListaFiltrada() {
        when(ordenCompraService.getPorEstado("PENDIENTE")).thenReturn(Arrays.asList(orden1));

        List<OrdenCompra> resultado = ordenCompraController.getOrdenesByEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
        verify(ordenCompraService, times(1)).getPorEstado("PENDIENTE");
    }

    @Test
    void testCrearOrden_retornaOrdenGuardada() {
        OrdenCompra nueva = new OrdenCompra(null, "Ana Torres", "PENDIENTE", null);
        OrdenCompra guardada = new OrdenCompra(3, "Ana Torres", "PENDIENTE", null);
        when(ordenCompraService.guardar(any(OrdenCompra.class))).thenReturn(guardada);

        OrdenCompra resultado = ordenCompraController.crearOrden(nueva);

        assertNotNull(resultado);
        assertEquals(3, resultado.getIdOrden());
        assertEquals("Ana Torres", resultado.getNombreCliente());
        verify(ordenCompraService, times(1)).guardar(nueva);
    }

    @Test
    void testActualizarOrden_existente_retornaOk() {
        OrdenCompra actualizada = new OrdenCompra(1, "Juan Pérez", "COMPLETADA", null);
        when(ordenCompraService.getPorId(1)).thenReturn(Optional.of(orden1));
        when(ordenCompraService.guardar(any(OrdenCompra.class))).thenReturn(actualizada);

        ResponseEntity<OrdenCompra> respuesta = ordenCompraController.actualizarOrden(1, actualizada);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("COMPLETADA", respuesta.getBody().getEstado());
    }

    @Test
    void testActualizarOrden_noExistente_retornaNotFound() {
        OrdenCompra actualizada = new OrdenCompra(99, "Nadie", "PENDIENTE", null);
        when(ordenCompraService.getPorId(99)).thenReturn(Optional.empty());

        ResponseEntity<OrdenCompra> respuesta = ordenCompraController.actualizarOrden(99, actualizada);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testEliminarOrden_existente_retornaNoContent() {
        when(ordenCompraService.getPorId(1)).thenReturn(Optional.of(orden1));
        doNothing().when(ordenCompraService).eliminar(1);

        ResponseEntity<Void> respuesta = ordenCompraController.eliminarOrden(1);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(ordenCompraService, times(1)).eliminar(1);
    }

    @Test
    void testEliminarOrden_noExistente_retornaNotFound() {
        when(ordenCompraService.getPorId(99)).thenReturn(Optional.empty());

        ResponseEntity<Void> respuesta = ordenCompraController.eliminarOrden(99);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        verify(ordenCompraService, never()).eliminar(99);
    }
}

package cl.duoc.evaluacion.ordenes_mascotas.service;

import cl.duoc.evaluacion.ordenes_mascotas.model.ItemCompra;
import cl.duoc.evaluacion.ordenes_mascotas.model.OrdenCompra;
import cl.duoc.evaluacion.ordenes_mascotas.repository.OrdenCompraRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenCompraServiceTest {

    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    @InjectMocks
    private OrdenCompraService ordenCompraService;

    private OrdenCompra orden1;
    private OrdenCompra orden2;
    private ItemCompra item1;
    private ItemCompra item2;

    // Equivalente a @Before de JUnit 4 — se ejecuta antes de cada prueba
    @BeforeEach
    void setUp() {
        item1 = new ItemCompra(1L, "COD001", "Alimento perro", 15000.0, null);
        item2 = new ItemCompra(2L, "COD002", "Collar antipulgas", 8500.0, null);

        orden1 = new OrdenCompra(1, "Juan Pérez", "PENDIENTE", Arrays.asList(item1, item2));
        orden2 = new OrdenCompra(2, "María González", "COMPLETADA", null);

        item1.setOrden(orden1);
        item2.setOrden(orden1);
    }

    // Equivalente a @After de JUnit 4 — se ejecuta después de cada prueba
    @AfterEach
    void tearDown() {
        orden1 = null;
        orden2 = null;
    }

    @Test
    void testGetTodas_retornaTodasLasOrdenes() {
        when(ordenCompraRepository.findAll()).thenReturn(Arrays.asList(orden1, orden2));

        List<OrdenCompra> resultado = ordenCompraService.getTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreCliente());
        verify(ordenCompraRepository, times(1)).findAll();
    }

    @Test
    void testGetPorId_existente_retornaOrden() {
        when(ordenCompraRepository.findById(1)).thenReturn(Optional.of(orden1));

        Optional<OrdenCompra> resultado = ordenCompraService.getPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombreCliente());
        assertEquals("PENDIENTE", resultado.get().getEstado());
        verify(ordenCompraRepository, times(1)).findById(1);
    }

    @Test
    void testGetPorId_noExistente_retornaVacio() {
        when(ordenCompraRepository.findById(99)).thenReturn(Optional.empty());

        Optional<OrdenCompra> resultado = ordenCompraService.getPorId(99);

        assertFalse(resultado.isPresent());
        verify(ordenCompraRepository, times(1)).findById(99);
    }

    @Test
    void testGetPorEstado_retornaOrdenesConEseEstado() {
        when(ordenCompraRepository.findByEstadoIgnoreCase("PENDIENTE"))
                .thenReturn(Arrays.asList(orden1));

        List<OrdenCompra> resultado = ordenCompraService.getPorEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
        verify(ordenCompraRepository, times(1)).findByEstadoIgnoreCase("PENDIENTE");
    }

    @Test
    void testGuardar_asignaRelacionBidireccionalYPersiste() {
        ItemCompra nuevoItem = new ItemCompra(null, "COD003", "Cama mascotas", 25000.0, null);
        OrdenCompra nuevaOrden = new OrdenCompra(null, "Pedro Ramos", "PENDIENTE",
                Arrays.asList(nuevoItem));

        when(ordenCompraRepository.save(nuevaOrden)).thenReturn(nuevaOrden);

        OrdenCompra resultado = ordenCompraService.guardar(nuevaOrden);

        assertNotNull(resultado);
        assertNotNull(resultado.getItems().get(0).getOrden());
        assertEquals(nuevaOrden, resultado.getItems().get(0).getOrden());
        verify(ordenCompraRepository, times(1)).save(nuevaOrden);
    }

    @Test
    void testEliminar_llamaDeleteByIdUnaVez() {
        doNothing().when(ordenCompraRepository).deleteById(1);

        ordenCompraService.eliminar(1);

        verify(ordenCompraRepository, times(1)).deleteById(1);
    }

    @Test
    void testCalcularTotal_retornaSumaDePreciosDeItems() {
        when(ordenCompraRepository.findById(1)).thenReturn(Optional.of(orden1));

        Double total = ordenCompraService.calcularTotal(1);

        // item1=15000 + item2=8500 = 23500
        assertEquals(23500.0, total);
    }

    @Test
    void testCalcularTotal_ordenNoExistente_retornaCero() {
        when(ordenCompraRepository.findById(99)).thenReturn(Optional.empty());

        Double total = ordenCompraService.calcularTotal(99);

        assertEquals(0.0, total);
    }
}

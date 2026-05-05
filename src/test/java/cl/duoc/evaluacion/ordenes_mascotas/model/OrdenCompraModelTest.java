package cl.duoc.evaluacion.ordenes_mascotas.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrdenCompraModelTest {

    private OrdenCompra orden;
    private ItemCompra item1;
    private ItemCompra item2;

    // Equivalente a @Before de JUnit 4
    @BeforeEach
    void setUp() {
        item1 = new ItemCompra(1L, "COD001", "Alimento perro", 15000.0, null);
        item2 = new ItemCompra(2L, "COD002", "Collar antipulgas", 8500.0, null);
        orden = new OrdenCompra(1, "Juan Pérez", "PENDIENTE", null);
    }

    // Equivalente a @After de JUnit 4
    @AfterEach
    void tearDown() {
        orden = null;
    }

    @Test
    void testGetCostoTotal_conVariosItems_retornaSumaCorrecta() {
        orden.setItems(Arrays.asList(item1, item2));

        Double total = orden.getCostoTotal();

        // 15000 + 8500 = 23500
        assertEquals(23500.0, total);
    }

    @Test
    void testGetCostoTotal_sinItems_retornaCero() {
        // items es null por defecto en setUp
        Double total = orden.getCostoTotal();

        assertEquals(0.0, total);
    }

    @Test
    void testGetCostoTotal_listaVacia_retornaCero() {
        orden.setItems(Collections.emptyList());

        Double total = orden.getCostoTotal();

        assertEquals(0.0, total);
    }

    @Test
    void testOrdenCompra_atributosAsignadosCorrectamente() {
        assertEquals(1, orden.getIdOrden());
        assertEquals("Juan Pérez", orden.getNombreCliente());
        assertEquals("PENDIENTE", orden.getEstado());
    }

    @Test
    void testItemCompra_precioAsignadoCorrectamente() {
        assertEquals(15000.0, item1.getPrecio());
        assertEquals("COD001", item1.getCodigoProducto());
        assertEquals("Alimento perro", item1.getNombreProducto());
    }
}

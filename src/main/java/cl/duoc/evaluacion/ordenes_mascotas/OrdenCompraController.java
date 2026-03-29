package cl.duoc.evaluacion.ordenes_mascotas;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class OrdenCompraController {
    private List<OrdenCompra> ordenes = new ArrayList<>();

    public OrdenCompraController() {
      // lista en memoria con los 8 registros
        ordenes.add(new OrdenCompra(1, "Andrés Tapia", "Entregado",
                Arrays.asList(new Producto("P01", "Alimento Perro"), new Producto("P02", "Collar")),
                Map.of(
                    "Alimento Perro", Arrays.asList(15000.0, 15000.0),
                    "Collar", Arrays.asList(5000.0)
                )));
        ordenes.add(new OrdenCompra(2, "María Fernández", "En Proceso",
                Arrays.asList(new Producto("G01", "Arena Gato")),
                Map.of(
                    "Arena Gato", Arrays.asList(8000.0, 8000.0)
                )));
        ordenes.add(new OrdenCompra(3, "Camila Rojas", "Entregado",
                Arrays.asList(new Producto("P03", "Cama Perro grande"), new Producto("P04", "Juguete Hueso")),
                Map.of(
                    "Cama Perro grande", Arrays.asList(25000.0),
                    "Juguete Hueso", Arrays.asList(4500.0, 4500.0)
                )));
        ordenes.add(new OrdenCompra(4, "Felipe Morales", "Cancelado",
                Arrays.asList(new Producto("P01", "Alimento Perro")),
                Map.of(
                    "Alimento Perro", Arrays.asList(15000.0)
                )));
        ordenes.add(new OrdenCompra(5, "Valentina Soto", "En Proceso",
                Arrays.asList(new Producto("A01", "Alimento Pez"), new Producto("A02", "Filtro Acuario")),
                Map.of(
                    "Alimento Pez", Arrays.asList(3000.0, 3000.0, 3000.0),
                    "Filtro Acuario", Arrays.asList(12000.0)
                )));
        ordenes.add(new OrdenCompra(6, "Diego Castro", "Entregado",
                Arrays.asList(new Producto("R01", "Rueda Hamster")),
                Map.of(
                    "Rueda Hamster", Arrays.asList(5000.0)
                )));
        ordenes.add(new OrdenCompra(7, "Javiera Pinto", "En Proceso",
                Arrays.asList(new Producto("P05", "Correa Retráctil")),
                Map.of(
                    "Correa Retráctil", Arrays.asList(10000.0)
                )));
        ordenes.add(new OrdenCompra(8, "Sebastián Reyes", "Cancelado",
                Arrays.asList(new Producto("G02", "Rascador Gato")),
                Map.of(
                    "Rascador Gato", Arrays.asList(35000.0)
                )));
    }

    @GetMapping("/ordenes")
    public List<OrdenCompra> getOrdenes() {
        return ordenes;
    }

    @GetMapping("/ordenes/{id}")
    public OrdenCompra getOrdenById(@PathVariable int id) {
        for (OrdenCompra orden : ordenes) {
            if (orden.getIdOrden() == id) {
                return orden;
            }
        }
        return null;
    }

    // Filtra por el estado de la compra
    @GetMapping("/ordenes/estado/{estado}")
    public List<OrdenCompra> getOrdenesByEstado(@PathVariable String estado) {
        List<OrdenCompra> ordenesFiltradas = new ArrayList<>();
        for (OrdenCompra orden : ordenes) {
            if (orden.getEstado().equalsIgnoreCase(estado)) {
                ordenesFiltradas.add(orden);
            }
        }
        return ordenesFiltradas;
    }

    // Total de la orden
    @GetMapping(path = "/ordenes/{idOrden}/total")
    public Double calcularCostoTotalOrden(@PathVariable("idOrden") Integer idOrden) {
        Double total = 0.0;
        for (OrdenCompra orden : ordenes) {
            if (orden.getIdOrden() == idOrden) {
                // Aquí recorremos todos los valores (todas las listas de precios) dentro del mapa
                for (List<Double> costos : orden.getCostosPorProducto().values()) {
                    for (Double costo : costos) {
                        total += costo;
                    }
                }
                break;
            }
        }
        return total;
    }
}
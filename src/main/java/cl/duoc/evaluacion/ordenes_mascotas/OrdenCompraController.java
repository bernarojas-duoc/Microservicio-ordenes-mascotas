package cl.duoc.evaluacion.ordenes_mascotas;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrdenCompraController {
    private List<OrdenCompra> ordenes = new ArrayList<>();

    public OrdenCompraController() {
        ordenes.add(new OrdenCompra(1, "Andrés Tapia", "Entregado",
                Arrays.asList(new Producto("P01", "Alimento Perro"), new Producto("P02", "Collar")),
                llenarListaCostos("Alimento Perro", 15000.0, 2000.0))); // Precio base y un impuesto/envío
                
        ordenes.add(new OrdenCompra(2, "María Fernández", "En Proceso",
                Arrays.asList(new Producto("G01", "Arena Gato")),
                llenarListaCostos("Arena Gato", 8000.0, 1500.0)));
                
        ordenes.add(new OrdenCompra(3, "Camila Rojas", "Entregado",
                Arrays.asList(new Producto("P03", "Cama Perro grande"), new Producto("P04", "Juguete Hueso")),
                llenarListaCostos("Cama Perro grande", 25000.0, 0.0)));
                
        ordenes.add(new OrdenCompra(4, "Felipe Morales", "Cancelado",
                Arrays.asList(new Producto("P01", "Alimento Perro")),
                llenarListaCostos("Alimento Perro", 15000.0, 2000.0)));
                
        ordenes.add(new OrdenCompra(5, "Valentina Soto", "En Proceso",
                Arrays.asList(new Producto("A01", "Alimento Pez"), new Producto("A02", "Filtro Acuario")),
                llenarListaCostos("Filtro Acuario", 12000.0, 3000.0)));
                
        ordenes.add(new OrdenCompra(6, "Diego Castro", "Entregado",
                Arrays.asList(new Producto("R01", "Rueda Hamster")),
                llenarListaCostos("Rueda Hamster", 5000.0, 1000.0)));
                
        ordenes.add(new OrdenCompra(7, "Javiera Pinto", "En Proceso",
                Arrays.asList(new Producto("P05", "Correa Retráctil")),
                llenarListaCostos("Correa Retráctil", 10000.0, 1500.0)));
                
        ordenes.add(new OrdenCompra(8, "Sebastián Reyes", "Cancelado",
                Arrays.asList(new Producto("G02", "Rascador Gato")),
                llenarListaCostos("Rascador Gato", 35000.0, 5000.0)));
    }

    private Map<String, List<Double>> llenarListaCostos(String producto, Double costoBase, Double costoExtra) {
        Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();
        List<Double> costos = Arrays.asList(costoBase, costoExtra);
        retorno.put(producto, costos);
        return retorno;
    }

    // Endpoint 1: Retorna todas las órdenes
    @GetMapping("/ordenes")
    public List<OrdenCompra> getOrdenes() {
        return ordenes;
    }

    // Endpoint 2: Retorna una orden por su ID
    @GetMapping("/ordenes/{id}")
    public OrdenCompra getOrdenById(@PathVariable int id) {
        for (OrdenCompra orden : ordenes) {
            if (orden.getIdOrden() == id) {
                return orden;
            }
        }
        return null; // Retorna nulo si no lo encuentra
    }

    // Endpoint 3: Consultar el estado de las órdenes
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

    // Endpoint 4: Calcula el costo total de un producto en una orden (Suma costo base + extra)
    @GetMapping(path = "/ordenes/{idOrden}/total/{nombreProducto}")
    public Double calcularCostoTotal(@PathVariable("idOrden") Integer idOrden,
                                     @PathVariable("nombreProducto") String nombreProducto) {
        Double total = 0.0;
        for (OrdenCompra orden : ordenes) {
            if (orden.getIdOrden() == idOrden) {
                if(orden.getCostosPorProducto().containsKey(nombreProducto)) {
                    List<Double> costos = orden.getCostosPorProducto().get(nombreProducto);
                    for (Double costo : costos) {
                        total = total + costo;
                    }
                }
            }
        }
        return total;
    }
}

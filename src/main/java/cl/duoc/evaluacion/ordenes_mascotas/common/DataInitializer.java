/*
 * package cl.duoc.evaluacion.ordenes_mascotas.common;
 * 
 * import cl.duoc.evaluacion.ordenes_mascotas.model.ItemCompra;
 * import cl.duoc.evaluacion.ordenes_mascotas.model.OrdenCompra;
 * import cl.duoc.evaluacion.ordenes_mascotas.model.Producto;
 * import cl.duoc.evaluacion.ordenes_mascotas.repository.OrdenCompraRepository;
 * import cl.duoc.evaluacion.ordenes_mascotas.repository.ProductoRepository;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.boot.CommandLineRunner;
 * import org.springframework.stereotype.Component;
 * 
 * import java.util.Arrays;
 * 
 * @Component
 * public class DataInitializer implements CommandLineRunner {
 * 
 * @Autowired
 * private ProductoRepository productoRepository;
 * 
 * @Autowired
 * private OrdenCompraRepository ordenCompraRepository;
 * 
 * @Override
 * public void run(String... args) throws Exception {
 * // 1. Insertar Productos (Tabla 1)
 * Producto p1 = new Producto("P01", "Alimento Perro Premium", 15000.0);
 * Producto p2 = new Producto("P02", "Collar Antiparasitario", 5000.0);
 * Producto p3 = new Producto("G01", "Arena Gata Biodegradable", 8000.0);
 * productoRepository.saveAll(Arrays.asList(p1, p2, p3));
 * 
 * // 2. Insertar Órdenes (Tabla 2) con sus Items (Tabla 3)
 * // Orden 1
 * OrdenCompra o1 = new OrdenCompra();
 * o1.setNombreCliente("Andrés Tapia");
 * o1.setEstado("ENTREGADO");
 * 
 * ItemCompra i1 = new ItemCompra(null, "P01", "Alimento Perro Premium",
 * 15000.0, o1);
 * ItemCompra i2 = new ItemCompra(null, "P02", "Collar Antiparasitario", 5000.0,
 * o1);
 * o1.setItems(Arrays.asList(i1, i2));
 * 
 * // Orden 2
 * OrdenCompra o2 = new OrdenCompra();
 * o2.setNombreCliente("María Fernández");
 * o2.setEstado("EN PROCESO");
 * ItemCompra i3 = new ItemCompra(null, "G01", "Arena Gata Biodegradable",
 * 8000.0, o2);
 * o2.setItems(Arrays.asList(i3));
 * 
 * // Orden 3
 * OrdenCompra o3 = new OrdenCompra();
 * o3.setNombreCliente("Camila Rojas");
 * o3.setEstado("PENDIENTE");
 * ItemCompra i4 = new ItemCompra(null, "P01", "Alimento Perro Premium",
 * 15000.0, o3);
 * o3.setItems(Arrays.asList(i4));
 * 
 * ordenCompraRepository.saveAll(Arrays.asList(o1, o2, o3));
 * 
 * System.out.println("Base de datos inicializada con éxito.");
 * }
 * }
 */
package cl.duoc.evaluacion.ordenes_mascotas;

import java.util.List;
import java.util.Map;

public class OrdenCompra {
  private int idOrden;
  private String nombreCliente;
  private String estado;
  private List<Producto> productos;
  private Map<String, List<Double>> costosPorProducto;

  public OrdenCompra(int idOrden, String nombreCliente, String estado, 
                      List<Producto> productos, Map<String, List<Double>> costosPorProducto) {
    this.idOrden = idOrden;
    this.nombreCliente = nombreCliente;
    this.estado = estado;
    this.productos = productos;
    this.costosPorProducto = costosPorProducto;
  }

  // Getters
  public int getIdOrden() { return idOrden; }
  public String getNombreCliente() { return nombreCliente; }
  public String getEstado() { return estado; }
  public List<Producto> getProductos() { return productos; }
  public Map<String, List<Double>> getCostosPorProducto() { return costosPorProducto; }
}

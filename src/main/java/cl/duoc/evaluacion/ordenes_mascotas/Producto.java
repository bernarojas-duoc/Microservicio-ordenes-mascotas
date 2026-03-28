package cl.duoc.evaluacion.ordenes_mascotas;

public class Producto {
  private String codigo;
  private String nombre;

  public Producto(String codigo, String nombre) {
      this.codigo = codigo;
      this.nombre = nombre;
  }

  public String getCodigo() {
      return codigo;
  }

  public String getNombre() {
      return nombre;
  }
}

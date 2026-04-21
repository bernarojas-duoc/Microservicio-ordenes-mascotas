package cl.duoc.evaluacion.ordenes_mascotas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idOrden;

  @NotBlank(message = "El nombre del cliente es obligatorio")
  private String nombreCliente;

  @NotBlank(message = "El estado es obligatorio")
  private String estado;

  @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<ItemCompra> items;

  // Métodos de utilidad
  public Double getCostoTotal() {
    if (items == null)
      return 0.0;
    return items.stream()
        .mapToDouble(ItemCompra::getPrecio)
        .sum();
  }
}

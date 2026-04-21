package cl.duoc.evaluacion.ordenes_mascotas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código del producto es obligatorio")
    private String codigoProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    @JsonBackReference
    private OrdenCompra orden;
}

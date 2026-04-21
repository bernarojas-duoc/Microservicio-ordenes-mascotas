package cl.duoc.evaluacion.ordenes_mascotas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "el nombre es obligatorio")
    private String nombre;

    private Double precioBase;
}

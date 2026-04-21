package cl.duoc.evaluacion.ordenes_mascotas.repository;

import cl.duoc.evaluacion.ordenes_mascotas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
}

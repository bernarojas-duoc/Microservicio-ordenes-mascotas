package cl.duoc.evaluacion.ordenes_mascotas.service;

import cl.duoc.evaluacion.ordenes_mascotas.model.OrdenCompra;
import cl.duoc.evaluacion.ordenes_mascotas.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    public List<OrdenCompra> getTodas() {
        return ordenCompraRepository.findAll();
    }

    public Optional<OrdenCompra> getPorId(int id) {
        return ordenCompraRepository.findById(id);
    }

    public List<OrdenCompra> getPorEstado(String estado) {
        return ordenCompraRepository.findByEstadoIgnoreCase(estado);
    }

    public OrdenCompra guardar(OrdenCompra orden) {
        // Asegurar la relación bidireccional para que JPA persista los items correctamente
        if (orden.getItems() != null) {
            orden.getItems().forEach(item -> item.setOrden(orden));
        }
        return ordenCompraRepository.save(orden);
    }

    public void eliminar(int id) {
        ordenCompraRepository.deleteById(id);
    }

    public Double calcularTotal(int id) {
        return ordenCompraRepository.findById(id)
                .map(OrdenCompra::getCostoTotal)
                .orElse(0.0);
    }
}

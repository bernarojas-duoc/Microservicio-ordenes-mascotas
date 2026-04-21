package cl.duoc.evaluacion.ordenes_mascotas.controller;

import cl.duoc.evaluacion.ordenes_mascotas.model.OrdenCompra;
import cl.duoc.evaluacion.ordenes_mascotas.service.OrdenCompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping
    public List<OrdenCompra> getOrdenes() {
        return ordenCompraService.getTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> getOrdenById(@PathVariable int id) {
        return ordenCompraService.getPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<OrdenCompra> getOrdenesByEstado(@PathVariable String estado) {
        return ordenCompraService.getPorEstado(estado);
    }

    @GetMapping("/{id}/total")
    public Double calcularCostoTotalOrden(@PathVariable int id) {
        return ordenCompraService.calcularTotal(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdenCompra crearOrden(@Valid @RequestBody OrdenCompra orden) {
        return ordenCompraService.guardar(orden);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompra> actualizarOrden(@PathVariable int id, @Valid @RequestBody OrdenCompra orden) {
        return ordenCompraService.getPorId(id)
                .map(existing -> {
                    orden.setIdOrden(id);
                    return ResponseEntity.ok(ordenCompraService.guardar(orden));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable int id) {
        if (ordenCompraService.getPorId(id).isPresent()) {
            ordenCompraService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
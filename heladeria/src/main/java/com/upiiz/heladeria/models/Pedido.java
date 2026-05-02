package com.upiiz.heladeria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long pedidoId;

    @NotNull(message = "Selecciona un sabor")
    @Column(name = "sabor_id", nullable = false)
    private Long saborId;

    @NotNull(message = "La fecha y hora son obligatorias")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull(message = "Selecciona un estado")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    public enum Estado {
        pendiente, entregado, cancelado
    }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public Long getSaborId() { return saborId; }
    public void setSaborId(Long saborId) { this.saborId = saborId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}

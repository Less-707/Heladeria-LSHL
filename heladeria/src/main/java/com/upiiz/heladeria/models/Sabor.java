package com.upiiz.heladeria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sabores")
public class Sabor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sabor_id")
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;

    @Column(name = "fecha_creacion")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;

    public Sabor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}

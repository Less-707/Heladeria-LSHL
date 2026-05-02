package com.upiiz.heladeria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "produccion")
public class Produccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produccion_id")
    private Long produccionId;

    @NotNull(message = "Selecciona un sabor")
    @Column(name = "sabor_id", nullable = false)
    private Long saborId;

    @NotNull(message = "La fecha de producción es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_produccion", nullable = false)
    private LocalDate fechaProduccion;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad_kg", nullable = false, precision = 8, scale = 2)
    private BigDecimal cantidadKg;

    @NotBlank(message = "El responsable es obligatorio")
    @Column(nullable = false, length = 100)
    private String responsable;

    @Column(precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(columnDefinition = "TEXT")
    private String notas;

    public Long getProduccionId() { return produccionId; }
    public void setProduccionId(Long produccionId) { this.produccionId = produccionId; }

    public Long getSaborId() { return saborId; }
    public void setSaborId(Long saborId) { this.saborId = saborId; }

    public LocalDate getFechaProduccion() { return fechaProduccion; }
    public void setFechaProduccion(LocalDate fechaProduccion) { this.fechaProduccion = fechaProduccion; }

    public BigDecimal getCantidadKg() { return cantidadKg; }
    public void setCantidadKg(BigDecimal cantidadKg) { this.cantidadKg = cantidadKg; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}

package com.upiiz.heladeria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingresa un correo válido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false, length = 255)
    private String password;

    public Usuario() {}

    public Usuario(String nombreCompleto, String email, String password) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

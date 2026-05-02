package com.upiiz.heladeria.services;

import com.upiiz.heladeria.models.Usuario;
import com.upiiz.heladeria.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> existeUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (existeUsuario.isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public Usuario autenticar(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            return usuario.get();
        }
        return null;
    }
}

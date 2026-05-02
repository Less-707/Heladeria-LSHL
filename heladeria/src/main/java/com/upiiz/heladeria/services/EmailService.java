package com.upiiz.heladeria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarContrasena(String destinatario, String nombreCompleto, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(destinatario);
        message.setSubject("Recuperación de contraseña - Heladería");
        message.setText(
            "Hola " + nombreCompleto + ",\n\n" +
            "Tu contraseña de acceso es: " + password + "\n\n" +
            "Si no solicitaste esto, ignora este correo.\n\n" +
            "Heladería"
        );
        mailSender.send(message);
    }
}

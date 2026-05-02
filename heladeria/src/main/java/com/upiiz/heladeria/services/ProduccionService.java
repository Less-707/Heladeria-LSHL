package com.upiiz.heladeria.services;

import com.upiiz.heladeria.models.Produccion;
import com.upiiz.heladeria.repositories.ProduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProduccionService {

    @Autowired
    private ProduccionRepository produccionRepository;

    public List<Produccion> listarTodos() {
        return produccionRepository.findAll();
    }

    public Produccion guardar(Produccion produccion) {
        return produccionRepository.save(produccion);
    }

    public Produccion buscarPorId(Long id) {
        return produccionRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        produccionRepository.deleteById(id);
    }

    public long contarTodos() {
        return produccionRepository.count();
    }

    public List<Long> contarPorMes() {
        List<Long> meses = new ArrayList<>(Collections.nCopies(12, 0L));
        for (Produccion p : produccionRepository.findAll()) {
            if (p.getFechaProduccion() != null) {
                int mes = p.getFechaProduccion().getMonthValue() - 1;
                meses.set(mes, meses.get(mes) + 1);
            }
        }
        return meses;
    }

    public Map<String, Long> contarPorResponsable() {
        Map<String, Long> raw = new LinkedHashMap<>();
        for (Produccion p : produccionRepository.findAll()) {
            String resp = (p.getResponsable() != null && !p.getResponsable().isBlank()) ? p.getResponsable() : "Desconocido";
            raw.merge(resp, 1L, Long::sum);
        }
        return raw.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }
}

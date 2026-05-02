package com.upiiz.heladeria.services;

import com.upiiz.heladeria.models.Sabor;
import com.upiiz.heladeria.repositories.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaborServicio {

    @Autowired
    private SaborRepository saborRepository;

    public List<Sabor> listarTodos() {
        return saborRepository.findAll();
    }

    public long contarTodos() {
        return saborRepository.count();
    }

    public void guardar(Sabor sabor) {
        saborRepository.save(sabor);
    }

    public Sabor buscarPorId(Long id) {
        return saborRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        saborRepository.deleteById(id);
    }

    public Map<String, Long> contarPorTipo() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Sabor s : saborRepository.findAll()) {
            String tipo = (s.getTipo() != null && !s.getTipo().isBlank()) ? s.getTipo() : "Otro";
            result.merge(tipo, 1L, Long::sum);
        }
        return result;
    }
}

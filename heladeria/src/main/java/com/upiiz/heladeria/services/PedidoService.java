package com.upiiz.heladeria.services;

import com.upiiz.heladeria.models.Pedido;
import com.upiiz.heladeria.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public long contarTodos() {
        return pedidoRepository.count();
    }

    public long contarPorEstado(Pedido.Estado estado) {
        return pedidoRepository.countByEstado(estado);
    }

    public List<Long> contarPorMes() {
        List<Long> meses = new ArrayList<>(Collections.nCopies(12, 0L));
        for (Pedido p : pedidoRepository.findAll()) {
            if (p.getFechaHora() != null) {
                int mes = p.getFechaHora().getMonthValue() - 1;
                meses.set(mes, meses.get(mes) + 1);
            }
        }
        return meses;
    }
}

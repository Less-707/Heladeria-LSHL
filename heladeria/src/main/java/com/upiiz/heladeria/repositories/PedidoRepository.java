package com.upiiz.heladeria.repositories;

import com.upiiz.heladeria.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    long countByEstado(Pedido.Estado estado);
}

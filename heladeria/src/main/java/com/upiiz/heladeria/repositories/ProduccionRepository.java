package com.upiiz.heladeria.repositories;

import com.upiiz.heladeria.models.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long> {
}

package com.eclub.ms_ventas.repositories;

import com.eclub.ms_ventas.models.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VentasRepository extends JpaRepository<Ventas,Long> {
}

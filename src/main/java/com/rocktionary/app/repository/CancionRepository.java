package com.rocktionary.app.repository;

import com.rocktionary.app.domain.Cancion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Cancion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {

    List<Cancion> findByNombreContaining(String cancionNombre);

}

package com.rocktionary.app.repository;

import com.rocktionary.app.domain.PuntuacionCancion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PuntuacionCancion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntuacionCancionRepository extends JpaRepository<PuntuacionCancion, Long> {

    @Query("select puntuacion_cancion from PuntuacionCancion puntuacion_cancion where puntuacion_cancion.user.login = ?#{principal.username}")
    List<PuntuacionCancion> findByUserIsCurrentUser();

}

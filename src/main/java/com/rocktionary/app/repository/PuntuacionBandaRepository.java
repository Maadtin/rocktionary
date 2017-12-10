package com.rocktionary.app.repository;

import com.rocktionary.app.domain.PuntuacionBanda;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PuntuacionBanda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntuacionBandaRepository extends JpaRepository<PuntuacionBanda, Long> {

    @Query("select puntuacion_banda from PuntuacionBanda puntuacion_banda where puntuacion_banda.user.login = ?#{principal.username}")
    List<PuntuacionBanda> findByUserIsCurrentUser();

}

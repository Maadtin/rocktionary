package com.rocktionary.app.repository;

import com.rocktionary.app.domain.PuntuacionAlbum;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PuntuacionAlbum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntuacionAlbumRepository extends JpaRepository<PuntuacionAlbum, Long> {

    @Query("select puntuacion_album from PuntuacionAlbum puntuacion_album where puntuacion_album.user.login = ?#{principal.username}")
    List<PuntuacionAlbum> findByUserIsCurrentUser();

}

package com.rocktionary.app.repository;

import com.rocktionary.app.domain.ComentarAlbum;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ComentarAlbum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentarAlbumRepository extends JpaRepository<ComentarAlbum, Long> {

    @Query("select comentar_album from ComentarAlbum comentar_album where comentar_album.user.login = ?#{principal.username}")
    List<ComentarAlbum> findByUserIsCurrentUser();

}

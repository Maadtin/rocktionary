package com.rocktionary.app.repository;

import com.rocktionary.app.domain.Album;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Album entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByNombreContaining(String albumNombre);

}

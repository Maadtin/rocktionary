package com.rocktionary.app.repository;

import com.rocktionary.app.domain.Discografica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Discografica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscograficaRepository extends JpaRepository<Discografica, Long> {

}

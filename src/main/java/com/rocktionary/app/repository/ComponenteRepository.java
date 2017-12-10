package com.rocktionary.app.repository;

import com.rocktionary.app.domain.Componente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Componente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long> {

}

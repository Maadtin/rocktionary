package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.PuntuacionCancion;

import com.rocktionary.app.repository.PuntuacionCancionRepository;
import com.rocktionary.app.web.rest.errors.BadRequestAlertException;
import com.rocktionary.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PuntuacionCancion.
 */
@RestController
@RequestMapping("/api")
public class PuntuacionCancionResource {

    private final Logger log = LoggerFactory.getLogger(PuntuacionCancionResource.class);

    private static final String ENTITY_NAME = "puntuacionCancion";

    private final PuntuacionCancionRepository puntuacionCancionRepository;

    public PuntuacionCancionResource(PuntuacionCancionRepository puntuacionCancionRepository) {
        this.puntuacionCancionRepository = puntuacionCancionRepository;
    }

    /**
     * POST  /puntuacion-cancions : Create a new puntuacionCancion.
     *
     * @param puntuacionCancion the puntuacionCancion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new puntuacionCancion, or with status 400 (Bad Request) if the puntuacionCancion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/puntuacion-cancions")
    @Timed
    public ResponseEntity<PuntuacionCancion> createPuntuacionCancion(@RequestBody PuntuacionCancion puntuacionCancion) throws URISyntaxException {
        log.debug("REST request to save PuntuacionCancion : {}", puntuacionCancion);
        if (puntuacionCancion.getId() != null) {
            throw new BadRequestAlertException("A new puntuacionCancion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuntuacionCancion result = puntuacionCancionRepository.save(puntuacionCancion);
        return ResponseEntity.created(new URI("/api/puntuacion-cancions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /puntuacion-cancions : Updates an existing puntuacionCancion.
     *
     * @param puntuacionCancion the puntuacionCancion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated puntuacionCancion,
     * or with status 400 (Bad Request) if the puntuacionCancion is not valid,
     * or with status 500 (Internal Server Error) if the puntuacionCancion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/puntuacion-cancions")
    @Timed
    public ResponseEntity<PuntuacionCancion> updatePuntuacionCancion(@RequestBody PuntuacionCancion puntuacionCancion) throws URISyntaxException {
        log.debug("REST request to update PuntuacionCancion : {}", puntuacionCancion);
        if (puntuacionCancion.getId() == null) {
            return createPuntuacionCancion(puntuacionCancion);
        }
        PuntuacionCancion result = puntuacionCancionRepository.save(puntuacionCancion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, puntuacionCancion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /puntuacion-cancions : get all the puntuacionCancions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of puntuacionCancions in body
     */
    @GetMapping("/puntuacion-cancions")
    @Timed
    public List<PuntuacionCancion> getAllPuntuacionCancions() {
        log.debug("REST request to get all PuntuacionCancions");
        return puntuacionCancionRepository.findAll();
        }

    /**
     * GET  /puntuacion-cancions/:id : get the "id" puntuacionCancion.
     *
     * @param id the id of the puntuacionCancion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the puntuacionCancion, or with status 404 (Not Found)
     */
    @GetMapping("/puntuacion-cancions/{id}")
    @Timed
    public ResponseEntity<PuntuacionCancion> getPuntuacionCancion(@PathVariable Long id) {
        log.debug("REST request to get PuntuacionCancion : {}", id);
        PuntuacionCancion puntuacionCancion = puntuacionCancionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(puntuacionCancion));
    }

    /**
     * DELETE  /puntuacion-cancions/:id : delete the "id" puntuacionCancion.
     *
     * @param id the id of the puntuacionCancion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/puntuacion-cancions/{id}")
    @Timed
    public ResponseEntity<Void> deletePuntuacionCancion(@PathVariable Long id) {
        log.debug("REST request to delete PuntuacionCancion : {}", id);
        puntuacionCancionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

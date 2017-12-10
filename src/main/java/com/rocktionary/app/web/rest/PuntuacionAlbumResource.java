package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.PuntuacionAlbum;

import com.rocktionary.app.repository.PuntuacionAlbumRepository;
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
 * REST controller for managing PuntuacionAlbum.
 */
@RestController
@RequestMapping("/api")
public class PuntuacionAlbumResource {

    private final Logger log = LoggerFactory.getLogger(PuntuacionAlbumResource.class);

    private static final String ENTITY_NAME = "puntuacionAlbum";

    private final PuntuacionAlbumRepository puntuacionAlbumRepository;

    public PuntuacionAlbumResource(PuntuacionAlbumRepository puntuacionAlbumRepository) {
        this.puntuacionAlbumRepository = puntuacionAlbumRepository;
    }

    /**
     * POST  /puntuacion-albums : Create a new puntuacionAlbum.
     *
     * @param puntuacionAlbum the puntuacionAlbum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new puntuacionAlbum, or with status 400 (Bad Request) if the puntuacionAlbum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/puntuacion-albums")
    @Timed
    public ResponseEntity<PuntuacionAlbum> createPuntuacionAlbum(@RequestBody PuntuacionAlbum puntuacionAlbum) throws URISyntaxException {
        log.debug("REST request to save PuntuacionAlbum : {}", puntuacionAlbum);
        if (puntuacionAlbum.getId() != null) {
            throw new BadRequestAlertException("A new puntuacionAlbum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuntuacionAlbum result = puntuacionAlbumRepository.save(puntuacionAlbum);
        return ResponseEntity.created(new URI("/api/puntuacion-albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /puntuacion-albums : Updates an existing puntuacionAlbum.
     *
     * @param puntuacionAlbum the puntuacionAlbum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated puntuacionAlbum,
     * or with status 400 (Bad Request) if the puntuacionAlbum is not valid,
     * or with status 500 (Internal Server Error) if the puntuacionAlbum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/puntuacion-albums")
    @Timed
    public ResponseEntity<PuntuacionAlbum> updatePuntuacionAlbum(@RequestBody PuntuacionAlbum puntuacionAlbum) throws URISyntaxException {
        log.debug("REST request to update PuntuacionAlbum : {}", puntuacionAlbum);
        if (puntuacionAlbum.getId() == null) {
            return createPuntuacionAlbum(puntuacionAlbum);
        }
        PuntuacionAlbum result = puntuacionAlbumRepository.save(puntuacionAlbum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, puntuacionAlbum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /puntuacion-albums : get all the puntuacionAlbums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of puntuacionAlbums in body
     */
    @GetMapping("/puntuacion-albums")
    @Timed
    public List<PuntuacionAlbum> getAllPuntuacionAlbums() {
        log.debug("REST request to get all PuntuacionAlbums");
        return puntuacionAlbumRepository.findAll();
        }

    /**
     * GET  /puntuacion-albums/:id : get the "id" puntuacionAlbum.
     *
     * @param id the id of the puntuacionAlbum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the puntuacionAlbum, or with status 404 (Not Found)
     */
    @GetMapping("/puntuacion-albums/{id}")
    @Timed
    public ResponseEntity<PuntuacionAlbum> getPuntuacionAlbum(@PathVariable Long id) {
        log.debug("REST request to get PuntuacionAlbum : {}", id);
        PuntuacionAlbum puntuacionAlbum = puntuacionAlbumRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(puntuacionAlbum));
    }

    /**
     * DELETE  /puntuacion-albums/:id : delete the "id" puntuacionAlbum.
     *
     * @param id the id of the puntuacionAlbum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/puntuacion-albums/{id}")
    @Timed
    public ResponseEntity<Void> deletePuntuacionAlbum(@PathVariable Long id) {
        log.debug("REST request to delete PuntuacionAlbum : {}", id);
        puntuacionAlbumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

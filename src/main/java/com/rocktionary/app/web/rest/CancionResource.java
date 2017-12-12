package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.Cancion;

import com.rocktionary.app.repository.CancionRepository;
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
 * REST controller for managing Cancion.
 */
@RestController
@RequestMapping("/api")
public class CancionResource {

    private final Logger log = LoggerFactory.getLogger(CancionResource.class);

    private static final String ENTITY_NAME = "cancion";

    private final CancionRepository cancionRepository;

    public CancionResource(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    /**
     * POST  /cancions : Create a new cancion.
     *
     * @param cancion the cancion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cancion, or with status 400 (Bad Request) if the cancion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cancions")
    @Timed
    public ResponseEntity<Cancion> createCancion(@RequestBody Cancion cancion) throws URISyntaxException {
        log.debug("REST request to save Cancion : {}", cancion);
        if (cancion.getId() != null) {
            throw new BadRequestAlertException("A new cancion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cancion result = cancionRepository.save(cancion);
        return ResponseEntity.created(new URI("/api/cancions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cancions : Updates an existing cancion.
     *
     * @param cancion the cancion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cancion,
     * or with status 400 (Bad Request) if the cancion is not valid,
     * or with status 500 (Internal Server Error) if the cancion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cancions")
    @Timed
    public ResponseEntity<Cancion> updateCancion(@RequestBody Cancion cancion) throws URISyntaxException {
        log.debug("REST request to update Cancion : {}", cancion);
        if (cancion.getId() == null) {
            return createCancion(cancion);
        }
        Cancion result = cancionRepository.save(cancion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cancion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cancions : get all the cancions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cancions in body
     */
    @GetMapping("/cancions")
    @Timed
    public List<Cancion> getAllCancions() {
        log.debug("REST request to get all Cancions");
        return cancionRepository.findAll();
        }

    /**
     * GET  /cancions/:id : get the "id" cancion.
     *
     * @param id the id of the cancion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cancion, or with status 404 (Not Found)
     */
    @GetMapping("/cancions/{id}")
    @Timed
    public ResponseEntity<Cancion> getCancion(@PathVariable Long id) {
        log.debug("REST request to get Cancion : {}", id);
        Cancion cancion = cancionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cancion));
    }

    /**
     * DELETE  /cancions/:id : delete the "id" cancion.
     *
     * @param id the id of the cancion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cancions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCancion(@PathVariable Long id) {
        log.debug("REST request to delete Cancion : {}", id);
        cancionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-cancion-by-nombre/{cancionNombre}")
    public List<Cancion> getCancionByNombre(@PathVariable String cancionNombre) {
        return cancionRepository.findByNombreContaining(cancionNombre);
    }


}

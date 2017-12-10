package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.PuntuacionBanda;

import com.rocktionary.app.repository.PuntuacionBandaRepository;
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
 * REST controller for managing PuntuacionBanda.
 */
@RestController
@RequestMapping("/api")
public class PuntuacionBandaResource {

    private final Logger log = LoggerFactory.getLogger(PuntuacionBandaResource.class);

    private static final String ENTITY_NAME = "puntuacionBanda";

    private final PuntuacionBandaRepository puntuacionBandaRepository;

    public PuntuacionBandaResource(PuntuacionBandaRepository puntuacionBandaRepository) {
        this.puntuacionBandaRepository = puntuacionBandaRepository;
    }

    /**
     * POST  /puntuacion-bandas : Create a new puntuacionBanda.
     *
     * @param puntuacionBanda the puntuacionBanda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new puntuacionBanda, or with status 400 (Bad Request) if the puntuacionBanda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/puntuacion-bandas")
    @Timed
    public ResponseEntity<PuntuacionBanda> createPuntuacionBanda(@RequestBody PuntuacionBanda puntuacionBanda) throws URISyntaxException {
        log.debug("REST request to save PuntuacionBanda : {}", puntuacionBanda);
        if (puntuacionBanda.getId() != null) {
            throw new BadRequestAlertException("A new puntuacionBanda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuntuacionBanda result = puntuacionBandaRepository.save(puntuacionBanda);
        return ResponseEntity.created(new URI("/api/puntuacion-bandas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /puntuacion-bandas : Updates an existing puntuacionBanda.
     *
     * @param puntuacionBanda the puntuacionBanda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated puntuacionBanda,
     * or with status 400 (Bad Request) if the puntuacionBanda is not valid,
     * or with status 500 (Internal Server Error) if the puntuacionBanda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/puntuacion-bandas")
    @Timed
    public ResponseEntity<PuntuacionBanda> updatePuntuacionBanda(@RequestBody PuntuacionBanda puntuacionBanda) throws URISyntaxException {
        log.debug("REST request to update PuntuacionBanda : {}", puntuacionBanda);
        if (puntuacionBanda.getId() == null) {
            return createPuntuacionBanda(puntuacionBanda);
        }
        PuntuacionBanda result = puntuacionBandaRepository.save(puntuacionBanda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, puntuacionBanda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /puntuacion-bandas : get all the puntuacionBandas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of puntuacionBandas in body
     */
    @GetMapping("/puntuacion-bandas")
    @Timed
    public List<PuntuacionBanda> getAllPuntuacionBandas() {
        log.debug("REST request to get all PuntuacionBandas");
        return puntuacionBandaRepository.findAll();
        }

    /**
     * GET  /puntuacion-bandas/:id : get the "id" puntuacionBanda.
     *
     * @param id the id of the puntuacionBanda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the puntuacionBanda, or with status 404 (Not Found)
     */
    @GetMapping("/puntuacion-bandas/{id}")
    @Timed
    public ResponseEntity<PuntuacionBanda> getPuntuacionBanda(@PathVariable Long id) {
        log.debug("REST request to get PuntuacionBanda : {}", id);
        PuntuacionBanda puntuacionBanda = puntuacionBandaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(puntuacionBanda));
    }

    /**
     * DELETE  /puntuacion-bandas/:id : delete the "id" puntuacionBanda.
     *
     * @param id the id of the puntuacionBanda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/puntuacion-bandas/{id}")
    @Timed
    public ResponseEntity<Void> deletePuntuacionBanda(@PathVariable Long id) {
        log.debug("REST request to delete PuntuacionBanda : {}", id);
        puntuacionBandaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

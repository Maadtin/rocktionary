package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.ComentarCancion;

import com.rocktionary.app.repository.ComentarCancionRepository;
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
 * REST controller for managing ComentarCancion.
 */
@RestController
@RequestMapping("/api")
public class ComentarCancionResource {

    private final Logger log = LoggerFactory.getLogger(ComentarCancionResource.class);

    private static final String ENTITY_NAME = "comentarCancion";

    private final ComentarCancionRepository comentarCancionRepository;

    public ComentarCancionResource(ComentarCancionRepository comentarCancionRepository) {
        this.comentarCancionRepository = comentarCancionRepository;
    }

    /**
     * POST  /comentar-cancions : Create a new comentarCancion.
     *
     * @param comentarCancion the comentarCancion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentarCancion, or with status 400 (Bad Request) if the comentarCancion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comentar-cancions")
    @Timed
    public ResponseEntity<ComentarCancion> createComentarCancion(@RequestBody ComentarCancion comentarCancion) throws URISyntaxException {
        log.debug("REST request to save ComentarCancion : {}", comentarCancion);
        if (comentarCancion.getId() != null) {
            throw new BadRequestAlertException("A new comentarCancion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentarCancion result = comentarCancionRepository.save(comentarCancion);
        return ResponseEntity.created(new URI("/api/comentar-cancions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentar-cancions : Updates an existing comentarCancion.
     *
     * @param comentarCancion the comentarCancion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentarCancion,
     * or with status 400 (Bad Request) if the comentarCancion is not valid,
     * or with status 500 (Internal Server Error) if the comentarCancion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comentar-cancions")
    @Timed
    public ResponseEntity<ComentarCancion> updateComentarCancion(@RequestBody ComentarCancion comentarCancion) throws URISyntaxException {
        log.debug("REST request to update ComentarCancion : {}", comentarCancion);
        if (comentarCancion.getId() == null) {
            return createComentarCancion(comentarCancion);
        }
        ComentarCancion result = comentarCancionRepository.save(comentarCancion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comentarCancion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentar-cancions : get all the comentarCancions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comentarCancions in body
     */
    @GetMapping("/comentar-cancions")
    @Timed
    public List<ComentarCancion> getAllComentarCancions() {
        log.debug("REST request to get all ComentarCancions");
        return comentarCancionRepository.findAll();
        }

    /**
     * GET  /comentar-cancions/:id : get the "id" comentarCancion.
     *
     * @param id the id of the comentarCancion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentarCancion, or with status 404 (Not Found)
     */
    @GetMapping("/comentar-cancions/{id}")
    @Timed
    public ResponseEntity<ComentarCancion> getComentarCancion(@PathVariable Long id) {
        log.debug("REST request to get ComentarCancion : {}", id);
        ComentarCancion comentarCancion = comentarCancionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comentarCancion));
    }

    /**
     * DELETE  /comentar-cancions/:id : delete the "id" comentarCancion.
     *
     * @param id the id of the comentarCancion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comentar-cancions/{id}")
    @Timed
    public ResponseEntity<Void> deleteComentarCancion(@PathVariable Long id) {
        log.debug("REST request to delete ComentarCancion : {}", id);
        comentarCancionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

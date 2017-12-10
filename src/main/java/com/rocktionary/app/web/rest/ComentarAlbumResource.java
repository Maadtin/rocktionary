package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.ComentarAlbum;

import com.rocktionary.app.repository.ComentarAlbumRepository;
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
 * REST controller for managing ComentarAlbum.
 */
@RestController
@RequestMapping("/api")
public class ComentarAlbumResource {

    private final Logger log = LoggerFactory.getLogger(ComentarAlbumResource.class);

    private static final String ENTITY_NAME = "comentarAlbum";

    private final ComentarAlbumRepository comentarAlbumRepository;

    public ComentarAlbumResource(ComentarAlbumRepository comentarAlbumRepository) {
        this.comentarAlbumRepository = comentarAlbumRepository;
    }

    /**
     * POST  /comentar-albums : Create a new comentarAlbum.
     *
     * @param comentarAlbum the comentarAlbum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentarAlbum, or with status 400 (Bad Request) if the comentarAlbum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comentar-albums")
    @Timed
    public ResponseEntity<ComentarAlbum> createComentarAlbum(@RequestBody ComentarAlbum comentarAlbum) throws URISyntaxException {
        log.debug("REST request to save ComentarAlbum : {}", comentarAlbum);
        if (comentarAlbum.getId() != null) {
            throw new BadRequestAlertException("A new comentarAlbum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentarAlbum result = comentarAlbumRepository.save(comentarAlbum);
        return ResponseEntity.created(new URI("/api/comentar-albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentar-albums : Updates an existing comentarAlbum.
     *
     * @param comentarAlbum the comentarAlbum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentarAlbum,
     * or with status 400 (Bad Request) if the comentarAlbum is not valid,
     * or with status 500 (Internal Server Error) if the comentarAlbum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comentar-albums")
    @Timed
    public ResponseEntity<ComentarAlbum> updateComentarAlbum(@RequestBody ComentarAlbum comentarAlbum) throws URISyntaxException {
        log.debug("REST request to update ComentarAlbum : {}", comentarAlbum);
        if (comentarAlbum.getId() == null) {
            return createComentarAlbum(comentarAlbum);
        }
        ComentarAlbum result = comentarAlbumRepository.save(comentarAlbum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comentarAlbum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentar-albums : get all the comentarAlbums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comentarAlbums in body
     */
    @GetMapping("/comentar-albums")
    @Timed
    public List<ComentarAlbum> getAllComentarAlbums() {
        log.debug("REST request to get all ComentarAlbums");
        return comentarAlbumRepository.findAll();
        }

    /**
     * GET  /comentar-albums/:id : get the "id" comentarAlbum.
     *
     * @param id the id of the comentarAlbum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentarAlbum, or with status 404 (Not Found)
     */
    @GetMapping("/comentar-albums/{id}")
    @Timed
    public ResponseEntity<ComentarAlbum> getComentarAlbum(@PathVariable Long id) {
        log.debug("REST request to get ComentarAlbum : {}", id);
        ComentarAlbum comentarAlbum = comentarAlbumRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comentarAlbum));
    }

    /**
     * DELETE  /comentar-albums/:id : delete the "id" comentarAlbum.
     *
     * @param id the id of the comentarAlbum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comentar-albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteComentarAlbum(@PathVariable Long id) {
        log.debug("REST request to delete ComentarAlbum : {}", id);
        comentarAlbumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.Discografica;

import com.rocktionary.app.repository.DiscograficaRepository;
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
 * REST controller for managing Discografica.
 */
@RestController
@RequestMapping("/api")
public class DiscograficaResource {

    private final Logger log = LoggerFactory.getLogger(DiscograficaResource.class);

    private static final String ENTITY_NAME = "discografica";

    private final DiscograficaRepository discograficaRepository;

    public DiscograficaResource(DiscograficaRepository discograficaRepository) {
        this.discograficaRepository = discograficaRepository;
    }

    /**
     * POST  /discograficas : Create a new discografica.
     *
     * @param discografica the discografica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discografica, or with status 400 (Bad Request) if the discografica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discograficas")
    @Timed
    public ResponseEntity<Discografica> createDiscografica(@RequestBody Discografica discografica) throws URISyntaxException {
        log.debug("REST request to save Discografica : {}", discografica);
        if (discografica.getId() != null) {
            throw new BadRequestAlertException("A new discografica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Discografica result = discograficaRepository.save(discografica);
        return ResponseEntity.created(new URI("/api/discograficas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discograficas : Updates an existing discografica.
     *
     * @param discografica the discografica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discografica,
     * or with status 400 (Bad Request) if the discografica is not valid,
     * or with status 500 (Internal Server Error) if the discografica couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discograficas")
    @Timed
    public ResponseEntity<Discografica> updateDiscografica(@RequestBody Discografica discografica) throws URISyntaxException {
        log.debug("REST request to update Discografica : {}", discografica);
        if (discografica.getId() == null) {
            return createDiscografica(discografica);
        }
        Discografica result = discograficaRepository.save(discografica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discografica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discograficas : get all the discograficas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of discograficas in body
     */
    @GetMapping("/discograficas")
    @Timed
    public List<Discografica> getAllDiscograficas() {
        log.debug("REST request to get all Discograficas");
        return discograficaRepository.findAll();
        }

    /**
     * GET  /discograficas/:id : get the "id" discografica.
     *
     * @param id the id of the discografica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discografica, or with status 404 (Not Found)
     */
    @GetMapping("/discograficas/{id}")
    @Timed
    public ResponseEntity<Discografica> getDiscografica(@PathVariable Long id) {
        log.debug("REST request to get Discografica : {}", id);
        Discografica discografica = discograficaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discografica));
    }

    /**
     * DELETE  /discograficas/:id : delete the "id" discografica.
     *
     * @param id the id of the discografica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discograficas/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscografica(@PathVariable Long id) {
        log.debug("REST request to delete Discografica : {}", id);
        discograficaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

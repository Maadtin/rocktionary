package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.Banda;

import com.rocktionary.app.repository.BandaRepository;
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
 * REST controller for managing Banda.
 */
@RestController
@RequestMapping("/api")
public class BandaResource {

    private final Logger log = LoggerFactory.getLogger(BandaResource.class);

    private static final String ENTITY_NAME = "banda";

    private final BandaRepository bandaRepository;

    public BandaResource(BandaRepository bandaRepository) {
        this.bandaRepository = bandaRepository;
    }

    /**
     * POST  /bandas : Create a new banda.
     *
     * @param banda the banda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new banda, or with status 400 (Bad Request) if the banda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bandas")
    @Timed
    public ResponseEntity<Banda> createBanda(@RequestBody Banda banda) throws URISyntaxException {
        log.debug("REST request to save Banda : {}", banda);
        if (banda.getId() != null) {
            throw new BadRequestAlertException("A new banda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Banda result = bandaRepository.save(banda);
        return ResponseEntity.created(new URI("/api/bandas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bandas : Updates an existing banda.
     *
     * @param banda the banda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated banda,
     * or with status 400 (Bad Request) if the banda is not valid,
     * or with status 500 (Internal Server Error) if the banda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bandas")
    @Timed
    public ResponseEntity<Banda> updateBanda(@RequestBody Banda banda) throws URISyntaxException {
        log.debug("REST request to update Banda : {}", banda);
        if (banda.getId() == null) {
            return createBanda(banda);
        }
        Banda result = bandaRepository.save(banda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, banda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bandas : get all the bandas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bandas in body
     */
    @GetMapping("/bandas")
    @Timed
    public List<Banda> getAllBandas() {
        log.debug("REST request to get all Bandas");
        return bandaRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /bandas/:id : get the "id" banda.
     *
     * @param id the id of the banda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the banda, or with status 404 (Not Found)
     */
    @GetMapping("/bandas/{id}")
    @Timed
    public ResponseEntity<Banda> getBanda(@PathVariable Long id) {
        log.debug("REST request to get Banda : {}", id);
        Banda banda = bandaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(banda));
    }

    /**
     * DELETE  /bandas/:id : delete the "id" banda.
     *
     * @param id the id of the banda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bandas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBanda(@PathVariable Long id) {
        log.debug("REST request to delete Banda : {}", id);
        bandaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-banda-by-nombre/{bandaNombre}")
    public List<Banda> getBandasByNombre(@PathVariable String bandaNombre) {
        return bandaRepository.findByNombreContaining(bandaNombre);
    }


}

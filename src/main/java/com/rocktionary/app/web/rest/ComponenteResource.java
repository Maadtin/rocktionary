package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.Componente;

import com.rocktionary.app.repository.ComponenteRepository;
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
 * REST controller for managing Componente.
 */
@RestController
@RequestMapping("/api")
public class ComponenteResource {

    private final Logger log = LoggerFactory.getLogger(ComponenteResource.class);

    private static final String ENTITY_NAME = "componente";

    private final ComponenteRepository componenteRepository;

    public ComponenteResource(ComponenteRepository componenteRepository) {
        this.componenteRepository = componenteRepository;
    }

    /**
     * POST  /componentes : Create a new componente.
     *
     * @param componente the componente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new componente, or with status 400 (Bad Request) if the componente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/componentes")
    @Timed
    public ResponseEntity<Componente> createComponente(@RequestBody Componente componente) throws URISyntaxException {
        log.debug("REST request to save Componente : {}", componente);
        if (componente.getId() != null) {
            throw new BadRequestAlertException("A new componente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Componente result = componenteRepository.save(componente);
        return ResponseEntity.created(new URI("/api/componentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /componentes : Updates an existing componente.
     *
     * @param componente the componente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated componente,
     * or with status 400 (Bad Request) if the componente is not valid,
     * or with status 500 (Internal Server Error) if the componente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/componentes")
    @Timed
    public ResponseEntity<Componente> updateComponente(@RequestBody Componente componente) throws URISyntaxException {
        log.debug("REST request to update Componente : {}", componente);
        if (componente.getId() == null) {
            return createComponente(componente);
        }
        Componente result = componenteRepository.save(componente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, componente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /componentes : get all the componentes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of componentes in body
     */
    @GetMapping("/componentes")
    @Timed
    public List<Componente> getAllComponentes() {
        log.debug("REST request to get all Componentes");
        return componenteRepository.findAll();
        }

    /**
     * GET  /componentes/:id : get the "id" componente.
     *
     * @param id the id of the componente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the componente, or with status 404 (Not Found)
     */
    @GetMapping("/componentes/{id}")
    @Timed
    public ResponseEntity<Componente> getComponente(@PathVariable Long id) {
        log.debug("REST request to get Componente : {}", id);
        Componente componente = componenteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(componente));
    }

    /**
     * DELETE  /componentes/:id : delete the "id" componente.
     *
     * @param id the id of the componente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/componentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteComponente(@PathVariable Long id) {
        log.debug("REST request to delete Componente : {}", id);
        componenteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.Wellbore;
import com.lakon.rto.repository.WellboreRepository;
import com.lakon.rto.web.rest.errors.BadRequestAlertException;
import com.lakon.rto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Wellbore.
 */
@RestController
@RequestMapping("/api")
public class WellboreResource {

    private final Logger log = LoggerFactory.getLogger(WellboreResource.class);

    private static final String ENTITY_NAME = "wellbore";

    private WellboreRepository wellboreRepository;

    public WellboreResource(WellboreRepository wellboreRepository) {
        this.wellboreRepository = wellboreRepository;
    }

    /**
     * POST  /wellbores : Create a new wellbore.
     *
     * @param wellbore the wellbore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wellbore, or with status 400 (Bad Request) if the wellbore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wellbores")
    @Timed
    public ResponseEntity<Wellbore> createWellbore(@Valid @RequestBody Wellbore wellbore) throws URISyntaxException {
        log.debug("REST request to save Wellbore : {}", wellbore);
        if (wellbore.getId() != null) {
            throw new BadRequestAlertException("A new wellbore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wellbore result = wellboreRepository.save(wellbore);
        return ResponseEntity.created(new URI("/api/wellbores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wellbores : Updates an existing wellbore.
     *
     * @param wellbore the wellbore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wellbore,
     * or with status 400 (Bad Request) if the wellbore is not valid,
     * or with status 500 (Internal Server Error) if the wellbore couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wellbores")
    @Timed
    public ResponseEntity<Wellbore> updateWellbore(@Valid @RequestBody Wellbore wellbore) throws URISyntaxException {
        log.debug("REST request to update Wellbore : {}", wellbore);
        if (wellbore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Wellbore result = wellboreRepository.save(wellbore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wellbore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wellbores : get all the wellbores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wellbores in body
     */
    @GetMapping("/wellbores")
    @Timed
    public List<Wellbore> getAllWellbores() {
        log.debug("REST request to get all Wellbores");
        return wellboreRepository.findAll();
    }

    /**
     * GET  /wellbores/:id : get the "id" wellbore.
     *
     * @param id the id of the wellbore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wellbore, or with status 404 (Not Found)
     */
    @GetMapping("/wellbores/{id}")
    @Timed
    public ResponseEntity<Wellbore> getWellbore(@PathVariable Long id) {
        log.debug("REST request to get Wellbore : {}", id);
        Optional<Wellbore> wellbore = wellboreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wellbore);
    }

    /**
     * DELETE  /wellbores/:id : delete the "id" wellbore.
     *
     * @param id the id of the wellbore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wellbores/{id}")
    @Timed
    public ResponseEntity<Void> deleteWellbore(@PathVariable Long id) {
        log.debug("REST request to delete Wellbore : {}", id);

        wellboreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

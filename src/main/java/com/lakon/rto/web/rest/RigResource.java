package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.Rig;
import com.lakon.rto.repository.RigRepository;
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
 * REST controller for managing Rig.
 */
@RestController
@RequestMapping("/api")
public class RigResource {

    private final Logger log = LoggerFactory.getLogger(RigResource.class);

    private static final String ENTITY_NAME = "rig";

    private RigRepository rigRepository;

    public RigResource(RigRepository rigRepository) {
        this.rigRepository = rigRepository;
    }

    /**
     * POST  /rigs : Create a new rig.
     *
     * @param rig the rig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rig, or with status 400 (Bad Request) if the rig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rigs")
    @Timed
    public ResponseEntity<Rig> createRig(@Valid @RequestBody Rig rig) throws URISyntaxException {
        log.debug("REST request to save Rig : {}", rig);
        if (rig.getId() != null) {
            throw new BadRequestAlertException("A new rig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rig result = rigRepository.save(rig);
        return ResponseEntity.created(new URI("/api/rigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rigs : Updates an existing rig.
     *
     * @param rig the rig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rig,
     * or with status 400 (Bad Request) if the rig is not valid,
     * or with status 500 (Internal Server Error) if the rig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rigs")
    @Timed
    public ResponseEntity<Rig> updateRig(@Valid @RequestBody Rig rig) throws URISyntaxException {
        log.debug("REST request to update Rig : {}", rig);
        if (rig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rig result = rigRepository.save(rig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rigs : get all the rigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rigs in body
     */
    @GetMapping("/rigs")
    @Timed
    public List<Rig> getAllRigs() {
        log.debug("REST request to get all Rigs");
        return rigRepository.findAll();
    }

    /**
     * GET  /rigs/:id : get the "id" rig.
     *
     * @param id the id of the rig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rig, or with status 404 (Not Found)
     */
    @GetMapping("/rigs/{id}")
    @Timed
    public ResponseEntity<Rig> getRig(@PathVariable Long id) {
        log.debug("REST request to get Rig : {}", id);
        Optional<Rig> rig = rigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rig);
    }

    /**
     * DELETE  /rigs/:id : delete the "id" rig.
     *
     * @param id the id of the rig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rigs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRig(@PathVariable Long id) {
        log.debug("REST request to delete Rig : {}", id);

        rigRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

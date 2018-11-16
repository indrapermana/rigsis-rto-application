package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.Well;
import com.lakon.rto.repository.WellRepository;
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
 * REST controller for managing Well.
 */
@RestController
@RequestMapping("/api")
public class WellResource {

    private final Logger log = LoggerFactory.getLogger(WellResource.class);

    private static final String ENTITY_NAME = "well";

    private WellRepository wellRepository;

    public WellResource(WellRepository wellRepository) {
        this.wellRepository = wellRepository;
    }

    /**
     * POST  /wells : Create a new well.
     *
     * @param well the well to create
     * @return the ResponseEntity with status 201 (Created) and with body the new well, or with status 400 (Bad Request) if the well has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wells")
    @Timed
    public ResponseEntity<Well> createWell(@Valid @RequestBody Well well) throws URISyntaxException {
        log.debug("REST request to save Well : {}", well);
        if (well.getId() != null) {
            throw new BadRequestAlertException("A new well cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Well result = wellRepository.save(well);
        return ResponseEntity.created(new URI("/api/wells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wells : Updates an existing well.
     *
     * @param well the well to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated well,
     * or with status 400 (Bad Request) if the well is not valid,
     * or with status 500 (Internal Server Error) if the well couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wells")
    @Timed
    public ResponseEntity<Well> updateWell(@Valid @RequestBody Well well) throws URISyntaxException {
        log.debug("REST request to update Well : {}", well);
        if (well.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Well result = wellRepository.save(well);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, well.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wells : get all the wells.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wells in body
     */
    @GetMapping("/wells")
    @Timed
    public List<Well> getAllWells() {
        log.debug("REST request to get all Wells");
        return wellRepository.findAll();
    }

    /**
     * GET  /wells/:id : get the "id" well.
     *
     * @param id the id of the well to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the well, or with status 404 (Not Found)
     */
    @GetMapping("/wells/{id}")
    @Timed
    public ResponseEntity<Well> getWell(@PathVariable Long id) {
        log.debug("REST request to get Well : {}", id);
        Optional<Well> well = wellRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(well);
    }

    /**
     * DELETE  /wells/:id : delete the "id" well.
     *
     * @param id the id of the well to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wells/{id}")
    @Timed
    public ResponseEntity<Void> deleteWell(@PathVariable Long id) {
        log.debug("REST request to delete Well : {}", id);

        wellRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

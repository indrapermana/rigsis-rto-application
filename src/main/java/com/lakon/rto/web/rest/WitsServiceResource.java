package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.WitsService;
import com.lakon.rto.repository.WitsServiceRepository;
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
 * REST controller for managing WitsService.
 */
@RestController
@RequestMapping("/api")
public class WitsServiceResource {

    private final Logger log = LoggerFactory.getLogger(WitsServiceResource.class);

    private static final String ENTITY_NAME = "witsService";

    private WitsServiceRepository witsServiceRepository;

    public WitsServiceResource(WitsServiceRepository witsServiceRepository) {
        this.witsServiceRepository = witsServiceRepository;
    }

    /**
     * POST  /wits-services : Create a new witsService.
     *
     * @param witsService the witsService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new witsService, or with status 400 (Bad Request) if the witsService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wits-services")
    @Timed
    public ResponseEntity<WitsService> createWitsService(@Valid @RequestBody WitsService witsService) throws URISyntaxException {
        log.debug("REST request to save WitsService : {}", witsService);
        if (witsService.getId() != null) {
            throw new BadRequestAlertException("A new witsService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WitsService result = witsServiceRepository.save(witsService);
        return ResponseEntity.created(new URI("/api/wits-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wits-services : Updates an existing witsService.
     *
     * @param witsService the witsService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated witsService,
     * or with status 400 (Bad Request) if the witsService is not valid,
     * or with status 500 (Internal Server Error) if the witsService couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wits-services")
    @Timed
    public ResponseEntity<WitsService> updateWitsService(@Valid @RequestBody WitsService witsService) throws URISyntaxException {
        log.debug("REST request to update WitsService : {}", witsService);
        if (witsService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WitsService result = witsServiceRepository.save(witsService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, witsService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wits-services : get all the witsServices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of witsServices in body
     */
    @GetMapping("/wits-services")
    @Timed
    public List<WitsService> getAllWitsServices() {
        log.debug("REST request to get all WitsServices");
        return witsServiceRepository.findAll();
    }

    /**
     * GET  /wits-services/:id : get the "id" witsService.
     *
     * @param id the id of the witsService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the witsService, or with status 404 (Not Found)
     */
    @GetMapping("/wits-services/{id}")
    @Timed
    public ResponseEntity<WitsService> getWitsService(@PathVariable Long id) {
        log.debug("REST request to get WitsService : {}", id);
        Optional<WitsService> witsService = witsServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(witsService);
    }

    /**
     * DELETE  /wits-services/:id : delete the "id" witsService.
     *
     * @param id the id of the witsService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wits-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteWitsService(@PathVariable Long id) {
        log.debug("REST request to delete WitsService : {}", id);

        witsServiceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

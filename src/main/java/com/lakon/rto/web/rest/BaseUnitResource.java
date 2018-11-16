package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.BaseUnit;
import com.lakon.rto.repository.BaseUnitRepository;
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
 * REST controller for managing BaseUnit.
 */
@RestController
@RequestMapping("/api")
public class BaseUnitResource {

    private final Logger log = LoggerFactory.getLogger(BaseUnitResource.class);

    private static final String ENTITY_NAME = "baseUnit";

    private BaseUnitRepository baseUnitRepository;

    public BaseUnitResource(BaseUnitRepository baseUnitRepository) {
        this.baseUnitRepository = baseUnitRepository;
    }

    /**
     * POST  /base-units : Create a new baseUnit.
     *
     * @param baseUnit the baseUnit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new baseUnit, or with status 400 (Bad Request) if the baseUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/base-units")
    @Timed
    public ResponseEntity<BaseUnit> createBaseUnit(@Valid @RequestBody BaseUnit baseUnit) throws URISyntaxException {
        log.debug("REST request to save BaseUnit : {}", baseUnit);
        if (baseUnit.getId() != null) {
            throw new BadRequestAlertException("A new baseUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseUnit result = baseUnitRepository.save(baseUnit);
        return ResponseEntity.created(new URI("/api/base-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /base-units : Updates an existing baseUnit.
     *
     * @param baseUnit the baseUnit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated baseUnit,
     * or with status 400 (Bad Request) if the baseUnit is not valid,
     * or with status 500 (Internal Server Error) if the baseUnit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/base-units")
    @Timed
    public ResponseEntity<BaseUnit> updateBaseUnit(@Valid @RequestBody BaseUnit baseUnit) throws URISyntaxException {
        log.debug("REST request to update BaseUnit : {}", baseUnit);
        if (baseUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseUnit result = baseUnitRepository.save(baseUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, baseUnit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /base-units : get all the baseUnits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of baseUnits in body
     */
    @GetMapping("/base-units")
    @Timed
    public List<BaseUnit> getAllBaseUnits() {
        log.debug("REST request to get all BaseUnits");
        return baseUnitRepository.findAll();
    }

    /**
     * GET  /base-units/:id : get the "id" baseUnit.
     *
     * @param id the id of the baseUnit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the baseUnit, or with status 404 (Not Found)
     */
    @GetMapping("/base-units/{id}")
    @Timed
    public ResponseEntity<BaseUnit> getBaseUnit(@PathVariable Long id) {
        log.debug("REST request to get BaseUnit : {}", id);
        Optional<BaseUnit> baseUnit = baseUnitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(baseUnit);
    }

    /**
     * DELETE  /base-units/:id : delete the "id" baseUnit.
     *
     * @param id the id of the baseUnit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/base-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteBaseUnit(@PathVariable Long id) {
        log.debug("REST request to delete BaseUnit : {}", id);

        baseUnitRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

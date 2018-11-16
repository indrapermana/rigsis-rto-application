package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.DerivedUnit;
import com.lakon.rto.repository.DerivedUnitRepository;
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
 * REST controller for managing DerivedUnit.
 */
@RestController
@RequestMapping("/api")
public class DerivedUnitResource {

    private final Logger log = LoggerFactory.getLogger(DerivedUnitResource.class);

    private static final String ENTITY_NAME = "derivedUnit";

    private DerivedUnitRepository derivedUnitRepository;

    public DerivedUnitResource(DerivedUnitRepository derivedUnitRepository) {
        this.derivedUnitRepository = derivedUnitRepository;
    }

    /**
     * POST  /derived-units : Create a new derivedUnit.
     *
     * @param derivedUnit the derivedUnit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new derivedUnit, or with status 400 (Bad Request) if the derivedUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/derived-units")
    @Timed
    public ResponseEntity<DerivedUnit> createDerivedUnit(@Valid @RequestBody DerivedUnit derivedUnit) throws URISyntaxException {
        log.debug("REST request to save DerivedUnit : {}", derivedUnit);
        if (derivedUnit.getId() != null) {
            throw new BadRequestAlertException("A new derivedUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DerivedUnit result = derivedUnitRepository.save(derivedUnit);
        return ResponseEntity.created(new URI("/api/derived-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /derived-units : Updates an existing derivedUnit.
     *
     * @param derivedUnit the derivedUnit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated derivedUnit,
     * or with status 400 (Bad Request) if the derivedUnit is not valid,
     * or with status 500 (Internal Server Error) if the derivedUnit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/derived-units")
    @Timed
    public ResponseEntity<DerivedUnit> updateDerivedUnit(@Valid @RequestBody DerivedUnit derivedUnit) throws URISyntaxException {
        log.debug("REST request to update DerivedUnit : {}", derivedUnit);
        if (derivedUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DerivedUnit result = derivedUnitRepository.save(derivedUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, derivedUnit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /derived-units : get all the derivedUnits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of derivedUnits in body
     */
    @GetMapping("/derived-units")
    @Timed
    public List<DerivedUnit> getAllDerivedUnits() {
        log.debug("REST request to get all DerivedUnits");
        return derivedUnitRepository.findAll();
    }

    /**
     * GET  /derived-units/:id : get the "id" derivedUnit.
     *
     * @param id the id of the derivedUnit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the derivedUnit, or with status 404 (Not Found)
     */
    @GetMapping("/derived-units/{id}")
    @Timed
    public ResponseEntity<DerivedUnit> getDerivedUnit(@PathVariable Long id) {
        log.debug("REST request to get DerivedUnit : {}", id);
        Optional<DerivedUnit> derivedUnit = derivedUnitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(derivedUnit);
    }

    /**
     * DELETE  /derived-units/:id : delete the "id" derivedUnit.
     *
     * @param id the id of the derivedUnit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/derived-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteDerivedUnit(@PathVariable Long id) {
        log.debug("REST request to delete DerivedUnit : {}", id);

        derivedUnitRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

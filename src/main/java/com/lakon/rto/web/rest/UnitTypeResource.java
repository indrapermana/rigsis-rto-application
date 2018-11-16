package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.UnitType;
import com.lakon.rto.repository.UnitTypeRepository;
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
 * REST controller for managing UnitType.
 */
@RestController
@RequestMapping("/api")
public class UnitTypeResource {

    private final Logger log = LoggerFactory.getLogger(UnitTypeResource.class);

    private static final String ENTITY_NAME = "unitType";

    private UnitTypeRepository unitTypeRepository;

    public UnitTypeResource(UnitTypeRepository unitTypeRepository) {
        this.unitTypeRepository = unitTypeRepository;
    }

    /**
     * POST  /unit-types : Create a new unitType.
     *
     * @param unitType the unitType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unitType, or with status 400 (Bad Request) if the unitType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unit-types")
    @Timed
    public ResponseEntity<UnitType> createUnitType(@Valid @RequestBody UnitType unitType) throws URISyntaxException {
        log.debug("REST request to save UnitType : {}", unitType);
        if (unitType.getId() != null) {
            throw new BadRequestAlertException("A new unitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitType result = unitTypeRepository.save(unitType);
        return ResponseEntity.created(new URI("/api/unit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unit-types : Updates an existing unitType.
     *
     * @param unitType the unitType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unitType,
     * or with status 400 (Bad Request) if the unitType is not valid,
     * or with status 500 (Internal Server Error) if the unitType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unit-types")
    @Timed
    public ResponseEntity<UnitType> updateUnitType(@Valid @RequestBody UnitType unitType) throws URISyntaxException {
        log.debug("REST request to update UnitType : {}", unitType);
        if (unitType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitType result = unitTypeRepository.save(unitType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unitType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unit-types : get all the unitTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unitTypes in body
     */
    @GetMapping("/unit-types")
    @Timed
    public List<UnitType> getAllUnitTypes() {
        log.debug("REST request to get all UnitTypes");
        return unitTypeRepository.findAll();
    }

    /**
     * GET  /unit-types/:id : get the "id" unitType.
     *
     * @param id the id of the unitType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unitType, or with status 404 (Not Found)
     */
    @GetMapping("/unit-types/{id}")
    @Timed
    public ResponseEntity<UnitType> getUnitType(@PathVariable Long id) {
        log.debug("REST request to get UnitType : {}", id);
        Optional<UnitType> unitType = unitTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unitType);
    }

    /**
     * DELETE  /unit-types/:id : delete the "id" unitType.
     *
     * @param id the id of the unitType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unit-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnitType(@PathVariable Long id) {
        log.debug("REST request to delete UnitType : {}", id);

        unitTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

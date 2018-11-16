package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.UnitTypeItem;
import com.lakon.rto.repository.UnitTypeItemRepository;
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
 * REST controller for managing UnitTypeItem.
 */
@RestController
@RequestMapping("/api")
public class UnitTypeItemResource {

    private final Logger log = LoggerFactory.getLogger(UnitTypeItemResource.class);

    private static final String ENTITY_NAME = "unitTypeItem";

    private UnitTypeItemRepository unitTypeItemRepository;

    public UnitTypeItemResource(UnitTypeItemRepository unitTypeItemRepository) {
        this.unitTypeItemRepository = unitTypeItemRepository;
    }

    /**
     * POST  /unit-type-items : Create a new unitTypeItem.
     *
     * @param unitTypeItem the unitTypeItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unitTypeItem, or with status 400 (Bad Request) if the unitTypeItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unit-type-items")
    @Timed
    public ResponseEntity<UnitTypeItem> createUnitTypeItem(@Valid @RequestBody UnitTypeItem unitTypeItem) throws URISyntaxException {
        log.debug("REST request to save UnitTypeItem : {}", unitTypeItem);
        if (unitTypeItem.getId() != null) {
            throw new BadRequestAlertException("A new unitTypeItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitTypeItem result = unitTypeItemRepository.save(unitTypeItem);
        return ResponseEntity.created(new URI("/api/unit-type-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unit-type-items : Updates an existing unitTypeItem.
     *
     * @param unitTypeItem the unitTypeItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unitTypeItem,
     * or with status 400 (Bad Request) if the unitTypeItem is not valid,
     * or with status 500 (Internal Server Error) if the unitTypeItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unit-type-items")
    @Timed
    public ResponseEntity<UnitTypeItem> updateUnitTypeItem(@Valid @RequestBody UnitTypeItem unitTypeItem) throws URISyntaxException {
        log.debug("REST request to update UnitTypeItem : {}", unitTypeItem);
        if (unitTypeItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitTypeItem result = unitTypeItemRepository.save(unitTypeItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unitTypeItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unit-type-items : get all the unitTypeItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unitTypeItems in body
     */
    @GetMapping("/unit-type-items")
    @Timed
    public List<UnitTypeItem> getAllUnitTypeItems() {
        log.debug("REST request to get all UnitTypeItems");
        return unitTypeItemRepository.findAll();
    }

    /**
     * GET  /unit-type-items/:id : get the "id" unitTypeItem.
     *
     * @param id the id of the unitTypeItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unitTypeItem, or with status 404 (Not Found)
     */
    @GetMapping("/unit-type-items/{id}")
    @Timed
    public ResponseEntity<UnitTypeItem> getUnitTypeItem(@PathVariable Long id) {
        log.debug("REST request to get UnitTypeItem : {}", id);
        Optional<UnitTypeItem> unitTypeItem = unitTypeItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unitTypeItem);
    }

    /**
     * DELETE  /unit-type-items/:id : delete the "id" unitTypeItem.
     *
     * @param id the id of the unitTypeItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unit-type-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnitTypeItem(@PathVariable Long id) {
        log.debug("REST request to delete UnitTypeItem : {}", id);

        unitTypeItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

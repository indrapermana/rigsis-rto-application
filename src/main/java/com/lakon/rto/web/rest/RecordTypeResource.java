package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.RecordType;
import com.lakon.rto.repository.RecordTypeRepository;
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
 * REST controller for managing RecordType.
 */
@RestController
@RequestMapping("/api")
public class RecordTypeResource {

    private final Logger log = LoggerFactory.getLogger(RecordTypeResource.class);

    private static final String ENTITY_NAME = "recordType";

    private RecordTypeRepository recordTypeRepository;

    public RecordTypeResource(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }

    /**
     * POST  /record-types : Create a new recordType.
     *
     * @param recordType the recordType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recordType, or with status 400 (Bad Request) if the recordType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/record-types")
    @Timed
    public ResponseEntity<RecordType> createRecordType(@Valid @RequestBody RecordType recordType) throws URISyntaxException {
        log.debug("REST request to save RecordType : {}", recordType);
        if (recordType.getId() != null) {
            throw new BadRequestAlertException("A new recordType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordType result = recordTypeRepository.save(recordType);
        return ResponseEntity.created(new URI("/api/record-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /record-types : Updates an existing recordType.
     *
     * @param recordType the recordType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recordType,
     * or with status 400 (Bad Request) if the recordType is not valid,
     * or with status 500 (Internal Server Error) if the recordType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/record-types")
    @Timed
    public ResponseEntity<RecordType> updateRecordType(@Valid @RequestBody RecordType recordType) throws URISyntaxException {
        log.debug("REST request to update RecordType : {}", recordType);
        if (recordType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecordType result = recordTypeRepository.save(recordType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recordType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /record-types : get all the recordTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recordTypes in body
     */
    @GetMapping("/record-types")
    @Timed
    public List<RecordType> getAllRecordTypes() {
        log.debug("REST request to get all RecordTypes");
        return recordTypeRepository.findAll();
    }

    /**
     * GET  /record-types/:id : get the "id" recordType.
     *
     * @param id the id of the recordType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recordType, or with status 404 (Not Found)
     */
    @GetMapping("/record-types/{id}")
    @Timed
    public ResponseEntity<RecordType> getRecordType(@PathVariable Long id) {
        log.debug("REST request to get RecordType : {}", id);
        Optional<RecordType> recordType = recordTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recordType);
    }

    /**
     * DELETE  /record-types/:id : delete the "id" recordType.
     *
     * @param id the id of the recordType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/record-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecordType(@PathVariable Long id) {
        log.debug("REST request to delete RecordType : {}", id);

        recordTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

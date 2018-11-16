package com.lakon.rto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lakon.rto.domain.RecordItem;
import com.lakon.rto.repository.RecordItemRepository;
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
 * REST controller for managing RecordItem.
 */
@RestController
@RequestMapping("/api")
public class RecordItemResource {

    private final Logger log = LoggerFactory.getLogger(RecordItemResource.class);

    private static final String ENTITY_NAME = "recordItem";

    private RecordItemRepository recordItemRepository;

    public RecordItemResource(RecordItemRepository recordItemRepository) {
        this.recordItemRepository = recordItemRepository;
    }

    /**
     * POST  /record-items : Create a new recordItem.
     *
     * @param recordItem the recordItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recordItem, or with status 400 (Bad Request) if the recordItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/record-items")
    @Timed
    public ResponseEntity<RecordItem> createRecordItem(@Valid @RequestBody RecordItem recordItem) throws URISyntaxException {
        log.debug("REST request to save RecordItem : {}", recordItem);
        if (recordItem.getId() != null) {
            throw new BadRequestAlertException("A new recordItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordItem result = recordItemRepository.save(recordItem);
        return ResponseEntity.created(new URI("/api/record-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /record-items : Updates an existing recordItem.
     *
     * @param recordItem the recordItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recordItem,
     * or with status 400 (Bad Request) if the recordItem is not valid,
     * or with status 500 (Internal Server Error) if the recordItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/record-items")
    @Timed
    public ResponseEntity<RecordItem> updateRecordItem(@Valid @RequestBody RecordItem recordItem) throws URISyntaxException {
        log.debug("REST request to update RecordItem : {}", recordItem);
        if (recordItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecordItem result = recordItemRepository.save(recordItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recordItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /record-items : get all the recordItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recordItems in body
     */
    @GetMapping("/record-items")
    @Timed
    public List<RecordItem> getAllRecordItems() {
        log.debug("REST request to get all RecordItems");
        return recordItemRepository.findAll();
    }

    /**
     * GET  /record-items/:id : get the "id" recordItem.
     *
     * @param id the id of the recordItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recordItem, or with status 404 (Not Found)
     */
    @GetMapping("/record-items/{id}")
    @Timed
    public ResponseEntity<RecordItem> getRecordItem(@PathVariable Long id) {
        log.debug("REST request to get RecordItem : {}", id);
        Optional<RecordItem> recordItem = recordItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recordItem);
    }

    /**
     * DELETE  /record-items/:id : delete the "id" recordItem.
     *
     * @param id the id of the recordItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/record-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecordItem(@PathVariable Long id) {
        log.debug("REST request to delete RecordItem : {}", id);

        recordItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

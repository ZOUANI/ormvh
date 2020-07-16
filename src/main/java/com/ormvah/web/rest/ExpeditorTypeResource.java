package com.ormvah.web.rest;

import com.ormvah.domain.ExpeditorType;
import com.ormvah.service.ExpeditorTypeService;
import com.ormvah.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ormvah.domain.ExpeditorType}.
 */
@RestController
@RequestMapping("/api")
public class ExpeditorTypeResource {

    private final Logger log = LoggerFactory.getLogger(ExpeditorTypeResource.class);

    private static final String ENTITY_NAME = "expeditorType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpeditorTypeService expeditorTypeService;

    public ExpeditorTypeResource(ExpeditorTypeService expeditorTypeService) {
        this.expeditorTypeService = expeditorTypeService;
    }

    /**
     * {@code POST  /expeditor-types} : Create a new expeditorType.
     *
     * @param expeditorType the expeditorType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expeditorType, or with status {@code 400 (Bad Request)} if the expeditorType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expeditor-types")
    public ResponseEntity<ExpeditorType> createExpeditorType(@RequestBody ExpeditorType expeditorType) throws URISyntaxException {
        log.debug("REST request to save ExpeditorType : {}", expeditorType);
        if (expeditorType.getId() != null) {
            throw new BadRequestAlertException("A new expeditorType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpeditorType result = expeditorTypeService.save(expeditorType);
        return ResponseEntity.created(new URI("/api/expeditor-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expeditor-types} : Updates an existing expeditorType.
     *
     * @param expeditorType the expeditorType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expeditorType,
     * or with status {@code 400 (Bad Request)} if the expeditorType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expeditorType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expeditor-types")
    public ResponseEntity<ExpeditorType> updateExpeditorType(@RequestBody ExpeditorType expeditorType) throws URISyntaxException {
        log.debug("REST request to update ExpeditorType : {}", expeditorType);
        if (expeditorType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpeditorType result = expeditorTypeService.save(expeditorType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expeditorType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expeditor-types} : get all the expeditorTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expeditorTypes in body.
     */
    @GetMapping("/expeditor-types")
    public List<ExpeditorType> getAllExpeditorTypes() {
        log.debug("REST request to get all ExpeditorTypes");
        return expeditorTypeService.findAll();
    }

    /**
     * {@code GET  /expeditor-types/:id} : get the "id" expeditorType.
     *
     * @param id the id of the expeditorType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expeditorType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expeditor-types/{id}")
    public ResponseEntity<ExpeditorType> getExpeditorType(@PathVariable Long id) {
        log.debug("REST request to get ExpeditorType : {}", id);
        Optional<ExpeditorType> expeditorType = expeditorTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expeditorType);
    }

    /**
     * {@code DELETE  /expeditor-types/:id} : delete the "id" expeditorType.
     *
     * @param id the id of the expeditorType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expeditor-types/{id}")
    public ResponseEntity<Void> deleteExpeditorType(@PathVariable Long id) {
        log.debug("REST request to delete ExpeditorType : {}", id);
        expeditorTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

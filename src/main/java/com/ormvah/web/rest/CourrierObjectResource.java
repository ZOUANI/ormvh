package com.ormvah.web.rest;

import com.ormvah.domain.CourrierObject;
import com.ormvah.service.CourrierObjectService;
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
 * REST controller for managing {@link com.ormvah.domain.CourrierObject}.
 */
@RestController
@RequestMapping("/api")
public class CourrierObjectResource {

    private final Logger log = LoggerFactory.getLogger(CourrierObjectResource.class);

    private static final String ENTITY_NAME = "courrierObject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourrierObjectService courrierObjectService;

    public CourrierObjectResource(CourrierObjectService courrierObjectService) {
        this.courrierObjectService = courrierObjectService;
    }

    /**
     * {@code POST  /courrier-objects} : Create a new courrierObject.
     *
     * @param courrierObject the courrierObject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courrierObject, or with status {@code 400 (Bad Request)} if the courrierObject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/courrier-objects")
    public ResponseEntity<CourrierObject> createCourrierObject(@RequestBody CourrierObject courrierObject) throws URISyntaxException {
        log.debug("REST request to save CourrierObject : {}", courrierObject);
        if (courrierObject.getId() != null) {
            throw new BadRequestAlertException("A new courrierObject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourrierObject result = courrierObjectService.save(courrierObject);
        return ResponseEntity.created(new URI("/api/courrier-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /courrier-objects} : Updates an existing courrierObject.
     *
     * @param courrierObject the courrierObject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courrierObject,
     * or with status {@code 400 (Bad Request)} if the courrierObject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courrierObject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/courrier-objects")
    public ResponseEntity<CourrierObject> updateCourrierObject(@RequestBody CourrierObject courrierObject) throws URISyntaxException {
        log.debug("REST request to update CourrierObject : {}", courrierObject);
        if (courrierObject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourrierObject result = courrierObjectService.save(courrierObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courrierObject.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /courrier-objects} : get all the courrierObjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courrierObjects in body.
     */
    @GetMapping("/courrier-objects")
    public List<CourrierObject> getAllCourrierObjects() {
        log.debug("REST request to get all CourrierObjects");
        return courrierObjectService.findAll();
    }

    /**
     * {@code GET  /courrier-objects/:id} : get the "id" courrierObject.
     *
     * @param id the id of the courrierObject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courrierObject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/courrier-objects/{id}")
    public ResponseEntity<CourrierObject> getCourrierObject(@PathVariable Long id) {
        log.debug("REST request to get CourrierObject : {}", id);
        Optional<CourrierObject> courrierObject = courrierObjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courrierObject);
    }

    /**
     * {@code DELETE  /courrier-objects/:id} : delete the "id" courrierObject.
     *
     * @param id the id of the courrierObject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/courrier-objects/{id}")
    public ResponseEntity<Void> deleteCourrierObject(@PathVariable Long id) {
        log.debug("REST request to delete CourrierObject : {}", id);
        courrierObjectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.ormvah.web.rest;

import com.ormvah.domain.Subdivision;
import com.ormvah.service.SubdivisionService;
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
 * REST controller for managing {@link com.ormvah.domain.Subdivision}.
 */
@RestController
@RequestMapping("/api")
public class SubdivisionResource {

    private final Logger log = LoggerFactory.getLogger(SubdivisionResource.class);

    private static final String ENTITY_NAME = "subdivision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubdivisionService subdivisionService;

    public SubdivisionResource(SubdivisionService subdivisionService) {
        this.subdivisionService = subdivisionService;
    }

    /**
     * {@code POST  /subdivisions} : Create a new subdivision.
     *
     * @param subdivision the subdivision to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subdivision, or with status {@code 400 (Bad Request)} if the subdivision has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subdivisions")
    public ResponseEntity<Subdivision> createSubdivision(@RequestBody Subdivision subdivision) throws URISyntaxException {
        log.debug("REST request to save Subdivision : {}", subdivision);
        if (subdivision.getId() != null) {
            throw new BadRequestAlertException("A new subdivision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subdivision result = subdivisionService.save(subdivision);
        return ResponseEntity.created(new URI("/api/subdivisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subdivisions} : Updates an existing subdivision.
     *
     * @param subdivision the subdivision to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subdivision,
     * or with status {@code 400 (Bad Request)} if the subdivision is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subdivision couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subdivisions")
    public ResponseEntity<Subdivision> updateSubdivision(@RequestBody Subdivision subdivision) throws URISyntaxException {
        log.debug("REST request to update Subdivision : {}", subdivision);
        if (subdivision.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subdivision result = subdivisionService.save(subdivision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subdivision.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subdivisions} : get all the subdivisions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subdivisions in body.
     */
    @GetMapping("/subdivisions")
    public List<Subdivision> getAllSubdivisions() {
        log.debug("REST request to get all Subdivisions");
        return subdivisionService.findAll();
    }

    /**
     * {@code GET  /subdivisions/:id} : get the "id" subdivision.
     *
     * @param id the id of the subdivision to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subdivision, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subdivisions/{id}")
    public ResponseEntity<Subdivision> getSubdivision(@PathVariable Long id) {
        log.debug("REST request to get Subdivision : {}", id);
        Optional<Subdivision> subdivision = subdivisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subdivision);
    }

    /**
     * {@code DELETE  /subdivisions/:id} : delete the "id" subdivision.
     *
     * @param id the id of the subdivision to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subdivisions/{id}")
    public ResponseEntity<Void> deleteSubdivision(@PathVariable Long id) {
        log.debug("REST request to delete Subdivision : {}", id);
        subdivisionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

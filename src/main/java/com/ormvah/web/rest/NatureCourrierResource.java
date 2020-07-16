package com.ormvah.web.rest;

import com.ormvah.domain.NatureCourrier;
import com.ormvah.service.NatureCourrierService;
import com.ormvah.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ormvah.domain.NatureCourrier}.
 */
@RestController
@RequestMapping("/api")
public class NatureCourrierResource {

    private final Logger log = LoggerFactory.getLogger(NatureCourrierResource.class);

    private static final String ENTITY_NAME = "natureCourrier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureCourrierService natureCourrierService;

    public NatureCourrierResource(NatureCourrierService natureCourrierService) {
        this.natureCourrierService = natureCourrierService;
    }

    /**
     * {@code POST  /nature-courriers} : Create a new natureCourrier.
     *
     * @param natureCourrier the natureCourrier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureCourrier, or with status {@code 400 (Bad Request)} if the natureCourrier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-courriers")
    public ResponseEntity<NatureCourrier> createNatureCourrier(@Valid @RequestBody NatureCourrier natureCourrier) throws URISyntaxException {
        log.debug("REST request to save NatureCourrier : {}", natureCourrier);
        if (natureCourrier.getId() != null) {
            throw new BadRequestAlertException("A new natureCourrier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureCourrier result = natureCourrierService.save(natureCourrier);
        return ResponseEntity.created(new URI("/api/nature-courriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-courriers} : Updates an existing natureCourrier.
     *
     * @param natureCourrier the natureCourrier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureCourrier,
     * or with status {@code 400 (Bad Request)} if the natureCourrier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureCourrier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-courriers")
    public ResponseEntity<NatureCourrier> updateNatureCourrier(@Valid @RequestBody NatureCourrier natureCourrier) throws URISyntaxException {
        log.debug("REST request to update NatureCourrier : {}", natureCourrier);
        if (natureCourrier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NatureCourrier result = natureCourrierService.save(natureCourrier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureCourrier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nature-courriers} : get all the natureCourriers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureCourriers in body.
     */
    @GetMapping("/nature-courriers")
    public List<NatureCourrier> getAllNatureCourriers() {
        log.debug("REST request to get all NatureCourriers");
        return natureCourrierService.findAll();
    }

    /**
     * {@code GET  /nature-courriers/:id} : get the "id" natureCourrier.
     *
     * @param id the id of the natureCourrier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureCourrier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-courriers/{id}")
    public ResponseEntity<NatureCourrier> getNatureCourrier(@PathVariable Long id) {
        log.debug("REST request to get NatureCourrier : {}", id);
        Optional<NatureCourrier> natureCourrier = natureCourrierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureCourrier);
    }

    /**
     * {@code DELETE  /nature-courriers/:id} : delete the "id" natureCourrier.
     *
     * @param id the id of the natureCourrier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-courriers/{id}")
    public ResponseEntity<Void> deleteNatureCourrier(@PathVariable Long id) {
        log.debug("REST request to delete NatureCourrier : {}", id);
        natureCourrierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

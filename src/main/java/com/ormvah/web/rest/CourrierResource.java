package com.ormvah.web.rest;

import com.ormvah.domain.Courrier;
import com.ormvah.service.CourrierService;
import com.ormvah.web.rest.errors.BadRequestAlertException;
import com.ormvah.service.dto.CourrierCriteria;
import com.ormvah.service.CourrierQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ormvah.domain.Courrier}.
 */
@RestController
@RequestMapping("/api")
public class CourrierResource {

    private final Logger log = LoggerFactory.getLogger(CourrierResource.class);

    private static final String ENTITY_NAME = "courrier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourrierService courrierService;

    private final CourrierQueryService courrierQueryService;

    public CourrierResource(CourrierService courrierService, CourrierQueryService courrierQueryService) {
        this.courrierService = courrierService;
        this.courrierQueryService = courrierQueryService;
    }

    /**
     * {@code POST  /courriers} : Create a new courrier.
     *
     * @param courrier the courrier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courrier, or with status {@code 400 (Bad Request)} if the courrier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/courriers")
    public ResponseEntity<Courrier> createCourrier(@RequestBody Courrier courrier) throws URISyntaxException {
        log.debug("REST request to save Courrier : {}", courrier);
        if (courrier.getId() != null) {
            throw new BadRequestAlertException("A new courrier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Courrier result = courrierService.save(courrier);
        return ResponseEntity.created(new URI("/api/courriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /courriers} : Updates an existing courrier.
     *
     * @param courrier the courrier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courrier,
     * or with status {@code 400 (Bad Request)} if the courrier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courrier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/courriers")
    public ResponseEntity<Courrier> updateCourrier(@RequestBody Courrier courrier) throws URISyntaxException {
        log.debug("REST request to update Courrier : {}", courrier);
        if (courrier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Courrier result = courrierService.save(courrier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courrier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /courriers} : get all the courriers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courriers in body.
     */
    @GetMapping("/courriers")
    public ResponseEntity<List<Courrier>> getAllCourriers(CourrierCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Courriers by criteria: {}", criteria);
        Page<Courrier> page = courrierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /courriers/count} : count all the courriers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/courriers/count")
    public ResponseEntity<Long> countCourriers(CourrierCriteria criteria) {
        log.debug("REST request to count Courriers by criteria: {}", criteria);
        return ResponseEntity.ok().body(courrierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /courriers/:id} : get the "id" courrier.
     *
     * @param id the id of the courrier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courrier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/courriers/{id}")
    public ResponseEntity<Courrier> getCourrier(@PathVariable Long id) {
        log.debug("REST request to get Courrier : {}", id);
        Optional<Courrier> courrier = courrierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courrier);
    }

    /**
     * {@code DELETE  /courriers/:id} : delete the "id" courrier.
     *
     * @param id the id of the courrier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/courriers/{id}")
    public ResponseEntity<Void> deleteCourrier(@PathVariable Long id) {
        log.debug("REST request to delete Courrier : {}", id);
        courrierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

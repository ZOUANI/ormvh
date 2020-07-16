package com.ormvah.web.rest;

import com.ormvah.domain.LeService;
import com.ormvah.service.LeServiceService;
import com.ormvah.web.rest.errors.BadRequestAlertException;
import com.ormvah.service.dto.LeServiceCriteria;
import com.ormvah.service.LeServiceQueryService;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ormvah.domain.LeService}.
 */
@RestController
@RequestMapping("/api")
public class LeServiceResource {

    private final Logger log = LoggerFactory.getLogger(LeServiceResource.class);

    private static final String ENTITY_NAME = "leService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeServiceService leServiceService;

    private final LeServiceQueryService leServiceQueryService;

    public LeServiceResource(LeServiceService leServiceService, LeServiceQueryService leServiceQueryService) {
        this.leServiceService = leServiceService;
        this.leServiceQueryService = leServiceQueryService;
    }

    /**
     * {@code POST  /le-services} : Create a new leService.
     *
     * @param leService the leService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leService, or with status {@code 400 (Bad Request)} if the leService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/le-services")
    public ResponseEntity<LeService> createLeService(@RequestBody LeService leService) throws URISyntaxException {
        log.debug("REST request to save LeService : {}", leService);
        if (leService.getId() != null) {
            throw new BadRequestAlertException("A new leService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeService result = leServiceService.save(leService);
        return ResponseEntity.created(new URI("/api/le-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /le-services} : Updates an existing leService.
     *
     * @param leService the leService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leService,
     * or with status {@code 400 (Bad Request)} if the leService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/le-services")
    public ResponseEntity<LeService> updateLeService(@RequestBody LeService leService) throws URISyntaxException {
        log.debug("REST request to update LeService : {}", leService);
        if (leService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeService result = leServiceService.save(leService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /le-services} : get all the leServices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leServices in body.
     */
    @GetMapping("/le-services")
    public ResponseEntity<List<LeService>> getAllLeServices(LeServiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeServices by criteria: {}", criteria);
        Page<LeService> page = leServiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /le-services/count} : count all the leServices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/le-services/count")
    public ResponseEntity<Long> countLeServices(LeServiceCriteria criteria) {
        log.debug("REST request to count LeServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(leServiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /le-services/:id} : get the "id" leService.
     *
     * @param id the id of the leService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/le-services/{id}")
    public ResponseEntity<LeService> getLeService(@PathVariable Long id) {
        log.debug("REST request to get LeService : {}", id);
        Optional<LeService> leService = leServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leService);
    }

    /**
     * {@code DELETE  /le-services/:id} : delete the "id" leService.
     *
     * @param id the id of the leService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/le-services/{id}")
    public ResponseEntity<Void> deleteLeService(@PathVariable Long id) {
        log.debug("REST request to delete LeService : {}", id);
        leServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

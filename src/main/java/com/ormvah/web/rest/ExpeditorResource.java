package com.ormvah.web.rest;

import com.ormvah.domain.Expeditor;
import com.ormvah.service.ExpeditorService;
import com.ormvah.web.rest.errors.BadRequestAlertException;
import com.ormvah.service.dto.ExpeditorCriteria;
import com.ormvah.service.ExpeditorQueryService;

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
 * REST controller for managing {@link com.ormvah.domain.Expeditor}.
 */
@RestController
@RequestMapping("/api")
public class ExpeditorResource {

    private final Logger log = LoggerFactory.getLogger(ExpeditorResource.class);

    private static final String ENTITY_NAME = "expeditor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpeditorService expeditorService;

    private final ExpeditorQueryService expeditorQueryService;

    public ExpeditorResource(ExpeditorService expeditorService, ExpeditorQueryService expeditorQueryService) {
        this.expeditorService = expeditorService;
        this.expeditorQueryService = expeditorQueryService;
    }

    /**
     * {@code POST  /expeditors} : Create a new expeditor.
     *
     * @param expeditor the expeditor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expeditor, or with status {@code 400 (Bad Request)} if the expeditor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expeditors")
    public ResponseEntity<Expeditor> createExpeditor(@RequestBody Expeditor expeditor) throws URISyntaxException {
        log.debug("REST request to save Expeditor : {}", expeditor);
        if (expeditor.getId() != null) {
            throw new BadRequestAlertException("A new expeditor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Expeditor result = expeditorService.save(expeditor);
        return ResponseEntity.created(new URI("/api/expeditors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expeditors} : Updates an existing expeditor.
     *
     * @param expeditor the expeditor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expeditor,
     * or with status {@code 400 (Bad Request)} if the expeditor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expeditor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expeditors")
    public ResponseEntity<Expeditor> updateExpeditor(@RequestBody Expeditor expeditor) throws URISyntaxException {
        log.debug("REST request to update Expeditor : {}", expeditor);
        if (expeditor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Expeditor result = expeditorService.save(expeditor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expeditor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expeditors} : get all the expeditors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expeditors in body.
     */
    @GetMapping("/expeditors")
    public ResponseEntity<List<Expeditor>> getAllExpeditors(ExpeditorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Expeditors by criteria: {}", criteria);
        Page<Expeditor> page = expeditorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expeditors/count} : count all the expeditors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expeditors/count")
    public ResponseEntity<Long> countExpeditors(ExpeditorCriteria criteria) {
        log.debug("REST request to count Expeditors by criteria: {}", criteria);
        return ResponseEntity.ok().body(expeditorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expeditors/:id} : get the "id" expeditor.
     *
     * @param id the id of the expeditor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expeditor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expeditors/{id}")
    public ResponseEntity<Expeditor> getExpeditor(@PathVariable Long id) {
        log.debug("REST request to get Expeditor : {}", id);
        Optional<Expeditor> expeditor = expeditorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expeditor);
    }

    /**
     * {@code DELETE  /expeditors/:id} : delete the "id" expeditor.
     *
     * @param id the id of the expeditor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expeditors/{id}")
    public ResponseEntity<Void> deleteExpeditor(@PathVariable Long id) {
        log.debug("REST request to delete Expeditor : {}", id);
        expeditorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

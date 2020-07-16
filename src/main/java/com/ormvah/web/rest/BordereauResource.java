package com.ormvah.web.rest;

import com.ormvah.domain.Bordereau;
import com.ormvah.service.BordereauService;
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
 * REST controller for managing {@link com.ormvah.domain.Bordereau}.
 */
@RestController
@RequestMapping("/api")
public class BordereauResource {

    private final Logger log = LoggerFactory.getLogger(BordereauResource.class);

    private static final String ENTITY_NAME = "bordereau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BordereauService bordereauService;

    public BordereauResource(BordereauService bordereauService) {
        this.bordereauService = bordereauService;
    }

    /**
     * {@code POST  /bordereaus} : Create a new bordereau.
     *
     * @param bordereau the bordereau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bordereau, or with status {@code 400 (Bad Request)} if the bordereau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bordereaus")
    public ResponseEntity<Bordereau> createBordereau(@Valid @RequestBody Bordereau bordereau) throws URISyntaxException {
        log.debug("REST request to save Bordereau : {}", bordereau);
        if (bordereau.getId() != null) {
            throw new BadRequestAlertException("A new bordereau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bordereau result = bordereauService.save(bordereau);
        return ResponseEntity.created(new URI("/api/bordereaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bordereaus} : Updates an existing bordereau.
     *
     * @param bordereau the bordereau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bordereau,
     * or with status {@code 400 (Bad Request)} if the bordereau is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bordereau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bordereaus")
    public ResponseEntity<Bordereau> updateBordereau(@Valid @RequestBody Bordereau bordereau) throws URISyntaxException {
        log.debug("REST request to update Bordereau : {}", bordereau);
        if (bordereau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bordereau result = bordereauService.save(bordereau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bordereau.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bordereaus} : get all the bordereaus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bordereaus in body.
     */
    @GetMapping("/bordereaus")
    public List<Bordereau> getAllBordereaus() {
        log.debug("REST request to get all Bordereaus");
        return bordereauService.findAll();
    }

    /**
     * {@code GET  /bordereaus/:id} : get the "id" bordereau.
     *
     * @param id the id of the bordereau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bordereau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bordereaus/{id}")
    public ResponseEntity<Bordereau> getBordereau(@PathVariable Long id) {
        log.debug("REST request to get Bordereau : {}", id);
        Optional<Bordereau> bordereau = bordereauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bordereau);
    }

    /**
     * {@code DELETE  /bordereaus/:id} : delete the "id" bordereau.
     *
     * @param id the id of the bordereau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bordereaus/{id}")
    public ResponseEntity<Void> deleteBordereau(@PathVariable Long id) {
        log.debug("REST request to delete Bordereau : {}", id);
        bordereauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.ormvah.web.rest;

import com.ormvah.domain.Voie;
import com.ormvah.service.VoieService;
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
 * REST controller for managing {@link com.ormvah.domain.Voie}.
 */
@RestController
@RequestMapping("/api")
public class VoieResource {

    private final Logger log = LoggerFactory.getLogger(VoieResource.class);

    private static final String ENTITY_NAME = "voie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoieService voieService;

    public VoieResource(VoieService voieService) {
        this.voieService = voieService;
    }

    /**
     * {@code POST  /voies} : Create a new voie.
     *
     * @param voie the voie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voie, or with status {@code 400 (Bad Request)} if the voie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voies")
    public ResponseEntity<Voie> createVoie(@Valid @RequestBody Voie voie) throws URISyntaxException {
        log.debug("REST request to save Voie : {}", voie);
        if (voie.getId() != null) {
            throw new BadRequestAlertException("A new voie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voie result = voieService.save(voie);
        return ResponseEntity.created(new URI("/api/voies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voies} : Updates an existing voie.
     *
     * @param voie the voie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voie,
     * or with status {@code 400 (Bad Request)} if the voie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voies")
    public ResponseEntity<Voie> updateVoie(@Valid @RequestBody Voie voie) throws URISyntaxException {
        log.debug("REST request to update Voie : {}", voie);
        if (voie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Voie result = voieService.save(voie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /voies} : get all the voies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voies in body.
     */
    @GetMapping("/voies")
    public List<Voie> getAllVoies() {
        log.debug("REST request to get all Voies");
        return voieService.findAll();
    }

    /**
     * {@code GET  /voies/:id} : get the "id" voie.
     *
     * @param id the id of the voie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voies/{id}")
    public ResponseEntity<Voie> getVoie(@PathVariable Long id) {
        log.debug("REST request to get Voie : {}", id);
        Optional<Voie> voie = voieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voie);
    }

    /**
     * {@code DELETE  /voies/:id} : delete the "id" voie.
     *
     * @param id the id of the voie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voies/{id}")
    public ResponseEntity<Void> deleteVoie(@PathVariable Long id) {
        log.debug("REST request to delete Voie : {}", id);
        voieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

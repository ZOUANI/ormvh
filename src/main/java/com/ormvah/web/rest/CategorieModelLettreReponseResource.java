package com.ormvah.web.rest;

import com.ormvah.domain.CategorieModelLettreReponse;
import com.ormvah.service.CategorieModelLettreReponseService;
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
 * REST controller for managing {@link com.ormvah.domain.CategorieModelLettreReponse}.
 */
@RestController
@RequestMapping("/api")
public class CategorieModelLettreReponseResource {

    private final Logger log = LoggerFactory.getLogger(CategorieModelLettreReponseResource.class);

    private static final String ENTITY_NAME = "categorieModelLettreReponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieModelLettreReponseService categorieModelLettreReponseService;

    public CategorieModelLettreReponseResource(CategorieModelLettreReponseService categorieModelLettreReponseService) {
        this.categorieModelLettreReponseService = categorieModelLettreReponseService;
    }

    /**
     * {@code POST  /categorie-model-lettre-reponses} : Create a new categorieModelLettreReponse.
     *
     * @param categorieModelLettreReponse the categorieModelLettreReponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieModelLettreReponse, or with status {@code 400 (Bad Request)} if the categorieModelLettreReponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-model-lettre-reponses")
    public ResponseEntity<CategorieModelLettreReponse> createCategorieModelLettreReponse(@Valid @RequestBody CategorieModelLettreReponse categorieModelLettreReponse) throws URISyntaxException {
        log.debug("REST request to save CategorieModelLettreReponse : {}", categorieModelLettreReponse);
        if (categorieModelLettreReponse.getId() != null) {
            throw new BadRequestAlertException("A new categorieModelLettreReponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieModelLettreReponse result = categorieModelLettreReponseService.save(categorieModelLettreReponse);
        return ResponseEntity.created(new URI("/api/categorie-model-lettre-reponses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-model-lettre-reponses} : Updates an existing categorieModelLettreReponse.
     *
     * @param categorieModelLettreReponse the categorieModelLettreReponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieModelLettreReponse,
     * or with status {@code 400 (Bad Request)} if the categorieModelLettreReponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieModelLettreReponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-model-lettre-reponses")
    public ResponseEntity<CategorieModelLettreReponse> updateCategorieModelLettreReponse(@Valid @RequestBody CategorieModelLettreReponse categorieModelLettreReponse) throws URISyntaxException {
        log.debug("REST request to update CategorieModelLettreReponse : {}", categorieModelLettreReponse);
        if (categorieModelLettreReponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorieModelLettreReponse result = categorieModelLettreReponseService.save(categorieModelLettreReponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieModelLettreReponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorie-model-lettre-reponses} : get all the categorieModelLettreReponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieModelLettreReponses in body.
     */
    @GetMapping("/categorie-model-lettre-reponses")
    public List<CategorieModelLettreReponse> getAllCategorieModelLettreReponses() {
        log.debug("REST request to get all CategorieModelLettreReponses");
        return categorieModelLettreReponseService.findAll();
    }

    /**
     * {@code GET  /categorie-model-lettre-reponses/:id} : get the "id" categorieModelLettreReponse.
     *
     * @param id the id of the categorieModelLettreReponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieModelLettreReponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-model-lettre-reponses/{id}")
    public ResponseEntity<CategorieModelLettreReponse> getCategorieModelLettreReponse(@PathVariable Long id) {
        log.debug("REST request to get CategorieModelLettreReponse : {}", id);
        Optional<CategorieModelLettreReponse> categorieModelLettreReponse = categorieModelLettreReponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieModelLettreReponse);
    }

    /**
     * {@code DELETE  /categorie-model-lettre-reponses/:id} : delete the "id" categorieModelLettreReponse.
     *
     * @param id the id of the categorieModelLettreReponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-model-lettre-reponses/{id}")
    public ResponseEntity<Void> deleteCategorieModelLettreReponse(@PathVariable Long id) {
        log.debug("REST request to delete CategorieModelLettreReponse : {}", id);
        categorieModelLettreReponseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

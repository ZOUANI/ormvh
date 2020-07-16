package com.ormvah.web.rest;

import com.ormvah.domain.ModelLettreReponse;
import com.ormvah.service.ModelLettreReponseService;
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
 * REST controller for managing {@link com.ormvah.domain.ModelLettreReponse}.
 */
@RestController
@RequestMapping("/api")
public class ModelLettreReponseResource {

    private final Logger log = LoggerFactory.getLogger(ModelLettreReponseResource.class);

    private static final String ENTITY_NAME = "modelLettreReponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelLettreReponseService modelLettreReponseService;

    public ModelLettreReponseResource(ModelLettreReponseService modelLettreReponseService) {
        this.modelLettreReponseService = modelLettreReponseService;
    }

    /**
     * {@code POST  /model-lettre-reponses} : Create a new modelLettreReponse.
     *
     * @param modelLettreReponse the modelLettreReponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelLettreReponse, or with status {@code 400 (Bad Request)} if the modelLettreReponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-lettre-reponses")
    public ResponseEntity<ModelLettreReponse> createModelLettreReponse(@Valid @RequestBody ModelLettreReponse modelLettreReponse) throws URISyntaxException {
        log.debug("REST request to save ModelLettreReponse : {}", modelLettreReponse);
        if (modelLettreReponse.getId() != null) {
            throw new BadRequestAlertException("A new modelLettreReponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModelLettreReponse result = modelLettreReponseService.save(modelLettreReponse);
        return ResponseEntity.created(new URI("/api/model-lettre-reponses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /model-lettre-reponses} : Updates an existing modelLettreReponse.
     *
     * @param modelLettreReponse the modelLettreReponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelLettreReponse,
     * or with status {@code 400 (Bad Request)} if the modelLettreReponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelLettreReponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-lettre-reponses")
    public ResponseEntity<ModelLettreReponse> updateModelLettreReponse(@Valid @RequestBody ModelLettreReponse modelLettreReponse) throws URISyntaxException {
        log.debug("REST request to update ModelLettreReponse : {}", modelLettreReponse);
        if (modelLettreReponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModelLettreReponse result = modelLettreReponseService.save(modelLettreReponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelLettreReponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /model-lettre-reponses} : get all the modelLettreReponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelLettreReponses in body.
     */
    @GetMapping("/model-lettre-reponses")
    public List<ModelLettreReponse> getAllModelLettreReponses() {
        log.debug("REST request to get all ModelLettreReponses");
        return modelLettreReponseService.findAll();
    }

    /**
     * {@code GET  /model-lettre-reponses/:id} : get the "id" modelLettreReponse.
     *
     * @param id the id of the modelLettreReponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelLettreReponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-lettre-reponses/{id}")
    public ResponseEntity<ModelLettreReponse> getModelLettreReponse(@PathVariable Long id) {
        log.debug("REST request to get ModelLettreReponse : {}", id);
        Optional<ModelLettreReponse> modelLettreReponse = modelLettreReponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelLettreReponse);
    }

    /**
     * {@code DELETE  /model-lettre-reponses/:id} : delete the "id" modelLettreReponse.
     *
     * @param id the id of the modelLettreReponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-lettre-reponses/{id}")
    public ResponseEntity<Void> deleteModelLettreReponse(@PathVariable Long id) {
        log.debug("REST request to delete ModelLettreReponse : {}", id);
        modelLettreReponseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

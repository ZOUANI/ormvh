package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.ModelLettreReponse;
import com.ormvah.repository.ModelLettreReponseRepository;
import com.ormvah.service.ModelLettreReponseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ModelLettreReponseResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ModelLettreReponseResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CHEMIN = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN = "BBBBBBBBBB";

    @Autowired
    private ModelLettreReponseRepository modelLettreReponseRepository;

    @Autowired
    private ModelLettreReponseService modelLettreReponseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModelLettreReponseMockMvc;

    private ModelLettreReponse modelLettreReponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelLettreReponse createEntity(EntityManager em) {
        ModelLettreReponse modelLettreReponse = new ModelLettreReponse()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .chemin(DEFAULT_CHEMIN);
        return modelLettreReponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelLettreReponse createUpdatedEntity(EntityManager em) {
        ModelLettreReponse modelLettreReponse = new ModelLettreReponse()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .chemin(UPDATED_CHEMIN);
        return modelLettreReponse;
    }

    @BeforeEach
    public void initTest() {
        modelLettreReponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelLettreReponse() throws Exception {
        int databaseSizeBeforeCreate = modelLettreReponseRepository.findAll().size();
        // Create the ModelLettreReponse
        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelLettreReponse)))
            .andExpect(status().isCreated());

        // Validate the ModelLettreReponse in the database
        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeCreate + 1);
        ModelLettreReponse testModelLettreReponse = modelLettreReponseList.get(modelLettreReponseList.size() - 1);
        assertThat(testModelLettreReponse.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testModelLettreReponse.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModelLettreReponse.getChemin()).isEqualTo(DEFAULT_CHEMIN);
    }

    @Test
    @Transactional
    public void createModelLettreReponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelLettreReponseRepository.findAll().size();

        // Create the ModelLettreReponse with an existing ID
        modelLettreReponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelLettreReponse)))
            .andExpect(status().isBadRequest());

        // Validate the ModelLettreReponse in the database
        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelLettreReponseRepository.findAll().size();
        // set the field null
        modelLettreReponse.setLibelle(null);

        // Create the ModelLettreReponse, which fails.


        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelLettreReponse)))
            .andExpect(status().isBadRequest());

        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelLettreReponseRepository.findAll().size();
        // set the field null
        modelLettreReponse.setCode(null);

        // Create the ModelLettreReponse, which fails.


        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelLettreReponse)))
            .andExpect(status().isBadRequest());

        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelLettreReponseRepository.findAll().size();
        // set the field null
        modelLettreReponse.setChemin(null);

        // Create the ModelLettreReponse, which fails.


        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelLettreReponse)))
            .andExpect(status().isBadRequest());

        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModelLettreReponses() throws Exception {
        // Initialize the database
        modelLettreReponseRepository.saveAndFlush(modelLettreReponse);

        // Get all the modelLettreReponseList
        restModelLettreReponseMockMvc.perform(get("/api/model-lettre-reponses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelLettreReponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].chemin").value(hasItem(DEFAULT_CHEMIN)));
    }
    
    @Test
    @Transactional
    public void getModelLettreReponse() throws Exception {
        // Initialize the database
        modelLettreReponseRepository.saveAndFlush(modelLettreReponse);

        // Get the modelLettreReponse
        restModelLettreReponseMockMvc.perform(get("/api/model-lettre-reponses/{id}", modelLettreReponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modelLettreReponse.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.chemin").value(DEFAULT_CHEMIN));
    }
    @Test
    @Transactional
    public void getNonExistingModelLettreReponse() throws Exception {
        // Get the modelLettreReponse
        restModelLettreReponseMockMvc.perform(get("/api/model-lettre-reponses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelLettreReponse() throws Exception {
        // Initialize the database
        modelLettreReponseService.save(modelLettreReponse);

        int databaseSizeBeforeUpdate = modelLettreReponseRepository.findAll().size();

        // Update the modelLettreReponse
        ModelLettreReponse updatedModelLettreReponse = modelLettreReponseRepository.findById(modelLettreReponse.getId()).get();
        // Disconnect from session so that the updates on updatedModelLettreReponse are not directly saved in db
        em.detach(updatedModelLettreReponse);
        updatedModelLettreReponse
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .chemin(UPDATED_CHEMIN);

        restModelLettreReponseMockMvc.perform(put("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelLettreReponse)))
            .andExpect(status().isOk());

        // Validate the ModelLettreReponse in the database
        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeUpdate);
        ModelLettreReponse testModelLettreReponse = modelLettreReponseList.get(modelLettreReponseList.size() - 1);
        assertThat(testModelLettreReponse.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testModelLettreReponse.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModelLettreReponse.getChemin()).isEqualTo(UPDATED_CHEMIN);
    }

    @Test
    @Transactional
    public void updateNonExistingModelLettreReponse() throws Exception {
        int databaseSizeBeforeUpdate = modelLettreReponseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelLettreReponseMockMvc.perform(put("/api/model-lettre-reponses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelLettreReponse)))
            .andExpect(status().isBadRequest());

        // Validate the ModelLettreReponse in the database
        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModelLettreReponse() throws Exception {
        // Initialize the database
        modelLettreReponseService.save(modelLettreReponse);

        int databaseSizeBeforeDelete = modelLettreReponseRepository.findAll().size();

        // Delete the modelLettreReponse
        restModelLettreReponseMockMvc.perform(delete("/api/model-lettre-reponses/{id}", modelLettreReponse.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.ModelLettreReponse;
import com.ormvah.repository.ModelLettreReponseRepository;
import com.ormvah.service.ModelLettreReponseService;
import com.ormvah.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ormvah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ModelLettreReponseResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
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
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restModelLettreReponseMockMvc;

    private ModelLettreReponse modelLettreReponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModelLettreReponseResource modelLettreReponseResource = new ModelLettreReponseResource(modelLettreReponseService);
        this.restModelLettreReponseMockMvc = MockMvcBuilders.standaloneSetup(modelLettreReponseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

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
        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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

        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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

        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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

        restModelLettreReponseMockMvc.perform(post("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelLettreReponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].chemin").value(hasItem(DEFAULT_CHEMIN.toString())));
    }
    
    @Test
    @Transactional
    public void getModelLettreReponse() throws Exception {
        // Initialize the database
        modelLettreReponseRepository.saveAndFlush(modelLettreReponse);

        // Get the modelLettreReponse
        restModelLettreReponseMockMvc.perform(get("/api/model-lettre-reponses/{id}", modelLettreReponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelLettreReponse.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.chemin").value(DEFAULT_CHEMIN.toString()));
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

        restModelLettreReponseMockMvc.perform(put("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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

        // Create the ModelLettreReponse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelLettreReponseMockMvc.perform(put("/api/model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
        restModelLettreReponseMockMvc.perform(delete("/api/model-lettre-reponses/{id}", modelLettreReponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModelLettreReponse> modelLettreReponseList = modelLettreReponseRepository.findAll();
        assertThat(modelLettreReponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelLettreReponse.class);
        ModelLettreReponse modelLettreReponse1 = new ModelLettreReponse();
        modelLettreReponse1.setId(1L);
        ModelLettreReponse modelLettreReponse2 = new ModelLettreReponse();
        modelLettreReponse2.setId(modelLettreReponse1.getId());
        assertThat(modelLettreReponse1).isEqualTo(modelLettreReponse2);
        modelLettreReponse2.setId(2L);
        assertThat(modelLettreReponse1).isNotEqualTo(modelLettreReponse2);
        modelLettreReponse1.setId(null);
        assertThat(modelLettreReponse1).isNotEqualTo(modelLettreReponse2);
    }
}

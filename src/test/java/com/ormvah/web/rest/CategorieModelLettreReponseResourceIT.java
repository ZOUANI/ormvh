package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.CategorieModelLettreReponse;
import com.ormvah.repository.CategorieModelLettreReponseRepository;
import com.ormvah.service.CategorieModelLettreReponseService;
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
 * Integration tests for the {@Link CategorieModelLettreReponseResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
public class CategorieModelLettreReponseResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private CategorieModelLettreReponseRepository categorieModelLettreReponseRepository;

    @Autowired
    private CategorieModelLettreReponseService categorieModelLettreReponseService;

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

    private MockMvc restCategorieModelLettreReponseMockMvc;

    private CategorieModelLettreReponse categorieModelLettreReponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorieModelLettreReponseResource categorieModelLettreReponseResource = new CategorieModelLettreReponseResource(categorieModelLettreReponseService);
        this.restCategorieModelLettreReponseMockMvc = MockMvcBuilders.standaloneSetup(categorieModelLettreReponseResource)
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
    public static CategorieModelLettreReponse createEntity(EntityManager em) {
        CategorieModelLettreReponse categorieModelLettreReponse = new CategorieModelLettreReponse()
            .libelle(DEFAULT_LIBELLE);
        return categorieModelLettreReponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieModelLettreReponse createUpdatedEntity(EntityManager em) {
        CategorieModelLettreReponse categorieModelLettreReponse = new CategorieModelLettreReponse()
            .libelle(UPDATED_LIBELLE);
        return categorieModelLettreReponse;
    }

    @BeforeEach
    public void initTest() {
        categorieModelLettreReponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieModelLettreReponse() throws Exception {
        int databaseSizeBeforeCreate = categorieModelLettreReponseRepository.findAll().size();

        // Create the CategorieModelLettreReponse
        restCategorieModelLettreReponseMockMvc.perform(post("/api/categorie-model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieModelLettreReponse)))
            .andExpect(status().isCreated());

        // Validate the CategorieModelLettreReponse in the database
        List<CategorieModelLettreReponse> categorieModelLettreReponseList = categorieModelLettreReponseRepository.findAll();
        assertThat(categorieModelLettreReponseList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieModelLettreReponse testCategorieModelLettreReponse = categorieModelLettreReponseList.get(categorieModelLettreReponseList.size() - 1);
        assertThat(testCategorieModelLettreReponse.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createCategorieModelLettreReponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieModelLettreReponseRepository.findAll().size();

        // Create the CategorieModelLettreReponse with an existing ID
        categorieModelLettreReponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieModelLettreReponseMockMvc.perform(post("/api/categorie-model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieModelLettreReponse)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieModelLettreReponse in the database
        List<CategorieModelLettreReponse> categorieModelLettreReponseList = categorieModelLettreReponseRepository.findAll();
        assertThat(categorieModelLettreReponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieModelLettreReponseRepository.findAll().size();
        // set the field null
        categorieModelLettreReponse.setLibelle(null);

        // Create the CategorieModelLettreReponse, which fails.

        restCategorieModelLettreReponseMockMvc.perform(post("/api/categorie-model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieModelLettreReponse)))
            .andExpect(status().isBadRequest());

        List<CategorieModelLettreReponse> categorieModelLettreReponseList = categorieModelLettreReponseRepository.findAll();
        assertThat(categorieModelLettreReponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorieModelLettreReponses() throws Exception {
        // Initialize the database
        categorieModelLettreReponseRepository.saveAndFlush(categorieModelLettreReponse);

        // Get all the categorieModelLettreReponseList
        restCategorieModelLettreReponseMockMvc.perform(get("/api/categorie-model-lettre-reponses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieModelLettreReponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getCategorieModelLettreReponse() throws Exception {
        // Initialize the database
        categorieModelLettreReponseRepository.saveAndFlush(categorieModelLettreReponse);

        // Get the categorieModelLettreReponse
        restCategorieModelLettreReponseMockMvc.perform(get("/api/categorie-model-lettre-reponses/{id}", categorieModelLettreReponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categorieModelLettreReponse.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieModelLettreReponse() throws Exception {
        // Get the categorieModelLettreReponse
        restCategorieModelLettreReponseMockMvc.perform(get("/api/categorie-model-lettre-reponses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieModelLettreReponse() throws Exception {
        // Initialize the database
        categorieModelLettreReponseService.save(categorieModelLettreReponse);

        int databaseSizeBeforeUpdate = categorieModelLettreReponseRepository.findAll().size();

        // Update the categorieModelLettreReponse
        CategorieModelLettreReponse updatedCategorieModelLettreReponse = categorieModelLettreReponseRepository.findById(categorieModelLettreReponse.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieModelLettreReponse are not directly saved in db
        em.detach(updatedCategorieModelLettreReponse);
        updatedCategorieModelLettreReponse
            .libelle(UPDATED_LIBELLE);

        restCategorieModelLettreReponseMockMvc.perform(put("/api/categorie-model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategorieModelLettreReponse)))
            .andExpect(status().isOk());

        // Validate the CategorieModelLettreReponse in the database
        List<CategorieModelLettreReponse> categorieModelLettreReponseList = categorieModelLettreReponseRepository.findAll();
        assertThat(categorieModelLettreReponseList).hasSize(databaseSizeBeforeUpdate);
        CategorieModelLettreReponse testCategorieModelLettreReponse = categorieModelLettreReponseList.get(categorieModelLettreReponseList.size() - 1);
        assertThat(testCategorieModelLettreReponse.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieModelLettreReponse() throws Exception {
        int databaseSizeBeforeUpdate = categorieModelLettreReponseRepository.findAll().size();

        // Create the CategorieModelLettreReponse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieModelLettreReponseMockMvc.perform(put("/api/categorie-model-lettre-reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieModelLettreReponse)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieModelLettreReponse in the database
        List<CategorieModelLettreReponse> categorieModelLettreReponseList = categorieModelLettreReponseRepository.findAll();
        assertThat(categorieModelLettreReponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorieModelLettreReponse() throws Exception {
        // Initialize the database
        categorieModelLettreReponseService.save(categorieModelLettreReponse);

        int databaseSizeBeforeDelete = categorieModelLettreReponseRepository.findAll().size();

        // Delete the categorieModelLettreReponse
        restCategorieModelLettreReponseMockMvc.perform(delete("/api/categorie-model-lettre-reponses/{id}", categorieModelLettreReponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieModelLettreReponse> categorieModelLettreReponseList = categorieModelLettreReponseRepository.findAll();
        assertThat(categorieModelLettreReponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieModelLettreReponse.class);
        CategorieModelLettreReponse categorieModelLettreReponse1 = new CategorieModelLettreReponse();
        categorieModelLettreReponse1.setId(1L);
        CategorieModelLettreReponse categorieModelLettreReponse2 = new CategorieModelLettreReponse();
        categorieModelLettreReponse2.setId(categorieModelLettreReponse1.getId());
        assertThat(categorieModelLettreReponse1).isEqualTo(categorieModelLettreReponse2);
        categorieModelLettreReponse2.setId(2L);
        assertThat(categorieModelLettreReponse1).isNotEqualTo(categorieModelLettreReponse2);
        categorieModelLettreReponse1.setId(null);
        assertThat(categorieModelLettreReponse1).isNotEqualTo(categorieModelLettreReponse2);
    }
}

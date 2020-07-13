package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.NatureCourrier;
import com.ormvah.repository.NatureCourrierRepository;
import com.ormvah.service.NatureCourrierService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ormvah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link NatureCourrierResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
public class NatureCourrierResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_DELAI = 1D;
    private static final Double UPDATED_DELAI = 2D;

    private static final Double DEFAULT_RELANCE = 1D;
    private static final Double UPDATED_RELANCE = 2D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private NatureCourrierRepository natureCourrierRepository;

    @Autowired
    private NatureCourrierService natureCourrierService;

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

    private MockMvc restNatureCourrierMockMvc;

    private NatureCourrier natureCourrier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NatureCourrierResource natureCourrierResource = new NatureCourrierResource(natureCourrierService);
        this.restNatureCourrierMockMvc = MockMvcBuilders.standaloneSetup(natureCourrierResource)
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
    public static NatureCourrier createEntity(EntityManager em) {
        NatureCourrier natureCourrier = new NatureCourrier()
            .libelle(DEFAULT_LIBELLE)
            .delai(DEFAULT_DELAI)
            .relance(DEFAULT_RELANCE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return natureCourrier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureCourrier createUpdatedEntity(EntityManager em) {
        NatureCourrier natureCourrier = new NatureCourrier()
            .libelle(UPDATED_LIBELLE)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return natureCourrier;
    }

    @BeforeEach
    public void initTest() {
        natureCourrier = createEntity(em);
    }

    @Test
    @Transactional
    public void createNatureCourrier() throws Exception {
        int databaseSizeBeforeCreate = natureCourrierRepository.findAll().size();

        // Create the NatureCourrier
        restNatureCourrierMockMvc.perform(post("/api/nature-courriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isCreated());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeCreate + 1);
        NatureCourrier testNatureCourrier = natureCourrierList.get(natureCourrierList.size() - 1);
        assertThat(testNatureCourrier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNatureCourrier.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testNatureCourrier.getRelance()).isEqualTo(DEFAULT_RELANCE);
        assertThat(testNatureCourrier.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNatureCourrier.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNatureCourrier.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureCourrier.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createNatureCourrierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = natureCourrierRepository.findAll().size();

        // Create the NatureCourrier with an existing ID
        natureCourrier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureCourrierMockMvc.perform(post("/api/nature-courriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isBadRequest());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = natureCourrierRepository.findAll().size();
        // set the field null
        natureCourrier.setLibelle(null);

        // Create the NatureCourrier, which fails.

        restNatureCourrierMockMvc.perform(post("/api/nature-courriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isBadRequest());

        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNatureCourriers() throws Exception {
        // Initialize the database
        natureCourrierRepository.saveAndFlush(natureCourrier);

        // Get all the natureCourrierList
        restNatureCourrierMockMvc.perform(get("/api/nature-courriers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureCourrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.doubleValue())))
            .andExpect(jsonPath("$.[*].relance").value(hasItem(DEFAULT_RELANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getNatureCourrier() throws Exception {
        // Initialize the database
        natureCourrierRepository.saveAndFlush(natureCourrier);

        // Get the natureCourrier
        restNatureCourrierMockMvc.perform(get("/api/nature-courriers/{id}", natureCourrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(natureCourrier.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.doubleValue()))
            .andExpect(jsonPath("$.relance").value(DEFAULT_RELANCE.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNatureCourrier() throws Exception {
        // Get the natureCourrier
        restNatureCourrierMockMvc.perform(get("/api/nature-courriers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNatureCourrier() throws Exception {
        // Initialize the database
        natureCourrierService.save(natureCourrier);

        int databaseSizeBeforeUpdate = natureCourrierRepository.findAll().size();

        // Update the natureCourrier
        NatureCourrier updatedNatureCourrier = natureCourrierRepository.findById(natureCourrier.getId()).get();
        // Disconnect from session so that the updates on updatedNatureCourrier are not directly saved in db
        em.detach(updatedNatureCourrier);
        updatedNatureCourrier
            .libelle(UPDATED_LIBELLE)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restNatureCourrierMockMvc.perform(put("/api/nature-courriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNatureCourrier)))
            .andExpect(status().isOk());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeUpdate);
        NatureCourrier testNatureCourrier = natureCourrierList.get(natureCourrierList.size() - 1);
        assertThat(testNatureCourrier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNatureCourrier.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testNatureCourrier.getRelance()).isEqualTo(UPDATED_RELANCE);
        assertThat(testNatureCourrier.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNatureCourrier.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNatureCourrier.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureCourrier.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingNatureCourrier() throws Exception {
        int databaseSizeBeforeUpdate = natureCourrierRepository.findAll().size();

        // Create the NatureCourrier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureCourrierMockMvc.perform(put("/api/nature-courriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isBadRequest());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNatureCourrier() throws Exception {
        // Initialize the database
        natureCourrierService.save(natureCourrier);

        int databaseSizeBeforeDelete = natureCourrierRepository.findAll().size();

        // Delete the natureCourrier
        restNatureCourrierMockMvc.perform(delete("/api/nature-courriers/{id}", natureCourrier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureCourrier.class);
        NatureCourrier natureCourrier1 = new NatureCourrier();
        natureCourrier1.setId(1L);
        NatureCourrier natureCourrier2 = new NatureCourrier();
        natureCourrier2.setId(natureCourrier1.getId());
        assertThat(natureCourrier1).isEqualTo(natureCourrier2);
        natureCourrier2.setId(2L);
        assertThat(natureCourrier1).isNotEqualTo(natureCourrier2);
        natureCourrier1.setId(null);
        assertThat(natureCourrier1).isNotEqualTo(natureCourrier2);
    }
}
